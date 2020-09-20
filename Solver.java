/******************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver input.txt
 *  Dependencies: java.util.Comparator
 *                edu.princeton.cs.algs4.In
 *                edu.princeton.cs.algs4.MinPQ
 *                edu.princeton.cs.algs4.ResizingArrayStack
 *                edu.princeton.cs.algs4.StdOut
 *  
 *  Implementation to solve n-puzzle
 *
 *  Note:  This class requires Princeton University's algs4.jar library
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
  private final Node initial;     // initial game node
  private final Node end;         // solution game node (null if no solution)

  // ****************** PUBLIC METHODS ******************
  /**
   * Initializes an n-puzzle board solver
   * 
   * @param initial the initial n-puzzle board object
   */
  public Solver(final Board initial) {
    if (initial == null) throw new IllegalArgumentException();
    this.initial = new Node(initial, null, 0);
    end = solve();
  }

  /**
   * is the initial board solvable?
   * 
   * @return is the initial board solvable?
   *  */ 
  public boolean isSolvable() {
    return end != null;
  }

  /**
   * Minimum number of moves to solve initial board (-1 if unsolvable)
   * 
   * @return min number of moves to solve initial board; -1 if unsolvable
   */
  public int moves() {
    if (end == null) return -1;
    return end.moves;
  }

  /**
   * The shortest sequence of moves to solve the given n-puzzle game board
   * 
   * @return shortest sequence of moves to solve the given n-puzzle game board
   */
  // crawls up the game tree
  // from the end node to the root node (initial board)
  // builds and returns stack to solution property
  public Iterable<Board> solution() {
    if (end == null) return null;

    final ResizingArrayStack<Board> result = new ResizingArrayStack<>();
    Node addNode = end;
    while (addNode != null) {
      result.push(addNode.board);
      addNode = addNode.previous;
    }
    return result;
  }

  // ****************** PRIVATE METHODS ******************
  // solve applies the A* algorithm to the initial gameboard
  // until either the initial board or its twin leads to a solution
  // all boards satisfy the property that only a board or its twin is solvable
  // thus, we test both to determine if a board is solvable
  private Node solve() {
    // priority queues to hold the incoming nodes
    final MinPQ<Node> pq = new MinPQ<>();
    final MinPQ<Node> pqTwin = new MinPQ<>();

    Node searchNode = initial; // the board node currently being processed
    final Board twinBoard = searchNode.board.twin();
    Node twinNode = new Node(twinBoard, null, 0); // the twin node being processed

    pq.insert(searchNode);
    pqTwin.insert(twinNode);

    // the A* algorithm
    // dequeues the minimum
    // adds the neighbors of the dequeued board
    // depends on priority function returned from Comparator method prioritize()
    while (!searchNode.board.isGoal() && !twinNode.board.isGoal()) {
      // search given board
      searchNode = pq.delMin();
      for (final Board b : searchNode.board.neighbors())
        // memory optimization:
        // only add a neighbor if not equivalent to the searchNode's parent node
        if (searchNode.previous == null || !b.equals(searchNode.previous.board))
          pq.insert(new Node(b, searchNode, searchNode.moves + 1));

      // search twin node
      twinNode = pqTwin.delMin();
      for (final Board b : twinNode.board.neighbors())
        if (twinNode.previous == null || !b.equals(twinNode.previous.board)) 
          pqTwin.insert(new Node(b, twinNode, twinNode.moves + 1));
    }
    if (twinNode.board.isGoal()) searchNode = null;
    return searchNode;
  }

    // ****************** PRIVATE NODE CLASS ******************
    // boards are stored as nodes so to build a tree of nodes
    // once the goal node is reached, can trace the solution via the previous property
    private class Node implements Comparable<Node> {
      Board board;          // n-puzzle game board
      Node previous;        // previous board node in sequence
      int moves, priority;  // number of moves to get to current board
                            // manhattan priority of board (manhattan distance + moves)
  
      public Node(final Board b, final Node previous, final int moves) {
        board = b;
        this.previous = previous;
        this.moves = moves;
        priority = this.moves + board.manhattan();
      }

      public int compareTo(Node cmpNode) {
        return this.priority - cmpNode.priority;
      }
    }
    
    // ****************** TEST CLIENT ******************
    /**
     * Solves an n-puzzle game board
     * Logs to console the sequence of moves leading to solution
     * Logs 'No solution possible' if no solution
     * 
     * @param args  the name of the text file containing the initial game board. 
     *              Refer to Board class to see game board format
     */
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
