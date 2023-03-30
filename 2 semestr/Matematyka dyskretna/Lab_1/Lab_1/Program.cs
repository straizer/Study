class Program
{
    static void Main()
    {
        Console.Write("Enter number: ");
        int number;
        while (!int.TryParse(Console.ReadLine(), out number))
            Console.Write("Try again: ");
        Console.WriteLine();

        var engine = new NumericalEngine();

        for (int i = 1; i <= number; i++)
            Console.WriteLine($"{i}. triangular number: {engine.TriangularNumber(i)}");

        Console.WriteLine();
        for (int i = 1; i <= number; i++)
            Console.WriteLine($"{i}. Fibonacci number: {engine.Fibonacci(i)}");

        Console.WriteLine();
        for (int i = 1; i <= number; i++)
            Console.WriteLine($"Factorial for {i}: {engine.Factorial(i)}");

        Console.WriteLine();
        for (int i = 1; i <= number; i++)
            Console.WriteLine($"Is {i} prime: {engine.IsPrime(i)}");
    }
}
