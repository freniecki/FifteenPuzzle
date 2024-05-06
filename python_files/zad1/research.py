import os
import sys

# run .jar for every strategy for every starting state in ~/IS_sem4/SISE/puzzles
command = 'java -jar /home/firaanki/IdeaProjects/SISE/build/libs/SISE-1.0-SNAPSHOT.jar'

strategy = ['bfs', 'dfs', 'astar']
orders = ['RDUL', 'RDLU', 'DRUL', 'DRLU', 'LUDR', 'LURD', 'ULDR', 'ULRD']  # for java usage
file_orders = ['rdul', 'rdlu', 'drul', 'drlu', 'ludr', 'lurd', 'uldr', 'ulrd']  # for file name usage
heuristics = ['manh', 'hamm']
file_sol_name = 'sol.txt'
file_stats_name = 'stats.txt'
puzzles_dir = './puzzles/'
results_dir = './results/'
stats_dir = './stats/'


def get_file_names(directory):
    file_names = []
    for filename in os.listdir(directory):
        if os.path.isfile(os.path.join(directory, filename)):
            file_names.append(filename)
    return sorted(file_names)


directory_path = '/home/firaanki/IS_4sem/SISE/puzzles'
file_names_list = get_file_names(directory_path)


def compute_bfs():
    for file_name in file_names_list:
        for i in range(8):
            draft_bfs = file_name[:-4] + '_bfs_' + file_orders[i] + '_'
            command_bfs = command + ' '
            command_bfs += strategy[0] + ' '
            command_bfs += orders[i] + ' '
            command_bfs += puzzles_dir + file_name + ' '
            command_bfs += results_dir + draft_bfs + file_sol_name + ' '
            command_bfs += stats_dir + draft_bfs + file_stats_name
            print(command_bfs)
            os.system(command_bfs)


def compute_dfs():
    for file_name in file_names_list:
        for i in range(8):  # count of orders
            draft_dfs = file_name[:-4] + '_dfs_' + file_orders[i] + '_'
            command_dfs = command + ' '
            command_dfs += strategy[1] + ' '
            command_dfs += orders[i] + ' '
            command_dfs += puzzles_dir + file_name + ' '
            command_dfs += results_dir + draft_dfs + file_sol_name + ' '
            command_dfs += stats_dir + draft_dfs + file_stats_name
            print(command_dfs)
            os.system(command_dfs)


def compute_astar():
    for file_name in file_names_list:
        for i in range(2):
            draft_astar = file_name[:-4] + '_astar_' + heuristics[i] + '_'
            command_astar = command + ' '
            command_astar += strategy[2] + ' '
            command_astar += heuristics[i] + ' '
            command_astar += puzzles_dir + file_name + ' '
            command_astar += results_dir + draft_astar + file_sol_name + ' '
            command_astar += stats_dir + draft_astar + file_stats_name
            print(command_astar)
            os.system(command_astar)


algorithm = sys.argv[1]

if algorithm == 'bfs':
    compute_bfs()
elif algorithm == 'dfs':
    compute_dfs()
elif algorithm == 'astar':
    compute_astar()
else:
    print('error with parameter')
    exit(-1)
