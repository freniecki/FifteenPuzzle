import os
import re


def get_file_names(directory, pattern):
    file_names = []
    for filename in os.listdir(directory):
        if os.path.isfile(os.path.join(directory, filename)) and pattern in filename:
            file_names.append(filename)
    return sorted(file_names)


def find_impostor(files_list):
    pattern = re.compile("^[a-zA-Z0-9]+_[0-9]+_[0-9]]+_[a-z]+_[a-z]+_[a-z]+.txt$")
    for file in files_list:
        pattern.match(file)
        count = 0
        for file_check in files_list:
            if file_check in file:
                count += 1
                if count > 1:
                    print(file_check)


stats_dir = '/home/firaanki/IS_4sem/SISE/stats'
results_dir = '/home/firaanki/IS_4sem/SISE/results'
strategies = ['bfs', 'dfs', 'astar']

for strategy in strategies:
    stats_files = get_file_names(stats_dir, strategy)
    results_files = get_file_names(results_dir, strategy)
    find_impostor(stats_files)
    find_impostor(results_files)
    if len(stats_files) != len(results_files):
        print(len(stats_files))
        print(len(results_files))
        print(strategy)
    else:
        print('strategy: ' + strategy + ' is git')
