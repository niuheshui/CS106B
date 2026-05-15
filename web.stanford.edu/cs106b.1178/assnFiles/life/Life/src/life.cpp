#include <cctype>
#include <cmath>
#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include "random.h"
// #include "console.h"
#include "filelib.h"
#include "grid.h"
// #include "gwindow.h"
#include "simpio.h"
#include "strlib.h"
// #include "lifegui.h"
using namespace std;

const int MAX_SIZE = 10;
void printHelloMessage();
void initGrid(Grid<int>& grid);
void printGrid(Grid<int>& grid);
void step(Grid<int>& grid, bool enableWrapping);

int main() {
#ifdef _concole_h
    setConsoleClearEnabled(false);
#endif
    printHelloMessage();

    Grid<int> grid;
    initGrid(grid);
    bool enableWrapping = getYesOrNo("Should the simulation wrap around the grid (y/n)?");

    printGrid(grid);
    bool run = true;

    do {
        string choice = toLowerCase(getLine("a)nimate, t)ick, q)uit?"));
        if (choice.size() != 1) {
            choice = "invalid";
        }

        switch(tolower(choice[0])) {
        case 'a': {
            int frame = getInteger("How many frames?");
            for (int i = 0; i< frame; ++i) {
#ifdef _console_h
                clearConsole();
#else
                cout << "==================== (console cleared) ====================" << endl;
#endif
                step(grid, enableWrapping);
                printGrid(grid);
#ifdef _gwindow_h
                pause(50);
#endif
            }
            break;
        }
        case 't': {
            step(grid, enableWrapping);
            printGrid(grid);
            break;
        }
        case 'q':{
            run = false;
            break;
        }
        default:
            cout << "Invalid choice; plase try agin." << endl;;
        }

    } while (run);

    cout << "Have a nice Life!" << endl;
    return 0;
}

void printHelloMessage() {
    cout << "Welcome to the CS 106B Game of Life," << endl;
    cout << "a simulation of the lifecycle of a bacteria colony." << endl;
    cout << "Cells (X) live and die by the following rules:" << endl;
    cout << "- A cell with 1 or fewer neighbors dies." << endl;
    cout << "- Locations with 2 neighbors remain stable." << endl;
    cout << "- Locations with 3 neighbors will create life." << endl;
    cout << "- A cell with 4 or more neighbors dies." << endl;
}

void initGrid(Grid<int>& grid, istream& in) {
    string buf;
    getLine(in, buf);
    int nRows = stringToInteger(buf);
    getLine(in, buf);
    int nCols = stringToInteger(buf);

    grid.resize(nRows, nCols);
    for(int row = 0; row < nRows; ++row) {
        getLine(in, buf);
        for (int col = 0; col < nCols; ++col) {
            if (buf[col] == 'X') {
                grid[row][col] = 1;
            } else {
                grid[row][col] = 0;
            }
        }
    }
}

string generateGrid() {
    ostringstream os;
    int nRows = randomInteger(1, MAX_SIZE);
    int nCols = randomInteger(1, MAX_SIZE);
    os << nRows  << endl;
    os << nCols << endl;

    for (int i = 0; i < nRows; ++i) {
        for (int j = 0; j < nCols; ++j) {
            if (randomBool()) {
                os << 'X';
            } else {
                os << '-';
            }
        }
        os << endl;
    }

    return os.str();
}

void initGrid(Grid<int>& grid) {
    ifstream in;
    string fileName;
    while(!openFile(in, (fileName = getLine("Grid input fileName?")))) {
        if (fileName == "random") {
            istringstream isn(generateGrid());
            return initGrid(grid, isn);
        }
        cout << "Unable to open that file.  Try again." << endl;
    }
    initGrid(grid, in);
    in.close();
}

void printGrid(Grid<int>& grid) {
    for (int i = 0; i < grid.numRows(); ++i) {
        for (int j = 0; j < grid.numCols(); ++j) {
            if (grid[i][j]) {
                cout << "X";
            } else {
                cout << "-";
            }
        }
        cout << endl;
    }
}

int countLivingNeighbors(Grid<int>& grid, int row, int col, bool enableWrapping) {
    int nRows = grid.numRows();
    int nCols = grid.numCols();
    int count = 0;
    for (int i = -1; i <= 1; ++i) {
        for (int j = -1; j <= 1; ++j) {
            if (i == 0 && j == 0) {
                continue;
            }
            int x = row + i;
            int y = col + j;
            if (enableWrapping) {
                x = ((row + nRows) + i) % nRows;
                y = ((col + nCols) + j) % nCols;
            }
            if (grid.inBounds(x, y)) {
                count += grid[x][y];
            }
        }
    }
    return count;
}

void step(Grid<int>& grid, bool enableWrapping) {
    Grid<int> newGrid = grid;

    for (int i = 0; i < newGrid.numRows(); ++i) {
        for (int j = 0; j < newGrid.numCols(); ++j) {
            int count = countLivingNeighbors(grid, i, j, enableWrapping);
            if (count < 2) {
                newGrid[i][j] = 0;
            } else if (count == 3) {
                newGrid[i][j] = 1;
            } else if (count >= 4) {
                newGrid[i][j] = 0;
            }
        }
    }

    grid = newGrid;
}