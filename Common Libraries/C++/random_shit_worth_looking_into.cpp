#include <iostream>

template <class Item>
std::string getType(void)
{
	std::string signature = __FUNCSIG__;
	return signature.substr(104, signature.length() - 111);
}

class Item
{
public:
	Item(void)
	{
		std::cout << "Item(void)" << std::endl;
	}

	Item(const Item& item)
	{
		std::cout << "Item(const Item& item)" << std::endl;
	}

	Item(Item&& item) noexcept
	{
		std::cout << "Item(Item&& item)" << std::endl;
	}

	~Item(void)
	{
		std::cout << "~Item(void)" << std::endl;
	}
};

class MyClass
{
public:
	template <class Item>
	MyClass(Item&& item) : item{ std::forward<Item>(item) }
	{
		std::cout << "MyClass(" << getType<decltype(item)>() << " item)" << std::endl;
	}

	virtual ~MyClass(void)
	{
		std::cout << "~MyClass(void)" << std::endl;
	}

private:
	Item item;
};
#include <sstream>

void print(std::stringstream& ss, std::string prompt)
{
	std::perror(prompt.c_str());
	std::cout << "good: " << ss.good() << std::endl;
	std::cout << "eof: " << ss.eof() << std::endl;
	std::cout << "fail: " << ss.fail() << std::endl;
	std::cout << "bad: " << ss.bad() << std::endl;
	std::cout << "rdstate: " << ss.rdstate() << std::endl << std::endl;
}

int main(void)
{
	std::cout << std::boolalpha;

	std::stringstream ss;
	ss.exceptions(std::ios_base::badbit);
	print(ss, "std::stringstream ss");

	ss.setstate(std::ios_base::eofbit);
	print(ss, "ss.setstate(std::ios_base::eofbit)");

	ss.setstate(std::ios_base::eofbit, true);
	print(ss, "ss.setstate(std::ios_base::eofbit, true)");

	ss.setstate(std::ios_base::failbit);
	print(ss, "ss.setstate(std::ios_base::failbit)");

	ss.setstate(std::ios_base::failbit, true);
	print(ss, "ss.setstate(std::ios_base::failbit, true)");

	try
	{
		ss.setstate(std::ios_base::badbit);
	}
	catch (const std::ios_base::failure& ex)
	{
		try
		{
			throw std::out_of_range("TEST!!!!!!!!!!");
		}
		catch (const std::out_of_range& xe)
		{
			std::cout << xe.what() << std::endl;
			ss.clear(std::ios_base::goodbit, true);
		}
	}
	print(ss, "ss.setstate(std::ios_base::badbit)");

	//ss.setstate(std::ios_base::badbit, true);
	//print(ss, "ss.setstate(std::ios_base::badbit, true)");

	ss.clear(std::ios_base::goodbit);
	print(ss, "ss.clear(std::ios_base::goodbit)");

	ss.clear(std::ios_base::goodbit, true);
	print(ss, "ss.clear(std::ios_base::goodbit, true)");

	//std::cout << "Object x" << std::endl;
	//auto x = MyClass{ Item{} };

	//std::cout << std::endl << "Object y" << std::endl;
	//auto item = Item{};
	//auto y = MyClass{ item };

	//std::cout << std::endl << "Finish" << std::endl;
}
