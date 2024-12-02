#!../env/bin/python

from aocd import get_data
import copy
import itertools

input = get_data(day=15, year=2015)
input = input.split('\n')
lines = [i.split(': ') for i in input]
ingrediants = dict()
props = []

for l in lines:
    properties = dict()
    for p in l[1].split(', '):
        pp=p.split(' ')
        properties[pp[0]]=int(pp[1])
        if pp[0] not in props and pp[0]!='calories':
            props.append(pp[0])
    ingrediants[l[0]]=properties

amount =[0]*len(ingrediants)
combinations = []
for i in range(0,101):
    for j in range(0,101):
        for k in range(0,101):
            for l in range(0,101):
                if i+j+k+l==100:
                    combinations.append([i,j,k,l])
max = 0
max_500_calories = 0
for combo in combinations:
    prop_sum = [0]*len(props)
    calorie_sum = 0
    for i in range(len(ingrediants.keys())):
        for p in range(len(props)):
            prop_sum[p] += ingrediants[list(ingrediants.keys())[i]][props[p]]*combo[i]
        calorie_sum += ingrediants[list(ingrediants.keys())[i]]['calories']*combo[i]
    sum = 1
    for p in prop_sum:
        if p>1:
            sum*=p
        else:
            sum*=0
    if sum>max:
        max=sum
    if sum>max_500_calories and calorie_sum == 500:
        max_500_calories = sum


print(max)
print(max_500_calories)

