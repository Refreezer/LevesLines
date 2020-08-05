from numpy import *
import sys
import pylab

func =sys.argv[1]


x, y = mgrid[-20*pi:20*pi:500j, -20*pi:20*pi:500j]

try:
    z = eval(func)
except NameError as e:
    print('IllegalFunction', file=sys.stderr)
    exit(1)

if __name__ == '__main__':
    cs = pylab.contour(x, y, z, levels= 20)
    # pylab.clabel(cs)


pylab.show()


