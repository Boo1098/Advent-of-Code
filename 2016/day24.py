#!../env/bin/python

from aocd import get_data
import copy
import itertools

input = get_data(day=24, year=2016)
#input = '''###########
#0.1.....2#
#.#######.#
#4.......3#
###########'''
input = input.split('\n')
digits = dict()
max_x = len(input)
max_y = len(input[0])
for x in range(len(input)):
    for y in range(len(input[x])):
        digit = None
        try:
            digit = int(input[x][y])
        except ValueError:
            digit = None
        if digit is not None and digit not in digits:
            digits[digit] = [x, y]


def get_space(coords, map=input):
    return map[coords[0]][coords[1]]


def calc_distance(a, b, heads=[], visited=[], count=0, map=input):
    if len(heads) == 0:
        heads.append(a)
    else:
        count += 1
    for head in heads:
        if get_space(head) == get_space(b):
            return count

    new_heads = []
    for head in heads:
        new_steps = [[head[0]+1, head[1]],
                     [head[0]-1, head[1]],
                     [head[0], head[1]+1],
                     [head[0], head[1]-1]]
        for step in new_steps:
            if get_space(step) != '#' and step not in new_heads and step not in visited:
                new_heads.append(step)
    visited = visited + new_heads

    return calc_distance(a, b, new_heads, visited, count)


permutations = itertools.permutations(digits.keys(), 2)

distances = dict()
for p in permutations:
    distances[p[0]*10+p[1]] = calc_distance(digits[p[0]], digits[p[1]], [], [], 0)

permutations = list(itertools.permutations(range(len(digits.keys()))[1:]))
min = 9e9
min_perm = ''
sum = 0
for p in permutations:
    sum = distances[p[0]]
    for i in range(len(p)-1):
        sum += distances[p[i]*10+p[i+1]]
    if sum < min:
        min = sum
        min_perm = p

print('Part 1')
print(min)
print(min_perm)
print()

permutations = list(itertools.permutations(range(len(digits.keys()))[1:]))
min = 9e9
min_perm = ''
sum = 0
for p in permutations:
    sum = distances[p[0]]
    for i in range(len(p)-1):
        sum += distances[p[i]*10+p[i+1]]
    sum += distances[p[-1]*10]
    if sum < min:
        min = sum
        min_perm = p

print('Part 2')
print(min)
print(min_perm)


