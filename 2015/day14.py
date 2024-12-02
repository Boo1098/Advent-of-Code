#!../env/bin/python

from aocd import get_data
import copy

input = get_data(day=14, year=2015)
time_run = 2503

input = input.split('\n')

lines = [i.split(' ') for i in input]

state = dict()
for l in lines:
    if l[0] not in state.keys():
        state[l[0]] = {'speed': int(l[3]), 'run_time': int(l[6]), 'rest_time': int(
            l[13]), 'timer': 0, 'run_state': True, 'location': 0, 'points': 0}

def simulate(s):
    for r in s.keys():
        deer = s[r]
        # Increment timer
        deer['timer'] += 1
        # Running?
        if deer['run_state']:
            # Move
            deer['location'] += deer['speed']
            if deer['timer'] >= deer['run_time']:
                deer['run_state'] = bool(not deer['run_state'])
                deer['timer'] = 0
        else:
            if deer['timer'] >= deer['rest_time']:
                deer['run_state'] = bool(not deer['run_state'])
                deer['timer'] = 0
    max = 0
    for r in s.keys():
        if s[r]['location']>max:
            max = s[r]['location']
            max_deer = r
    s[max_deer]['points'] +=1
    return s


current_state = copy.deepcopy(state)
for i in range(time_run):
    current_state = simulate(current_state)

max = 0
for r in current_state.keys():
    if current_state[r]['location'] > max:
        max = current_state[r]['location']

print('Part 1: '+str(max))

max = 0
for r in current_state.keys():
    if current_state[r]['points'] > max:
        max = current_state[r]['points']

print('Part 2: '+str(max))
