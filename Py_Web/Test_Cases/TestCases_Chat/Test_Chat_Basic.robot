*** Settings ***
Library  BuilIn
Library  Selenium2Library



*** variables ***
${BROWSER}  chrome

# Test Variables
${testURL}  http://10.23.175.161:3000/ecc

*** keywords ***
Open WebAgent Page
    Open Browser ${testURL} ${BROWSER}

*** test cases ***
Open WebAgent Page
