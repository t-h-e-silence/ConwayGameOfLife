package com.example.gameoflife;

import java.util.concurrent.Callable;

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
            //    Grid newGenerationGrid = grid;
            for (int i = startRow; i < step; i++) {
                System.out.println("Work on row: " + startRow);
                nextGeneration(startRow, step);
            }
            grid = newGenerationGrid;
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public synchronized void nextGeneration(int currentRow, int step) {
        //  int aliveCells = grid.getAmountOfAliveCells();
        int M = grid.getRows();
        int N = grid.getCols();
        int edgeRow = step + currentRow;
        for (int row = currentRow; row < edgeRow; row++) {// && row < M
            System.out.println("  row: " + row);
            for (int col = 0; col < N; col++) {
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                        if ((row + i >= 0 && row + i < M) && (col + j >= 0 && col + j < N))
                            aliveNeighbours += grid.getCells()[row + i][col + j];
                aliveNeighbours -= grid.getCells()[row][col];
                if ((grid.cells[row][col] == 1) && (aliveNeighbours < 2))
                    newGenerationGrid.getCells()[row][col] = 0;
                else if ((grid.cells[row][col] == 1) && (aliveNeighbours > 3))
                    newGenerationGrid.getCells()[row][col] = 0;
                else if ((grid.getCells()[row][col] == 0) && (aliveNeighbours == 3))
                    newGenerationGrid.getCells()[row][col] = 1;
                else
                    newGenerationGrid.getCells()[row][col] = grid.cells[row][col];
            }
        }
//+        newGenerationGrid.setAmountOfAliveCells(aliveCells);
    }
}