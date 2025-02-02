import matplotlib.pyplot as plt
import numpy as np
import scipy.optimize as opt

n_values = np.array([100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                     1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000])

f_values = np.array([0.293217134, 0.980364513, 1.896155119, 3.440258312, 4.587092113,
                     7.61340332, 9.473242426, 13.47536521, 15.92954822, 19.56992645,
                     25.1285409, 30.26942282, 37.17220469, 41.45215893, 46.1513907,
                     53.48855033, 63.18006034, 72.70423965, 70.6115078, 87.23112741])


def improved_model(n, A, B):
    return A * n * np.emath.logn(n / (n - 1), B * n)


A_guess = max(f_values) / max(n_values) ** 2
B_guess = 1 / np.mean(n_values)

initial_guess = [A_guess, B_guess]
bounds = ([0, 0], [np.inf, np.inf])

try:
    popt, _ = opt.curve_fit(improved_model, n_values, f_values, p0=initial_guess, bounds=bounds, maxfev=1000)
    A_est, B_est = popt
    print(f"Lepsze dopasowanie: A = {A_est:.25f}, B = {B_est:.0f}")

    plt.scatter(n_values, f_values, label="Dane rzeczywiste", color="red")
    plt.plot(n_values, improved_model(n_values, A_est, B_est), label="Nowa dopasowana funkcja", color="blue")
    plt.xlabel("n")
    plt.ylabel("f(n)")
    plt.legend()
    plt.title("Poprawione dopasowanie funkcji do danych")
    plt.show()
except RuntimeError as e:
    print(f"Błąd dopasowania: {e}")
