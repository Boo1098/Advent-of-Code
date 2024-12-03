#!../env/bin/python

from aocd import get_data
import math
import itertools
import re
import os

input = get_data(day=3, year=2024)
# Add a do on the end in case it ends with don't
input = input + 'do()'
# Remove new lines because I don't know how regex works with newlines lmao
input = input.replace('\n', 'f' )

lines = input.split('\n')

mul_pattern = r'mul\((\d{1,3}),(\d{1,3})\)'
dont_pattern = r'don\'t\(\).*?do\(\)'
# Sub with a letter in case of cross line mul action
new=re.sub(dont_pattern,'f',input)


# Part 1
matches = re.finditer(mul_pattern,input)
sum = 0
for match in matches:
    sum += int(match.group(1))*int(match.group(2))
print(sum)

# Part 2
matches = re.finditer(mul_pattern,new)
sum = 0
for match in matches:
    sum += int(match.group(1))*int(match.group(2))
print(sum)
