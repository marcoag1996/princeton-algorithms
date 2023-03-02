/* *****************************************************************************
 *  Name: Marco Sebastian Aguilar Viera
 *  Date: 01-March-2023
 *  Description: API for a board
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;
    private int blankRow;
    private int blankCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    // 0 value represents a blank space
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }
        this.tiles = copyOf(tiles);
        this.n = tiles.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    blankRow = row;
                    blankCol = col;
                    return;
                }
            }
        }
    }

    private int[][] copyOf(int[][] matrix) {
        int[][] clone = new int[matrix.length][];
        for (int row = 0; row < matrix.length; row++) {
            clone[row] = matrix[row].clone();
        }
        return clone;
    }


    // string representation of this board
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(n).append('\n');
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                output.append(String.format("%2d ", tiles[row][col]));
            }
            output.append('\n');
        }
        return output.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    public int hamming() {
        int result = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == blankRow && col == blankCol) {
                    continue;
                }
                if (tiles[row][col] != (n * row + col + 1)) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int result = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == blankRow && col == blankCol) {
                    continue;
                }
                result += manhattan(row, col);
            }
        }
        return result;
    }

    private int manhattan(int row, int col) {
        int destVal = tiles[row][col] - 1;
        int destRow = destVal / n;
        int destCol = destVal % n;
        return Math.abs(destRow - row) + Math.abs(destCol - col);
    }

    // is this board the goal board?
    public boolean isGoal() {
        // return hamming() == 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == blankRow && col == blankCol) {
                    continue;
                }
                if (tiles[row][col] != (n * row + col + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.blankCol != this.blankCol)
            return false;
        if (that.blankRow != this.blankRow)
            return false;
        if (that.n != this.n)
            return false;
        for (int row = 0; row < that.n; row++) {
            for (int col = 0; col < that.n; col++) {
                if (this.tiles[row][col] != that.tiles[row][col])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        if (blankRow > 0) { // can change to the up
            // int[][] north = tiles.clone();
            int[][] north = copyOf(tiles);
            swap(north, blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(new Board(north));
        }
        if (blankRow < n - 1) { // can change down
            // int[][] south = tiles.clone();
            int[][] south = copyOf(tiles);
            swap(south, blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(new Board(south));
        }
        if (blankCol > 0) { // can change to the west <-
            // int[][] west = tiles.clone();
            int[][] west = copyOf(tiles);
            swap(west, blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(new Board(west));
        }
        if (blankCol < n - 1) { // can change to the east ->
            // int[][] east = tiles.clone();
            int[][] east = copyOf(tiles);
            swap(east, blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(new Board(east));
        }
        return neighbors;
    }

    private void swap(int[][] v, int rowA, int colA, int rowB, int colB) {
        int aux = v[rowA][colA];
        v[rowA][colA] = v[rowB][colB];
        v[rowB][colB] = aux;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // int[][] cloned = tiles.clone();
        int[][] cloned = copyOf(tiles);
        if (blankRow != 0) {
            swap(cloned, 0, 0, 0, 1);
        }
        else {
            swap(cloned, 1, 0, 1, 1);
        }
        return new Board(cloned);
    }

    // extra method to visualize(n added in the assignment)
    /*
    private void draw(double x, double y) {
        StdDraw.text(x - .03 * n, y, String.valueOf(n));
        for (int i = 0; i < n; i++) {
            y -= .03;
            x -= .03 * n;
            for (int j = 0; j < n; j++) {
                StdDraw.text(x, y, String.valueOf(tiles[i][j]));
                x += 0.03;
            }
        }
    }

     */

    // unit testing (not graded)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] set = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                set[i][j] = StdIn.readInt();
            }
        }
        Board board = new Board(set);
        System.out.println(board.dimension());
        System.out.println(board.hamming());
        System.out.println(board.isGoal());
        System.out.println(board.manhattan());
        for (Board k : board.neighbors()) {
            System.out.println(k);
        }
        System.out.println(board.twin());
    }

}
