#include "Polygon.h"

int main(void)
{
    Polygon polygon(4, "1");
    polygon[0] = Point(0, 0, "a");
    polygon[1] = Point(1, 0, "b");
    polygon[2] = Point(1, 1, "c");
    polygon[3] = Point(0, 1, "d");

    std::cout << polygon << "has circumference of " << polygon.getCircumference() << std::endl << std::endl;

    Polygon polygon2(polygon);
    polygon2.setName("2");
    polygon2[0] = Point(7, 7, "e");

    Polygon polygon3 = polygon2;
    polygon3.setName("3");
    polygon3[0] = Point(12, 12, "f");

    std::cout << polygon << std::endl;
    std::cout << polygon2 << std::endl;
    std::cout << polygon3 << std::endl;
}