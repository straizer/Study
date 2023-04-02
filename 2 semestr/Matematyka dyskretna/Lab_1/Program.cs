/// <summary>
/// Main class.
/// </summary>
class Program
{
    /// <summary>
    /// Main program.
    /// </summary>
    static void Main()
    {
        try
        {
            var number = GetInput();
            WriteResults(number, "triangular number", NumericalEngine.TriangularNumber);
            WriteResults(number, "Fibonacci number", NumericalEngine.Fibonacci);
            WriteResults(number, "factorial", NumericalEngine.Factorial);
            WriteResults(number, "prime", NumericalEngine.IsPrime);
        }
        catch (IOException)
        {
            Environment.Exit(0);
        }
    }

    /// <summary>
    /// Gets input from user.
    /// </summary>
    /// <remarks>Only integer in range 1 - 65535 is accepted.</remarks>
    /// <exception cref="IOException"/>
    /// <returns>Integer entered by user.</returns>
    static ushort GetInput()
    {
        Console.Write("Enter number (1 - 65535): ");
        ushort input;
        do
        {
            try
            {
                string? raw = Console.ReadLine();
                if (!ushort.TryParse(raw, out input) || input == 0)
                    throw new ArgumentOutOfRangeException();
                break;
            }
            catch (SystemException ex) when (ex is OutOfMemoryException || ex is ArgumentOutOfRangeException)
            {
                Console.Write("Try again: ");
            }

        } while (true);
        return input;
    }

    /// <summary>
    /// Prints given <paramref name="function"/> output from 1 to <paramref name="limit"/>.
    /// </summary>
    /// <typeparam name="T"><paramref name="function"/> output type.</typeparam>
    /// <param name="limit">Max number to pass to <paramref name="function"/>.</param>
    /// <param name="description">Additional description to print.</param>
    /// <param name="function">Function whose output will be calculated and printed.</param>
    /// <exception cref="IOException"/>
    static void WriteResults<T>(ushort limit, string description, Func<ushort, T> function)
    {
        Console.WriteLine();
        for (ushort i = 1; i <= limit; i++)
        {
            var iPadded = i.ToString().PadLeft((int)Math.Floor(Math.Log10(limit) + 1));
            try
            {
                Console.WriteLine($"{iPadded}. {description}: {function(i)}");
            }
            catch (ArgumentOutOfRangeException ex)
            {
                Console.WriteLine($"{iPadded}. {description}: {ex.Message}.");
            }
        }
    }
}
