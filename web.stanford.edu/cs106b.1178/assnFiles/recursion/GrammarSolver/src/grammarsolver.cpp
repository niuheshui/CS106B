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

GrammarGenerator GrammarGenerator::parse(istream& input) {
    GrammarGenerator g;
    HashMap<string, Vector<GrammarGenerator::Rule>>& m = g.grammar;
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

    return g;
}

const GrammarGenerator::Rule& GrammarGenerator::randomRule(const Vector<Rule>& rules) const {
    return rules.get(randomInteger(0, rules.size() - 1));
}

std::string GrammarGenerator::generate(const Symbol& symbol) {
    if (!this->grammar.containsKey(symbol)) {
        return symbol;
    }
    const Rule& rule = this->randomRule(this->grammar[symbol]);
    string result;
    for (const string& s : rule) {
        result += generate(s) + " ";
    }
    return trim(result);
}

Vector<std::string> GrammarGenerator::generate(const Symbol& symbol, int times) {
    Vector<std::string> result;
    while (times--) {
        result.push_back(this->generate(symbol));
    }
    return result;
}


