#!../env/bin/python

from aocd import get_data
import math
import itertools

input = get_data(day=2, year=2024)

lines = input.split('\n')
lines = [l.split(' ') for l in lines]
