#include "ComplexNumber.h"

int main(void)
{
    Point p1(1, 1);
    Point p2(2, 2);

    std::cout << getMiddlePoint(p1, p2) << std::endl;
    std::cout << getDistance(p1, p2) << std::endl;
    std::cout << (p1 == p2) << std::endl;
    std::cout << (p1 == p1) << std::endl;
    std::cout << (p1 != p2) << std::endl;
    std::cout << (p1 != p1) << std::endl;
    std::cout << p1 << std::endl;
    std::cout << p2 << std::endl;
    std::cout << p1 + p2 << std::endl;
    p1 += p2;
    std::cout << p1 << std::endl;
    std::cout << "Enter x and y (separated by space): ";
    std::cin >> p2;
    std::cout << p2 << std::endl;

    ComplexNumber z1(1, 2);
    std::cout << z1 << std::endl;
    ComplexNumber z2;
    std::cout << "Enter real and imaginary part (separated by space): ";
    std::cin >> z2;
    std::cout << z2 << std::endl;
    std::cout << (z1 == z2) << std::endl;
    std::cout << (z1 != z1) << std::endl;
    z1 += z2;
    std::cout << z1 << std::endl;
    std::cout << z1 + z2 << std::endl;
}
