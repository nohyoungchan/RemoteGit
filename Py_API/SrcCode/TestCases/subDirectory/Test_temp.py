'''
Created on Feb 8, 2017

@author: Admin
'''
import os
import subprocess

print(" I am at Test_temp")

'''
def test_https(self):
       testname = 'test_https'
       if (inic.get(self.__class__.__name__, testname) == 'out'):
           self.skipTest(" ")
           
        
       log.info("I am here to test https");
       #url= 'https://ccapi2.shoretel.com/'
       url = ini.get('C2HB2', 'ccapiURL')
       payload =  {'message':'authenticate','user':'young2@yc2.com','password':'Shoreadmin1'};
       headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
       
       print("#### This sets up the https connection")
       r = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
       print(r.text)
       
       # Getting certificate from return
       output = r.text.split(",");
       output = output[1].split(":");
       strCertificate = output[1];
       
       print("#### Send request to get agent")
       jsonGetAgentMessage = {"version":1,"topic":"contact-center","message":"subscribe-events","request-id":10,"subscribe":[["ecc","entity",["agent"]]]}
       payload= {'request': jsonGetAgentMessage , 'ticket': strCertificate}
       r = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
       #r = requests.post(url, data=json.dumps(payload), headers=headers)
       print(r.text)
       #Utility.printJSonStr(r.text)
       Utility.parseJasonString(r.text)
       
       
       print("#### Send request to poll")
       payload= {'request':'poll' , 'ticket': strCertificate}
       r = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
       print(r.text)
       #Utility.printJSonStr(r.text)
       Utility.parseJasonString(r.text)
       Utility.parseJasonStringWithEntity(r.text, "agents")
       
       print("#### Send request to unsubscrbe agent")
       jsonGetAgentMessage = {"version":1,"topic":"contact-center","message":"unsubscribe-events","request-id":10,"unsubscribe":[["ecc","entity",["agent"]]]}
       payload= {'request': jsonGetAgentMessage , 'ticket': strCertificate}
       r = requests.post(url, data=json.dumps(payload), headers=headers, verify=False)
       print(r.text)
       #Utility.printJSonStr(r.text)
       Utility.parseJasonString(r.text)

'''

def run_win_cmd(cmd):
    result = []
    process = subprocess.Popen(cmd,
                               shell=True,
                               stdout=subprocess.PIPE,
                               stderr=subprocess.PIPE)
    for line in process.stdout:
        result.append(line)
    errcode = process.returncode
    for line in result:
        print(line)
    if errcode is not None:
        raise Exception('cmd %s failed, see above for details', cmd)

def runMe():
    print("I am here runMe")
    cmdSSL = 'java -jar C:\young\software\sikulix\sikulix.jar  -r C:\young\Automation\Sikuli\sslHandle.sikuli'
    cmdHB = 'java -jar C:\young\software\sikulix\sikulix.jar  -r C:\young\Automation\Sikuli\HB_AIC_Login.sikuli'
    run_win_cmd(cmdSSL)
    run_win_cmd(cmdHB)
    #subprocess.Popen('java -jar C:\young\software\sikulix\sikulix.jar  -r C:\young\Automation\Sikuli\sslHandle.sikuli')
    #subprocess.Popen('java -jar C:\young\software\sikulix\sikulix.jar  -r C:\young\Automation\Sikuli\HB_AIC_Login.sikuli')
    
    print("I am done")

class b():
    bb = 33
    def __init__(self):
        pass

class a(b):
    
    def __init__(self):
        pass
    
    def hello(self):
        print( "BB is  " + b.bb)



def printHello():
    global hello
    hello = "good"
    print (hello)

if __name__ == '__main__':
    #runMe();
    printHello()
    
    #unittest.main();
    #TestMain();