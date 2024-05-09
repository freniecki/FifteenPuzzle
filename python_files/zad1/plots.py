import os
import matplotlib.pyplot as plt
import numpy as np

stats_dir = '/home/firaanki/IS_4sem/SISE/stats'

orders = ['RDUL', 'RDLU', 'DRUL', 'DRLU', 'LUDR', 'LURD', 'ULDR', 'ULRD']
kryterium = ['Długość znalezionego rozwiązania', 'Liczba stanów odwiedzonych',
             'Liczba stanów przetworzonych', 'Maksymalna osiągnięta głębokość rekursji',
             'Czas trwania procesu obliczeniowego']

file_orders_bfs = ['rdul', 'rdlu', 'drul', 'drlu', 'ludr', 'lurd', 'uldr', 'ulrd']
file_orders_astar = ['manh', 'hamm']

legends_astar = ['Manhattan', 'Hamming']
legend_all = ['BFS', 'DFS', 'A*']

base = '4x4_0'


def get_file_names(directory, pattern):
    file_names = []
    for filename in os.listdir(directory):
        if os.path.isfile(os.path.join(directory, filename)) and pattern in filename:
            file_names.append(filename)
    return sorted(file_names)


def get_stats_decimal(pattern, file_orders):
    order_count = len(file_orders)
    stats = get_file_names(stats_dir, pattern)
    # sorted file names list by order // [order][depth][file_name]
    stats_by_orders = [[[] for _ in range(7)] for _ in range(order_count)]

    for file in stats:
        for order in range(order_count):
            if file_orders[order] in file:
                for depth in range(1, 8):
                    file_pattern = base + str(depth)
                    if file_pattern in file:
                        stats_by_orders[order][depth - 1].append(file)

    # [order][depth][attributes_position][attribute]
    stats_by_orders_decimal = [[[[] for _ in range(len(stats_by_orders[order_index][depth_index]))]
                                for depth_index in range(7)]
                               for order_index in range(order_count)]

    for order in range(order_count):  # for every order
        for depth in range(len(stats_by_orders[order])):  # for every depth
            for file in range(len(stats_by_orders[order][depth])):  # for every file in depth
                with open(os.path.join(stats_dir, stats_by_orders[order][depth][file]), 'r') as my_file:
                    lines = my_file.readlines()
                    stats_by_orders_decimal[order][depth][file] = [int(line.strip()) for line in lines]

    return stats_by_orders_decimal


def get_all_stats_decimal(pattern):
    # list of sorted files
    stats = get_file_names(stats_dir, pattern)
    stats_by_depth = [[] for _ in range(7)]

    for file in stats:
        for i in range(1, 8):
            file_pattern = base + str(i)
            if file_pattern in file:
                stats_by_depth[i - 1].append(file)

    all_stats_decimal = [[[] for _ in range(len(stats_by_depth[i]))] for i in range(7)]

    for i in range(7):
        for j in range(len(stats_by_depth[i])):
            with open(os.path.join(stats_dir, stats_by_depth[i][j]), 'r') as my_file:
                lines = my_file.readlines()
                all_stats_decimal[i][j] = [int(line.strip()) for line in lines]

    return all_stats_decimal


def count_avg(stats):
    # [order][depth][attribute]
    stats_avg = [[[0] * 5 for _ in range(7)] for _ in range(len(stats))]

    for order in range(len(stats)):  # for every order
        count = [0] * 7  # list keeping added operation count
        for depth in range(len(stats[order])):  # for every depth
            for iteration in range(len(stats[order][depth])):  # for every file in depth
                for attribute in range(5):
                    stats_avg[order][depth][attribute] += stats[order][depth][iteration][attribute]

                count[depth] += 1

            for attribute in range(5):
                stats_avg[order][depth][attribute] /= count[depth]

    return stats_avg


def count_avg_all(stats):
    stats_avg_all = [[[0] * 5 for _ in range(7)] for _ in range(3)]
    strategies = ['bfs', 'dfs', 'astar']

    #  avg_order[order][depth][attribute] avg_strategy[depth][attribute]

    for strategy in range(len(strategies)):
        stats_avg = stats[strategy]
        strategy_sum = [[0] * 5 for _ in range(7)]
        for order in range(len(stats_avg)):  # for every order
            for depth in range(7):  # for every depth
                for attribute in range(5):  # for every attribute
                    strategy_sum[depth][attribute] += stats_avg[order][depth][attribute]

            for depth in range(7):
                for attribute in range(5):
                    stats_avg_all[strategy][depth][attribute] = strategy_sum[depth][attribute] / len(stats_avg)

    return stats_avg_all


def plot_graphs(data, index, y_label, plot_title, bar_width, legends):
    colors = ['red', 'gray', 'brown', 'yellow', 'blue', 'green', 'violet', 'pink']

    for _ in range(7):
        br = np.arange(len(data[0]))

        for order in range(len(data)):
            work_data = []
            for j in range(7):
                work_data.append(data[order][j][index])

            plt.bar(br, work_data, color=colors[order], width=bar_width, label=legends[order])
            br = [x + bar_width for x in br]
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
    plt.xticks([r + bar_width for r in range(len(data[0]))],
               ['1', '2', '3', '4', '5', '6', '7'])
    plt.title(plot_title)
    plt.legend(unique_handles, unique_labels)
    plt.tight_layout()
    plt.show()


def run_strategy(strategy, file_orders, plot_title, bar_width, legends):
    stats = get_stats_decimal(strategy, file_orders)
    stats_avg = count_avg(stats)
    # for value in range(0, 5):
    #     plot_graphs(stats_avg, value, kryterium[value], plot_title, bar_width, legends)

    return stats_avg


def run_all(stats):
    stats_avg_all = count_avg_all(stats)
    for value in range(0, 5):
        plot_graphs(stats_avg_all, value, kryterium[value], 'Ogółem', 0.2, legend_all)


def print_shit():
    list = get_stats_decimal('dfs', file_orders_bfs)
    for i in range(8):
        print(list[i])

    print()

    list_d = count_avg(list)
    for i in range(8):
        print(list_d[i])


stats_bfs = run_strategy('bfs', file_orders_bfs,  'BFS', 0.1, orders)
stats_dfs = run_strategy('dfs', file_orders_bfs,  'DFS', 0.1, orders)
stats_astar = run_strategy('astar', file_orders_astar, 'A*', 0.2, legends_astar)

stats_all = [stats_bfs, stats_dfs, stats_astar]

run_all(stats_all)

# list = get_stats_decimal('bfs', file_orders_bfs, 8)
# for i in range(8):
#     print(list[i])

#
# avg_all = count_avg_all(stats_all, 3)
# for i in range(3):
#     print(avg_all[i])
