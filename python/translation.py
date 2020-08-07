import re
import sys

func = sys.argv[1]

#заменяем ^ на ** - возведение в степень
func = re.sub(r'\^', "**", func)
#удаляем пробелы
func = re.sub(r' ', "", func)
func = re.sub(r'\*\*', "#", func)
func = re.sub(r'\*', "**", func)
func = re.sub(r"\){1}?", ")*", func)
func = re.sub(r'\*#', "#", func)
func = re.sub(r'\*{2,3}', "*", func)
func = re.sub(r'#', " ** ", func)
func = re.sub(r'(\*$|\s)', "", func)
func = re.sub(r'\*/', "/", func)
func = re.sub(r'\*\+', "+", func)
func = re.sub(r'\*-', "-", func)


for entrance in re.findall(r'log\(.+\s?,', func):
    func = func[:func.index(entrance) + len(entrance) - 1] + ")/log(" + func[func.index(entrance) + len(entrance):]

#автоматически расставляеем звёздочки между числом и x|y и закрывающейся скобкой и x|y
for entrance in re.findall(r'[0-9\)][a-zA-Z\(]',func) + re.findall(r'[xy\)][0-9\(]', func):
    func = func[:func.index(entrance) + 1] + "*"+func[func.index(entrance) + 1:]

#заменяем sqrt на возведение в степень 0.5
for entrance in re.findall(r'sqrt\(\S+\)', func):
    func = func[:func.index(entrance) + len(entrance) + 1] + "**(0.5)"+func[func.index(entrance) + len(entrance) + 1:]

#заменяем все арккотангенсы, т.к. питон не работает с арккотангенсами
for entrance in re.findall(r'actg\(\S+?\)', func):
    new_entrance = func[func.index(entrance):func.index(entrance) + len(entrance)]
    new_entrance = re.sub(r'actg', "pi/2 - atan", new_entrance)
    func = func[:func.index(entrance)] + "("+ new_entrance + ")" + func[func.index(entrance) + len(entrance):]

func = re.sub(r'\*[)]', ')', func)
func = re.sub(r'ctg', "1/tan", func)


print (func)