'''
Created on Jan 11, 2017

@author: Youngchan Noh
'''

import configparser
import time
import logging
from logging.handlers import RotatingFileHandler
from pathlib import Path
import json

log = logging.getLogger(__name__)


def wait(intSec):
    log.info("Wait for {} sec".format(intSec));
    time.sleep(intSec);


def readINI(iniFileName):
    try:
        from configparser import ConfigParser
    except ImportError:
        from configparser import ConfigParser  # ver. < 3.0

    # instantiate
    ini = ConfigParser()

    # parse existing file from parent directory
    ini.read(iniFileName)
    return ini;

