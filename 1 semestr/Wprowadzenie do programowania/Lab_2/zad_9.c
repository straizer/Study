#include <stdio.h>
#include <math.h>

int main(void)
{
	float radius, side, height;
	char choice;
	_Bool correct_input;
	printf("Calculate area of circle(1), triangle(2) or square(3) and confirm by (ENTER)\n");
	while (1)
	{
		correct_input = 0;
		choice = getchar();
		getchar();
		switch (choice)
		{
			case '1':
				correct_input = 1;
				printf("Enter radius of circle: ");
				scanf("%f", &radius);
				printf("Area of circle: %.2f\n", radius*M_PI);
				break;
			case '2':
				correct_input = 1;
				printf("Enter base length of triangle: ");
				scanf("%f", &side);
				printf("Enter height length of triangle: ");
				scanf("%f", &height);
				printf("Area of triangle: %.2f\n", side*height/2);
				break;
			case '3':
				correct_input = 1;
				printf("Enter side length of square: ");
				scanf("%f", &side);
				printf("Area of square: %.2f\n", side*side);
				break;
			default:
				printf("Only (1), (2) or (3) are accepted\n");
				break;
		}
		if (correct_input) break;
	}
	return 0;
}
