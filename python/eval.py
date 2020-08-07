import sys
from math import *


try:
    x = float(sys.argv[2])
    y = float(sys.argv[3])
except ValueError as e:
    print('IllegalPoint', file=sys.stderr)

try:
    print(eval(sys.argv[1]))
except (NameError, SyntaxError) as e:
    print('IllegalFunction', file=sys.stderr)
except ZeroDivisionError as e:
    print('ZeroDivisionError', file=sys.stderr)
except ValueError as e:
    print('math domain error IllegalFunction', file=sys.stderr)
