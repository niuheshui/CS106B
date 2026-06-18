/*
 * File: grammarsolver.cpp
 * --------------------------
 * Name:
 * Section leader:
 * This file contains grammar generating code for CS106B.
 */

#include <cassert>
#include <string>
#include <iostream>
#include <vector>
#include "grammarsolver.h"
#include "hashmap.h"
#include "random.h"
#include "strlib.h"
#include "simpio.h"
#include "filelib.h"

using namespace std;

namespace {

using Rule = Vector<string>;
using Rules = Vector<Rule>;

const Rule& randomRule(Rules& rules) {
    return rules.get(randomInteger(0, rules.size() - 1));
}

string grammarGenerate(HashMap<string, Rules>& m, string symbol) {
    if (!m.containsKey(symbol)) {
        return symbol;
    }
    const Rule& rule = randomRule(m[symbol]);
    string result;
    for (const string& s : rule) {
        result += grammarGenerate(m, s) + " ";
    }
    return trim(result);
}

}

/**
 * Generates grammar for a given symbol a certain number of times given
 * a BNF input file.
 *
 * This will be called by grammarmain.cpp.
 *
 * @param input - Input stream of BNF file.
 * @param symbol - Symbol to generate
 * @param times - Number of times grammar is generated
 * @return Vector of strings of size times with random generations of symbol
 */
Vector<string> grammarGenerate(istream& input, string symbol, int times) {
    HashMap<string, Rules> m;
    string s;
    while (true) {
        getLine(input, s);
        if (!input) {
            break;
        }
        vector<string> v = stringSplit(s, "::=");
        string key = v[0];
#ifdef TEST
        assert(v.size() == 2);
        cout << key << endl;
        assert(!m.containsKey(key));
#endif
        v = stringSplit(v[1], "|");
        for (const string& s : v) {
            Rule rule = stringSplit(s, " ");
#ifdef TEST
            assert(rule.size() > 0);
            cout << "\t" << rule << endl;
#endif
            m[key] += rule;
        }
    }
    Vector<string> result;
    while (times--) {
        result.push_back(grammarGenerate(m, symbol));
    }
    return result;
}
