import re
import sys

func = sys.argv[1]

func = re.sub(r'\^', "**", func)
func = re.sub(r' ', "", func)
func = re.sub(r'\*\*', "#", func)
func = re.sub(r'\*', "**", func)
func = re.sub(r"\){1}?", ")*", func)
func = re.sub(r'\*#', "#", func)
func = re.sub(r'\*\)', ')', func)
func = re.sub(r'\*{2,3}', "*", func)
func = re.sub(r'#', " ** ", func)
func = re.sub(r'(\*$|\s)', "", func)
func = re.sub(r'\*/', "/", func)
func = re.sub(r'\*\+', "+", func)
func = re.sub(r'\*-', "-", func)


for entrance in re.findall(r'(\d|\))\w',func) + re.findall(r'\w\d', func):
    func = func[:func.index(entrance) + 1] + "*"+func[func.index(entrance) + 1:]

for entrance in re.findall(r'sqrt\(\S+\)', func):
    func = func[:func.index(entrance) + len(entrance) + 1] + "**(0.5)"+func[func.index(entrance) + len(entrance) + 1:]



for entrance in re.findall(r'actg\(\S+?\)', func):
    new_entrance = func[func.index(entrance):func.index(entrance) + len(entrance)]
    new_entrance = re.sub(r'actg', "pi/2 - atan", new_entrance)
    func = func[:func.index(entrance)] + "("+ new_entrance + ")" + func[func.index(entrance) + len(entrance):]

func = re.sub(r'ctg', "1/tan", func)
func = re.sub(r'atan', 'arctan', func)

print (func)