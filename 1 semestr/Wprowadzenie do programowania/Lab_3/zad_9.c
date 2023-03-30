#include <stdio.h>

int main(void)
{
    printf("Enter height of square: ");
    int height;
    scanf("%d", &height);
    
    printf("Enter width of square: ");
    int width;
    scanf("%d", &width);
    
    for (int i = 0; i < height; i++)
    {
        printf("%c", '\n');
        for (int j = 0; j < width; j++)
            printf("%c", '*');
    }
}