/* *****************************************************************************
 *  Name: Marco Sebastian Aguilar Viera
 *  Date: 01-March-2023
 *  Description: Implementation of A* algorithm
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

public class Solver {
    private boolean solvable;
    private MinPQ<NodeBoard> minPQ;
    private NodeBoard solutionNode;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solutionNode = null;
        minPQ = new MinPQ<>();
        minPQ.insert(new NodeBoard(initial, 0, null));
        while (true) {
            NodeBoard currNode = minPQ.delMin();
            Board currBoard = currNode.getBoard();
            // Check if we found the solution
            if (currBoard.isGoal()) {
                solvable = true;
                solutionNode = currNode;
                break;
            }
            // Check if is solvable, if swap any pair is goal(not solvable)
            if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
                solvable = false;
                break;
            }

            // Insert possible candidates but the previous
            int moves = currNode.getMoves();
            Board prevBoard = moves > 0 ? currNode.prev().getBoard() : null;

            for (Board candidates : currBoard.neighbors()) {
                if (prevBoard != null && candidates.equals(prevBoard)) { // can't be added
                    continue;
                }
                minPQ.insert(new NodeBoard(candidates, moves + 1, currNode));
            }
        }
    }

    private class NodeBoard implements Comparable<NodeBoard> {
        private final NodeBoard prev;
        private final Board board;
        private final int moves; // moves done until this node

        NodeBoard(Board board, int moves, NodeBoard prev) {
            this.prev = prev;
            this.board = board;
            this.moves = moves;
        }

        @Override
        public int compareTo(NodeBoard that) {
            return this.priority() - that.priority();
        }

        public int priority() { // using manhattan distance
            return board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public NodeBoard prev() {
            return prev;
        }

        public int getMoves() {
            return moves;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? solutionNode.getMoves() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) return null;
        Deque<Board> solution = new LinkedList<>();
        NodeBoard currNode = solutionNode;
        while (currNode != null) {
            solution.addFirst(currNode.getBoard());
            currNode = currNode.prev();
        }
        return solution;
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
