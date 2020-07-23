from numpy import *
import matplotlib.pyplot as plt
import sys
import random

func = sys.argv[1]



print(func)

x, y = mgrid[-3*pi:3*pi:100j, -3*pi:3*pi:300j]


z = eval(func)

fig, ax = plt.subplots()

ax.contour(z, levels = 50)

fig.set_figwidth(9)
fig.set_figheight(9)
name = str("images\\" + str(random.randint(1,100)) + ".png")
plt.savefig(name)
print(name)
