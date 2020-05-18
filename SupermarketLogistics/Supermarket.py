from Shopper import Shopper
import random
import csv
import statistics
import scipy.stats
import math
import datetime
import holidays
import matplotlib.pyplot as plt
import numpy as np

# Store hours
STORE_HOURS = [0, 15]

# Holidays (I went off public holidays)
HOLIDAYS = [[1,1], [1,20], [2,14], [5,25], [7,4], [9,7], [11,11], [11,26], [12,25]]

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
def timeSpentNormal():

    if random.random() < 0.5:
        return random.uniform(AVG_SHOPPER_TIME[0][0], AVG_SHOPPER_TIME[0][1])
    else:
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


def generateShopper(dayNum, weather, isTuesday):
    shopper = Shopper(isTuesday)

    # Senior Tuesdays
    if dayNum == 1 and shopper.getSenior():

        # Senior entering during discount
        if random.random() < SENIOR_DISCOUNT_CHANCE:
            timeEntered = random.uniform(SENIOR_DISCOUNT_TIME[0], SENIOR_DISCOUNT_TIME[1])

        # Senior entering not during discount
        else:
            timeEntered = random.uniform(0, 10)

            # Push shopper outside of discount time
            if timeEntered > 4:
                timeEntered += 2

        shopper.setRush("Senior")
        timeSpent = random.uniform(SENIOR_TIME_SPENT[0], SENIOR_TIME_SPENT[1])

    # 0-5 Week 6-7 Weekend
    # Cover weekdays
    elif dayNum < 6:

        # Cover senior
        # Assume seniors come in at all times for regular shopping
        # From experience seniors don't go out past dinner, so let's say 6pm
        if shopper.getSenior():
            timeSpent = random.uniform(SENIOR_TIME_SPENT[0], SENIOR_TIME_SPENT[1])
            timeEntered = random.uniform(SENIOR_ENTER_TIME[0], SENIOR_ENTER_TIME[1])
            shopper.setRush("Senior")

        else:
            timeEnteredRandom = random.random()

            # 6 - 12
            if timeEnteredRandom < WEEKDAY_CHANCES[0]:
                timeEntered = random.uniform(WEEKDAY_TIMES[0][0], WEEKDAY_TIMES[0][1])
                timeSpent = timeSpentNormal()

            # 12 - 1
            # Lunch rush condition
            elif timeEnteredRandom < WEEKDAY_CHANCES[0] + WEEKDAY_CHANCES[1]:
                timeEntered = random.uniform(WEEKDAY_TIMES[1][0], WEEKDAY_TIMES[1][1])
                if random.random() > RUSH:
                    timeSpent = timeSpentNormal()
                else:
                    timeSpent = random.uniform(LUNCH_RUSH[0], LUNCH_RUSH[1])
                    shopper.setRush("Lunch")

            # 1 - 5
            elif timeEnteredRandom < WEEKDAY_CHANCES[0] + WEEKDAY_CHANCES[1] + WEEKDAY_CHANCES[2]:
                timeEntered = random.uniform(WEEKDAY_TIMES[2][0], WEEKDAY_TIMES[2][1])
                timeSpent = timeSpentNormal()

            # 5 - 6:30
            # Dinner rush condition
            elif timeEnteredRandom < WEEKDAY_CHANCES[0] + WEEKDAY_CHANCES[1] + WEEKDAY_CHANCES[2] + WEEKDAY_CHANCES[3]:
                timeEntered = random.uniform(WEEKDAY_TIMES[3][0], WEEKDAY_TIMES[3][1])
                if random.random() > RUSH:
                    timeSpent = timeSpentNormal()
                else:
                    timeSpent = random.uniform(DINNER_RUSH[0], DINNER_RUSH[1])
                    shopper.setRush("Dinner")

            # 6:30 - 9
            else:
                timeEntered = random.uniform(WEEKDAY_TIMES[4][0], WEEKDAY_TIMES[4][1])
                timeSpent = timeSpentNormal()

    # Cover weekends
    else:
        shopper.setRush("Weekend")
        # Cover elder
        if shopper.getSenior():
            timeSpent = random.uniform(SENIOR_TIME_SPENT[0], SENIOR_TIME_SPENT[1])
            timeEntered = random.uniform(SENIOR_ENTER_TIME[0], SENIOR_ENTER_TIME[1])
            shopper.setRush("Senior")

        # TODO: Implement something that handles the extra 40% (waiting on response from instructor)
        # Cover nice weather
        elif weather:
            # Used short time definition as dinner rusher
            timeSpent = random.uniform(DINNER_RUSH[0], DINNER_RUSH[1])
            timeEntered = random.uniform(STORE_HOURS[0], STORE_HOURS[1])

        # Cover all others
        else:
            # Weekend averages to 60 min
            timeSpent = random.uniform(WEEKEND_TIME[0], WEEKEND_TIME[1])
            timeEntered = random.uniform(STORE_HOURS[0], STORE_HOURS[1])

    """
    min = int(timeSpent)
    seconds = ((timeSpent * 60) % 60)
    shopper.setTimeSpent(min + seconds)
    """

    # Handling case for customers who like to enter close to closing
    if int(round(timeEntered, 2)) == STORE_HOURS[1]:
        timeEntered = 14.99

    shopper.setTimeSpent(round(timeSpent, 2))
    shopper.setTimeEntered(round(timeEntered, 2))

    if timeEntered + (timeSpent / 60) > STORE_HOURS[1]:
        timeSpent = STORE_HOURS[1] - timeEntered
        shopper.setTimeSpent(round(timeSpent * 60, 2))

    return shopper


# Date must be in datetime form
# date(yyyy, mm, dd)
def holidayMultiplier(date):

    us_holidays = holidays.US()

    # Today is holiday
    if date in us_holidays:
        return HOLIDAY_MULT[2]

    # Tomorrow is holiday
    elif date + datetime.timedelta(days=1) in us_holidays:
        return HOLIDAY_MULT[1]

    # Holiday in next week
    elif any(date + datetime.timedelta(days=i) in us_holidays for i in range(7)):
        return HOLIDAY_MULT[0]

    # Regular day
    else:
        return 1


def writeData(date, dayNice):

    day = date.weekday()

    with open(DAYS[day] + ".csv", "w", newline='') as dayFile:
        writer = csv.writer(dayFile, delimiter=',')
        writer.writerow(["Time Entered (hr)", "Time Spent (min)", "Rushing", "Senior", "Nice Day: " + str(dayNice)])

        numShoppers = AVERAGE_SHOPPERS[day] * holidayMultiplier(date)

        if dayNice and day > 5:
            numShoppers = numShoppers * WEEKEND_WEATHER_NICE_MULT

        for i in range(round(numShoppers)):
            shopper = generateShopper(day, dayNice, day == 1)
            processing = shopper.writing()
            processing.append(str(dayNice))
            writer.writerow(processing)

    readData(date)


def readData(date):

    # This is a reader function that shows mean and std of time spent
    # Divides data based on category of lunch / dinner / senior / other
    # Other is normal shopping, should have highest deviation
    # Times on weekends should be longer
    day = date.weekday()

    with open(DAYS[day] + ".csv", "r", newline='') as dayFileReader:
        reader = csv.reader(dayFileReader)
        next(reader)
        helper = ["Lunch", "Dinner", "Senior", "Other"]
        rushTimes = [[], [], [], []]
        newCustomers = np.zeros(16)
        customersInStore = np.zeros(16)
        for row in reader:

            # Count number people enter store per hour
            timeEntered = float(row[0])
            timeSpent = float(row[1])

            if int(timeEntered) == 15:
                print(row)

            newCustomers[int(timeEntered)] += 1

            # Count number people in store per hour
            for i in range(int(timeEntered), int(timeEntered + (timeSpent / 60)) + 1):
                customersInStore[i] += 1

            # Count rush times
            rush = row[2]
            senior = row[3]
            if rush == 'Lunch':
                rushTimes[0].append(timeSpent)
            elif rush == 'Dinner':
                rushTimes[1].append(timeSpent)
            elif senior == 'True':
                rushTimes[2].append(timeSpent)
            else:
                rushTimes[3].append(timeSpent)

        print(DAYS[day] + ":")
        print("New customers per hour: ", end="")
        print(newCustomers)
        print("Customers in store per hour: ", end="")
        print(customersInStore)
        print("Customers total:", np.sum(newCustomers), "lunch:", len(rushTimes[0]), "dinner:", len(rushTimes[1]), "seniors:", len(rushTimes[2]))
        print("There will be", customersInStore[-1], "customers at closing time.")
        for rushArray in rushTimes:
            if len(rushArray) != 0:
                print(helper[rushTimes.index(rushArray)], end=' ')
                print("time spent in store: mean: " + str(round(statistics.mean(rushArray), 3)), "std: " + str(round(statistics.stdev(rushArray), 3)))


if __name__ == '__main__':

    user_month = input("Please enter a month (1 - 12)")
    user_day = input("Please enter a day (1 - 28/29/30/31)")
    user_year = input("Please enter a year")
    user_weather = input("Is the weather nice? (yes / no)")
    writeData(datetime.date(int(user_year), int(user_month), int(user_day)), user_weather == "yes")

    writeData(datetime.date.today(), False)
