"""
Testing utilities.  Do not modify this file!

Authors: Aaron Cass, Chris Fernandes, Kristina Striegnitz
"""

VERBOSE = True
num_pass = 0
num_fail = 0

def assert_equals(msg, expected, actual):
    """
    Check whether code being tested produces the correct result for a 
    specific test case. Prints a message indicating whether it does.

    Parameters
    ----------
    msg : str
        a message to print at the beginning
    expected
        the correct result
    actual
        the result of the code under test
    """
    global num_pass, num_fail

    if expected == actual:
        if VERBOSE:
            print(msg)
            print("PASS")
            print()
        num_pass += 1
    else:
        print(msg)
        print("**** FAIL")
        print("expected: " + str(expected))
        print("actual: " + str(actual))
        print()
        num_fail += 1

def start_tests(header):
    """
    Initializes summary statistics so we are ready to run tests using
    assert_equals.

    Parameters
    ---------- 
    header : str
        a header to print at the beginning of the tests
    """
    global num_pass, num_fail
    print(header)
    for i in range(0,len(header)):
        print("=",end="")
    print("")
    num_pass = 0
    num_fail = 0

def finish_tests():
    """Prints summary statistics after the tests are complete."""
    print(f"Passed {num_pass:d}/{(num_pass+num_fail):d}")
    print(f"Failed {num_fail:d}/{(num_pass+num_fail):d}")
    print()

def set_verbose(verbose):
    global VERBOSE
    VERBOSE = verbose