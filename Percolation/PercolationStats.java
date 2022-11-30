import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double [] fractions;
    private final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0.");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials <= 0.");
        }
        fractions = new double[trials];
        Percolation percolation = new Percolation(n);
        int countOpen = 0;

        for(int i = 0; i < trials; i++) {
            while(percolation.percolates() == false) {
                int col = StdRandom.uniformInt(n) + 1; // base 1
                int row = StdRandom.uniformInt(n) + 1; // base 1
                if(!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    countOpen++;
                }
            }
            fractions[i] = countOpen / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(fractions.length);
    }

    // high endpoint of 95% confidence interval 
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(fractions.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.print("mean                    = " + percolationStats.mean());
        StdOut.print("stddev                  = " + percolationStats.stddev());
        StdOut.print("95% confidence interval = " + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi());

    }
}