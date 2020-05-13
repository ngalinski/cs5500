from Shopper import Shopper
import random
import csv
import statistics

# Days
DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

# Define average shoppers per day
AVERAGE_SHOPPERS = [800, 1000, 1200, 900, 2500, 4000, 5000]

# Percentage shoppers
# Weekdays: Lunch, Dinner, Rest
WEEKDAY_TIMES = [0.20, 0.20, 0.70]

# Weekends: If weather nice, multiply average shopper by constant below
# I think nice days are common, so I'm saying 55% chance of having a nice day
WEEKEND_WEATHER_NICE = 1.4

# Shopper time: Shopper time, 50% chance each interval, randgen inside interval
AVG_SHOPPER_TIME = [[6, 25], [25, 75]]

# Seniors: Seniors take more time, randgen inside interval
# Average number of senior in US is 16%
SENIOR_TIME = [45, 60]

# Holidays: Holidays increase traffic.
# Week before, day before, day of
HOLIDAY = [1.15, 1.40, 0.2]

random.seed(1)


def generateShopper(dayNum, weather):
    shopper = Shopper()
    # 0-5 Week 6-7 Weekend
    # Cover weekdays
    if dayNum < 6:
        # Cover elder
        if shopper.getSenior():
            timeSpent = random.uniform(45, 60)
            shopper.setRush("Senior")

        else:
            # Cover weekday rushes
            rush = random.random()
            if rush < WEEKDAY_TIMES[0]:
                # Lunch Rusher: Stay between 8 - 12 min Avg 10 min
                timeSpent = random.uniform(8, 12)
                shopper.setRush("Lunch")
            elif rush < WEEKDAY_TIMES[0] + WEEKDAY_TIMES[1]:
                # Dinner Rusher: Stay between 17 - 23 min Avg 20 min
                timeSpent = random.uniform(17, 23)
                shopper.setRush("Dinner")

            # Cover all others
            else:
                if random.random() < 0.5:
                    timeSpent = random.uniform(6, 25)
                else:
                    timeSpent = random.uniform(25, 75)

    # Cover weekends
    else:
        shopper.setRush("Weekend")
        # Cover elder
        if shopper.getSenior():
            timeSpent = random.uniform(45, 60)

        # Cover nice weather
        elif weather:
            # Used short time definition as dinner rusher
            timeSpent = random.uniform(17, 23)

        # Cover all others
        else:
            # Weekend averages to 60 min
            timeSpent = random.uniform(55, 65)

    """
    min = int(timeSpent)
    seconds = ((timeSpent * 60) % 60)
    shopper.setTimeSpent(min + seconds)
    """
    shopper.setTimeSpent(round(timeSpent, 2))
    return shopper


if __name__ == '__main__':

    for day in DAYS:

        dayNice = random.random() < 0.55

        with open(day + ".csv", "w", newline='') as dayFile:
            writer = csv.writer(dayFile, delimiter=',')
            writer.writerow(["Time Spend", "Rushing", "Senior", "Nice Day: " + str(dayNice)])
            for numShoppers in range(AVERAGE_SHOPPERS[DAYS.index(day)]):
                shopper = generateShopper(DAYS.index(day), dayNice)
                timeSpent = shopper.getTimeSpent()
                rush = shopper.getRush()
                senior = shopper.getSenior()
                row = [str(timeSpent), rush, str(senior)]
                writer.writerow(row)

        with open(day + ".csv", "r", newline='') as dayFileReader:
            reader = csv.reader(dayFileReader)
            next(reader)
            helper = ["Lunch", "Dinner", "Senior", "Other"]
            times = [[],[],[],[]]
            for row in reader:
                timeSpent = float(row[0])
                rush = row[1]
                senior = row[2]
                if rush == 'Lunch':
                    times[0].append(timeSpent)
                elif rush == 'Dinner':
                    times[1].append(timeSpent)
                elif senior == 'True':
                    times[2].append(timeSpent)
                else:
                    times[3].append(timeSpent)
            for timeArrays in times:
                if len(timeArrays) != 0:
                    print(helper[times.index(timeArrays)])
                    print(statistics.mean(timeArrays), statistics.stdev(timeArrays))

