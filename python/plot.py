import re
import sys
import numpy as np
from math import *

func = sys.argv[1]
func = re.sub(r'\^', "**", func)
func = re.sub(r' ', "", func)
func = re.sub(r'\*\*', "#", func)
func = re.sub(r'\*', "**", func)
func = re.sub(r"\){1}?", ")*", func)
func = re.sub(r'\*#', "#", func)
func = re.sub(r'\*\)', ')', func)
func = re.sub(r'\*{3}', "*", func)
func = re.sub(r'#', " ** ", func)
func = re.sub(r'(\*$|\s)', "", func)
func = re.sub(r'ctg', "1/tan", func)


for entrance in re.findall(r'(\d|\))\w',func) + re.findall(r'\w\d', func):
	func = func[:func.index(entrance) + 1] + "*"+func[func.index(entrance) + 1:]


for entrance in re.findall(r'sqrt\(\S+\)', func):
    func = func[:func.index(entrance) + len(entrance) + 1] + "**(0.5)"+func[func.index(entrance) + len(entrance) + 1:]

re.sub(r"sqrt", "", func)

xstart = float(sys.argv[2])
xend = float(sys.argv[3])
steps = float(sys.argv[4])


x_vals = np.arange(xstart, xend, (xend-xstart) / steps)
for x in x_vals:
	y = eval(func)
	print(y)
