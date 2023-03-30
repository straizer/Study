#include <stdio.h>
#include <limits.h>
#include <float.h>

int main(void)
{
	printf("Max short: %d\n", SHRT_MAX);
	printf("Max int: %d\n", INT_MAX);
	printf("Max float: %e\n", FLT_MAX);
	printf("Min positive float: %e\n", FLT_MIN);
	return 0;
}
