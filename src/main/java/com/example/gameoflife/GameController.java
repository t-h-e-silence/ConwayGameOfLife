package com.example.gameoflife;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/my-app/")
public class GameController {
    private static final Logger Log = Logger.getLogger(String.valueOf(GameController.class));
    Grid grid;
    Grid newGenerationGrid;
    ThreadPoolExecutor executor =
            new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());
    List<MyRunnable> tasks;


    @PostMapping("/newGame")
    public ResponseEntity<Grid> createStartGrid(@RequestBody Grid responseGrid) {
        tasks = new ArrayList<>();
        Log.log(Level.INFO, "New grid generation started for request: " + responseGrid);
        grid = new Grid(responseGrid.getRows(), responseGrid.getCols());
        grid.setAmountOfActiveThreads(responseGrid.getAmountOfActiveThreads());
        grid.setAmountOfAliveCells(responseGrid.getAmountOfAliveCells());
        newGenerationGrid = new Grid(responseGrid.getRows(), responseGrid.getCols());
        fillGrids(grid, newGenerationGrid);
        tasks = createTasks(responseGrid.getAmountOfActiveThreads(), tasks);
        Log.log(Level.INFO, "New grid generated:" + grid);
        return ResponseEntity.ok(grid);
    }

    @PostMapping("/newGeneration")
    public ResponseEntity<Grid> newGeneration(@RequestBody Grid grid) {
        letsGame(executor, tasks);
        for (int i = 0; i < grid.getCols(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                grid.getCells()[i][j] = newGenerationGrid.getCells()[i][j];
            }
        }
        Log.log(Level.INFO, "One more generation!");
        return ResponseEntity.ok(grid);
    }

    @PostMapping("/gameOver")
    public ResponseEntity<Grid> gameOver(@RequestBody Grid responseGrid) {
        executor.shutdown();
        Log.log(Level.INFO, "GAME OVER!");
        return ResponseEntity.ok(responseGrid);
    }

    List<MyRunnable> createTasks(int amountOfActiveThreads, List <MyRunnable> tasks) {
        executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
        executor.setCorePoolSize(amountOfActiveThreads);
        executor.setMaximumPoolSize(amountOfActiveThreads);
        int rowsPerThreads = (int) Math.ceil((double) grid.getRows() / amountOfActiveThreads);
        int row = 0;
        for (int i = 0; i < amountOfActiveThreads; i++) {
            tasks.add(new MyRunnable(i, row, rowsPerThreads, grid, newGenerationGrid));
            row = row + rowsPerThreads;
        }
        return tasks;
    }

    public static void letsGame(ExecutorService executor, List<MyRunnable> tasks) {
        Log.log(Level.INFO, "Thread started.");
        CompletableFuture<?>[] futures = tasks.stream()
                .map(task -> CompletableFuture.runAsync(task, executor))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        Log.log(Level.INFO, "Threads joined.");
    }

    public static void fillGrids(Grid grid, Grid newGenerationGrid) {
        for (int i = 0; i < grid.getAmountOfAliveCells(); i++) {
            grid.getCells()[generate(0, grid.getRows()-1)][generate(0, grid.getCols()-1)] = 1;
        }
        int amountOfAlive = 0;
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                if (grid.getCells()[i][j] == 1) {
                    amountOfAlive++;
                }
            }
        }
        grid.setAmountOfAliveCells(amountOfAlive);
        newGenerationGrid.setCells(new int[grid.getRows()][grid.getCols()]);
        newGenerationGrid.setAmountOfAliveCells(amountOfAlive);
        Log.log(Level.INFO, " Amount of alive: " + amountOfAlive);
    }

    public static int generate(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}