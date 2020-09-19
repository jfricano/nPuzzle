import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private final Node initial;
  private final Node end;
  private final ResizingArrayStack<Board> solution;

  // ****************** PRIVATE INNER CLASS ******************
  private class Node {
    Board board;
    Node previous;
    int moves, manhattan, priority;

    public Node(final Board b, final Node previous, final int moves) {
      board = b;
      this.previous = previous;
      this.moves = moves;
      manhattan = board.manhattan();
      priority = this.moves + manhattan;
    }
  }

  // ****************** PUBLIC METHODS ******************
  // find a solution to the initial board (using the A* algorithm)
  public Solver(final Board initial) {
    this.initial = new Node(initial, null, 0);
    end = solve();
    solution = getSolution();
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return solution != null;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (solution == null) return -1;
    else return end.moves;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    return solution;
  }

  // ****************** PRIVATE HELPER METHODS ******************
  private ResizingArrayStack<Board> getSolution() {
    if (end == null) return null;

    final ResizingArrayStack<Board> result = new ResizingArrayStack<>();
    Node addNode = end;
    while (addNode != null) {
      result.push(addNode.board);
      addNode = addNode.previous;
    }
    return result;
  }

  private Comparator<Node> prioritize() {
    return (Node a, Node b) -> {
      if (a.priority < b.priority)
        return -1;
      else if (a.priority > b.priority)
        return 1;
      else
        return 0;
    };
  }

  private Node solve() {
    MinPQ<Node> pq = new MinPQ<>(prioritize());
    Node searchNode = initial;
    pq.insert(searchNode);

    while (!searchNode.board.isGoal()) {
      searchNode = pq.delMin();
      for (Board b : searchNode.board.neighbors()) {
        if (searchNode.previous == null || !b.equals(searchNode.previous.board)) {
          pq.insert(new Node(b, searchNode, searchNode.moves + 1));
        } 
      }
    }

    return searchNode;
  }

  // test client (see below)
  public static void main(final String[] args) {
    // create initial board from file
    final In in = new In(args[0]);
    final int n = in.readInt();
    final int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        tiles[i][j] = in.readInt();
    final Board initial = new Board(tiles);

    // solve the puzzle
    final Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (final Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
