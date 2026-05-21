#include <cctype>
#include <cmath>
#include <fstream>
#include <iostream>
#include "filelib.h"
#include "queue.h"
#include "simpio.h"
#include "lexicon.h"
#include <string>
// #include "console.h"

using namespace std;

void bfs(const string&, const string&, const Lexicon&);
void initDict(Lexicon&);
void run(const Lexicon&);

int main() {
    // Lexicon dict("/Users/justin/proj/CS106B/web.stanford.edu/cs106b.1178/assnFiles/serafini/WordLadder/res/dictionary.txt");
    Lexicon dict;
    initDict(dict);
    run(dict);
    cout << "Have a nice day." << endl;
    return 0;
}

void run(const Lexicon& dict) {
    while (true) {
        cout << endl;
        string w1 = toLowerCase(getLine("Word #1 (or Enter to quit):"));
        if (w1 == "") {
            break;
        }
        string w2 = toLowerCase(getLine("Word #2 (or Enter to quit):"));
        if (w2 == "") {
            break;
        }
        if (w1.length() != w2.length()) {
            cout << "The two words must be the same length." << endl;
            continue;
        }

        if (!(dict.contains(w1) && dict.contains(w2))) {
            cout << "The two words must be found in the dictionary." << endl;
            continue;
        }

        bfs(w1, w2, dict);
    }
}

void initDict(Lexicon& dict) {
    ifstream in;
    while (!openFile(in, getLine("Dictionary file name?"))) {
        cout << "Unable to open that file.  Try again." << endl;
    }
    dict = Lexicon(in);
    in.close();
}

void printResult(const string& s1, const string& s2, Stack<string>& stack) {
    cout << "A ladder from " << s2 << " back to " << s1 <<  ":" << endl;
    string str;
    while (!stack.isEmpty()) {
        str = stack.pop() + str;
        if (!stack.isEmpty()) {
            str = " " + str;
        }
    }
    cout << str << endl;;
}

// s2 convert to target
// example: data -> code
// data cata cota coda code
void bfs(const string& target, const string& s2, const Lexicon& dict) {
    Queue<Stack<string>> q = {{s2}};
    Set<string> st = {s2};

    while (!q.isEmpty()) {
        Stack<string> sk = q.dequeue();
        cout << "dequeue: " << sk << endl;
        string str = sk.peek();
        if (str == target) {
            printResult(target, s2, sk);
            return;
        }
        for (int i = 0; i < target.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                char origin = str[i];
                str[i] = c;
                if (!st.contains(str) && dict.contains(str)) {
                    st.add(str);
                    sk.push(str);
                    q.enqueue(sk);
                    sk.pop();
                }
                str[i] = origin;
            }
        }
    }
}


