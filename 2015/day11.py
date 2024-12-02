#!../env/bin/python

from aocd import get_data
import math

input = get_data(day=11, year=2015)

input = input.split('\n')[0]

print(input)


def is_valid(password):
    rule1 = False
    rule2 = True
    rule3 = False

    for i in range(len(password)-2):
        if chr(ord(password[i+2])-2) == chr(ord(password[i+1])-1) == chr(ord(password[i])):
            rule1 = True

    if 'i' in password:
        rule2 = False
    if 'o' in password:
        rule2 = False
    if 'l' in password:
        rule2 = False

    pair_count = 0
    skip = False
    for i in range(len(password)-1):
        if not skip:
            if password[i] == password[i+1]:
                pair_count += 1
                skip = True
        else:
            skip = False
    if pair_count >= 2:
        rule3 = True

    return rule1 and rule2 and rule3


def increment_pass(password):
    if password[-1] != 'z':
        password = password[:-1]+chr(ord(password[-1])+1)
        return password
    else:
        return increment_pass(password[:-1])+'a'


current_pass = str(input)
while not is_valid(current_pass):
    current_pass = increment_pass(current_pass)

print("Part 1: "+str(current_pass))

current_pass = increment_pass(current_pass)
while not is_valid(current_pass):
    current_pass = increment_pass(current_pass)

print("Part 2: "+str(current_pass))
