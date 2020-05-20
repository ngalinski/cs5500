# Shopper Class File
"""
This shopper class function builds a random shopper.
The shopper has a random chance at being a senior.
"""

import random

SENIOR_CHANCE = 0.16
SENIOR_CHANCE_TUESDAY = 0.40


# More seniors at 10 - 12 on Tuesday
# Higher chance of being a senior on tuesday at 10-12
class Shopper():
    """
    Shopper object class
    """

    def __init__(self, is_tuesday):
        """
        Initiation function for a shopper \n
        :param bool is_tuesday: Shopper has higher chance of being senior on tuesday
        """
        self.is_tuesday = is_tuesday
        self.is_senior = self.senior()
        self.time_spent = 0
        self.rush = "Normal"
        self.time_entered = 0

    def senior(self):
        """
        Generator for whether customer is a senior \n
        :return: True if senior, false otherwise
        """
        if self.is_tuesday:
            return random.random() < SENIOR_CHANCE_TUESDAY
        return random.random() < SENIOR_CHANCE

    def get_senior(self):
        """
        Getter function for whether shopper senior \n
        :return: Whether shopper is a senior
        """
        return self.is_senior

    def set_time_spent(self, time):
        """
        Setter function for time spent in store \n
        :param float time: time spent in store
        :return: nothing
        """
        self.time_spent = time

    def get_time_spent(self):
        """
        Getter function for time spent in store \n
        :return: time spent in store (float)
        """
        return self.time_spent

    def set_rush(self, rush):
        """
        Setter for customer type \n
        :param string rush: What type of customer
        :return: nothing
        """
        self.rush = rush

    def get_rush(self):
        """
        Getter for customer type \n
        :return: type of customer (string)
        """
        return self.rush

    def set_time_entered(self, time_entered):
        """
        Setter for time entered \n
        :param float time_entered: time entered
        :return: nothing
        """
        self.time_entered = time_entered

    def get_time_entered(self):
        """
        Getter for time entered \n
        :return: time entered (float)
        """
        return self.time_entered

    def get_time_exited(self):
        """
        Getter for time exited \n
        :return: time exited (float)
        """
        return self.time_entered + self.time_spent

    def writing(self):
        """
        Helper function for writing all relevant info to csv \n
        :return: String with all info of shopper
        """
        return [str(self.time_entered), str(self.time_spent), self.rush, str(self.is_senior)]
