import numpy as np


def sigmoid(z):
    return 1.0 / (1.0 + np.exp(-z))


class Network(object):
    # constructor for neuron:
    #   sizes - list of no. of neuron in every layer
    def __init__(self, sizes):
        self.num_layers = len(sizes)
        self.sizes = sizes
        self.biases = [np.random.Generator.standard_normal(y, 1) for y in sizes[1:]]
        self.weights = [np.random.Generator.standard_normal(y, x)
                        for x, y in zip(sizes[:-1], sizes[1:])]

    # count output in following layer
    def feedforward(self, a):
        for b, w in zip(self.biases, self.weights):
            a = sigmoid(np.dot(w, a) + b)
        return a

    def SGD(self, training_data, epochs, mini_batch_size, eta, test_data=None):
        if test_data:
            n_test = len(test_data)
        n = len(training_data)

        for j in np.xrange(epochs):
            np.random.shuffle(training_data)
            mini_batches = [
                training_data[k:k + mini_batch_size]
                for k in np.xrange(0, n, mini_batch_size)]
            for mini_batch in mini_batches:
                self.update_mini_batch(mini_batch, eta)
            if test_data:
                print("Epoch {0}: {1} / {2}".format(j, self.evaluate(test_data), n_test))
            else:
                print("Epoch {0} complete".format(j))
