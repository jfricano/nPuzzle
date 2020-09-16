import java.util.Arrays;
import java.util.function.BiFunction;
import edu.princeton.cs.algs4.StdOut;

public class Board {
  private int[][] tiles;
  private int n, hDist, mDist;
  // Constructor. You may assume that the constructor receives an n-by-n array
  // containing the n2 integers between 0 and n2 − 1, where 0 represents the blank
  // square. You may also assume that 2 ≤ n < 128.

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    n = tiles.length;
    this.tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        this.tiles[i][j] = tiles[i][j];
    hDist = hamming();
    mDist = manhattan();
  }

  // string representation of this board
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

  // board n n
  public int dimension() {
    return n;
  }

  // Hamming and Manhattan distances. To measure how close a board is to the goal
  // board, we define two notions of distance.

  // The Hamming distance betweeen a board and the goal board is the number of
  // tiles in the wrong position.

  // number of tiles out of place
  public int manhattan() {
    return distance((row, col) -> {
      int properRow = properRow(tiles[row][col]);
      int properCol = properCol(tiles[row][col]);
      return Math.abs(row - properRow) + Math.abs(col - properCol);
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
    return mDist == 0;
  }

  // does this board equal y?
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

  // Neighboring boards. The neighbors() method returns an iterable containing the
  // neighbors of the board. Depending on the location of the blank square, a
  // board can have 2, 3, or 4 neighbors.

  // all neighboring boards
  // public Iterable<Board> neighbors() {

  // }

  // a board that is obtained by exchanging any pair of tiles
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
  private int properRow(int tile) {
    return (tile - 1) / n;
  }

  private int properCol(int tile) {
    return (tile - 1) % n;
  }

  private int distance(BiFunction<Integer, Integer, Integer> func) {
    int count = 0;
    for (int row = 0; row < n; row++)
      for (int col = 0; col < n; col++)
        count += tiles[row][col] == 0 ? 0 : func.apply(row, col);
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

    // test neighbors
  }
}
