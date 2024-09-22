#pragma once

#include <iosfwd>

namespace utils
{
	namespace io
	{
		bool _isStreamGood(std::iostream& stream);
	}
}

//int main() {
//    string line;
//    std::string filename = "test.txt"
//    ifstream f("filename");
//    if (!f.is_open()) // no file/permission denied
//        perror(("error while opening file " + filename).c_str());
//
//    while (getline(f, line)) {
//        process(&line);
//    }
//
//    if (f.bad()) // directory (bad ofter first getline)
//        perror(("error while reading file " + filename).c_str());
//    else if (f.fail()) // no data extracted/eof
//        cerr << "Other error while extracting IO" << endl;
//
//    f.close();
//    return 0;
//}
