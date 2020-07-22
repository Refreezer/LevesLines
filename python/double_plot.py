import re
import sys

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

func = sys.argv[1]
xstart = float(sys.argv[2])
xend = float(sys.argv[3])
xstart = float(sys.argv[4])
xend = float(sys.argv[5])
steps = float(sys.argv[6])

x_vals = list(range(xstart, xend, (xstart- xend)/ steps))
y_vals = list(range(ystart, yend, (ystart- yend)/ steps))


for x, y in zip(x_vals,y_vals):
	print(eval(func))