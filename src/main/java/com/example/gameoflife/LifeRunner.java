package com.example.gameoflife;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LifeRunner {

    static int amountOfActiveThreads = 3;
    static int SIZE = 6;
    //  volatile static Grid newGenerationGrid = new Grid(SIZE, SIZE);

    public static void startGame() throws InterruptedException {
        //i need to change runnable for callable to return new value
        ExecutorService executor = Executors.newFixedThreadPool(amountOfActiveThreads);
        Grid grid = new Grid(SIZE, SIZE);
        Grid newGenerationGrid = new Grid(SIZE, SIZE);
        System.out.println("Start grid: ");
        printGrid(grid);
        int rowsPerThreads = grid.getRows() / amountOfActiveThreads;
        List<MyRunnable> tasks = new ArrayList<>();
        //add tasks to execute to the list
        int row = 0;
        for (int i = 0; i < amountOfActiveThreads; i++) {
           tasks.add(new MyRunnable(i, row, rowsPerThreads, grid, newGenerationGrid));
            row = row + rowsPerThreads;
        }
        while (grid.amountOfAliveCells != 0 || grid.amountOfAliveCells != grid.amountOfAlLCells) {
            for (MyRunnable task: tasks) {
                executor.submit(task);
            }
         /*  }
        
          this implementation works ony for one task
          List<Future<Void>> futures = executor.invokeAll(tasks);

            for(Future<Void> future : futures){
                try{
                    System.out.println("future.isDone = " + future.isDone());
                }
                catch (CancellationException ce) {
                    ce.printStackTrace();
                }
            }
         /*
          в цій реалізвції походу тільки перший таск ранить
          CompletableFuture<?>[] futures = tasks.stream()
                    .map(task -> CompletableFuture.runAsync(task, executor))
                    .toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(futures).join();*/
        //    System.out.println("All threads here");

            grid.setCells(newGenerationGrid.getCells());
            grid.setAmountOfAliveCells();
            // System.out.println("NEW GENERATION: ");
            //printGrid(grid);
        }
        executor.shutdown();
    }

  /*  public static Grid createGrid(int height, int width, int amountOfAliveCells) {
        Grid grid = new Grid(50, 50);
        Random random = new Random();
        for (int i = 0; i < grid.amountOfAlLCells; i++) {
            grid.cells[random.nextInt(width)][random.nextInt(height)] = 1;
        }
        return grid;
    }
*/
    public static void printGrid(Grid grid) {
        System.out.println("__________________________________");
        for (int i = 0; i < grid.getCols(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                if (grid.cells[i][j] == 1) {
                    System.out.print("* ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println("__________________________________");
    }
}
/**/