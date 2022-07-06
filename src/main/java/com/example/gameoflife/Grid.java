package com.example.gameoflife;

import java.util.Random;

public class Grid {
    public int[][] cells;
    public int rows;
    public int cols;
    public int amountOfAlLCells;
    public int amountOfAliveCells;

    public Grid() {
    }

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new int[cols][rows];
        this.amountOfAlLCells = cols * rows;
        this.amountOfAliveCells = 15;

        Random random = new Random();
        for (int i = 0; i < amountOfAliveCells; i++) {
            this.cells[random.nextInt(rows)][random.nextInt(this.cols)] = 1;
        }
    }

    public int[][] getCells() {
        return cells;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int getAmountOfAliveCells() {
        return amountOfAliveCells;
    }

    public void setAmountOfAliveCells() {
        int amountOfAlive = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (cells[i][j] == 1) {
                 amountOfAlive++;
                }
            }
        }
        this.amountOfAliveCells=amountOfAlive;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getAmountOfAlLCells() {
        return amountOfAlLCells;
    }
}
