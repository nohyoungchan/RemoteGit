'''
Created on Feb 9, 2017

@author: Admin
'''
'''
Created on Jan 12, 2017
@author: Youngchan Noh
'''
from SrcCode.TestBaseObject import BaseTestCase
from SrcCode import cfg
import logging


print(" I am at Test_HB2")

## Global Variables ###
log = logging.getLogger(__name__)
all = cfg.allObjects
ini = all.ini
inij = all.inij
inic = None

#logging.config.fileConfig('logging.ini')

def setUpModule():
    log.info("I am here at setupModule() 2");
    inic = all.inic

def tearDownModule():
    log.info("I am here at tearDownModule() 2");


class HbTest2(BaseTestCase):
    #def __init__(self, name):
    #   self.name = name
    
    @classmethod 
    def setUpClass(self):
        try:
            log.info("I am here at setup 2");
            
        except:
            log.info("I am here at except2");
            #os.popen('java -jar C:\young\software\sikulix\sikulix.jar -r  C:\young\Automation\Sikuli\firefox_ssl_handle.sikuli')

        
    def test_googleTwo(self):
        testname = 'test_googleTwo'
        if (self.startTest(testname) == 'out'):
            self.skipTest(" ")
        log.info("I am here at test_google 2");

        self.endTest(testname)

        
    @classmethod 
    def tearDownClass(self):
        log.info("I am here at teatDown() 2");
        #self.driver.close();
        #self.driver.quit();
    
