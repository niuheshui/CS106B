// Your first program!

#include <cstring>
#include <iostream>
#include <iomanip>
#include <thread>
#include <chrono>
// #include "console.h"
#include "vector.h"
#include <vector>

using namespace std;

int ID = 1;
const int DELAY = 1000;

void delay(long ms) {
    std::this_thread::sleep_for(std::chrono::milliseconds(ms));
}

class A {
public:

    int id = ID++;
    int parent = -1;
    friend std::ostream& operator<<(std::ostream& os, const A& a) {
        return os << "A(id=" << a.id << ",parent=" << a.parent << ")";
    }

    A() {
        delay(DELAY);
        cout << "A(): " << *this << endl;
    }

    A(const A& a) {
        delay(DELAY);
        this->parent = a.id;
        cout << "A(const A& " << a << "): " << *this << endl;
    }

    A& operator=(const A& a) {
        delay(DELAY);
        this->parent = a.id;
        cout << "A& operator=(const A& " << a << "): " << *this << endl;
        return *this;
    }

    A(A&& a) {
        this->parent = a.id;
        cout << "A(A&& " << a << "): " << *this << endl;
    }

    A& operator=(A&& a) {
        this->parent = a.id;
        cout << "A& operator=(A&& " << a << "): " << *this << endl;
        return *this;
    }

    ~A() {
        cout << "~A(): " << *this << endl;
    }

};

int main() {
    cout << "=========== The Main Function Start ===========" << endl;
    auto start = std::chrono::steady_clock::now();
    Vector<A> v;
    // v.ensureCapacity(10);
    A a;

    v.push_back(a);
    cout << endl << endl;
    v.push_back(a);
    cout << endl << endl;
    v.push_back(a);
    cout << endl << endl;
    v.push_back(a);
    cout << endl << endl;
    v.insert(0, a);

    auto end = std::chrono::steady_clock::now();
    auto cost = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();
    cout << "cost: " << cost << endl;
    cout << "=========== The Main Function End ===========" << endl;

    return 0;
}
