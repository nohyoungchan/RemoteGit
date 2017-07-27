'''
Created on Feb 6, 2017

@author: Admin
'''

import logging
import requests
import cfg
import sys
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from germanium.static import *


log = logging.getLogger(__name__)
ini = cfg.ini;
inip = cfg.inip



class WebPage():
    def __init__(self):
        #$self.name = name
        self.systemToTest = cfg.systemToTest
        self.webBrowserType = cfg.webBrowserType
        #self.driver = None
        self.waitForElementSec=10
        self.driver = None


    def startWebBrowser(self):
        print("WebBrowserType is " + self.webBrowserType)
        open_browser(self.webBrowserType)
        self.driver = get_web_driver()
    
    def openWebPage(self, webAddress):
        testname = 'openeWebPage'
        log.info("I am here(function) : " + testname);
        go_to(webAddress)

    def closeWebPage(self):
        testname = 'closeWebPage'
        log.info("I am here(function) : " + testname);
        close_browser()

    def findUsingID(self, id):
        log.info("I am finding => " + id)
        #element = self.driver.find_element_by_id(id)
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
            #element = WebDriverWait(self.driver, self.waitForElementSec).until(
            #    lambda x: x.find_element_by_xpath(strXPath))
            #element = self.driver.WebDriverWait(self.driver, self.waitForElementSec).until(
            # EC.element_to_be_clickable((strXPath)))

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
        if type=="id":
            element = self.driver.find_element_by_id(id)
            return element
        elif type=="name":
            log.info("I am finding => " + name)
            element = self.driver.find_element_by_name(name)
        elif type=="css":
            log.info("I am finding => " + name)
            element = self.driver.find_element_by_css_selector(name)
        elif type=="className":
            log.info("I am finding => " + name)
            element = self.driver.find_element_by_class_name(name)
        else:
            log.error("Canning fine element type")

    def maximize(self):
        log.info("Maximize Browser")
        self.driver.maximize_window()






    
        