#include <cctype>
#include <cmath>
#include <fstream>
#include <iostream>
#include <string>
#include "vector.h"
#include "random.h"
#include "queue.h"
#include "hashmap.h"
#include "simpio.h"
#include "filelib.h"
// #include "console.h"

using namespace std;
using MapKey = Vector<string>;
using MapVal = Vector<string>;
using Map = HashMap<MapKey, MapVal>;

void initWords(Vector<string>&);
int getN();
void initMap(const Vector<string>&, Map&, const int);
void generate(const Map&, const int);
int getGenerateCount(const int);

int main() {
    Vector<string> words;
    Map map;

    initWords(words);
    int n = getN();
    initMap(words, map, n);

    int count;
    while ((count = getGenerateCount(n))) {
        generate(map, count);
    }

    // cout << "map: " << map << endl;
    // cout << "words: " << words << endl;

    cout << "Exiting." << endl;
    return 0;
}

void initWords(Vector<string>& words) {
    string filename = "tomsawyer.txt";
    ifstream in;
    // while (!openFile(in, getLine("Input file name?"))) {
    while (!openFile(in, filename)) {
        cout << "Unable to open that file.  Try again." << endl;
    }

    string word;
    while (in >> word) {
        words.add(word);
    }
    in.close();
}

int getN() {
    int n;
    while ((n = getInteger("Value of N?")) < 2) {
        cout << "N must be 2 or greater." << endl;
    }
    return n;
}

void initMap(const Vector<string>& words, Map& map, const int n) {
    Vector<string> q;
    int size = words.size();
    for (int i = 0; i < n; ++i) {
        q.add(words[i]);
    }

    for (int i = n - 1; i < size - 1; ++i) {
        string nextWord = words[i + 1];
        map[q] += nextWord;
        q.remove(0);
        q.add(nextWord);
    }
}

template <typename T>
T randomEle(const Vector<T>& v) {
    return v[randomInteger(0, v.size() - 1)];
}

int getGenerateCount(const int n) {
    int count;
    cout << endl;
    while ((count = getInteger("# of random words to generate (0 to quit)?")) < n && count != 0) {
        cout << "Must be at least" << n << " words." << endl;
    }
    return count;
}

void generate(const Map& map, const int count) {
    Vector<MapKey> keys = map.keys();

    MapKey q = randomEle(keys);
    int size = q.size();

    cout << "...";
    for (int i = 0; i < size; ++i) {
        cout << " " << q[i];
    }

    for (int i = 0; i < count - size; ++i) {
        string word = randomEle(map[q]);
        cout << " " << word;
        q.remove(0);
        q.add(word);
    }

    cout << " " << "..." << endl;
}




