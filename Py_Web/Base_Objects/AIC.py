'''
Created on Feb 6, 2017

@author: Admin
'''

import logging
import Base_Objects
import cfg
from Base_Objects import Utility
from Base_Objects.Utility import wait
from Base_Objects.WebPage import WebPage
import UtilityGM


log = logging.getLogger(__name__)
ini = cfg.ini;
inij = cfg.inij;
iniSTT = cfg.iniSystemToTest


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

        #self.startWebBrowser()
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

        if (txtReturned.finds("Stop taking requests")): #If agent is idle, release.
            log.info("#### Agent is idle=>Click release button ####");
            webElement.click()
        else:
            log.info("State=> " + txtReturned);

        
    def resume(self, linkName):
        pass
    
