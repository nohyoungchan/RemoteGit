'''
Created on Jan 12, 2017
@author: Youngchan Noh
'''
import logging
import unittest
import cfg
from Base_Objects.API import JasonAPITester
from Base_Objects.TestBaseObjects import BaseTestCase
from Base_Objects.Utility import readINI, wait

print(" I am at Test_HB")

## Global Variables ###
log = logging.getLogger(__name__)
ini = cfg.ini
inij = cfg.inij
inic = cfg.inic
iniSTT = cfg.iniSystemToTest

#Read specific system data
url = iniSTT.get('ccapi', 'ccapiURL')
apiTester = JasonAPITester(url)

def setUpModule():
    log.info("I am here at setupModule()");


def tearDownModule():
    log.info("I am here at tearDownModule()");


class Test_API_Class(BaseTestCase):

    @classmethod
    def setUpClass(self):
        try:
            log.info("I am here at setup");
            self.superName = iniSTT.get('ccapi', 'sup1.username')
            self.superPW = iniSTT.get('ccapi', 'sup1.password')
            self.superID = iniSTT.get("ccapi", "sup1.supervisorID")
        except:
            log.info("Except:Skipping HBTest class->setUpClass function");

    def test_0(self):
        testname = 'test_0'
        log.info("I am here(function) : " + testname);
        if (self.startTest(testname) == 'exclude'):
            self.skipTest(" ")
        # apiTest login
        apiTester.logIn(self.superName, self.superPW)
        self.endTest(testname)

    def test_https(self):
        testname = 'test_https'
        if (self.startTest(testname) == 'exclude'):
            self.skipTest(" ")

        log.info("I am here to test httpsWithClass");
        strEntity = 'agent'
        apiTester.subscribe(strEntity, self.superID)
        apiTester.poll()
        apiTester.unsubscribe(strEntity)

        self.endTest(testname)

    @classmethod
    def tearDownClass(self):
        log.info("I am here at tearDown()");



