#include <stdio.h>

int main(void)
{
	int a1, b1, c1, a2, b2, c2;
	printf("Enter a1: ");
	scanf("%d", &a1);
	printf("Enter b1: ");
	scanf("%d", &b1);
	printf("Enter c1: ");
	scanf("%d", &c1);
	printf("Enter a2: ");
	scanf("%d", &a2);
	printf("Enter b2: ");
	scanf("%d", &b2);
	printf("Enter c2: ");
	scanf("%d", &c2);
	double w = a1*b2 - a2*b1;
	double w_x = c1*b2 - c2*b1;
	double w_y = a1*c2 - a2*c1;
	printf("\nx = %.2f\ny = %.2f\n", w_x/w, w_y/w);
	return 0;
}
