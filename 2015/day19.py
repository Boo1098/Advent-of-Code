#!../env/bin/python

from aocd import get_data
import copy
import itertools

input = get_data(day=19, year=2015)

# Sample input
# input = '''H => HO
# H => OH
# O => HH
#
# HOH'''

lines = input.split('\n')
input_string = lines[-1]
lines = [l.split(' => ') for l in lines[:-2]]


possible = []
for i in range(len(input_string)):
    one_char = input_string[i]
    two_char = None
    if i < len(input_string)-1:
        two_char = input_string[i:i+2]
    for j in range(len(lines)):
        if one_char == lines[j][0] or (two_char is not None and two_char == lines[j][0]):
            new_string = input_string[:i]+lines[j][1] + \
                input_string[i+len(lines[j][0]):]
            possible.append(new_string)

unique_possible = list(set(possible))
print(len(unique_possible))

# Greedy depth first search. This technically is not always the optimal soltuion but it works here
def dfs(working_string, subs, depth=0):
    if working_string == 'e':
        return depth
    for i in range(len(working_string)):
        for sub in subs:
            if sub[1] in working_string:
                new_string = working_string.replace(sub[1], sub[0], 1)
                new_depth = dfs(new_string, subs, depth+1)
                if new_depth != -1:
                    return new_depth
    return -1


lines = sorted(lines, key=lambda x: -len(x[1]))
print(dfs(input_string, lines))
