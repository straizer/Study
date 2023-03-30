// rekurencja
int fibonacci(int number)
{
    if (number == 1 || number == 2)
        return 1;
    return fibonacci(number - 1) + fibonacci(number - 2);
}

// petla
int fibonacci2(int number)
{
    if (number == 1 || number == 2)
        return 1;
        
    int last = 1;
    int second_last = 1;
    for (int i = 3; i <= number; i++)
    {
        int temp = last + second_last;
        second_last = last;
        last = temp;
    }
    return last;
}