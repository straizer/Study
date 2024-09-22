import matplotlib.pyplot as plt
import numpy as np
import numpy.linalg as nla

np.set_printoptions(precision=6, floatmode='fixed')

def print_array(name, array):
    array_string = str(array).replace('\n', '\n' + ' ' * (len(name) + 3))
    print(name, '=', array_string)

def jacobi(A, B):
    D = np.diag(np.diag(A))
    D_inv = nla.inv(D)
    M = (- D_inv) @ (A - D)
    print_array('    M', M)
    print(f'\t||M|| = {norm_inf(M):.6f}')
    C = D_inv @ B
    x = np.zeros(B.size)
    errors = []
    while True:
        new_x = M @ x + C
        errors.append(error := norm_inf(x - new_x))
        if error <= 1e-7:
            return new_x, np.array(errors)
        x = new_x

def gauss_seidel(A, B):
    L = np.tril(A) - np.diag(np.diag(A))
    DU_inv = nla.inv(A - L)
    M = (- DU_inv) @ L
    print_array('    M', M)
    print(f'\t||M|| = {norm_inf(M):.6f}')
    C = DU_inv @ B
    x = np.zeros(B.size)
    errors = []
    while True:
        new_x = M @ x + C
        errors.append(error := norm_inf(x - new_x))
        if error <= 1e-7:
            return new_x, np.array(errors)
        x = new_x

def norm_inf(a):
    return np.abs(a).sum(axis=len(a.shape)-1).max()

if __name__ == '__main__':
    data = np.loadtxt('data.txt', dtype=np.float64)
    A = data[:-1]
    B = data[-1]

    print('JACOBI')
    x, jacobi_errors = jacobi(A, B)
    jacobi_errors = np.log10(jacobi_errors)
    errors_slope = (jacobi_errors[-1] - jacobi_errors[0]) / jacobi_errors.size
    print(f'\torder of convergence = {errors_slope:.6f}')
    B_check = A @ x
    B_error = B - B_check
    print_array('\tx', x)
    print_array('\tB check', B_check)
    print_array('\tB orgin', B)
    print_array('\tB error', B_error)
    print()

    print('GAUSS-SEIDEL')
    x, gauss_seidel_errors = gauss_seidel(A, B)
    gauss_seidel_errors = np.log10(gauss_seidel_errors)
    errors_slope = (gauss_seidel_errors[-1] - gauss_seidel_errors[0]) / gauss_seidel_errors.size
    print(f'\torder of convergence = {errors_slope:.6f}')
    B_check = A @ x
    B_error = B - B_check
    print_array('\tx', x)
    print_array('\tB check', B_check)
    print_array('\tB orgin', B)
    print_array('\tB error', B_error)
    print()

    fig = plt.figure()
    ax = fig.add_subplot()
    ax.set_xlabel('iterations')
    ax.set_ylabel('log_10(||x_(n+1) - x_n||_inf)')
    ax.set_xlim(0, 16)
    ax.set_ylim(-8, 0)
    ax.plot(range(jacobi_errors.size), jacobi_errors, label = 'Jacobi')
    ax.plot(range(gauss_seidel_errors.size), gauss_seidel_errors, label = 'Gauss-Seidel')
    ax.grid()
    ax.legend()
    ax.set_aspect('equal')
    fig.show()