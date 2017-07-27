'''
Created on Feb 6, 2017

@author: Youngchan Noh
1. This is the main module running all test cases
'''
##### Import area
import unittest
from Test_Cases.TestCases_AIC.Test_AIC_Basic import Test_AIC_BasicScenarios
from Test_Cases.TestCases_API.Test_API_Basic import Test_API_Class



#### Running test cases
if __name__ == '__main__':
    unittest.main()

    '''
    print(" I am at main-start")

    test_classes_to_run = [Test_AIC_BasicScenarios,Test_API_Class]

    loader = unittest.TestLoader()
    suites_list = []
    for test_class in test_classes_to_run:
        suite = loader.loadTestsFromTestCase(test_class)
        suites_list.append(suite)

    big_suite = unittest.TestSuite(suites_list)
    runner = unittest.TextTestRunner()
    runner.run(big_suite)

    print(" I am at main-end")
    '''

