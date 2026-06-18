/*
 * CS 106B Recursion Problems
 * This file declares necessary function prototypes, so that other files
 * are able to call the recursive functions you will write.
 *
 * !!! DO NOT MODIFY THIS FILE !!!
 * !!! DO NOT MODIFY THIS FILE !!!
 * !!! DO NOT MODIFY THIS FILE !!!
 *
 * Please do not modify this provided file. Your turned-in files should work
 * with an unmodified version of all provided code files.
 *
 * (If you want to declare your own helper function prototypes,
 *  do that near the top of your .cpp file, not here.)
 */

#ifndef _recursionproblems_h
#define _recursionproblems_h
#define TEST

#include <iostream>
#include <string>
// #include "gbufferedimage.h"
// #include "gwindow.h"
#include "vector.h"
#include "hashmap.h"

class GrammarGenerator {
public:
    using Symbol = std::string;
    using Rule = Vector<Symbol>;

    static GrammarGenerator parse(std::istream& input);

    std::string generate(const Symbol& symbol);

    Vector<std::string> generate(const Symbol& symbol, int times);

private:
    HashMap<Symbol, Vector<Rule>> grammar;

    const GrammarGenerator::Rule& randomRule(const Vector<Rule>& rules) const;
};

#endif

/*
 * !!! DO NOT MODIFY THIS FILE !!!
 * !!! DO NOT MODIFY THIS FILE !!!
 * !!! DO NOT MODIFY THIS FILE !!!
 */
