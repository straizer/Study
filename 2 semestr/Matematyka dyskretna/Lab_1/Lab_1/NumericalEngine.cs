/// <summary>
/// Provides static methods for obtaining n-th values of mathematical sequences.
/// </summary>
class NumericalEngine
{
    private static ulong[] triangularNumbersCache = { 0, 1 };
    private static ulong[] fibonacciNumbersCache = { 0, 1 };
    private static ulong[] factorialsCache = { 1, 1 };
    private static bool[] primeCache = { false, false };
            
    /// <summary>
    /// Calculates <paramref name="n"/>-th triangular number.
    /// </summary>
    /// <param name="n">Number of triangular number to calculate.</param>
    /// <remarks>First two triangular numbers are 0 and 1.</remarks>
    /// <returns><paramref name="n"/>-th triangular number.</returns>
    public static ulong TriangularNumber(ushort n)
    {
        if (n > 100)
            TriangularNumber((ushort)(n - 100));
        if (n >= triangularNumbersCache.Length)
            ExtendArray(ref triangularNumbersCache, 0UL, (uint)(n + 1));
        if (triangularNumbersCache[n] == 0 && n != 0)
            triangularNumbersCache[n] = n + TriangularNumber((ushort)(n - 1));
        return triangularNumbersCache[n];
    }

    /// <summary>
    /// Calculates <paramref name="n"/>-th Fibonacci number.
    /// </summary>
    /// <param name="n">Number of Fibonacci number to calculate.</param>
    /// <remarks>First two Fibonacci numbers are 0 and 1. If <paramref name="n"/> > 93, throws <see cref="ArgumentOutOfRangeException"/>.</remarks>
    /// <exception cref="ArgumentOutOfRangeException"/>
    /// <returns><paramref name="n"/>-th Fibonacci number.</returns>
    public static ulong Fibonacci(ushort n)
    {
        if (n > 93)
            throw new ArgumentOutOfRangeException(nameof(n), "Argument too big to compute");
        if (n >= fibonacciNumbersCache.Length)
            ExtendArray(ref fibonacciNumbersCache, 0UL, (uint)(n + 1));
        if (fibonacciNumbersCache[n] == 0 && n != 0)
            fibonacciNumbersCache[n] = Fibonacci((ushort)(n - 1)) + Fibonacci((ushort)(n - 2));
        return fibonacciNumbersCache[n];
    }

    /// <summary>
    /// Calculates factorial of <paramref name="n"/>.
    /// </summary>
    /// <param name="n">Number whose factorial will be calculated.</param>
    /// <remarks>0! = 1 and 1! = 1. If <paramref name="n"/> > 20, throws <see cref="ArgumentOutOfRangeException"/>.</remarks>
    /// <exception cref="ArgumentOutOfRangeException"/>
    /// <returns><paramref name="n"/>!</returns>
    public static ulong Factorial(ushort n)
    {
        if (n > 20)
            throw new ArgumentOutOfRangeException(nameof(n), "Argument too big to compute");
        if (n >= factorialsCache.Length)
            ExtendArray(ref factorialsCache, 0UL, (uint)(n + 1));
        if (factorialsCache[n] == 0)
            factorialsCache[n] = n * Factorial((ushort)(n - 1));
        return factorialsCache[n];
    }

    /// <summary>
    /// Checks if <paramref name="n"/> is prime.
    /// </summary>
    /// <param name="n">Number to check.</param>
    /// <remarks>0 and 1 are marked as not prime.</remarks>
    /// <returns><see langword="true"/> if <paramref name="n"/> is prime; <see langword="false"/> otherwise.</returns>
    public static bool IsPrime(ushort n)
    {
        var limit = (uint)Math.Floor(Math.Sqrt(n));
        var oldArraySize = ExtendArray(ref primeCache, true, (uint)(n + 1));
        for (uint i = 2; i <= limit; i++)
            if (primeCache[i])
                for (uint j = oldArraySize > i ? oldArraySize : i + 1; j < primeCache.Length; j++)
                    if (j % i == 0)
                        primeCache[j] = false;
        return primeCache[n];
    }

    /// <summary>
    /// Extends array given by reference to new size and insert default values.
    /// </summary>
    /// <typeparam name="T">Type of array and default value.</typeparam>
    /// <param name="array">Array to extend.</param>
    /// <param name="defaultValue">Default value to insert.</param>
    /// <param name="newSize">New size of <paramref name="array"/>, default is <see langword="null"/>.</param>
    /// <remarks>
    /// If <paramref name="array"/> size is greater or equal to <paramref name="newSize"/>, <paramref name="array"/> is not extended.
    /// </remarks>
    /// <returns>Size of <paramref name="array"/> before extending.</returns>
    private static uint ExtendArray<T>(ref T[] array, T defaultValue, uint newSize)
    {
        var oldSize = array.Length;
        if (newSize > oldSize)
        {
            var tempArray = Enumerable.Repeat(defaultValue, (int)newSize).ToArray();
            array.CopyTo(tempArray, 0);
            array = tempArray;
        }
        return (uint)oldSize;
    }
}
