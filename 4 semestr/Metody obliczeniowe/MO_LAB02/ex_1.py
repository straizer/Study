import numpy as np

np.set_printoptions(precision=6, floatmode='fixed')

def print_array(name, array):
    array_string = str(array).replace('\n', '\n' + ' ' * (len(name) + 3))
    print(name, '=', array_string)

def lower_upper_decomposition(A):
    n = A.shape[0]
    a = A.copy()
    for k in range(n - 1):
        akk = a[k][k]
        for i in range(k + 1, n):
            aux = a[i][k] / akk if akk else 0
            for j in range(k + 1, n):
                a[i][j] -= a[k][j] * aux
            a[i][k] = aux
    U = np.triu(a)
    L = a - U
    return L, U

def eliminate_forward(L, B):
    n = L.shape[0]
    b = B.copy()
    for k in range(n - 1):
        for i in range(k + 1, n):
            b[i] -= b[k] * L[i][k]
    return b

def substitute_backward(U, Y):
    n = U.shape[0]
    y = Y.copy()
    y[n-1] /= U[n-1][n-1]
    for i in range(n-2, -1, -1):
        s = 0
        for j in range(i+1, n):
            s += U[i][j] * y[j]
        y[i] -= s
        y[i] /= U[i][i]
    return y

if __name__ == '__main__':
    data = np.loadtxt('data.txt', dtype=np.float64)
    A = data[:-1]
    B = data[-1]

    L, U = lower_upper_decomposition(A)
    L += np.eye(A.shape[0])

    print_array('L', L)
    print_array('U', U)
    print(f'{np.allclose(A, L @ U, atol=1e-6) = }')

    y = eliminate_forward(L, B)
    x = substitute_backward(U, y)
    print(f'solution = {x}')

    B_check = A @ x
    print_array('B_check', B_check)
    print_array('B_orgin', B)

    error = B - B_check
    print(f'error = {error}')
