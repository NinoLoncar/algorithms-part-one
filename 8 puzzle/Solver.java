import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    private SearchNode solution;
    private Boolean solvable = false;

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        pq.insert(new SearchNode(initial, 0, null));
        twinPq.insert(new SearchNode(initial.twin(), 0, null));
        SearchNode min = pq.min();
        SearchNode minTwin = twinPq.min();

        do {
            min = pq.delMin();
            minTwin = twinPq.delMin();

            if (min.board.isGoal()) {
                solvable = true;
                solution = min;
                break;
            }

            if (minTwin.board.isGoal()) {
                solvable = false;
                break;
            }

            for (Board neighbor : min.board.neighbors()) {
                if (min.previous == null || !neighbor.equals(min.previous.board)) {
                    pq.insert(new SearchNode(neighbor, min.numberOfMoves + 1, min));
                }
            }

            for (Board twinNeighbor : minTwin.board.neighbors()) {
                if (minTwin.previous == null || !twinNeighbor.equals(minTwin.previous.board)) {
                    twinPq.insert(new SearchNode(twinNeighbor, minTwin.numberOfMoves + 1, minTwin));
                }
            }
        } while (!min.board.isGoal());
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable())
            return -1;
        return solution.numberOfMoves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        List<Board> solutionPath = new ArrayList<>();
        SearchNode node = solution;
        while (node != null) {
            solutionPath.add(node.board);
            node = node.previous;
        }
        Collections.reverse(solutionPath);
        return solutionPath;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int numberOfMoves;
        private final SearchNode previous;
        private final int manhattanPriority;

        public SearchNode(Board board, int numberOfMoves, SearchNode previous) {
            this.board = board;
            this.numberOfMoves = numberOfMoves;
            this.previous = previous;
            this.manhattanPriority = board.manhattan() + numberOfMoves;
        }

        public int compareTo(SearchNode o) {
            return Integer.compare(this.manhattanPriority, o.manhattanPriority);
        }
    }
}
