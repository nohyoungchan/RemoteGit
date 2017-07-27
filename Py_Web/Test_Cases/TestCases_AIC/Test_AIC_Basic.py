'''
Created on Feb 6, 2017

@author: Admin
'''
import logging

import cfg
from Base_Objects.TestBaseObjects import BaseTestCase
from Base_Objects.AIC import AIC
from Base_Objects.Utility import wait
from germanium.static import *
from time import sleep



## Global Variables ###

log = logging.getLogger(__name__)
ini = cfg.ini
iniSystemToTest = cfg.iniSystemToTest

aicURL = iniSystemToTest.get('url', 'agentHBURL')

def setUpModule():
    log.info("I am here at setupModule()");


def tearDownModule():
    log.info("I am here at tearDownModule()");


class Test_AIC_BasicScenarios(BaseTestCase):
#class Test_AIC_BasicScenarios(unittest.TestCase):

    @classmethod
    def setUpClass(self):
        log.info("I am here at setup");
        #self.aicURL = iniSystemToTest.get('url', 'agentHBURL')

        username = iniSystemToTest.get('agent', 'agent1.name')
        password = iniSystemToTest.get('agent', 'agent1.password')
        extension = iniSystemToTest.get('agent', 'agent1.extension')
        self.agent1 = AIC(username, password ,extension)

    def test_0(self):
        testname = 'test_0'
        log.info("I am here(function) : " + testname);
        if (self.startTest(testname) == 'exclude'):
            self.skipTest(" ")
        self.endTest(testname)

    def test_Login_Resume_Release_Logout(self):
        testname = 'test_Login_Resume_Release_Logout'
        if (self.startTest(testname) == 'exclude'):
            self.skipTest(" ")
        try:
            open_browser('chrome')
            go_to('https://cc.shoretel.com/ecc')

            click(XPath(".//*[@id='username']"))
            type_keys('<ctrl-a>')
            type_keys('young2@yc2.com')
            sleep(2)
            click(XPath(".//*[@id='password']"))
            type_keys('<ctrl-a>')
            type_keys('Shoreadmin1<enter>')

            get_germanium().wait_for_page_to_load()
            sleep(5)

            click(XPath(".//*[@id='header']/div/ng-include/div[3]/div[4]/button[1]"))
            '''
            self.agent1.start_WebBrowser()
            self.agent1.logIn(aicURL)
            self.agent1.maximize()
            wait(5)
            self.agent1.release()
            #agent1.closeWebPage()
            #self.assertEqual("something" "no man", "What the...")
            # assert(self.driver.title == "ShoreTel Connect Contact Center"), "The time doesn't match"
            self.endTest(testname)
            '''
        except:
            log.error("Failed on login")
            #log.error("It has failed with title => " + self.driver.title, exc_info=False)
            # log.error("It has failed with title => " + self.driver.title)
            log.error("Failed on login")

    @classmethod
    def tearDownClass(self):
        testname = 'testDownClass'
        log.info("I am here(function) : " + testname);
        # self.driver.close();
        # self.driver.quit();

