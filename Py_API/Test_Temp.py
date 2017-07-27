'''
Created on Jan 31, 2017

@author: Admin
'''

import requests
import logging
import json
import time
log = logging.getLogger(__name__)



class Util():
    def __init__(self):
        pass

    def wait(self, intSec):
        log.info("Wait for {} sec".format(intSec));
        time.sleep(intSec);

    def readINI(self, iniFileName):
        try:
            from configparser import ConfigParser
        except ImportError:
            from configparser import ConfigParser  # ver. < 3.0

        # instantiate
        ini = ConfigParser()

        # parse existing file from parent directory
        ini.read(iniFileName)
        return ini;
        '''
        ini  = Utility.readINI('../testData.ini');
        inij = Utility.readINI('../testDataJason.ini');
        inic = Utility.readINI('../testCases.ini')
        '''

    def printJSonStr(self, strJSon):
        data = strJSon.strip('[]')
        dataArray = data.split(',')

        for data in dataArray:
            print(data)

    def parseJasonString(self, strJSon):
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

    def parseJasonStringWithEntity(self, strJSon, strEntity):
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

utilities = Util()

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

    def subscirbe(self, strEntity, strSupID):
        print("#### Send request to get agent")
        supID = int(strSupID)
        jsonGetAgentMessage = {"version": 1, "topic": "contact-center", "message": "subscribe-events", "request-id": 10,
                               "subscribe": [["ecc", "entity", [strEntity]]]}
        payload = {'request': jsonGetAgentMessage, 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        # r = requests.post(url, data=json.dumps(payload), headers=headers)
        print("Subscirbe result: " + r.text)
        # Utility.printJSonStr(r.text)
        utilities.parseJasonString(r.text)

    def poll(self):
        print("#### Send request to poll")
        payload = {'request': 'poll', 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        print("poll result : " + r.text)

        # Utility.printJSonStr(r.text)
        utilities.parseJasonString(r.text)
        utilities.parseJasonStringWithEntity(r.text, "agents")

    def unsubscribe(self, strEntity):
        print("#### Send request to unsubscrbe agent")
        jsonGetAgentMessage = {"version": 1, "topic": "contact-center", "message": "unsubscribe-events",
                               "request-id": 10, "unsubscribe": [["ecc", "entity", [strEntity]]]}
        payload = {'request': jsonGetAgentMessage, 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        print(r.text)
        # Utility.printJSonStr(r.text)
        utilities.parseJasonString(r.text)



#### Running test cases



if __name__ == '__main__':
    ccapiURL = 'https://ccapi2.shoretel.com/'
    superName = 'young2@yc2.com'
    superPW = 'Shoreadmin1'
    supervisorID = '10'
    strEntity = 'agent'

    apiTester = JasonAPITester(ccapiURL)
    apiTester.logIn(superName, superPW)
    apiTester.subscirbe(strEntity, supervisorID)
    apiTester.poll()
    apiTester.unsubscribe(strEntity)


