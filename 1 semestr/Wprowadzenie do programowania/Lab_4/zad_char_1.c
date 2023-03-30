#include <stdio.h>

int main(void)
{
    size_t string_length = 100;
    char string[string_length];
    
    printf("Enter string: ");
    scanf("%[^\n]", string);
    
    for (int i = 0; i < string_length; i++)
        if (string[i] == '\0')
            string_length = i;
            
    printf("String backwards: ");
    for (int i = string_length - 1; i >= 0; i--)
        printf("%c", string[i]);
}