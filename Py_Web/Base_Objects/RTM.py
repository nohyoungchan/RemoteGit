'''
Created on Feb 6, 2017

@author: Admin
'''

import logging
import json
import Base_Objects
from Base_Objects import Utility
from Base_Objects.Utility import wait
from Base_Objects import WebPage

log = logging.getLogger(__name__)
ini = Base_Objects.ini;
inij = Base_Objects.inij;

class RTM(WebPage):
    
    def __init__(self):
        #$self.name = name
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
        