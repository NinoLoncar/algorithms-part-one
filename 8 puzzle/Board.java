import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int[][] board;

    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles.length];
        copyArray(tiles, board);
    }

    public static void main(String[] args) {
    }

    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append(board.length);
        for (int[] rows : board) {
            boardString.append("\n");
            for (int j = 0; j < board.length; j++) {
                boardString.append(rows[j]).append(" ");
            }
            boardString.setLength(boardString.length() - 1);
        }
        return boardString.toString();
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int wrongPositions = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != i * board.length + j + 1) {
                    wrongPositions++;
                }
            }
        }
        return wrongPositions;
    }

    public int manhattan() {
        int distances = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != i * board.length + j + 1) {
                    int expectedX = (board[i][j] - 1) / board.length;
                    int expectedY = (board[i][j] - 1) % board.length;
                    int distance = Math.abs(i - expectedX) + Math.abs(j - expectedY);
                    distances += distance;
                }
            }
        }
        return distances;
    }

    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != i * board.length + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        Board yBoard = (Board) y;

        if (yBoard.dimension() != this.dimension()) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != yBoard.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>(List.of());
        int zeroX = 0;
        int zeroY = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break;
                }
            }
        }
        if (zeroX > 0) {
            int[][] neighborTiles = new int[board.length][board.length];
            copyArray(board, neighborTiles);
            neighborTiles[zeroX][zeroY] = neighborTiles[zeroX - 1][zeroY];
            neighborTiles[zeroX - 1][zeroY] = 0;
            Board neighborBoard = new Board(neighborTiles);
            neighbors.add(neighborBoard);
        }
        if (zeroX < board.length - 1) {
            int[][] neighborTiles = new int[board.length][board.length];
            copyArray(board, neighborTiles);
            neighborTiles[zeroX][zeroY] = neighborTiles[zeroX + 1][zeroY];
            neighborTiles[zeroX + 1][zeroY] = 0;
            Board neighborBoard = new Board(neighborTiles);
            neighbors.add(neighborBoard);
        }
        if (zeroY > 0) {
            int[][] neighborTiles = new int[board.length][board.length];
            copyArray(board, neighborTiles);
            neighborTiles[zeroX][zeroY] = neighborTiles[zeroX][zeroY - 1];
            neighborTiles[zeroX][zeroY - 1] = 0;
            Board neighborBoard = new Board(neighborTiles);
            neighbors.add(neighborBoard);
        }
        if (zeroY < board.length - 1) {
            int[][] neighborTiles = new int[board.length][board.length];
            copyArray(board, neighborTiles);
            neighborTiles[zeroX][zeroY] = neighborTiles[zeroX][zeroY + 1];
            neighborTiles[zeroX][zeroY + 1] = 0;
            Board neighborBoard = new Board(neighborTiles);
            neighbors.add(neighborBoard);
        }
        return neighbors;
    }


    public Board twin() {
        int[][] twinTiles = new int[board.length][board.length];
        copyArray(board, twinTiles);

        if (twinTiles[0][0] == 0 || twinTiles[0][1] == 0) {
            int help = twinTiles[twinTiles.length - 1][0];
            twinTiles[twinTiles.length - 1][0] = twinTiles[twinTiles.length - 1][1];
            twinTiles[twinTiles.length - 1][1] = help;
        } else {
            int help = twinTiles[0][0];
            twinTiles[0][0] = twinTiles[0][1];
            twinTiles[0][1] = help;
        }
        return new Board(twinTiles);
    }

    private void copyArray(int[][] source, int[][] destination) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source.length);
        }
    }
}
