import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n, numberOfOpenSites = 0;
    private boolean[] grid;
    private WeightedQuickUnionUF unionFind;

    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException();
        this.n = n;
        grid = new boolean[n * n];
        unionFind = new WeightedQuickUnionUF(n * n + 2);

    }

    public static void main(String[] args) {
    }

    private void checkRowAndColumn(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
    }

    private int getIndex(int row, int col) {
        return ((row - 1) * n + col - 1);
    }

    private void createUnions(int index) {

        if (index < n) {
            unionFind.union(index + 1, 0);
        }
        if (index >= (n * n) - n) {
            unionFind.union(index + 1, (n * n + 1));
        }
        if ((index % n) != 0 && grid[index - 1]) {
            unionFind.union(index + 1, index);
        }
        if ((index + 1) % n != 0 && grid[index + 1]) {
            unionFind.union(index + 1, index + 2);
        }
        if (index >= n && grid[index - n]) {
            unionFind.union(index + 1, index - n + 1);
        }
        if (index < (n * n - n) && grid[index + n]) {
            unionFind.union(index + 1, index + n + 1);
        }
    }

    public void open(int row, int col) {
        checkRowAndColumn(row, col);
        int i = getIndex(row, col);
        if (!grid[i]) {
            grid[i] = true;
            numberOfOpenSites++;
            createUnions(i);
        }
    }

    public boolean isOpen(int row, int col) {
        checkRowAndColumn(row, col);
        int i = getIndex(row, col);
        return grid[i];
    }

    public boolean isFull(int row, int col) {
        checkRowAndColumn(row, col);
        int i = getIndex(row, col);
        return unionFind.find(0) == unionFind.find(i + 1);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return unionFind.find(0) == unionFind.find(n * n + 1);
    }
}
