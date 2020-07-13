import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameOfLife {

    //board to hold currentUniverse
    public char[][] currentUniverse;
    //future universe
    public char[][] futureUniverse;
    //track number of generations
    public static int gen;
    //instance of Random class
    public Random rand;
    //alive cell count
    public static int cellsAlive = 0;
    //GUI
    GameOfLifeGUI gui;

    //Direction class to find neighbors
    static class Direction {
        int x;
        int y;

        public Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //create all directions
    Direction north = new Direction(-1, 0);
    Direction south = new Direction(1, 0);
    Direction east = new Direction(0, 1);
    Direction west = new Direction(0, -1);
    Direction southEast = new Direction(1, 1);
    Direction southWest = new Direction(1, -1);
    Direction northEast = new Direction(-1, 1);
    Direction northWest = new Direction(-1, -1);

    //Array of directions
    List<Direction> directions = Arrays.asList(north, south, east, west, southEast, southWest, northEast, northWest);

    //Constructor to set currentUniverse dimensions and seed
    public GameOfLife(int n) {
        currentUniverse = new char[n][n];
        gui = new GameOfLifeGUI();
        gen = 1;
    }

    //method to initialize the currentUniverse
    public void initializeCurrentUniverse() {
        //setting seed in rand
        rand = new Random();

        //using next boolean to decide if a cell is alive or dead
        //Initial generation
        for (int i = 0; i < currentUniverse.length; i++) {
            for (int j = 0; j < currentUniverse.length; j++) {
                if (rand.nextBoolean()) {
                    currentUniverse[i][j] = 'O';
                    cellsAlive++;
                } else {
                    currentUniverse[i][j] = ' ';
                }
            }
        }
        displayCurrentUniverse();
    }

    public void displayCurrentUniverse() {
        while (gen <= 100) {
            gui.printCurrentUniverse(currentUniverse);
            hold();
            initializeFutureUniverse(); //find the next generation
            gen++;
        }
    }

    public void hold() {
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    public void initializeFutureUniverse() {
        futureUniverse = new char[currentUniverse.length][currentUniverse.length];
        for (int i = 0; i < currentUniverse.length; i++) {
            for (int j = 0; j < currentUniverse.length; j++) {
                int aliveNeighbors = aliveCount(i, j);
                if (currentUniverse[i][j] == 'O') {
                    if (aliveNeighbors == 2 || aliveNeighbors == 3) {
                        futureUniverse[i][j] = 'O';
                    } else {
                        futureUniverse[i][j] = ' ';
                        cellsAlive--;
                    }
                } else {
                    if (aliveNeighbors == 3) {
                        futureUniverse[i][j] = 'O';
                        cellsAlive++;
                    } else {
                        futureUniverse[i][j] = ' ';
                    }
                }
            }
        }

        currentUniverse = Arrays.copyOf(futureUniverse, futureUniverse.length);
    }

    //checks all 8 neighbors according to the rules of Conway's game of life
    public int aliveCount(int row, int col) {
        int alive = 0;

        for (Direction direction : directions) {
            int r = (direction.x + row + currentUniverse.length) % currentUniverse.length;
            int c = (direction.y + col + currentUniverse.length) % currentUniverse.length;

            if (currentUniverse[r][c] == 'O') {
                alive++;
            }
        }

        return alive;
    }
}
