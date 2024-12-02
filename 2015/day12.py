#!../env/bin/python

from aocd import get_data
import math
import json

input = get_data(day=12, year=2015)

input = input.split('\n')[0]
# input = '{"a":{"b":4},"c":-1}'

data = json.loads(input)

p1 = 0


def traverse(json_data):
    sum = 0
    if isinstance(json_data, dict):
        for i in json_data.keys():
            if isinstance(i, int):
                sum += i
            else:
                j = json_data[i]
                if isinstance(j, int):
                    sum += j
                else:
                    sum += traverse(j)
    if isinstance(json_data, list):
        for i in json_data:
            if isinstance(i, int):
                sum += i
            else:
                sum += traverse(i)
    return sum


p1 = traverse(data)

print("Part 1: "+str(p1))


def traverse_nored(json_data):
    sum = 0
    dict_sum = 0
    red_flag = False
    if isinstance(json_data, dict):
        for i in json_data.keys():
            if isinstance(i, int):
                dict_sum += i
            else:
                j = json_data[i]
                if isinstance(j, int):
                    dict_sum += j
                elif isinstance(j, str):
                    if j == 'red':
                        red_flag = True
                else:
                    dict_sum += traverse_nored(j)
    if not red_flag:
        sum += dict_sum
    if isinstance(json_data, list):
        for i in json_data:
            if isinstance(i, int):
                sum += i
            else:
                sum += traverse_nored(i)
    return sum


p2 = traverse_nored(data)

print("Part 2: "+str(p2))
