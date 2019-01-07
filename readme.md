# Imitation

### Rules
The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead, (or populated and unpopulated, respectively). Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:

1. Any live cell with fewer than two live neighbors dies, as if by underpopulation.
2. Any live cell with two or three live neighbors lives on to the next generation.
3. Any live cell with more than three live neighbors dies, as if by overpopulation.
4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.

### Run
Compile:

    javac -sourcepath ./src -d bin src/com/github/mamadaliev/Main.java


Run:

    java -classpath ./bin com.github.mamadaliev.Main

### Demo:
![Demo](https://octodex.github.com/images/tentocats.jpg)
