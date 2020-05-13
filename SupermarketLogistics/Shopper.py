# Shopper Class File

import random

class Shopper(object):

    def __init__(self):

        self.isSenior = self.ifSenior()
        self.timeSpent = 0
        self.rush = "None"

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
