from numpy import *
import sys
import pylab
import re

func =sys.argv[1]
func = re.sub(r'atan', 'arctan', func)
func = re.sub(r'asin', 'arcsin', func)
func = re.sub(r'acos', 'arccos', func)
func = re.sub(r'asin', 'arcsin', func)

x, y = mgrid[-20*pi:20*pi:500j, -20*pi:20*pi:500j]

try:
    z = eval(func)
except (NameError, SyntaxError) as e:
    print('IllegalFunction', file=sys.stderr)
    exit(1)

if __name__ == '__main__':
    cs = pylab.contour(x, y, z, levels= 20)
    # pylab.clabel(cs)


pylab.show()


