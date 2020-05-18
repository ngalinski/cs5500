# Shopper Class File

import random

SENIOR_CHANCE = 0.16
SENIOR_CHANCE_TUESDAY = 0.40


# More seniors at 10 - 12 on Tuesday
# Higher chance of being a senior on tuesday at 10-12
class Shopper(object):

    def __init__(self, isTuesday):
        self.isTuesday = isTuesday
        self.isSenior = self.senior()
        self.timeSpent = 0
        self.rush = "Normal"
        self.timeEntered = 0

    def senior(self):
        if self.isTuesday:
            return random.random() < SENIOR_CHANCE_TUESDAY
        else:
            return random.random() < SENIOR_CHANCE

    def getSenior(self):
        return self.isSenior

    def setTimeSpent(self, time):
        self.timeSpent = time

    def getTimeSpent(self):
        return self.timeSpent

    def setRush(self, rush):
        self.rush = rush

    def getRush(self):
        return self.rush

    def setTimeEntered(self, timeEntered):
        self.timeEntered = timeEntered

    def getTimeEntered(self):
        return self.timeEntered

    def getTimeExited(self):
        return self.timeEntered + self.timeSpent

    def writing(self):
        return [str(self.timeEntered), str(self.timeSpent), self.rush, str(self.isSenior)]
