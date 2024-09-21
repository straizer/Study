import matplotlib.pyplot as plt
import numpy as np

def main() -> None:
    f = lambda x: np.arctan(x) / np.tan(x)
    df = lambda x: 1 / np.tan(x) / (np.square(x) + 1) - np.arctan(x) / np.square(np.sin(x))
    start_a = 1
    start_b = 2

    fig, ax = plt.subplots()

    bisections_steps = process(ax, bisection, f, start_a, start_b)
    secant_steps     = process(ax, secant, f, start_a, start_b)
    Newton_steps     = process(ax, Newton, f, df, start_a)

    max_steps = max(len(bisections_steps), len(secant_steps), len(Newton_steps))
    x_axis = np.arange(0, max_steps, 5)
    ax.set(xlabel='iterations', ylabel='log10|f(x_i)|', title='plot', xticks=x_axis)
    ax.grid()
    fig.savefig("plot.png")
    fig.show()

def bisection(f, a, b, accepted_error=1e-14):
    current_error = np.inf
    steps = []
    while current_error > accepted_error:
        x = (a + b) / 2
        f_x = f(x)
        if np.sign(f_x) == np.sign(f(a)):
            a = x
        else:
            b = x
        current_error = np.abs(b - a)
        steps.append(x)
    return steps[-1], steps

def secant(f, a, b, accepted_error=1e-14):
    current_error = np.inf
    steps = []
    while current_error > accepted_error:
        f_a = f(a)
        f_b = f(b)
        x = (a * f_b - b * f_a) / (f_b - f_a)
        f_x = f(x)
        if np.sign(f_x) == np.sign(f_a):
            a = x
        else:
            b = x
        current_error = np.abs(f_x)
        steps.append(x)
    return steps[-1], steps

def Newton(f, df, a, accepted_error=1e-14):
    current_error = np.inf
    steps = []
    while current_error > accepted_error:
        a = a - f(a) / df(a)
        current_error = np.abs(f(a))
        steps.append(a)
    return steps[-1], steps

def process(axes, method, *args):
    result, steps = method(*args)
    print(method.__name__, "in", len(steps), "iterations:", result)
    print(steps)

    x_axis = np.arange(1, len(steps) + 1, 1)
    y_axis = np.log10(np.abs(args[0](steps)))
    axes.plot(x_axis, y_axis)

    return steps

if __name__ == "__main__":
    main()