import os
import matplotlib.pyplot as plt
import numpy as np

# ./stats/4x4_07_00194_bfs_drlu_stats.txt
# 7
# 260
# 543
# 7
# 29

stats_dir = '/home/firaanki/IS_4sem/SISE/stats'


def get_file_names(directory, pattern):
    file_names = []
    for filename in os.listdir(directory):
        if os.path.isfile(os.path.join(directory, filename)) and pattern in filename:
            file_names.append(filename)
    return sorted(file_names)


def get_stats_decimal(pattern, file_orders, mode_count):
    stats = get_file_names(stats_dir, pattern)
    stats_by_orders = [[] for _ in range(mode_count)]

    for file in stats:
        for i in range(mode_count):
            if file_orders[i] in file:
                stats_by_orders[i].append(file)
                continue

    stats_by_orders_decimal = [[[] for _ in range(len(stats_by_orders[i]))] for i in range(mode_count)]

    for i in range(mode_count):  # for every order
        for j in range(len(stats_by_orders[i])):  # for every file in given order
            with open(os.path.join(stats_dir, stats_by_orders[i][j]), 'r') as my_file:
                lines = my_file.readlines()
                stats_by_orders_decimal[i][j] = [int(line.strip()) for line in lines]

    return stats_by_orders_decimal


def count_avg(stats, mode_count):
    stats_avg = [[[0] * 5 for _ in range(7)] for _ in range(mode_count)]

    for i in range(mode_count):  # for every order
        count = [0] * 7  # list keeping added operation count
        for sublist in stats[i]:  # for every record
            depth_index = sublist[0] - 1
            for j in range(1, 5):
                stats_avg[i][depth_index][0] = sublist[0]
                stats_avg[i][depth_index][j] += sublist[j]

            count[depth_index] += 1

        for j in range(len(stats_avg[i])):
            for k in range(1, 5):
                stats_avg[i][j][k] /= count[j]

    return stats_avg


def plot_graphs(data, index, y_label, mode_count, plot_title, barWidth, legends):
    colors = ['red', 'gray', 'brown', 'yellow', 'blue', 'green', 'violet', 'pink']

    for _ in range(7):
        br = np.arange(len(data[0]))

        for order in range(mode_count):
            work_data = []
            for j in range(7):
                work_data.append(data[order][j][index])

            plt.bar(br, work_data, color=colors[order], width=barWidth, label=legends[order])
            br = [x + barWidth for x in br]
        br.clear()

    handles, labels = plt.gca().get_legend_handles_labels()

    unique_labels = []
    unique_handles = []
    for i, label in enumerate(labels):
        if label not in unique_labels:
            unique_labels.append(label)
            unique_handles.append(handles[i])

    plt.xlabel('Głębokość')
    plt.ylabel(y_label)
    plt.xticks([r + barWidth for r in range(len(data[0]))],
               ['1', '2', '3', '4', '5', '6', '7'])
    plt.title(plot_title)
    plt.legend(unique_handles, unique_labels)
    plt.tight_layout()
    plt.show()


orders = ['RDUL', 'RDLU', 'DRUL', 'DRLU', 'LUDR', 'LURD', 'ULDR', 'ULRD']
kryterium = ['Długość znalezionego rozwiązania', 'Liczba stanów odwiedzonych',
             'Liczba stanów przetworzonych', 'Maksymalna osiągnięta głębokość rekursji',
             'Czas trwania procesu obliczeniowego']
file_orders_bfs = ['rdul', 'rdlu', 'drul', 'drlu', 'ludr', 'lurd', 'uldr', 'ulrd']
file_orders_astar = ['manh', 'hamm']

legends_astar = ['Manhattan', 'Hamming']


def function(strategy, file_orders, mode_count, plot_title, barWidth, legends):
    stats = get_stats_decimal(strategy, file_orders, mode_count)
    stats_avg = count_avg(stats, mode_count)
    for value in range(0, 5):
        plot_graphs(stats_avg, value, kryterium[value], mode_count, plot_title, barWidth, legends)


# function('bfs', file_orders_bfs, 8, 'BFS', 0.1, orders)
# function('dfs', file_orders_bfs, 8, 'DFS', 0.1, orders)
function('astar', file_orders_astar, 2, 'A*', 0.2, legends_astar)
