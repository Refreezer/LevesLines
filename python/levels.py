from numpy import *
import sys
import pylab

func =sys.argv[1]

x, y = mgrid[-20*pi:20*pi:500j, -20*pi:20*pi:500j]


z = eval(func)


if __name__ == '__main__':
    cs = pylab.contour(x, y, z, 20)
    # pylab.clabel(cs)


pylab.show()
