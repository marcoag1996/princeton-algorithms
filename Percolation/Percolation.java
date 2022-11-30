import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF normalQU;
    private final boolean []isOpen;
    private final int n;
    private int openCount;
    private final int startIndex;
    private final int endIndex;
    //creates n-by-n grid, all sites blocked
    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        this.n = n;
        this.normalQU = new WeightedQuickUnionUF(n*n+2);//add one at the top and bottom, main initial point
        this.isOpen = new boolean[n*n+2];
        this.openCount = 0;
        this.startIndex = 0;
        this.endIndex = n*n+1;
        this.isOpen[startIndex] = true;
        this.isOpen[endIndex] = true;
    }

    public boolean checkBounds(int row, int col) {
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException("Row is out of bounds.");
        }
        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException("Column is out of bounds.");
        }
        return true;
    }

    //opens the site (row,col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        int index = getIndex(row, col);
        this.isOpen[index] = true;
        openCount++;

        //connect it to the initial point if row 0
        if(row == 0) {
            this.normalQU.union(index, this.startIndex);
        }
        if(row == this.n) {
            this.normalQU.union(index, this.endIndex);
        }
        addUnion(row, col);
    }

    public int getIndex(int row, int col) {
        checkBounds(row, col);
        return (row-1) * this.n + col;
    }

    public void addUnion(int row, int col) {
        //check for valid connections to neighbors
        int[] cols = {col,col,col-1,col+1};
        int[] rows = {row-1,row+1,row,row};
        for(int i = 0; i < 4; i++) {
            // neighbor is valid coordinate and is open
            if(0 < rows[i] && rows[i] <= n && 0 < cols[i] && cols[i] <= n && isOpen(rows[i], cols[i])) {
                this.normalQU.union(getIndex(row, col), getIndex(rows[i], cols[i]));
            }
        }
    }

    //is the site open(row,col)
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return this.isOpen[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.normalQU.find(startIndex) == this.normalQU.find(getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.normalQU.find(this.startIndex) == this.normalQU.find(this.endIndex);
    }

    // test client (optional)
    public static void main(String[] args) {
        StdOut.println("Please run PercolationStats instead.");
        int size = Integer.parseInt(args[0]);

        Percolation percolation = new Percolation(size);
        int argCount = args.length;
        for (int i = 1; argCount >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            StdOut.printf("Adding row: %d  col: %d %n", row, col);
            percolation.open(row, col);
            if (percolation.percolates()) {
                StdOut.printf("%nThe System percolates %n");
            }
            argCount -= 2;
        }
        if (!percolation.percolates()) {
            StdOut.print("Does not percolate %n");
        }
        /*
        int N = StdIn.readInt();
        Percolation percolation = new Percolation(N);

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            percolation.open(p, q);
            if (percolation.percolates()) {
                StdOut.println("System percolates!");
            } else {
                StdOut.println("System does not percolate yet.");
            }
            StdOut.println(p + " " + q);
        }
        StdOut.println("Finished!");
         * 
         */
    }
}
