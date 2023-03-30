#include <stdio.h>

int main(void)
{
	int a, b, c;
	printf("Enter a: ");
	scanf("%d", &a);
	printf("Enter b: ");
	scanf("%d", &b);
	printf("Enter c: ");
	scanf("%d", &c);
	printf("\nV = %d", a*b*c);
	printf("\nP = %d\n", 2*(a*b+a*c+b*c));
	return 0;
}
