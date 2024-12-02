#!../env/bin/python

from aocd import get_data
import copy
import itertools

input = get_data(day=25, year=2016)
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


def resolve_loop(code, reg, registers):
    reg_delta = dict()
    reg_copy = registers.copy()
    reg_copy = run(0, reg_copy, code)
    for k in reg_copy:
        reg_delta[k] = reg_copy[k]-registers[k]
    multiplier = registers[reg]/-reg_delta[reg]
    for k in reg_delta:
        registers[k] += int(reg_delta[k]*multiplier)
    return registers


def run(start=0, registers=dict(), code=lines):
    output_string = ''
    cutoff=22
    while start < len(code) and len(output_string) < cutoff:
        current_line = code[start]
        command = current_line[0]

        if command == 'cpy':
            if not is_int(current_line[2]):
                registers[current_line[2]] = get_value(
                    current_line[1], registers)
            start += 1

        if command == 'inc':
            registers[current_line[1]] += 1
            start += 1

        if command == 'dec':
            registers[current_line[1]] -= 1
            start += 1

        if command == 'jnz':
            if get_value(current_line[1], registers) != 0:
                if not is_int(current_line[1]) and is_int(current_line[2]) and int(current_line[2]) < 0:
                    loop_code = code[(start+int(current_line[2])):start]
                    flag = False
                    for l in loop_code:
                        if l[0] not in ['dec', 'inc', 'jnz', 'cpy']:
                            flag = True
                        if l[0] == 'cpy':
                            for ll in loop_code:
                                if ll[0] not in ['cpy', 'jnz'] and ll[1] == l[1]:
                                    flag = True
                        if l[0] == 'jnz' and is_int(l[2]) and int(l[2])>0:
                            flag = True
                    if flag:
                        start += get_value(current_line[2], registers)
                    else:
                        registers = resolve_loop(
                            loop_code, current_line[1], registers)
                        start += 1
                else:
                    start += get_value(current_line[2], registers)
            else:
                start += 1

        if command == 'tgl':
            i = get_value(current_line[1], registers)
            if 0 <= start+i < len(code):
                line = code[start+i]
                if line[0] == 'inc':
                    line[0] = 'dec'
                elif len(line) == 2:
                    line[0] = 'inc'
                elif line[0] == 'jnz':
                    line[0] = 'cpy'
                elif len(line) == 3:
                    line[0] = 'jnz'
                code[start+i] = line
            start += 1

        if command == 'out':
            output_string = output_string + \
                str(get_value(current_line[1], registers))
            start += 1
    if len(output_string) == cutoff:
        return (output_string)
    return registers


for a in range(2,10000):
    #try:
    output = run(0, {'a': a}, copy.deepcopy(lines))
    #except:
    #    output = 'oop'
    if output.startswith('0101010101010101010101'):
        print(str(a))
        break
    #elif a % 100 == 0:
    #    print(str(a)+' '+output)

# print('Part 2: '+str(run(0, {'a': 12}, copy.deepcopy(lines))['a']))
