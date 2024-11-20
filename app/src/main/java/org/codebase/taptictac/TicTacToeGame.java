package org.codebase.taptictac;

public class TicTacToeGame {
    private static final int EMPTY = 2;
    private static final int PLAYER_X = 0;
    private static final int PLAYER_O = 1;

    private boolean active;
    private int currentPlayer;
    private int[] gameState;
    private int[][] winPositions;
    private int moveCount;
    private int[] winningCells;

    public TicTacToeGame() {
        reset();
    }

    public void reset() {
        active = true;
        currentPlayer = PLAYER_X;
        gameState = new int[]{EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};
        winPositions = new int[][]{
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };
        moveCount = 0;
        winningCells = null;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCurrentPlayer() {
        return currentPlayer == PLAYER_X ? "X" : "O";
    }

    public String getNextPlayer() {
        return currentPlayer == PLAYER_X ? "O" : "X";
    }

    public boolean markCell(int position) {
        if (gameState[position] == EMPTY) {
            gameState[position] = currentPlayer;
            moveCount++;
            return true;
        }
        return false;
    }

    public boolean checkWinner() {
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != EMPTY) {
                winningCells = winPosition;
                active = false;
                return true;
            }
        }
        return false;
    }

    public boolean isDraw() {
        return moveCount == 9 && active;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == PLAYER_X ? PLAYER_O : PLAYER_X;
    }

    public int[] getWinningCells() {
        return winningCells;
    }
}
