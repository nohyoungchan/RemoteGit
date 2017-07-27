
import unittest
import cfg
import time
from Base_Objects import Utility

inic = cfg.inic

print(" I am at TestBaseObject.py" )
class BaseObject(object):

    def __init__(self):
        pass

class BaseTestCase(unittest.TestCase):

    def startTest(self, testName):
        if (inic.get(self.__class__.__name__, testName) == 'exclude'):
            print("@@@@@ Test Skipped by testCases.ini: " + testName)
            return "exclude"
        print("@@@@@ Starting test : "+ testName)

    def startTestClass(self, className, testName):
        if (inic.get(className, testName) == 'exclude'):
            print("@@@@@ Test Skipped by testCases.ini: " + testName)
            return "exclude"
        print("@@@@@ Starting test : "+ testName)


    def endTest(self, testName):
        print("@@@@@ Ending test : " + testName)
        Utility.wait(2)
