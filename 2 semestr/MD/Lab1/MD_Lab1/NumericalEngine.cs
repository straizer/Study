class NumericalEngine
{
    public int TriangularNumber(int number)
    {
        if (number == 1)
            return 1;
        return (number + TriangularNumber(number - 1));
    }

    public int Fibonacci(int number)
    {
        if (number == 0 || number == 1)
            return 1;
        return Fibonacci(number - 1) + Fibonacci(number - 2);
    }

    public ulong Factorial(ulong number)
    {
        if (number == 1)
            return 1;
        return number * Factorial(number - 1);
    }

    public bool IsPrime(int number)
    {
        var limit = (int)Math.Floor(Math.Sqrt(number));
        var array = Enumerable.Repeat<bool>(true, number + 1).ToArray();
        array[0] = false;
        array[1] = false;
        for (int i = 2; i <= limit; i++)
            if (array[i])
                for (int j = i + 1; j < array.Length; j++)
                    if (j % i == 0)
                        array[j] = false;
        return array[number];
    }
}
