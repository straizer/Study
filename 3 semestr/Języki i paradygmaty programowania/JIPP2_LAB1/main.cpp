#include "Polygon.h"

int main(void)
{
    Polygon polygon(4);
    polygon[0] = Point(0, 0, "a");
    polygon[1] = Point(1, 0, "b");
    polygon[2] = Point(1, 1, "c");
    polygon[3] = Point(0, 1, "d");

    std::cout << polygon << "has circumference of " << polygon.getCircumference() << std::endl;
}