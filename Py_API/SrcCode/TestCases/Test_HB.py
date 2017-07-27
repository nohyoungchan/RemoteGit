'''
Created on Jan 12, 2017
@author: Youngchan Noh
'''
import logging
import unittest
from SrcCode import cfg
from SrcCode.BaseObjects.API_Tester import JasonAPITester
from SrcCode.TestBaseObject import BaseTestCase

print(" I am at Test_HB")


## Global Variables ###
log = logging.getLogger(__name__)

all = cfg.allObjects
ini = all.ini
inij = all.inij
inic = all.inic


def setUpModule():
    log.info("I am here at setupModule()");

def tearDownModule():
    log.info("I am here at tearDownModule()");


class HbTest(BaseTestCase):

    apiTester = None
    
    @classmethod 
    def setUpClass(self):
        try:
            log.info("I am here at setup");
            # Create apiTester
            systemToTest = ini.get('system', 'systemToTest')
            url=ini.get(systemToTest, 'ccapiURL')
            self.superID = ini.get(systemToTest,  'sup1.supervisorID')
            HbTest.apiTester = JasonAPITester(systemToTest, url)
            
            # apiTest login
            HbTest.apiTester.logIn(ini.get('C2HB2', 'sup1.username'), ini.get('C2HB2', 'sup1.password'))
            
        except:
            logging.info("I am here at except");
           

    def test_https(self):
        testname = 'test_https'
        if (self.startTest(testname) == 'out'):
            self.skipTest(" ")
            
        log.info("I am here to test httpsWithClass");
        strEntity = 'agent'
        apiTester = HbTest.apiTester
        apiTester.subscirbe(strEntity, self.superID)
        apiTester.poll()
        apiTester.unsubscribe(strEntity)

        self.endTest(testname)
        

    @classmethod 
    def tearDownClass(self):
        log.info("I am here at teatDown()");

  