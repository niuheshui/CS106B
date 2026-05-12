// Your first program!

#include <iostream>
#include "console.h"
#include "vector.h"

using namespace std;

void f() {
    Vector<int> v;
    v.add(1);
    v.add(2);
    v.add(3);
    v.add(3);
    cout << v << endl;
}

int main() {
    f();
    return 0;
}
