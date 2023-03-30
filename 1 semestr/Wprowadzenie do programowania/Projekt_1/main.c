#include <stdio.h>
#include <math.h>

double sin_series(double x, int term_count)
{
    double sum = x;
    double term = x;
    for(int i = 1; i <= term_count; i++)
    {
        term = -term * x * x / (2*i) / (2*i+1);
        sum += term;
    }
    return sum;
}

double cos_series(double x, int term_count)
{
    double sum = 1;
    double term = 1;
    for(int i = 1; i <= term_count; i++)
    {
        term = -term * x * x / (2*i) / (2*i-1);
        sum += term;
    }
    return sum;
}

double sin_x_plus_a_series(double x, double a, int term_count)
{
    double sin_a = sin_series(a, term_count);
    double cos_a = cos_series(a, term_count);
    
    double sin_term = sin_a;
    double cos_term = x*cos_a;
    double sum = sin_term + cos_term;
    for(int i = 1; i <= term_count; i++)
    {
        sin_term = -sin_term * x * x / (2*i) / (2*i-1);
        cos_term = -cos_term * x * x / (2*i) / (2*i+1);
        sum += sin_term + cos_term;
    }
    return sum;
}

int main(void)
{
    printf("TAYLOR SERIES FOR sin(x+a)\nEnter a parameter: ");
    double a;
    scanf("%lf", &a);
    printf("Enter inteval start: ");
    double start;
    scanf("%lf", &start);
    printf("Enter inteval stop: ");
    double stop;
    scanf("%lf", &stop);
    printf("Enter count of intervals: ");
    int interval_count;
    scanf("%d", &interval_count);
    printf("Enter count of terms in series (greater number reduce error): ");
    int term_count;
    scanf("%d", &term_count);
    
    printf("\n      x |     series(x,a) |        sin(x+a) |      error ");
    printf("\n--------|-----------------|-----------------|------------");
    double step = (stop - start) / (interval_count - 1);
    double x = start;
    for (int i = 0; i < interval_count; i++)
    {
        double series_result = sin_x_plus_a_series(x, a, term_count);
        double sin_result = sin(x+a);
        double error = fabs(series_result - sin_result);
        printf("\n%7.2lf | %15.8lf | %15.8lf |   %.2e", x, series_result, sin_result, error);
        x += step;
    }
}