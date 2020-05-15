# Shopper Class File

import random

class Shopper(object):

    def __init__(self, isTuesday):

        self.isTuesday = isTuesday
        self.isSenior = self.ifSenior()
        self.timeSpent = 0
        self.rush = "Normal"
        self.timeEntered = 0

    def ifSenior(self):
        ifSenior = random.random()
        return ifSenior < 0.16

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