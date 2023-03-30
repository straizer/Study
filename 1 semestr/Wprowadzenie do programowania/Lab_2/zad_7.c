#include <stdio.h>

int main(void)
{
	int x, y;
	char choice;
	_Bool correct_input;
	printf("Enter x: ");
	scanf("%d", &x);
	printf("Enter y: ");
	scanf("%d", &y);
	getchar();
	printf("Press (1) to calculate sum or (2) to calculate difference and confirm by (ENTER)\n");
	while (1)
	{
		correct_input = 0;
		choice = getchar();
		getchar();
		switch (choice)
		{
			case '1':
				correct_input = 1;
				printf("x + y = %d\n", x+y);
				break;
			case '2':
				correct_input = 1;
				printf("x - y = %d\n", x-y);
				break;
			default:
				printf("Only (1) or (2) are accepted\n");
				break;
		}
		if (correct_input) break;
	}
	return 0;
}
