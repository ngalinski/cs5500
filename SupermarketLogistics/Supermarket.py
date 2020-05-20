"""
This program takes in a date and outputs the corresponding csv file with shopper information.
It also provides some analytics for the specified date highlighted in the doc file on github.
"""

import random
import csv
import statistics
import datetime
import scipy.stats
import numpy as np
import holidays

from Shopper import Shopper

# Store hours
STORE_HOURS = [0, 15]

# Holidays (I went off public holidays)
HOLIDAYS = [[1, 1], [1, 20], [2, 14], [5, 25], [7, 4], [9, 7], [11, 11], [11, 26], [12, 25]]

# Holidays: Holidays increase traffic.
# Week before, day before, day of
HOLIDAY_MULT = [1.15, 1.40, 0.2]

# Days
DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

# Define average shoppers per day
AVERAGE_SHOPPERS = [800, 1000, 1200, 900, 2500, 4000, 5000]

# Percentage shoppers
# Weekdays: Let's just use this as our splits
# 6 - 12, 12 - 1, 1 - 5, 5 - 6:30, 6:30 - 9
# I used even splits but we can edit this here instead of making it more complicated
WEEKDAY_CHANCES = [0.20, 0.20, 0.20, 0.20, 0.20]
WEEKDAY_TIMES = [[0, 6], [6, 7], [7, 11], [11, 12.5], [12.5, 15]]

# Percentage of shoppers entering at lunch/dinner that are rushing
RUSH = 0.75

# Lunch rush time
LUNCH_RUSH = [6, 14]

# Dinner rush time
DINNER_RUSH = [16, 24]

# Weekends: If weather nice, multiply average shopper by constant below
# I think nice days are common, so I'm saying 55% chance of having a nice day
WEEKEND_WEATHER_NICE_MULT = 1.4

# Weekend time spent
WEEKEND_TIME = [50, 70]

# Shopper time: Shopper time, 50% chance each interval, randgen inside interval
AVG_SHOPPER_TIME = [[6, 25], [25, 75]]

# Seniors: Seniors take more time, randgen inside interval
# Average number of senior in US is 16%
SENIOR_TIME_SPENT = [45, 60]

# Senior chance of going between 10-12 on tuesday
SENIOR_DISCOUNT_CHANCE = 0.60

# Senior discount time
SENIOR_DISCOUNT_TIME = [4, 6]

# Seniors will not be going into a store past 6pm
SENIOR_ENTER_TIME = [0, 12]


# We will be using a normal distribution for the time spent shopping for normal customers
# We will be using 68 - 95 - 99.7 with max and min being 3 sd away from mean
def time_spent_normal():
    """
    Random generation for time spent if shopper is a normal customer on a weekday. \n
    :return: Randomized float time between two intervals
    """

    if random.random() < 0.5:
        return random.uniform(AVG_SHOPPER_TIME[0][0], AVG_SHOPPER_TIME[0][1])
    return random.uniform(AVG_SHOPPER_TIME[1][0], AVG_SHOPPER_TIME[1][1])

    """
    mean = 25
    minH = 6
    maxH = 75
    std = 6
    my_dist = my_distribution(minH, maxH, mean, std)
    x = np.linspace(minH, maxH, 100)
    plt.plot(x, my_dist.pdf(x))
    plt.show()
    print(my_dist.mean(), my_dist.std())
    sample = my_dist.rvs(size=100000)
    print('min:', sample.min(), 'max:', sample.max())
    """


# In case we need to do some kind of normal distribution
# Unlikely but the code is here just in case
def my_distribution(min_val, max_val, mean, std):
    """
    This code is only if we need a bell normal distribution for times. \n
    :param min_val: min time
    :param max_val: max time
    :param mean: mean time
    :param std: standard deviation of time
    :return: Randomized time using a formula below
    """
    scale = max_val - min_val
    location = min_val
    # Mean and standard deviation of the unscaled beta distribution
    unscaled_mean = (mean - min_val) / scale
    unscaled_var = (std / scale) ** 2
    # Computation of alpha and beta can be derived from mean and variance formulas
    t = unscaled_mean / (1 - unscaled_mean)
    beta = ((t / unscaled_var) - (t * t) - (2 * t) - 1) / ((t * t * t) + (3 * t * t) + (3 * t) + 1)
    alpha = beta * t
    # Not all parameters may produce a valid distribution
    if alpha <= 0 or beta <= 0:
        raise ValueError('Cannot create distribution for the given parameters.')
    # Make scaled beta distribution with computed parameters
    return scipy.stats.beta(alpha, beta, scale=scale, loc=location)


def generate_shopper(day_num, weather, is_tuesday):
    """
    Function for generating 1 random shopper. \n
    :param day_num: day number in week, monday is 0
    :param weather: whether the weather is nice on a particular day
    :param is_tuesday: whether the day is tuesday
    :return: Shopper object with specified properties
    """
    shopper = Shopper(is_tuesday)

    # Senior Tuesdays
    if day_num == 1 and shopper.get_senior():

        # Senior entering during discount
        if random.random() < SENIOR_DISCOUNT_CHANCE:
            time_entered = random.uniform(SENIOR_DISCOUNT_TIME[0], SENIOR_DISCOUNT_TIME[1])

        # Senior entering not during discount
        else:
            time_entered = random.uniform(0, 10)

            # Push shopper outside of discount time
            if time_entered > 4:
                time_entered += 2

        shopper.set_rush("Senior")
        time_spent = random.uniform(SENIOR_TIME_SPENT[0], SENIOR_TIME_SPENT[1])

    # 0-5 Week 6-7 Weekend
    # Cover weekdays
    elif day_num < 6:

        # Cover senior
        # Assume seniors come in at all times for regular shopping
        # From experience seniors don't go out past dinner, so let's say 6pm
        if shopper.get_senior():
            time_spent = random.uniform(SENIOR_TIME_SPENT[0], SENIOR_TIME_SPENT[1])
            time_entered = random.uniform(SENIOR_ENTER_TIME[0], SENIOR_ENTER_TIME[1])
            shopper.set_rush("Senior")

        else:
            time_entered_random = random.random()

            # 6 - 12
            if time_entered_random < WEEKDAY_CHANCES[0]:
                time_entered = random.uniform(WEEKDAY_TIMES[0][0], WEEKDAY_TIMES[0][1])
                time_spent = time_spent_normal()

            # 12 - 1
            # Lunch rush condition
            elif time_entered_random < WEEKDAY_CHANCES[0] + WEEKDAY_CHANCES[1]:
                time_entered = random.uniform(WEEKDAY_TIMES[1][0], WEEKDAY_TIMES[1][1])
                if random.random() > RUSH:
                    time_spent = time_spent_normal()
                else:
                    time_spent = random.uniform(LUNCH_RUSH[0], LUNCH_RUSH[1])
                    shopper.set_rush("Lunch")

            # 1 - 5
            elif time_entered_random < WEEKDAY_CHANCES[0] + WEEKDAY_CHANCES[1] + WEEKDAY_CHANCES[2]:
                time_entered = random.uniform(WEEKDAY_TIMES[2][0], WEEKDAY_TIMES[2][1])
                time_spent = time_spent_normal()

            # 5 - 6:30
            # Dinner rush condition
            elif time_entered_random < WEEKDAY_CHANCES[0] + WEEKDAY_CHANCES[1]\
                    + WEEKDAY_CHANCES[2] + WEEKDAY_CHANCES[3]:
                time_entered = random.uniform(WEEKDAY_TIMES[3][0], WEEKDAY_TIMES[3][1])
                if random.random() > RUSH:
                    time_spent = time_spent_normal()
                else:
                    time_spent = random.uniform(DINNER_RUSH[0], DINNER_RUSH[1])
                    shopper.set_rush("Dinner")

            # 6:30 - 9
            else:
                time_entered = random.uniform(WEEKDAY_TIMES[4][0], WEEKDAY_TIMES[4][1])
                time_spent = time_spent_normal()

    # Cover weekends
    else:
        shopper.set_rush("Weekend")
        # Cover elder
        if shopper.get_senior():
            time_spent = random.uniform(SENIOR_TIME_SPENT[0], SENIOR_TIME_SPENT[1])
            time_entered = random.uniform(SENIOR_ENTER_TIME[0], SENIOR_ENTER_TIME[1])
            shopper.set_rush("Senior")

        # TODO: Implement something that handles the extra 40% (waiting on response from instructor)
        # Cover nice weather
        elif weather:
            # Used short time definition as dinner rusher
            time_spent = random.uniform(DINNER_RUSH[0], DINNER_RUSH[1])
            time_entered = random.uniform(STORE_HOURS[0], STORE_HOURS[1])

        # Cover all others
        else:
            # Weekend averages to 60 min
            time_spent = random.uniform(WEEKEND_TIME[0], WEEKEND_TIME[1])
            time_entered = random.uniform(STORE_HOURS[0], STORE_HOURS[1])

    # Handling case for customers who like to enter close to closing
    if int(round(time_entered, 2)) == STORE_HOURS[1]:
        time_entered = 14.99

    shopper.set_time_spent(round(time_spent, 2))
    shopper.set_time_entered(round(time_entered, 2))

    # Handle shoppers who stay past store closing
    if time_entered + (time_spent / 60) > STORE_HOURS[1]:
        time_spent = STORE_HOURS[1] - time_entered
        shopper.set_time_spent(round(time_spent * 60, 2))

    return shopper


# Date must be in datetime form
# date(yyyy, mm, dd)
def holiday_multiplier(date):
    """
    Function for holiday handling of number of customers. \n
    :param date: datetime date
    :return: Multiplier for specific holiday category
    """

    us_holidays = holidays.US()

    # Today is holiday
    if date in us_holidays:
        return HOLIDAY_MULT[2]

    # Tomorrow is holiday
    if date + datetime.timedelta(days=1) in us_holidays:
        return HOLIDAY_MULT[1]

    # Holiday in next week
    if any(date + datetime.timedelta(days=i) in us_holidays for i in range(7)):
        return HOLIDAY_MULT[0]

    # Regular day
    return 1


def write_data(date, day_nice):
    """
    Function that writes the shoppers in a date to a csv file. \n
    :param date: datetime date
    :param dayNice: whether the day is nice
    :return: nothing
    """

    day = date.weekday()
    with open(DAYS[day] + ".csv", "w", newline='') as day_file_writer:
        writer = csv.writer(day_file_writer, delimiter=',')
        writer.writerow(["Time Entered (hr)", "Time Spent (min)",
                         "Rushing", "Senior", "Nice Day: " + str(day_nice)])

        num_shoppers = AVERAGE_SHOPPERS[day] * holiday_multiplier(date)

        if day_nice and day > 5:
            num_shoppers = num_shoppers * WEEKEND_WEATHER_NICE_MULT

        for i in range(round(num_shoppers)):
            shopper = generate_shopper(day, day_nice, day == 1)
            processing = shopper.writing()
            processing.append(str(day_nice))
            writer.writerow(processing)

    read_data(date)


def read_data(date):
    """
    This is a reader function that shows analytics of a specific date. \n
    :param date: datetime date
    :return: nothing
    """

    # This is a reader function that shows mean and std of time spent
    # Divides data based on category of lunch / dinner / senior / other
    # Other is normal shopping, should have highest deviation
    # Times on weekends should be longer
    day = date.weekday()

    with open(DAYS[day] + ".csv", "r", newline='') as day_file_reader:
        reader = csv.reader(day_file_reader)
        next(reader)
        helper = ["Lunch", "Dinner", "Senior", "Other"]
        rush_times = [[], [], [], []]
        new_customers = np.zeros(16)
        customers_in_store = np.zeros(16)
        for row in reader:

            # Count number people enter store per hour
            time_entered = float(row[0])
            time_spent = float(row[1])

            if int(time_entered) == 15:
                print(row)

            new_customers[int(time_entered)] += 1

            # Count number people in store per hour
            for i in range(int(time_entered), int(time_entered + (time_spent / 60)) + 1):
                customers_in_store[i] += 1

            # Count rush times
            rush = row[2]
            senior = row[3]
            if rush == 'Lunch':
                rush_times[0].append(time_spent)
            elif rush == 'Dinner':
                rush_times[1].append(time_spent)
            elif senior == 'True':
                rush_times[2].append(time_spent)
            else:
                rush_times[3].append(time_spent)

        print(DAYS[day] + ":")
        print("New customers per hour: ", end="")
        print(new_customers)
        print("Customers in store per hour: ", end="")
        print(customers_in_store)
        print("Customers total:", np.sum(new_customers), "lunch:", len(rush_times[0]),
              "dinner:", len(rush_times[1]), "seniors:", len(rush_times[2]))
        print("There will be", customers_in_store[-1], "customers at closing time.")
        for rush_array in rush_times:
            if len(rush_array) != 0:
                print(helper[rush_times.index(rush_array)], end=' ')
                print("time spent in store: mean: " + str(round(statistics.mean(rush_array), 3)),
                      "std: " + str(round(statistics.stdev(rush_array), 3)))


if __name__ == '__main__':

    USER_MONTH = input("Please enter a month (1 - 12)")
    USER_DAY = input("Please enter a day (1 - 28/29/30/31)")
    USER_YEAR = input("Please enter a year")
    USER_WEATHER = input("Is the weather nice? (yes / no)")
    write_data(datetime.date(int(USER_YEAR), int(USER_MONTH), int(USER_DAY)), USER_WEATHER == "yes")

    write_data(datetime.date.today(), False)
