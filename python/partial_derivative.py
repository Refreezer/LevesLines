from sympy import *
import sys


func = sys.argv[1]


print(func)

x,y = symbols(sys.argv[2] + " "  + sys.argv[3])

print (diff(eval(func),eval(sys.argv[2])))