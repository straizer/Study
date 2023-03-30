#include <stdio.h>

int shiftCharacter(int, int, int);
int shouldJumpForward(int);
int shouldJumpBackward(int);

int main(void)
{
    size_t string_length = 100;
    char string[string_length];
    
    printf("Enter string: ");
    scanf("%[^\n]", string);
    
    for (int i = 0; i < string_length; i++)
        if (string[i] == '\0')
            string_length = i;
            
    printf("\nString encrypted: ");
    for (int i = 0; i < string_length; i++)
        printf("%c", shiftCharacter(string[i], 3, 1));
            
    printf("\nString decrypted: ");
    for (int i = 0; i < string_length; i++)
        printf("%c", shiftCharacter(string[i], 3, 0));
}

int shiftCharacter(int character, int key, int b_encryptTrue_decryptFalse)
{
    if ((character < 65 || character > 90) && (character < 97 || character > 122))
        return character;
    if (b_encryptTrue_decryptFalse)
        return character - key + (shouldJumpForward(character) ? 26 : 0);
    else
        return character + key - (shouldJumpBackward(character) ? 26 : 0);
}

int shouldJumpForward(int i)
{
    return (i > 64 && i < 68) || (i > 96 && i < 100) ? 1 : 0;
}

int shouldJumpBackward(int i)
{
    return (i > 87 && i < 91) || (i > 119 && i < 123) ? 1 : 0;
}