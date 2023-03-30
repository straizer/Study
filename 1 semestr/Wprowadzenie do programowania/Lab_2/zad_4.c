#include <stdio.h>
#include <math.h>

int main(void)
{
	int x, mi, sigma;
	printf("Enter x: ");
	scanf("%d", &x);
	printf("Enter mi: ");
	scanf("%d", &mi);
	printf("Enter sigma: ");
	scanf("%d", &sigma);
	printf("\nf(x) = %f\n", exp(-pow(x-mi, 2)/2./pow(sigma, 2))/sigma/sqrt(2*M_PI));
	return 0;
}
