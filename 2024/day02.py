#!../env/bin/python

from aocd import get_data
import math
import itertools

input = get_data(day=2, year=2024)

lines = input.split('\n')
lines = [l.split(' ') for l in lines]
sum = 0


for l in lines:
    flag_dec = True
    for i in range(len(l)-1):
        if int(l[i])-int(l[i+1]) <=0:
            flag_dec = False
        if int(l[i])-int(l[i+1]) >3:
            flag_dec = False
    flag_asc = True
    for i in range(len(l)-1):
        if int(l[i])-int(l[i+1]) >=0:
            flag_asc = False
        if int(l[i])-int(l[i+1]) <-3:
            flag_asc = False
    safe = flag_asc or flag_dec
    if safe:
        sum +=1



print(sum)

def is_safe(l, depth = 0):
    dec_flag = True
    for i in range(len(l)-1):
        if int(l[i])-int(l[i+1]) <=0:
            if depth ==0:
                ll= l.copy()
                ll.pop(i+1)
                if is_safe(ll,1):
                    return True
            dec_flag = False
        if int(l[i])-int(l[i+1]) >3:
            if depth ==0:
                ll= l.copy()
                ll.pop(i+1)
                if is_safe(ll,1):
                    return True
            dec_flag = False
    asc_flag = True
    for i in range(len(l)-1):
        if int(l[i])-int(l[i+1]) >=0:
            if depth ==0:
                ll= l.copy()
                ll.pop(i+1)
                if is_safe(ll,1):
                    return True
            asc_flag = False
        if int(l[i])-int(l[i+1]) <-3:
            if depth ==0:
                ll= l.copy()
                ll.pop(i+1)
                if is_safe(ll,1):
                    return True
            asc_flag = False
    safe = dec_flag or asc_flag
    if not safe and depth == 0:
        ll = l.copy()
        ll.pop(0)
        return is_safe(ll,1)
    return safe

sum = 0
for l in lines:
    flag_dec = True
    
    if is_safe(l):
        sum +=1



print(sum)

