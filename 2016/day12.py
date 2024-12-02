#!../env/bin/python

from aocd import get_data
import copy
import itertools

input = get_data(day=12, year=2016)
input = input.split('\n')
lines = [l.split(' ') for l in input]


def is_int(a):
    try:
        int(a)
        return True
    except ValueError:
        return False


def get_value(a, registers):
    if is_int(a):
        return int(a)
    elif a in registers:
        return registers[a]
    else:
        registers[a] = 0
        return 0


def run(start=0, registers=dict(), code=lines):
    while start < len(code):
        current_line = code[start]
        command = current_line[0]

        if command == 'cpy':
            registers[current_line[2]] = get_value(current_line[1], registers)
            start += 1

        if command == 'inc':
            registers[current_line[1]] += 1
            start += 1

        if command == 'dec':
            registers[current_line[1]] -= 1
            start += 1

        if command == 'jnz':
            if get_value(current_line[1], registers) != 0:
                start += get_value(current_line[2], registers)
            else:
                start += 1
    return registers


print('Part 1: '+str(run()['a']))
print('Part 2: '+str(run(0, {'c': 1})['a']))
