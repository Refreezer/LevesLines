import sys
from math import *


try:
    x = float(sys.argv[2])
    y = float(sys.argv[3])
except ValueError as e:
    print('IllegalPoint', file=sys.stderr)

try:
    print(eval(sys.argv[1]))
except NameError as e:
    print('IllegalFunction', file=sys.stderr)
