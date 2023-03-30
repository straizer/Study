#include <stdio.h>

int main(void)
{
	int x;
	printf("Enter number: ");
	scanf("%d", &x);
	printf("Number x is %s\n", x%2==0 ? "even" : "odd");
	return 0;
}
