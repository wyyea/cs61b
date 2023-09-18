package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private static int Times;
    private double Mean = -1;
    private double Stddev = -1;
    private double ConfidenceLow = -1;
    private double ConfidenceHigh = -1;
    private double[] stats;

    /**
     * perform T independent experiments on an N-by-N grid
     *
     * @param N, T, pf
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        int row, col;
        Times = T;
        stats = new double[Times];
        for (int t = 0; t < Times; t++) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                row = StdRandom.uniform(N);
                col = StdRandom.uniform(N);
                percolation.open(row, col);
            }
            stats[t] = (double) percolation.numberOfOpenSites() / (N * N);
        }
    }

    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        if (Mean != -1) return Mean;
        double sum = 0;
        for (double s : stats) sum += s;
        Mean = sum / Times;
        return Mean;
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        if (Times == 1) return Double.NaN;
        if (Stddev != -1) return Stddev;
        double sumOfStats = 0;
        double m = mean();
        for (double s : stats) sumOfStats += (s - m) * (s - m);
        Stddev = Math.sqrt(sumOfStats / (Times - 1));
        return Stddev;
    }

    /**
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLow() {
        if (ConfidenceLow != -1) return ConfidenceLow;
        double mu = mean(), sigma = stddev();
        return mu - 1.96 * sigma / Math.sqrt(Times);
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHigh() {
        if (ConfidenceHigh != -1) return ConfidenceHigh;
        double mu = mean(), sigma = stddev();
        return mu + 1.96 * sigma / Math.sqrt(Times);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(1024, 30, new PercolationFactory());
        System.out.println(
                "mean: "
                        + ps.mean()
                        + "\nstddev: "
                        + ps.stddev()
                        + "\nlow: "
                        + ps.confidenceLow()
                        + "\nhigh: "
                        + ps.confidenceHigh());
    }
}
