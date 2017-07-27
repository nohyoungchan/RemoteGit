'''
Created on Jan 31, 2017

@author: Admin
'''

import cfg
import requests
import logging
import json
from Base_Objects import Utility_API, Utility

print(" I am at API_Tesr.py")
log = logging.getLogger(__name__)



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
        Utility_API.parseJasonString(r.text)

    def poll(self):
        print("#### Send request to poll")
        payload = {'request': 'poll', 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        print(r.text)

        # Utility.printJSonStr(r.text)
        Utility_API.parseJasonString(r.text)
        Utility_API.parseJasonStringWithEntity(r.text, "agents")

    def unsubscribe(self, strEntity):
        print("#### Send request to unsubscrbe agent")
        jsonGetAgentMessage = {"version": 1, "topic": "contact-center", "message": "unsubscribe-events",
                               "request-id": 10, "unsubscribe": [["ecc", "entity", [strEntity]]]}
        payload = {'request': jsonGetAgentMessage, 'ticket': self.certificate}
        r = requests.post(self.url, data=json.dumps(payload), headers=self.headers, verify=False)
        print(r.text)
        # Utility.printJSonStr(r.text)
        Utility_API.parseJasonString(r.text)
