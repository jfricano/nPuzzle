import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
  private Node initial;
  private Node solution;

  private class Node {
    Board board;
    Node previous;
    int moves, manhattan, priority;

    public Node(Board b, Node previous, int moves) {
      board = b;
      this.previous = previous;
      this.moves = moves;
    }
  }
  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) throw new IllegalArgumentException();
    b = new Board;
  }

  // To detect such situations, use the fact that boards are divided into two
  // equivalence classes with respect to reachability:
  // Those that can lead to the goal board
  // Those that can lead to the goal board if we modify the initial board by
  // swapping any pair of tiles (the blank square is not a tile).

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    MinPQ j = new MinPQ();
    return true;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (!isSolvable())
      return -1;
    else
      return 0;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!isSolvable())
      return null;
  }

  // test client (see below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }

  }

}
