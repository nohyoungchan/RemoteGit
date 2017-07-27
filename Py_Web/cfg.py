# Read ini files

import unittest

import logging
from Base_Objects.Utility import readINI, wait
from Base_Objects import Utility_API, Utility
from Base_Objects import API

print(" I am at cfg.fy" )


#### Logging configuration ####
# set up logging to file - see previous section for more details
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(name)-20s %(levelname)-8s %(message)s',
                    datefmt='%m-%d %H:%M',
                    filename='Log/myapp.log',
                    filemode='w')
# define a Handler which writes INFO messages or higher to the sys.stderr
console = logging.StreamHandler()
console.setLevel(logging.INFO)
# set a format which is simpler for console use
#formatter = logging.Formatter("[%(asctime)-19.19s] [%(name)-12s] [%(levelname)-5.5s]  %(message)s")
formatter = logging.Formatter("[%(name)-12s] [%(levelname)-5.5s]  %(message)s")
# tell the handler to use this format
console.setFormatter(formatter)
# add the handler to the root logger
logging.getLogger('').addHandler(console)
log = logging.getLogger(__name__)


#Read All data
inic = readINI("testCases.ini")
ini = readINI("testData.ini")
inij = readINI("Config_Files/testDataJason.ini")
inip =  readINI("Config_Files/testProperty.ini")  # This contains all id/css/xpath information
#Read system info
webBrowserType = ini.get('system', 'webBrowserType')
systemToTest = ini.get('system', 'systemToTest')
systemToTestIni = "Config_Files/" + "testData_" + systemToTest + ".ini"

#Read specific system data
iniSystemToTest = readINI(systemToTestIni);

#Initialization



