#include <stdio.h>

int main(void)
{
	int x, y;
	printf("Enter x: ");
	scanf("%d", &x);
	printf("Enter y: ");
	scanf("%d", &y);
	printf("\nx is %.2f%% of y\n", x*100.0/y);
	return 0;
}
