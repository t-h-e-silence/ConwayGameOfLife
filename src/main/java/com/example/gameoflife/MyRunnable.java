package com.example.gameoflife;

public class MyRunnable implements Runnable {
    int id;
    int startRow;
    int step;
    Grid grid;
    Grid newGenerationGrid;

    public MyRunnable(int i, int startRow, int step, Grid grid, Grid newGenerationGrid) {
        this.id = i;
        this.startRow = startRow;
        this.step = step;
        this.grid = grid;
        this.newGenerationGrid = newGenerationGrid;
    }

    public void run() {
        try {
            nextGeneration(startRow, step);
            grid = newGenerationGrid;
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void nextGeneration(int currentRow, int step) {
        int edgeRow = step + currentRow;
        int aliveCells = newGenerationGrid.getAmountOfAliveCells();
        for (int row = currentRow; row < edgeRow && row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        if ((row + i >= 0 && row + i < grid.getRows()) && (col + j >= 0 && col + j < grid.getCols()))
                            aliveNeighbours += grid.getCells()[row + i][col + j];
                aliveNeighbours -= grid.getCells()[row][col];
                if ((grid.getCells()[row][col] == 1) && (aliveNeighbours < 2 || aliveNeighbours > 3)) {
                    newGenerationGrid.getCells()[row][col] = 0;
                    aliveCells--;
                } else if ((grid.getCells()[row][col] == 0) && (aliveNeighbours == 3)) {
                    newGenerationGrid.getCells()[row][col] = 1;
                    aliveCells++;
                } else {
                    newGenerationGrid.getCells()[row][col] = grid.getCells()[row][col];
                }
            }
        }
        newGenerationGrid.setAmountOfAliveCells(aliveCells);
    }
}