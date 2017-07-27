'''
Created on Feb 6, 2017

@author: Youngchan Noh
1. This is the main module running all test cases
'''
##### Import area
from SrcCode.TestCases.Test_HB import HbTest
#from SrcCode.TestCases.Test_HB2 import HbTest2

import unittest

print(" I am at Test_Main")


#### Running test cases
if __name__ == '__main__':
    unittest.main()

    # The following is needed when there are multiple modules with classes
    #Add classes with comma
    '''
    print(" I am at main-start")


    test_classes_to_run = [HbTest, HbTest2]
    
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




