#include <stdio.h>
#include <math.h>

int main(void)
{
	int x, y;
	printf("Enter x: ");
	scanf("%d", &x);
	printf("Enter y: ");
	scanf("%d", &y);
	double y1 = log(x)+10.;
	double y2 = pow(1.5, x);
	double y3 = exp(x);
	if (y<y1 && y>y2 && y<y3)
	{
		printf("\nPoint lays between functions y=log(x)+10, y=1.5^x and y=e^x\n");
	}
	else
	{
		printf("\nPoint doesn't lay between functions y=log(x)+10, y=1.5^x and y=e^x\n");
	}
	return 0;
}
