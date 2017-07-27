import logging
import json

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