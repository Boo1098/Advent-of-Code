#!../env/bin/python

from aocd import get_data
import math

input = get_data(day=9, year=2015)

# input = '''London to Dublin = 464
# London to Belfast = 518
# Dublin to Belfast = 141'''

input = input.split('\n')

lines = [i.split(' ') for i in input]

places = []
for l in lines:
    if l[0] not in places:
        places.append(l[0])
    if l[2] not in places:
        places.append(l[2])

min = 9e9


def traverse(place, places_visited, length):
    np = places_visited.copy()
    np.append(place)
    if len(np) == len(places):
        return length
    min_path = 9e9
    for l in lines:
        if l[0] == place:
            if l[2] not in places_visited:
                t = traverse(l[2], np, length+int(l[4]))
                if t < min_path:
                    min_path = t
        if l[2] == place:
            if l[0] not in places_visited:
                t = traverse(l[0], np, length+int(l[4]))
                if t < min_path:
                    min_path = t
    return min_path


for place in places:
    test = traverse(place, [], 0)
    if test < min:
        min = test

print("Part 1: "+str(min))

max = 0


def traverse_max(place, places_visited, length):
    np = places_visited.copy()
    np.append(place)
    if len(np) == len(places):
        return length
    max_path = 0
    for l in lines:
        if l[0] == place:
            if l[2] not in places_visited:
                t = traverse_max(l[2], np, length+int(l[4]))
                if t > max_path:
                    max_path = t
        if l[2] == place:
            if l[0] not in places_visited:
                t = traverse_max(l[0], np, length+int(l[4]))
                if t > max_path:
                    max_path = t
    return max_path


for place in places:
    test = traverse_max(place, [], 0)
    if test > max:
        max = test

print("Part 2: "+str(max))
