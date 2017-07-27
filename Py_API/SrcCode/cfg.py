# Read ini files

import unittest

import logging
from SrcCode.BaseObjects.Utilities import Util, AllObjects
print(" I am at cfg.fy" )


#### Declare global variables ####
# set up logging to file - see previous section for more details
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(name)-20s %(levelname)-8s %(message)s',
                    datefmt='%m-%d %H:%M',
                    filename='../log/myapp.log',
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


#Setup All objects
allObjects = AllObjects()
utilities = Util()
allObjects.ini = utilities.readINI('../testData.ini');
allObjects.inij = utilities.readINI('../testDataJason.ini');
allObjects.inic = utilities.readINI('../testCases.ini')
allObjects.log = logging.getLogger(__name__)


