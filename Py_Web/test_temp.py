from selenium import webdriver
from Base_Objects.Utility import readINI, wait
from selenium import webdriver
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from germanium.static import *
from time import sleep

'''
driver = webdriver.Chrome()
#password = driver.find_element_by_xpath(".//*[@id='username']")

from germanium.static import *
from time import sleep

inip = readINI("Config_Files/testProperty.ini")
driver = webdriver.Chrome()
driver.get('https://cc.shoretel.com/ecc')
driver.find_element_by_id("username")

#usernameTxtBox = driver.find_element_by_id('username')
#passwordTxtBox = driver.find_element_by_id('password')
#submitBtn = driver.find_element_by_id('submitBtn')

usernameTxtBox = driver.find_element_by_css_selector('#username')
passwordTxtBox = driver.find_element_by_css_selector('#password')
submitBtn = driver.find_element_by_css_selector('#submitBtn')

usernameTxtBox.clear()
usernameTxtBox.send_keys("young2@yc2.com")

passwordTxtBox.clear()
passwordTxtBox.send_keys("Shoreadmin1")

submitBtn.click()
wait(5)
driver.maximize_window()
wait(2)


#releaseButton  = WebDriverWait(driver, 10).until(EC._element_if_visible((".//*[@id='header']/div/ng-include/div[3]/div[4]/button[1]")))
releaseButton = driver.find_element_by_xpath(".//*[@id='header']/div/ng-include/div[3]/div[4]/button[1]")

#releaseButton = driver.find_element_by_css_selector('.btn.title-bg')
print(" I am here " +releaseButton.text)
wait(4)
releaseButton.click()

'''


#open_browser('chrome')
#go_to('https://cc.shoretel.com/ecc')
#close_browser()
#sleep(5)


open_browser('ff')

go_to('https://cc.shoretel.com/ecc')

click(XPath(".//*[@id='username']"))
type_keys('<ctrl-a>')
type_keys('young2@yc2.com')
sleep(2)
click(XPath(".//*[@id='password']"))
type_keys('<ctrl-a>')
type_keys('Shoreadmin1<enter>')

get_germanium().wait_for_page_to_load()
sleep(5)

click(XPath(".//*[@id='header']/div/ng-include/div[3]/div[4]/button[1]"))
