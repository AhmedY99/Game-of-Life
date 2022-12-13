package project3demo4;

/**
 * Description: The game simulates the growth of a bacteria population by
 * implementing simple rules which determine which cells reproduce, which cells
 * die, and which simply survive. Author: Ahmed Yussuf Due: 04/11/2021
 */
import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Constants and variable needed by the game
 *
 * @author Ayuss
 */
public class Project3Demo4 extends GraphicsProgram {

    final int WINDOW_WIDTH = 480;
    final int WINDOW_HEIGHT = 500;
    final String TATILE = "Game Of Life";
    final Color DEAD_COLOR = Color.WHITE;
    final Color LIVE_COLOR = Color.black;
    double gridWidth;
    int cellSize = 10;
    int gapSize = 3;
    int CELL_Y_OFF_SET = 10;
    int numRows = 30;
    int numCols = 30;
    double x, y;
    GRect[][] grid = new GRect[numRows][numCols];
    JTextField output;
    JTextField output1;
    JButton reset;
    JButton start;
    int population = 0;
    int generation = 0;
    String msg;
    String msg1;
    boolean isStarted = false;
    int[][] nextGen = new int[numRows][numCols];

    public static void main(String[] args) {
        new Project3Demo4().start();
    }

    @Override
    public void init() {
        setUpWindow();
        initdrawGrid();
        initBorder();
    }

    @Override
    public void run() {

        while (true) {
            if (isStarted) {
                countNeighbors();
                calcNextGeneration();
                updateDisplay();
            }
            pause(300);
        }
    }

    /**
     * set the window size set title
     */
    public void setUpWindow() {
        // set window size
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // set title
        setTitle(TATILE);
        //setBackground(Color.PINK);

    }

    /**
     * This method gets the dimension of the cell and Set status of cell (alive
     * or dead). The initdrawGrid()method creates a grid of GRect cell that
     * represents a grid of either alive or dead cells depending on their
     * status. This method also keeps track of where the mouse is clicked so the
     * user can choose to change whether a cell is alive or dead by clicking on
     * it.
     */
    public void initdrawGrid() {
        //use nested for loop to create the grid rectangles 
        //and set colors besed on their conditions 
        // if dead set white color 
        // if live set red color
        // give each a mouse listener
        gridWidth = (((WINDOW_WIDTH / 2) - ((cellSize) * gapSize)) / cellSize / 2);
        for (int i = 0; i < numRows; i++) {
            y = (i * gridWidth) + (i * gapSize) + 4;
            for (int j = 0; j < numCols; j++) {
                x = ((numCols * gridWidth) / 4 - (numCols * gapSize) / 2 + j * gridWidth + j * gapSize) + 8;
                GRect Cell = new GRect(x, y, cellSize, cellSize);
                Cell.setFillColor(DEAD_COLOR);
                Cell.setFilled(true);

                Cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        if (Cell.getFillColor() == DEAD_COLOR) {
                            Cell.setFillColor(LIVE_COLOR);
                            population++;
                        } else {
                            Cell.setFillColor(DEAD_COLOR);
                            population--;
                        }
                        updateDisplay();
                    }
                });
                add(Cell);
                grid[i][j] = Cell;
            }
        }
    }

    public void initBorder() {
        // Add start and reset buttons
        // JTextField for displaying the generation and population
        start = new JButton("Start");
        start.addActionListener(this);
        start.setFont(new Font("Aldhabi", Font.CENTER_BASELINE, 10));
        add(start, SOUTH);

        output1 = new JTextField("Generation:         ");
        add(output1, SOUTH);

        output = new JTextField("Population:         ");
        add(output, SOUTH);

        reset = new JButton("Reset");
        reset.addActionListener(this);
        reset.setFont(new Font("Aldhabi", Font.CENTER_BASELINE, 10));
        add(reset, SOUTH);

    }

    /**
     * displays the text and number of generation and population
     */
    public void updateDisplay() {
        // Add text field to display generation# and population#
        msg = "Population:   " + population;
        output.setText(msg);

        msg1 = "Generation: " + generation;
        output1.setText(msg1);
    }

    /**
     * constructs the start/pause and reset button to handle the game
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        String buttonName = ae.getActionCommand();
        if (buttonName.equals("Start")) {
            isStarted = true;
            start.setText("Pause");
        } else if (buttonName.equals("Pause")) {
            isStarted = false;
            start.setText("Start");
        }
        if (buttonName.equals(
                "Reset")) {
            clearGrid();
        }
    }

    /**
     * clear the cell and the restore the game
     */
    public void clearGrid() {
        // use nested for loops to iterate through the grid 
        // and setFillColor of every cell to their original color.
        // change the # population and generation to thier initial point 
        // call display method
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                grid[i][j].setFillColor(DEAD_COLOR);
                population = 0;
                generation = 0;
                updateDisplay();

            }
        }

    }

    /**
     * Count the number of live neighbors a cell has count all the cell in 8
     * adjacent cells.
     */
    public void countNeighbors() {
        // nested for loops to count the neighbors of each cell.
        // set the current nextGen elememt to zero
        // increment nextGen++ 
        for (int l = 1; l < numRows - 1; l++) {
            for (int m = 1; m < numRows - 1; m++) {
                nextGen[l][m] = 0;
                //1
                if (grid[l - 1][m - 1].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                } //2
                if (grid[l][m - 1].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                } //3
                if (grid[l + 1][m - 1].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                } //4
                if (grid[l + 1][m].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                } //5
                if (grid[l + 1][m + 1].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                } // 6 
                if (grid[l][m + 1].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                } // 7
                if (grid[l - 1][m + 1].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                } //8
                if (grid[l - 1][m].getFillColor() == LIVE_COLOR) {
                    nextGen[l][m]++;
                }
            }
        }
    }

    /**
     * Apply the four rules to each cell update the # of generation and
     * population
     */
    public void calcNextGeneration() {
        // nested for loops to iterate through the neighbors array 
        // apply the death by isolation, death by overpopulation, and birth rules
        for (int l = 1; l < numRows - 1; l++) {
            for (int m = 1; m < numRows - 1; m++) {
                // cell survives to the next generation.
                if ((grid[l][m].getFillColor() == LIVE_COLOR)) {
                    if ((nextGen[l][m] == 2) || (nextGen[l][m] == 3)) {
                        grid[l][m].setFillColor(LIVE_COLOR);

                    } // cell dies from over population.
                    else if ((nextGen[l][m] >= 4)) {
                        grid[l][m].setFillColor(DEAD_COLOR);
                        population--;
                    } //cell dies due to isolation
                    else if ((nextGen[l][m] <= 1)) {
                        grid[l][m].setFillColor(DEAD_COLOR);
                        population--;
                    }
                } // A new cell is born
                else {
                    if (nextGen[l][m] == 3) {
                        grid[l][m].setFillColor(LIVE_COLOR);
                        population++;
                    }
                }
            }
        }
        generation++;
    }
}
