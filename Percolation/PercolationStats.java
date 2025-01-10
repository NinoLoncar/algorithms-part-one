import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double[] percolationTresholds;
    private int trials;

    public PercolationStats(int n, int trials) {
        checkArgs(n, trials);
        percolationTresholds = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            percolationTresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

    private void checkArgs(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
    }

    public double mean() {
        return StdStats.mean(percolationTresholds);
    }

    public double stddev() {
        return StdStats.stddev(percolationTresholds);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }
}
