from numpy import *
import sys
import pylab
import re

func = sys.argv[1]
func = re.sub(r'atan', 'arctan', func)
func = re.sub(r'asin', 'arcsin', func)
func = re.sub(r'acos', 'arccos', func)
func = re.sub(r'asin', 'arcsin', func)

try:
    point = tuple(map(float, [sys.argv[2], sys.argv[3]]))
    point_of_dir = tuple(map(float, [sys.argv[4], sys.argv[5]]))
    grad = tuple(map(float, (sys.argv[6][1:-1].split(';'))))
    print([point[0], grad[0] + point[0]], [point[1], grad[1] + point[1]])
except ValueError as e:
    print('IllegalPoint', file=sys.stderr)

x, y = mgrid[-20 * pi:20 * pi:500j, -20 * pi:20 * pi:500j]

try:
    z = eval(func)
except (NameError, SyntaxError) as e:
    print('IllegalFunction', file=sys.stderr)
    exit(1)

if __name__ == '__main__':
    cs = pylab.contour(x, y, z, levels=20)
    # pylab.clabel(cs)

pylab.plot([point[0], point_of_dir[0]], [point[1], point_of_dir[1]], color="black")
pylab.plot([point[0], grad[0] + point[0]], [point[1], grad[1] + point[1]], color="red")
pylab.show()
