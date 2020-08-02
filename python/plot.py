import sys
import numpy as np

func = sys.argv[1]

xstart = float(sys.argv[2])
xend = float(sys.argv[3])
steps = float(sys.argv[4])

x_vals = np.arange(xstart, xend, (xend - xstart) / steps)
for x in x_vals:
    y = eval(func)
    print(y)
