class NumericalEngine
{
    private ulong[] triangularNumbersCache = { 0, 1 };
    private ulong[] fibonacciNumbersCache = { 0, 1 };
    private ulong[] factorialsCache = { 1, 1 };
    private bool[] primeCache = { false, false };

    public ulong TriangularNumber(int number)
    {
        if (number >= triangularNumbersCache.Length)
        {
            ExtendArray(ref triangularNumbersCache, 0UL, number + 1);
            triangularNumbersCache[number] = (ulong)number + TriangularNumber(number - 1);
        }
        return triangularNumbersCache[number];
    }

    public ulong Fibonacci(int number)
    {
        if (number >= fibonacciNumbersCache.Length)
        {
            ExtendArray(ref fibonacciNumbersCache, 0UL, number + 1);
            fibonacciNumbersCache[number] = Fibonacci(number - 1) + Fibonacci(number - 2);
        }
        return fibonacciNumbersCache[number];
    }

    public ulong Factorial(int number)
    {
        if (number >= factorialsCache.Length)
        {
            ExtendArray(ref factorialsCache, 0UL, number + 1);
            factorialsCache[number] = (ulong)number * Factorial(number - 1);
        }
        return factorialsCache[number];
    }

    public bool IsPrime(int number)
    {
        var limit = (int)Math.Floor(Math.Sqrt(number));
        var oldArraySize = ExtendArray(ref primeCache, true, number + 1);
        for (int i = 2; i <= limit; i++)
            if (primeCache[i])
                for (int j = oldArraySize; j < primeCache.Length; j++)
                    if (j % i == 0)
                        primeCache[j] = false;
        return primeCache[number];
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
    /// If <paramref name="newSize"/> is <see langword="null"/>, <paramref name="array"/> is extended by 1 element.
    /// </remarks>
    /// <returns>Size of <paramref name="array"/> before extending.</returns>
    private static int ExtendArray<T>(ref T[] array, T defaultValue, int? newSize = null)
    {
        var oldSize = array.Length;
        newSize ??= oldSize + 1;
        if (newSize > oldSize)
        {
            var tempArray = Enumerable.Repeat(defaultValue, (int)newSize).ToArray();
            array.CopyTo(tempArray, 0);
            array = tempArray;
        }
        return oldSize;
    }
}
