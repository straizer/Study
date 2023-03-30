#include <stdio.h>

int main(void)
{
	int x, y;
	printf("Enter x: ");
	scanf("%d", &x);
	printf("Enter y: ");
	scanf("%d", &y);
	printf("\nx + y = %d", x+y);
	printf("\nx - y = %d", x-y);
	printf("\nx * y = %d", x*y);
	printf("\nx / y = %f", (double)x/y);
	printf("\nx % y = %d\n", x%y);
	return 0;
}
