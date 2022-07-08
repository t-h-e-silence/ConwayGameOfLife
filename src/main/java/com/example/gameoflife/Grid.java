package com.example.gameoflife;

import java.util.concurrent.atomic.AtomicInteger;


public class Grid {
    private int[][] cells;
    private int rows;
    private int cols;
    private int amountOfAlLCells;
    AtomicInteger amountOfAliveCells = new AtomicInteger(0);
    private int amountOfActiveThreads;

    public Grid() {
    }

    public Grid(int rows, int cols, int amountOfAliveCells, int amountOfActiveThreads) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new int[cols][rows];
        this.amountOfAliveCells.set(amountOfAliveCells);
        this.amountOfAlLCells = cols * rows;
        this.amountOfActiveThreads = amountOfActiveThreads;
    }

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new int[cols][rows];
        this.amountOfAlLCells = cols * rows;
    }

    public int[][] getCells() {
        return cells;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int getAmountOfAliveCells() {
        return amountOfAliveCells.get();
    }

    public void setAmountOfAliveCells(int amountOfAlive) {
        this.amountOfAliveCells.set(amountOfAlive);
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

    public int getAmountOfActiveThreads() {
        return amountOfActiveThreads;
    }

    public void setAmountOfActiveThreads(int amountOfActiveThreads) {
        this.amountOfActiveThreads = amountOfActiveThreads;
    }
}
