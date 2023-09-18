package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import static org.junit.Assert.assertFalse;

import java.lang.IllegalArgumentException;
import org.junit.Test;
import org.junit.Assert.*;

public class Percolation {

    private int N;
    private boolean[][] site;
    /**
     * index [0, N * N - 1]: row * N + col, col = k % N, row = k / N; index N * N: the top source(in
     * dupUF, it is the bottom source)
     */
    private WeightedQuickUnionUF UF;

    /** only to find whether a point connected to bottom */
    private WeightedQuickUnionUF dupUF;

    private boolean isPercolate = false;
    private int numOfOpen = 0;

    /**
     * create N-by-N grid, with all sites initially blocked
     *
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        site = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                assert (site[i][j] == false);
            }
        }
        UF = new WeightedQuickUnionUF(N * N + 1);
        dupUF = new WeightedQuickUnionUF(N * N + 1);
    }

    /**
     * open the site (row, col) if it is not open already
     *
     * @param row, col
     */
    public void open(int row, int col) {
        check_indices(row, col);
        if (site[row][col]) return;
        site[row][col] = true;
        numOfOpen++;
        if (row > 0 && site[row - 1][col]) {
            UF.union(index_map(row, col), index_map(row - 1, col));
            dupUF.union(index_map(row, col), index_map(row - 1, col));
        }
        if (row < N - 1 && site[row + 1][col]) {
            UF.union(index_map(row, col), index_map(row + 1, col));
            dupUF.union(index_map(row, col), index_map(row + 1, col));
        }
        if (col > 0 && site[row][col - 1]) {
            UF.union(index_map(row, col), index_map(row, col - 1));
            dupUF.union(index_map(row, col), index_map(row, col - 1));
        }
        if (col < N - 1 && site[row][col + 1]) {
            UF.union(index_map(row, col), index_map(row, col + 1));
            dupUF.union(index_map(row, col), index_map(row, col + 1));
        }
        if (row == N - 1) {
            dupUF.union(index_map(row, col), N * N);
        }
        if (row == 0) {
            UF.union(index_map(row, col), N * N);
        }
        if (UF.connected(index_map(row, col), N * N)
                && dupUF.connected(index_map(row, col), N * N)) {
            isPercolate = true;
        }
    }

    /**
     * @param row, col
     * @return is the site (row, col) open
     */
    public boolean isOpen(int row, int col) {
        check_indices(row, col);
        return site[row][col];
    }

    /**
     * @param row, col
     * @return whether the site (row, col) is full
     */
    public boolean isFull(int row, int col) {
        check_indices(row, col);
        if (!isOpen(row, col)) return false;
        return UF.connected(index_map(row, col), N * N);
    }

    /**
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    /**
     * @return whether the system percolates
     */
    public boolean percolates() {
        return isPercolate;
    }

    /**
     * check the (row, col) is in N times N array
     *
     * @param row, col
     */
    private void check_indices(int row, int col) {
        if (row < 0 || row >= N) {
            throw new IndexOutOfBoundsException(row);
        }
        if (col < 0 || col >= N) {
            throw new IndexOutOfBoundsException(col);
        }
    }

    /**
     * map the 2D coordinate(row, col) into 1D index
     *
     * @param row, col
     */
    private int index_map(int row, int col) {
        return row * N + col;
    }

    /** use for unit testing */
    public static void main(String[] args) {}
}
