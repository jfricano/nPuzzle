<ul>
  <li><a target="_blank" href="https://algs4.cs.princeton.edu/code/" >Info and how to install environment</a></li>
  <li><a href="https://algs4.cs.princeton.edu/code/javadoc/" target="_blank" rel="noopener noreferrer">algs4.jar Documentation</a></li>
  <li><a href="https://algs4.cs.princeton.edu/code/algs4.jar">Download algs4.jar package</a></li>
</ul>

Challenge:  Write a program to solve the 8-puzzle problem (and its natural generalizations) using the <a href="https://en.wikipedia.org/wiki/A*_search_algorithm">A<sup>*</sup> search algorithm</a>.

<b>The problem.</b> The 8-puzzle is a sliding puzzle that is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8, plus a blank square. The goal is to rearrange the tiles so that they are in row-major order, using as few moves as possible. You are permitted to slide tiles either horizontally or vertically into the blank square. The following diagram shows a sequence of moves from an initial board (left) to the goal board (right).

Board.java
================================
A data type that models an n-by-n board with sliding tiles and provides the API noted below.

A board is representing by an nxn two-dimensional array.  Each tile in the board is represented by a positive integer from 1 to n<sup>2</sup>, with 0 representing the empty space on the game board (0 is not a tile).  

<i>Performance specifications:</i>   All Board methods run in time proportional to n<sup>2</sup> (or better) in the worst case.

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
                                           
    // string representation of this board
    public String toString()

    // board dimension n
    public int dimension()

    // number of tiles out of place
    public int hamming()

    // sum of Manhattan distances between tiles and goal
    public int manhattan()

    // is this board the goal board?
    public boolean isGoal()

    // does this board equal y?
    public boolean equals(Object y)

    // all neighboring boards
    public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()

    // unit testing (not graded)
    public static void main(String[] args)


Solver.java
================================
An implementation of the A<sup>*</sup> search algorithm to solve n-by-n slider puzzles providing the API below.  The Solver will find the solution to the given n-puzzle problem utilizing the shortest sequence of moves necessary to achieve the solution.  The solver also detects and reports on unsolvable puzzles.

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)

    // is the initial board solvable? 
    public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()

    // test client 
    public static void main(String[] args)

