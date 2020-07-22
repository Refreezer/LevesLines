from sympy import diff,symbols,sin,cos,asin,acos,atan,tan,sqrt,log
import math
import sys
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


for entrance in re.findall(r'(\d|\))\w',func) + re.findall(r'\w\d', func):
	func = func[:func.index(entrance) + 1] + "*"+func[func.index(entrance) + 1:]

print(func)

x,y = symbols(sys.argv[2] + " "  + sys.argv[3])

print (diff(eval(func),eval(sys.argv[2])))