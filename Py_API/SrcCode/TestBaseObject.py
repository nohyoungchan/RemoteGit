'''
Created on Jan 11, 2017

@author: Admin
'''
import unittest
from SrcCode.cfg import allObjects
from SrcCode.BaseObjects.Utilities import Util

inic = allObjects.inic

print(" I am at TestBaseObject.py" )
class BaseObject(object):

    def __init__(self):
        pass
        
class BaseTestCase(unittest.TestCase):

    def startTest(self, testName):
        if (inic.get(self.__class__.__name__, testName) == 'out'):
            print("@@@@@ Test Skipped by testCases.ini: " + testName)
            return "out"
        print("@@@@@ Starting test : "+ testName)

    def endTest(self, testName):
        print("@@@@@ Ending test : " + testName)
