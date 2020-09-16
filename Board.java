import edu.princeton.cs.algs4.StdOut;

public class Board {
  private int[][] tiles;
  private int dimension;
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
  }

  // string representation of this board
  public String toString() {
    String output = "";
    for (int i = 0; i < tiles.length; i++) {
      for (int j = 0; j < tiles[i].length; j++) {
        output += tiles[i][j];
        // if j mod tiles[i].length == tiles[i].length - 1 add newline
        // else add space
        output += j % tiles[i].length == tiles[i].length - 1 ? '\n' : "  ";
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
  // public int hamming() {
  //   int count = 0;
  //   for (int i = 0; i < dimension(); i++) {
  //     if (i == dimension() - 1 && tiles[i][dimension() - 1] != 0) {
  //       count++;
  //     }
  //     for (int j = 0; j < tiles[i].length; j++) {
  //       if (i == dimension() - 1 && j == dimension() - 1)
  //         continue;
  //       if (i == dimension() - 1 && j == dimension() - 1 && tiles[i][j] != 0)
  //         count++;
  //     }
  //   }
  //   return count;
  // }

  // The Manhattan distance between a board and the goal board is the sum of the
  // Manhattan distances (sum of the vertical and horizontal distance) from the
  // tiles to their goal positions.

  // sum of Manhattan distances between tiles and goal
  // public int manhattan() {
  //   int count = 0;
  //   for (int i = 0; i < tiles.length; i++) {
  //     for (int j = 0; j < tiles[i].length; j++) {
  //       // if tile is 0
  //       if (tiles[i][j] == 0) count += (tiles.length - i) + (tiles[i].length - j);
  //       // if tile is not 0
  //     }
  //   }
  //   return count;
  // }

  // is this board the goal board?
  public boolean isGoal() {
    int lastPos = dimension - 1;
    for (int i = 0; i < dimension; i++) {
      if (i == lastPos && tiles[i][lastPos] != 0) {
        return false;
      }
      for (int j = 0; j < dimension; j++) {
        if (i == lastPos && j == lastPos)
          continue;
        if (i == lastPos && j == lastPos && tiles[i][j] != 0)
          return false;
      }
    }
    return true;
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

  // Performance requirements. Your implementation should support all Board
  // methods in time proportional to n2 (or better) in the worst case.

  // unit testing (not graded)
  public static void main(String[] args) {
    // test constructor, toSTring()
    int[] row1 = { 0, 1, 2 };
    int[] row2 = { 3, 4, 5 };
    int[] row3 = { 6, 7, 8 };
    int[][] tiles = { row1, row2, row3 };

    Board b = new Board(tiles);
    StdOut.println(b.toString());

    // test dimension
    StdOut.println("dimension:  " + b.dimension());
    StdOut.println();

    // test isGoal
    StdOut.println("isGoal(): false == " + b.isGoal());
    int[] row4 = { 1, 2, 3 };
    int[] row5 = { 4, 5, 6 };
    int[] row6 = { 7, 8, 0 };
    int[][] tiles2 = { row4, row5, row6 };
    b = new Board(tiles2);
    StdOut.println("isGoal(): true == " + b.isGoal());

    // test hamming
    // test manhattan
    // test equals
    // test neighbors
    // test twin
  }
}
