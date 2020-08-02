from numpy import *
import sys
import pylab


func =sys.argv[1]
point = tuple(map(float, [sys.argv[2], sys.argv[3]]))
point_of_dir = tuple(map(float, [sys.argv[4], sys.argv[5]]))

grad = tuple(map(float, (sys.argv[6][1:-1].split(';'))))

print([point[0],grad[0] + point[0]], [point[1], grad[1] + point[1]])


x, y = mgrid[-20*pi:20*pi:500j, -20*pi:20*pi:500j]


z = eval(func)

if __name__ == '__main__':
    cs = pylab.contour(x, y, z, 20)
    # pylab.clabel(cs)


pylab.plot([point[0],point_of_dir[0]], [point[1],point_of_dir[1]], color = "black")
pylab.plot([point[0],grad[0] + point[0]], [point[1], grad[1] + point[1]] , color = "red")
pylab.show()
