import java.util.Arrays;
import java.util.function.BiFunction;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ResizingArrayQueue;

public class Board {
  private int[][] tiles;
  private int n, hDist, mDist;

  /**
   * Initializes an n-puzzle board
   * 
   * @param tiles a 2D array of ints, arranged as [row][col]
   */
  public Board(int[][] tiles) {
    n = tiles.length;
    this.tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        this.tiles[i][j] = tiles[i][j];
    hDist = hamming();
    mDist = manhattan();
  }

  /**
   * Returns the String representation of the board as a grid
   * 
   * @return String representation of board as a grid, dimension is identified at
   *         top of grid
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++)
        s.append(String.format("%2d ", tiles[i][j]));
      s.append("\n");
    }
    return s.toString();
  }

  /**
   * Returns the n dimension of an n x n board
   * 
   * @return Dimension n, of n x n board
   */
  public int dimension() {
    return n;
  }

  /**
   * Returns the board's total "Manhattan distance", i.e. the sum of all tiles'
   * row + column distance to their goal positions
   * 
   * @return sum of all tiles' Manhattan Distances
   */
  public int manhattan() {
    return distance((row, col) -> {
      int properRow = properRow(tiles[row][col]);
      int properCol = properCol(tiles[row][col]);
      return Math.abs(row - properRow) + Math.abs(col - properCol);
    });
  }

  /**
   * Returns the Hamming distance, i.e. the total number of tiles in the wrong
   * position
   * 
   * @return the Hamming distance of the Board
   */
  public int hamming() {
    return distance((row, col) -> {
      int properRow = properRow(tiles[row][col]);
      int properCol = properCol(tiles[row][col]);
      return row != properRow || col != properCol ? 1 : 0;
    });
  }

  /**
   * Has the goal state been reached?
   * 
   * @return boolean value representing whether the board has reached its goal
   *         state
   */
  public boolean isGoal() {
    return mDist == 0;
  }

  /**
   * Are two Boards identical?
   * 
   * @param y the object being compared
   * @return boolean value representing whether the passed object is a board
   *         identical to the instance
   */
  public boolean equals(Object y) {
    if (y == this)
      return true;
    else if (y == null)
      return false;
    else if (y.getClass() != this.getClass())
      return false;
    else {
      Board that = (Board) y;
      return Arrays.deepEquals(this.tiles, that.tiles);
    }
  }

  /**
   * Returns the "neighbors" of a board state, i.e. all possible moves from the
   * instance's current board state
   *
   * @return iterable collection of all board neighbors (between 2 - 4 neighbors
   *         possible from any given state)
   */
  public Iterable<Board> neighbors() {
    ResizingArrayQueue<Board> q = new ResizingArrayQueue<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] == 0) {
          for (int row = i - 1; row <= i + 1; row += 2)
            if (row >= 0 && row < n)
              q.enqueue(getNeighbor(i, j, row, j));
          for (int col = j - 1; col <= j + 1; col += 2)
            if (col >= 0 && col < n)
              q.enqueue(getNeighbor(i, j, i, col));
        }
      }
    }
    return q;
  }

  /**
   * Returns a board "twin", i.e. a board obtained by exchanging any pair of
   * tiles. Either a board or its twin will be unsolveable.
   * 
   * @return the board "twin" obtained by swapping the 0th and 1st columns of the
   *         0th row
   */
  public Board twin() {
    Board twin;
    int swp;
    int[][] newBoard = new int[n][n];
    for (int row = 0; row < n; row++)
      for (int col = 0; col < n; col++)
        newBoard[row][col] = this.tiles[row][col];
    twin = new Board(newBoard);
    swp = twin.tiles[0][0];
    twin.tiles[0][0] = twin.tiles[0][1];
    twin.tiles[0][1] = swp;
    return twin;
  }

  // ************************ PRIVATE METHODS ************************
  // helper method to determine the goal row of the passed tile
  private int properRow(int tile) {
    return (tile - 1) / n;
  }

  // helper method to determine the goal column of the passed tile
  private int properCol(int tile) {
    return (tile - 1) % n;
  }

  // helper method to facilitate the Manhattan and Hamming distance methods
  private int distance(BiFunction<Integer, Integer, Integer> func) {
    int count = 0;
    for (int row = 0; row < n; row++)
      for (int col = 0; col < n; col++)
        count += tiles[row][col] == 0 ? 0 : func.apply(row, col);
    return count;
  }

  // helper function for neighbors()
  // performs the neighbor swap
  private Board getNeighbor(int emptyRow, int emptyCol, int swpRow, int swpCol) {
    int[][] neighborTiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        neighborTiles[i][j] = tiles[i][j];
    neighborTiles[emptyRow][emptyCol] = neighborTiles[swpRow][swpCol];
    neighborTiles[swpRow][swpCol] = 0;
    return new Board(neighborTiles);
  }

  // ************************ unit testing ************************
  public static void main(String[] args) {
    // test out-of-place tiles
    int[] row1 = { 0, 1, 2 };
    int[] row2 = { 3, 4, 5 };
    int[] row3 = { 6, 7, 8 };
    int[][] tiles = { row1, row2, row3 };

    Board b = new Board(tiles);
    StdOut.println(b.toString());
    StdOut.println("n:\t3 == " + b.dimension());
    StdOut.println("manhattan:\t12 == " + b.manhattan());
    StdOut.println("hamming:\t8 == " + b.hamming());
    StdOut.println("isGoal():\tfalse == " + b.isGoal());

    StdOut.println();

    // test in-place tiles
    int[] row4 = { 1, 2, 3 };
    int[] row5 = { 4, 5, 6 };
    int[] row6 = { 7, 8, 0 };
    int[][] tiles2 = { row4, row5, row6 };
    Board b2 = new Board(tiles2);
    StdOut.println(b2.toString());
    StdOut.println("n:\t3 == " + b2.dimension());
    StdOut.println("manhattan:\t0 == " + b2.manhattan());
    StdOut.println("hamming:\t0 == " + b2.hamming());
    StdOut.println("isGoal():\ttrue == " + b2.isGoal());

    StdOut.println();

    // test equals()
    int[] row7 = { 0, 1, 2 };
    int[] row8 = { 3, 4, 5 };
    int[] row9 = { 6, 7, 8 };
    int[][] tiles3 = { row7, row8, row9 };
    Board b3 = new Board(tiles3);
    StdOut.println("equals():\tfalse == " + b.equals(b2));
    StdOut.println("equals():\ttrue == " + b.equals(b3));

    StdOut.println();

    // test twins
    StdOut.println("TWINS!\n" + b.toString() + '\n' + b.twin().toString());

    StdOut.println();

    // test neighbors
    int[] row10 = { 4, 1, 2 };
    int[] row11 = { 8, 0, 5 };
    int[] row12 = { 6, 7, 3 };
    int[][] tiles4 = { row10, row11, row12 };
    Board b4 = new Board(tiles4);
    StdOut.println("NEIGHBORS!");
    int i = 0;
    for (Board board : b4.neighbors()) {
      StdOut.println("Starting:");
      StdOut.println(b4.toString());
      StdOut.println("Neighbor" + (++i) + ":");
      StdOut.println(board.toString());
    }
  }
}
