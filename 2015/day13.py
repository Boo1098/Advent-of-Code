#!../env/bin/python

from aocd import get_data

input = get_data(day=13, year=2015)

input = input.split('\n')

lines = [i.split(' ') for i in input]

people = []
for l in lines:
    if l[0] not in people:
        people.append(l[0])

max = 0


def calc_happiness(p1, p2):
    sum = 0
    for l in lines:
        if p1 == l[0] and p2 == l[10][:-1]:
            if l[2] == 'gain':
                sum += int(l[3])
            else:
                sum -= int(l[3])
        if p2 == l[0] and p1 == l[10][:-1]:
            if l[2] == 'gain':
                sum += int(l[3])
            else:
                sum -= int(l[3])
    return sum


def traverse(person, people_seated, happiness):
    np = people_seated.copy()
    np.append(person)
    if len(np) == len(people):
        #  Add remaining happiness to close the loop
        happiness += calc_happiness(person, people_seated[0])
        return happiness
    max_happiness = 0
    for p in people:
        if p not in np:
            t = traverse(p, np, happiness+calc_happiness(p, person))
            if t > max_happiness:
                max_happiness = t
    return max_happiness


for person in people:
    test = traverse(person, [], 0)
    if test > max:
        max = test

print("Part 1: "+str(max))

# Happy accident, I ignore anyone with no data and give 0 happiness for them already
people.append('Me')
max = 0

for person in people:
    test = traverse(person, [], 0)
    if test > max:
        max = test

print("Part 2: "+str(max))
