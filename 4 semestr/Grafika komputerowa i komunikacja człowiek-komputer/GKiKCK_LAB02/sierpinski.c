/*
 * Laboratorium Grafiki Komputerowej
 * Pierwszy program wykorzystuj�cy OpenGL'a
 */

/* System */
#include<stdio.h>
#include<stdlib.h>

/* Biblioteka GLUT */
#include <freeglut.h>

int iterations = 0;

void drawSquare(float x, float y, float size)
{
	glVertex2f(x, y);
	glVertex2f(x, y + size);
	glVertex2f(x + size, y + size);
	glVertex2f(x + size, y);		
}

/* W tej funkcji okre�lamy to co ma byc narysowane na ekranie.
 * Jest wywo�ywana zawsze wtedy, gdy trzeba przerysowa� ekran - bufor ramki.
 */
void drawScene(void)
{
	float size = 100;
	float edge = - size / 2;
	int repeater = 3;	
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(1.0f, 0.0f, 0.0f);
	glBegin(GL_QUADS);	
	drawSquare(edge, edge, size);
	glColor3f(0.0f, 0.0f, 0.0f);
	for (int iteration = 0; iteration < iterations; iteration++)
	{
		float localSize = size / repeater;
		for (int i = 1; i <= repeater; i += 3)
			for (int j = 1; j <= repeater; j += 3)
				drawSquare(edge + i * localSize, edge + j * localSize, localSize);
		repeater *= 3;
	}	
	glEnd();
	glFlush();
}

/* T� funkcj� wywo�uje system w momencie gdy u�ytkownik zmieni mysz�
 * rozmiar g�ownego okna. jej zadaniem jest zachowanie propocji wymiar�w
 * rysowanych obiekt�w niezale�nie od wymiar�w okna.
 */
void reshapeWindow(int width, int height)
{
	int aspectRatio; // = width / height
	
	// Na wypadek dzielenia przez 0
	if (height == 0)
		height = 1;

	// Wyliczamy wsp�czynnik proporcji
	aspectRatio = width / height;

	// Ustawiamy wielko�ci okna okna urz�dzenia w zakresie
	// od 0,0 do wysoko��, szeroko��
	glViewport(0, 0, width, height);
     
	// Ustawiamy uk�ad wsp�rz�dnych obserwatora
    glMatrixMode(GL_PROJECTION); 

	// Resetujemy macierz projkecji 
    glLoadIdentity();
    
	// Korekta  
    if(width <= height)
		glOrtho(-100.0, 100.0, -100.0/aspectRatio, 100.0/aspectRatio, 1.0, -1.0);  
     else
		glOrtho(-100.0*aspectRatio, 100.0*aspectRatio, -100.0, 100.0, 1.0, -1.0);
 
	// Ustawiamy macierz modelu
    glMatrixMode(GL_MODELVIEW);
    
	// Resetujemy macierz modelu
    glLoadIdentity();	
}

/* Ta funkcja s�u�y do wst�pnej konfiguracji OpenGLa.
 * Zanim co� narysujemy musimy wywo�a� t� funkcj�.
 */
void initOpenGL(int argc, char **argv)
{
	// Inicjujemy bibliotek� GLUT
	glutInit(&argc, argv);
	// Inicjujemy: format koloru, jeden bufor ramki
    glutInitDisplayMode(GLUT_RGB | GLUT_SINGLE);
	// Ustawiamy pocz�tkowe wymiary okna
    glutInitWindowSize(800, 600);
	// Ustawiamy pozycj� okna - lewy g�rny naro�nik
	glutInitWindowPosition(150,150);
	// Tworzymy g��wne okno programu
    int mainWindow = glutCreateWindow("Pierwsze Laboratorium");

	// Sprawdzamy powodzenie operacji
	if(mainWindow == 0){
		puts("Nie mozna stworzyc okna!!!\nWyjscie z programu.\n");
		exit(EXIT_FAILURE);
	}

	// Czynimy aktywnym okno g��wne programu
	glutSetWindow(mainWindow);

	// Tutaj rejestrujemy funkcje narz�dziowe - tzw. callbacks
	glutDisplayFunc(drawScene);
	glutReshapeFunc(reshapeWindow);
	
	// Ustawiamy domy�lny, czarny kolor t�a okna - bufor ramki malujemy na czarno
	glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
}

/* Funkcja main */
int main(int argc, char **argv)
{
	if (argc == 2)
		iterations = atoi(argv[1]);
	else
	{
		printf("Podaj liczbe iteracji: ");
		scanf("%d", &iterations);
	}
	
	// ustawienia pocz�tkowe
	initOpenGL(argc, argv);

	// Wej�cie do p�tli programu
	glutMainLoop();
	
	return 0;
}
