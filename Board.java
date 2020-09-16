import java.util.function.BiFunction;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  private int[][] tiles;
  private int dimension, hDist, mDist;
  // Constructor. You may assume that the constructor receives an n-by-n array
  // containing the n2 integers between 0 and n2 − 1, where 0 represents the blank
  // square. You may also assume that 2 ≤ n < 128.

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    dimension = tiles.length;
    this.tiles = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        this.tiles[i][j] = tiles[i][j];
      }
    }
    hDist = hamming();
    mDist = manhattan();
  }

  // string representation of this board
  public String toString() {
    String output = "";
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        output += tiles[i][j];
        // if j mod tiles[i].length == tiles[i].length - 1 add newline
        // else add space
        output += j % dimension == dimension - 1 ? (i % dimension == dimension - 1 ? "" : "\n") : "  ";
      }
    }
    return output;
  }

  // board dimension n
  public int dimension() {
    return dimension;
  }

  // Hamming and Manhattan distances. To measure how close a board is to the goal
  // board, we define two notions of distance.

  // Caching the Hamming and Manhattan priorities. To avoid recomputing the
  // Manhattan priority of a search node from scratch each time during various
  // priority queue operations, pre-compute its value when you construct the
  // search node; save it in an instance variable; and return the saved value as
  // needed. This caching technique is broadly applicable: consider using it in
  // any situation where you are recomputing the same quantity many times and for
  // which computing that quantity is a bottleneck operation.

  // The Hamming distance betweeen a board and the goal board is the number of
  // tiles in the wrong position.

  // number of tiles out of place
  public int manhattan() {
    return distance((row, col) -> {
      int properRow = properRow(tiles[row][col]);
      int properCol = properCol(tiles[row][col]);
      return Math.abs(row - properRow) 
             + Math.abs(col - properCol);
    });
  }

  // The Manhattan distance between a board and the goal board is the sum of the
  // Manhattan distances (sum of the vertical and horizontal distance) from the
  // tiles to their goal positions.

  // sum of Manhattan distances between tiles and goal
  public int hamming() {
    return distance((row, col) -> {
      int properRow = properRow(tiles[row][col]);
      int properCol = properCol(tiles[row][col]);
      return row != properRow || col != properCol ? 1 : 0;
    });
  }

  // is this board the goal board?
  public boolean isGoal() {
    return manhattan() == 0;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    return false;
  }

  // Neighboring boards. The neighbors() method returns an iterable containing the
  // neighbors of the board. Depending on the location of the blank square, a
  // board can have 2, 3, or 4 neighbors.

  // The critical optimization. A* search has one annoying feature: search nodes
  // corresponding to the same board are enqueued on the priority queue many times
  // (e.g., the bottom-left search node in the game-tree diagram above). To reduce
  // unnecessary exploration of useless search nodes, when considering the
  // neighbors of a search node, don’t enqueue a neighbor if its board is the same
  // as the board of the previous search node in the game tree.

  // all neighboring boards
  // public Iterable<Board> neighbors() {

  // }

  // a board that is obtained by exchanging any pair of tiles
  // public Board twin() {
  // return new Board();
  // }

  // ************************ PRIVATE METHODS ************************
  private int properRow(int tile) {
    return tile == 0 ? dimension - 1 : (tile - 1) / dimension;
  }

  private int properCol(int tile) {
    return tile == 0 ? dimension - 1 : (tile - 1) % dimension;
  }

  private int distance(BiFunction<Integer, Integer, Integer> func) {
    int count = 0;
    for (int row = 0; row < dimension; row++) {
      for (int col = 0; col < dimension; col++) {
        count += func.apply(row, col);
      }
    }
    return count;
  }

  // Performance requirements. Your implementation should support all Board
  // methods in time proportional to n2 (or better) in the worst case.

  // unit testing (not graded)
  public static void main(String[] args) {
    // test out-of-place tiles
    int[] row1 = { 0, 1, 2 };
    int[] row2 = { 3, 4, 5 };
    int[] row3 = { 6, 7, 8 };
    int[][] tiles = { row1, row2, row3 };

    Board b = new Board(tiles);
    StdOut.println(b.toString());
    StdOut.println("dimension:\t3 == " + b.dimension());
    StdOut.println("manhattan:\t16 == " + b.manhattan());
    StdOut.println("hamming:\t9 == " + b.hamming());
    StdOut.println("isGoal():\tfalse == " + b.isGoal());

    StdOut.println();

    // test in-place tiles
    int[] row4 = { 1, 2, 3 };
    int[] row5 = { 4, 5, 6 };
    int[] row6 = { 7, 8, 0 };
    int[][] tiles2 = { row4, row5, row6 };
    Board b2 = new Board(tiles2);
    StdOut.println(b2.toString());
    StdOut.println("dimension:\t3 == " + b2.dimension());
    StdOut.println("manhattan:\t0 == " + b2.manhattan());
    StdOut.println("hamming:\t0 == " + b2.hamming());
    StdOut.println("isGoal():\ttrue == " + b2.isGoal());

    // test equals
    // test neighbors
    // test twin
  }
}
