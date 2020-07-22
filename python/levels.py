from numpy import *
import matplotlib.pyplot as plt
import sys
import random
import re

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

x, y = mgrid[-3*pi:3*pi:100j, -3*pi:3*pi:300j]
z = eval(func)

fig, ax = plt.subplots()

ax.contour(z, levels = 50)
fig.set_figwidth(9)
fig.set_figheight(9)
name = str("./images/" + str(random.randint(1,100)) + ".png")
plt.savefig(name)
print(name)
