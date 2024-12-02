#!../env/bin/python

from aocd import get_data
import copy
import itertools

input = get_data(day=18, year=2015)
#input = '''.#.#.#
#...##.
##....#
#..#...
##.#..#
#####..'''
#input = '''##.#.#
#...##.
##....#
#..#...
##.#..#
#####.#'''
lines = input.split('\n')
max_y = len(lines)
max_x = len(lines[0])


def around_on(x, y, map):
    points_to_check = [[x-1, y], [x+1, y],
                       [x-1, y-1], [x+1, y-1],
                       [x-1, y+1], [x+1, y+1],
                       [x, y-1], [x, y+1]]
    sum = 0
    for p in points_to_check:
        if 0 <= p[0] < max_x and 0 <= p[1] < max_y:
            if map[p[0]][p[1]] == '#':
                sum += 1
    return sum


start = copy.deepcopy(lines)
for i in range(100):
    next_step = [str()]*len(start)
    for x in range(len(start)):
        for y in range(len(start[x])):
            on_around = around_on(x, y, start)
            #print("; "+str(y)+", "+str(x)+": "+str(on_around))
            if start[x][y] == '#' and on_around in [2, 3]:
                next_step[x] = next_step[x] + '#'
            elif start[x][y] == '#':
                next_step[x] = next_step[x] + '.'
            elif start[x][y] == '.' and on_around == 3:
                next_step[x] = next_step[x] + '#'
            elif start[x][y] == '.':
                next_step[x] = next_step[x] + '.'
    start = next_step

sum = 0
for x in range(len(start)):
    for y in range(len(start[x])):
        if start[x][y] == '#':
            sum += 1
print(sum)


start = copy.deepcopy(lines)
for i in range(100):
    next_step = [str()]*len(start)
    start[0] = '#'+start[0][1:-1]+'#'
    start[-1] = '#'+start[-1][1:-1]+'#'
    for x in range(len(start)):
        for y in range(len(start[x])):
            on_around = around_on(x, y, start)
            #print("; "+str(y)+", "+str(x)+": "+str(on_around))
            if start[x][y] == '#' and on_around in [2, 3]:
                next_step[x] = next_step[x] + '#'
            elif start[x][y] == '#':
                next_step[x] = next_step[x] + '.'
            elif start[x][y] == '.' and on_around == 3:
                next_step[x] = next_step[x] + '#'
            elif start[x][y] == '.':
                next_step[x] = next_step[x] + '.'
    start = next_step
    start[0] = '#'+start[0][1:-1]+'#'
    start[-1] = '#'+start[-1][1:-1]+'#'

sum = 0
for x in range(len(start)):
    for y in range(len(start[x])):
        if start[x][y] == '#':
            sum += 1
print(sum)
