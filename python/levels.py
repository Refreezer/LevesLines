from numpy import *
import sys
import random
import pylab

func =sys.argv[1]



x, y = mgrid[-3*pi:3*pi:100j, -3*pi:3*pi:300j]

z = eval(func)

if __name__ == '__main__':
    cs = pylab.contour(x, y, z, 20)
    # pylab.clabel(cs)



name = str("images\\" + str(random.randint(1,100)) + ".png")
pylab.savefig(name)
print(name)
# следующая строка вызывает окно pylab
pylab.show()
