from germanium.static import *
from time import sleep
import sys
sys.path.append('my/path/to/module/folder')


def clickXPath(strXPath):
    click(XPath(strXPath))


def selectAll():
    type_keys('<ctrl-a>')


def sleepSec(intWait):
    sleep(intWait)
