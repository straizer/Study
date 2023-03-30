#pragma once

#include <stdlib.h>
#include <time.h>
#include <math.h>
#define INTERVAL_COUNT 10000
#define POINTS_COUNT INTERVAL_COUNT * 100

double c_od, c_do;

static double* from = &c_od;
static double* to = &c_do;

inline double f3(double x)
{
	return pow(x, 3) * sin(x) + 2 * pow(x, 2) + 3 * x + cos(2 * x);
}      
inline double f4(double x)
{
	return 2 * pow(x, 4) * cos(x / 4.) + 5 * pow(x, 3) * sin(3 * x) - 4 * pow(x, 2) + 6 * x + 2 - 4 * cos(2 * x);
}


inline double prostokaty(double (*f)(double))
{
	double step = (*to - *from) / INTERVAL_COUNT;
	double sum = 0;
	double interval = *from + step;
	while (interval < *to)
	{
		sum += (*f)(interval);
		interval += step;
	}
	return sum * step;
}

inline double trapezy(double (*f)(double))
{
	double step = (*to - *from) / INTERVAL_COUNT;
	double sum = ((*f)(*from) + (*f)(*to)) / 2;
	double interval = *from + step;
	double _to = *to - step;
	while (interval < _to)
	{
		sum += (*f)(interval);
		interval += step;
	}
	return sum * step;
}
	   
inline double mc(double (*f)(double))
{
	double x_range = *to - *from;
	double step = x_range / INTERVAL_COUNT;
	double interval = *from;
	double min = 0;
	double max = 0;
	double value;
	while (interval <= *to)
	{
		value = f(interval);
		if (value < min)
			min = value;
		else if (value > max)
			max = value;
		interval += step;
	}
	double y_range = max - min;

	srand(time(0));
	int points_counter = 0;
	int inside_points_counter = 0;
	double random_x, random_y;
	while (points_counter < POINTS_COUNT)
	{
		random_x = rand() / (double)RAND_MAX * x_range + *from;
		random_y = rand() / (double)RAND_MAX * y_range + min;
		value = f(random_x);
		points_counter++;
		if (random_y > 0)
		{
			if (value > random_y)
				inside_points_counter++;
		}
		else
			if (value < random_y)
				inside_points_counter--;
	}
	return x_range * y_range * inside_points_counter / points_counter;
}