#!../env/bin/python

from aocd import get_data
import math

input = get_data(day=1, year=2024)

input = input.split('\n')

left = []
right = []
for a in input:
    s = a.split('   ')
    left.append(s[0])
    right.append(s[1])

left.sort()
right.sort()

sum = 0
for i in range(len(left)):
    sum += abs(int(left[i])-int(right[i]))

print('Part 1: '+str(sum))

sum = 0
for l in left:
    for r in right:
        if int(l) == int(r):
            sum += int(l)

print('Part 2: '+str(sum))
