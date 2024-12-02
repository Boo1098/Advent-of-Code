#!../env/bin/python

from aocd import get_data
import copy
import itertools

input = get_data(day=16, year=2015)
input = input.split('\n')
lines = [i.split(': ',1) for i in input]

things_i_know = '''children: 3
cats: 7
samoyeds: 2
pomeranians: 3
akitas: 0
vizslas: 0
goldfish: 5
trees: 3
cars: 2
perfumes: 1'''

known = dict()
for l in things_i_know.split('\n'):
    prop = l.split(': ')
    known[prop[0]]=int(prop[1])

sues = []
for l in lines:
    sue = dict()
    props = l[1].split(', ')
    for prop in props:
        p = prop.split(': ')
        sue[p[0]]=int(p[1])
    sues.append(sue)

for s in range(len(sues)):
    sue = sues[s]
    possible = True
    for p in sue:
        if known[p] != sue[p]:
            possible = False
    if possible:
        print(s+1)

    
for s in range(len(sues)):
    sue = sues[s]
    possible = True
    for p in sue:
        if p in ['cats', 'trees']:
            if known[p] >= sue[p]:
                possible = False
        elif p in ['pomeranians', 'goldfish']:
            if known[p] <= sue[p]:
                possible = False
        elif known[p] != sue[p]:
            possible = False
    if possible:
        print(s+1)
