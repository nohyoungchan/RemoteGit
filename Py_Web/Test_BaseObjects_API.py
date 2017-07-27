import logging
import json
import cfg
import requests
import logging
import json
import UtilityAll
from germanium.static import *

log = logging.getLogger(__name__)




def printJSonStr(strJSon):
    data = strJSon.strip('[]')
    dataArray = data.split(',')

    for data in dataArray:
        print(data)


def parseJasonString(strJSon):
    try:
        print("@ starting: parseJasonString")
        # remove [] from the first and last character. decode is needed to do dumps in this case
        data = strJSon.strip('[]')
        # load creates json dictionary
        decoded = json.loads(data)
        # dumps create json object from json string
        print(json.dumps(decoded, sort_keys=True, indent=4))

    except (ValueError, KeyError, TypeError):
        print("JSON format error at parseJasonString")


def parseJasonStringWithEntity(strJSon, strEntity):
    try:
        print("@ starting: parseJasonStringWithEntity")
        # remove [] from the first and last character. decode is needed to do dumps in this case
        jsonStripped = strJSon.strip('[]')
        # load creates json dictionary
        decoded = json.loads(jsonStripped)
        entityList = decoded[strEntity]
        # This is how you read dictions
        print(strEntity + " is : ")
        print(len(entityList))
        for entity in entityList:
            print(entity)
            print("agent-cos => {0}".format(entity["agent-cos"]))
            print("agent-email => {0}".format(entity["agent-email"]))
            print("agent-extension => {0}".format(entity["agent-extension"]))
            print("agent-id => {0}".format(entity["agent-id"]))
            print("agent-name => {0}".format(entity["agent-name"]))
            print("agent-number => {0}".format(entity["agent-number"]))
            print("agent-supervisor => {0}".format(entity["agent-supervisor"]))
            print("agent-username => {0}".format(entity["agent-username"]))
            print("screen-pop-profile-id => {0}".format(entity["screen-pop-profile-id"]))
            # print (entityList[0])

            # inventory = json.dumps(decoded  , sort_keys=True, indent=4)
            # dumps create json object from json string
            # print (inventory)


    except (ValueError, KeyError, TypeError):
        print("JSON format error at parseJasonStringWithEntity")

class JasonAPITester():
    def __init__(self, url):
        # $self.name = name
        self.url = None
        self.headers = None
        self.certificate = None
        self.url = url

    def logIn(self, username, password):
        log.info("I am here at login() with " + username + " " + password);
        self.headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
        payload = {'message': 'authenticate', 'user': username, 'password': password};

        print("#### This sets up the https connection")
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        print(r.text)
        # Make dictionary
        r = json.loads(r.text)
        self.certificate = r['ticket']

    def subscribe(self, strEntity, strSupID):
        print("#### Send request to get agent")
        supID = int(strSupID)
        jsonGetAgentMessage = {"version": 1, "topic": "contact-center", "message": "subscribe-events", "request-id": supID,
                               "subscribe": [["ecc", "entity", [strEntity]]]}
        payload = {'request': jsonGetAgentMessage, 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        # r = requests.post(url, data=json.dumps(payload), headers=headers)
        print(r.text)
        # Utility.printJSonStr(r.text)
        print ("I am here at subscribe")
        parseJasonString(r.text)

    def poll(self):
        print("#### Send request to poll")
        payload = {'request': 'poll', 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        print(r.text)

        # Utility.printJSonStr(r.text)
        parseJasonString(r.text)
        parseJasonStringWithEntity(r.text, "agents")

    def unsubscribe(self, strEntity):
        print("#### Send request to unsubscrbe agent")
        jsonGetAgentMessage = {"version": 1, "topic": "contact-center", "message": "unsubscribe-events",
                               "request-id": 10, "unsubscribe": [["ecc", "entity", [strEntity]]]}
        payload = {'request': jsonGetAgentMessage, 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        print(r.text)
        # Utility.printJSonStr(r.text)
        parseJasonString(r.text)

######################################################################################################3


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


