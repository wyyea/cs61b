package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import static org.junit.Assert.assertFalse;

import java.lang.IllegalArgumentException;
import org.junit.Test;
import org.junit.Assert.*;

public class Percolation {

    private int N;
    public boolean[][] site;
    /**
     * N * N, row * N + col, col = k % N, row = k / N
     */
    private WeightedQuickUnionUF UF;

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
                site[i][j] = false;
            }
        }
        UF = new WeightedQuickUnionUF(N * N);
    }

    /**
     * open the site (row, col) if it is not open already
     * 
     * @param row, col
     */
    public void open(int row, int col) {
        check_indices(row, col);
        if (site[row][col])
            return;
        site[row][col] = true;
        if (row > 0 && site[row - 1][col]) {
            UF.union(index_map(row, col), index_map(row - 1, col));
        }
        if (row < N-1 && site[row + 1][col]) {
            UF.union(index_map(row, col), index_map(row + 1, col));
        }
        if (col > 0 && site[row][col - 1]) {
            UF.union(index_map(row, col), index_map(row, col - 1));
        }
        if (col < N-1 && site[row][col + 1]) {
            UF.union(index_map(row, col), index_map(row, col + 1));
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
        if(!isOpen(row, col))
            return false;
        for(int i = 0; i < N; i++){
            if(isOpen(0, i) && UF.connected(index_map(row, col), index_map(0, i)))
                return true;
        }
        return false;
    }

    /**
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        int cnt = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (site[i][j])
                    cnt++;
        return cnt;
    }

    /**
     * @return whether the system percolates
     */
    public boolean percolates() {
        for (int i = 0; i < N; i++) {
            if (isFull(N - 1, i))
                return true;
        }
        return false;
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

    /**
     * use for unit testing
     */
    public static void main(String[] args) {
    }
}
