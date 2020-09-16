public class Board {

  // Constructor. You may assume that the constructor receives an n-by-n array
  // containing the n2 integers between 0 and n2 − 1, where 0 represents the blank
  // square. You may also assume that 2 ≤ n < 128.

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {

  }

  // string representation of this board
  public String toString() {
    return "";
  }

  // board dimension n
  public int dimension() {
    return 0;
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
  public int hamming() {
    return 0;
  }

  // The Manhattan distance between a board and the goal board is the sum of the
  // Manhattan distances (sum of the vertical and horizontal distance) from the
  // tiles to their goal positions.

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    return 0;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return false;
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
  public Iterable<Board> neighbors() {

  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    return new Board();
  }

  // Performance requirements. Your implementation should support all Board
  // methods in time proportional to n2 (or better) in the worst case.

  // unit testing (not graded)
  public static void main(String[] args) {

  }

}
