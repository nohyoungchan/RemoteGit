#Python 2.7.6
#RestfulClient.py

import requests
from requests.auth import HTTPDigestAuth
import json
import logging
import time


class Util():
    def __init__(self):
        pass

    def wait(self, intSec):
        logging.info("Wait for {} sec".format(intSec));
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

# Replace with the correct URL
url = "http://10.23.196.87:8000/v0/tenant"  #Glen's api
#url = "http://10.23.195.154:8000/v0/tenant"  #Young's api

# It is a good practice not to hardcode the credentials. So ask the user to enter credentials at runtime
#myResponse = requests.get(url,auth=HTTPDigestAuth(raw_input("username: "), raw_input("Password: ")), verify=True)

myResponse = requests.get(url);
#print (myResponse.status_code)

# For successful API call, response code will be 200 (OK)
if(myResponse.ok):

    # Loading the response data into a dict variable
    # json.loads takes in only binary or string variables so using content to fetch binary content
    # Loads (Load String) takes a Json file and converts into python data structure (dict or list, depending on JSON)
    jData = json.loads(myResponse.content)

    print("The response contains {0} properties".format(len(jData)))
    print("\n")
    print(jData)
    # Utility.printJSonStr(r.text)
    #utilities.parseJasonString(jData)
    for key in jData:
      #print (key + " : " + str(jData[key]))
      print (str(jData[key]))
      #utilities.printJSonStr(str(jData[key]))
      #utilities.parseJasonString(str(jData[key]))
      print(json.dump(jData[key], indent=4, sort_keys=True))
else:
  # If response code is not ok (200), print the resulting http error code with description
    myResponse.raise_for_status()