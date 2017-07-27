'''
Created on Feb 6, 2017

@author: Admin
'''

import logging
import cfg
import time
import UtilityAll
import unittest
import UtilityGM

from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

from germanium.static import *
from time import sleep

log = logging.getLogger(__name__)
ini = cfg.ini;
inij = cfg.inij;
inic = cfg.inic
iniSTT = cfg.iniSystemToTest
inip = cfg.inip


###########################################################################################################################



class WebPage():
    def __init__(self):
        # $self.name = name
        self.systemToTest = cfg.systemToTest
        self.webBrowserType = cfg.webBrowserType
        # self.driver = None
        self.waitForElementSec = 10

    def startWebBrowser(self):
        print("WebBrowserType is " + self.webBrowserType)
        if (self.webBrowserType == 'chrome'):
            self.driver = webdriver.Chrome()
        elif (self.webBrowserType == 'firefox'):
            self.driver = webdriver.Firefox()
        elif (self.webBrowserType == 'ie'):
            self.driver = webdriver.Ie()
        else:
            log.error("Enter correct webBrowserType")

    def openWebPage(self, webAddress):
        testname = 'openeWebPage'
        log.info("I am here(function) : " + testname);
        self.driver.get(webAddress)

    def closeWebPage(self):
        testname = 'closeWebPage'
        log.info("I am here(function) : " + testname);
        self.driver.close()

    def findUsingID(self, id):
        log.info("I am finding => " + id)
        # element = self.driver.find_element_by_id(id)
        element = WebDriverWait(self.driver, self.waitForElementSec).until(
            lambda x: x.find_element_by_id(id))
        return element

    """
    1. Function name: findUsingXPath
    2. Parameters
        1) strObjectName: This is like aic_xpath/ccd_xpath/rtm_xpath
        2) strXPathName: This is a name of xpath configured on testProperty.ini
    """

    def findUsingXPath(self, strObjectName, strXPathName):
        log.info("I am finding => " + strObjectName + " : " + strXPathName)
        try:
            strXPath = inip.get(strObjectName, strXPathName)
            print("* XPath for " + strXPathName + " is => " + strXPath + "\n")
            # element = WebDriverWait(self.driver, self.waitForElementSec).until(
            #    lambda x: x.find_element_by_xpath(strXPath))
            # element = self.driver.WebDriverWait(self.driver, self.waitForElementSec).until(
            # EC.element_to_be_clickable((strXPath)))

            #click(XPath(strXPath))

            element = self.driver.find_element_by_xpath(strXPath)
            print(" What is found???")
            print("* element found is => " + element.getText())
            return element
        except:
            log.error("Ok I cannot find xpath")

    def findUsingName(self, name):
        log.info("I am finding => " + name)
        element = self.driver.find_element_by_name(name)
        return element

    def findUsingCSS(self, cssName):
        log.info("I am finding => " + cssName)
        element = self.driver.find_element_by_css_selector(cssName)
        return element

    def findUsingClassName(self, className):
        log.info("I am finding => " + className)
        element = self.driver.find_element_by_class_name(className)
        return element

    def findElemtnt(self, type, name):
        if type == "id":
            element = self.driver.find_element_by_id(id)
            return element
        elif type == "name":
            log.info("I am finding => " + name)
            element = self.driver.find_element_by_name(name)
        elif type == "css":
            log.info("I am finding => " + name)
            element = self.driver.find_element_by_css_selector(name)
        elif type == "className":
            log.info("I am finding => " + name)
            element = self.driver.find_element_by_class_name(name)
        else:
            log.error("Canning fine element type")

    def maximize(self):
        log.info("Maximize Browser")
        self.driver.maximize_window()

#########################################################################################3

class AIC(WebPage):
    '''
    def __init__(self, username, password, extension):
        super(AIC, self).__init__()
        self.username = username
        self.password = password
        self.extension = extension
    '''

    def __init__(self, username, password, extension):
        super(AIC, self).__init__()
        self.username = username
        self.password = password
        self.extension = extension

    def start_WebBrowser(self):
        log.info("I am Starting WebBroswer");
        self.startWebBrowser()

    def logIn(self, url):
        log.info("I am here at login() with " + self.username + " " + self.password);

        # self.startWebBrowser()
        self.openWebPage(url)

        # Find elements
        usernameTxtBox = self.findUsingID('username')
        passwordTxtBox = self.findUsingID('password')
        submitBtn = self.findUsingID('submitBtn')

        usernameTxtBox.clear()
        usernameTxtBox.send_keys(self.username)

        passwordTxtBox.clear()
        passwordTxtBox.send_keys(self.password)

        submitBtn.click()

    def logout(self):
        pass

    def release(self):
        UtilityGM.clickXPath(".//*[@id='header']/div/ng-include/div[3]/div[4]/button[1]")
        webElement = self.findUsingXPath('aic_xpath', 'btnResume')
        log.info("Text from Resume button is " + webElement.getText())
        txtReturned = webElement.getText();
        wait(2);

        if (txtReturned.finds("Stop taking requests")):  # If agent is idle, release.
            log.info("#### Agent is idle=>Click release button ####");
            webElement.click()
        else:
            log.info("State=> " + txtReturned);

    def resume(self, linkName):
        pass

###############################################################
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

##############################################################
class CCD(WebPage):
    def __init__(self):
        # $self.name = name
        self.username = ini.get('system', 'systemToTest')
        self.password = ini.get('system', 'systemToTest')
        self.address = ini.get('system', 'systemToTest')

    def login(self):
        pass

    def logout(self):
        pass

    def enterToTextBox(self, strToEnter):
        pass

    def openPage(self, pageName):
        pass


###########################################################################

###########################################################################################################################

class RTM(WebPage):
    def __init__(self):
        # $self.name = name
        self.username = ini.get('system', 'systemToTest')
        self.password = ini.get('system', 'systemToTest')
        self.address = ini.get('system', 'systemToTest')

    def login(self):
        pass

    def logout(self):
        pass

    def enterToTextBox(self, strToEnter):
        pass

    def openPage(self, pageName):
        pass


