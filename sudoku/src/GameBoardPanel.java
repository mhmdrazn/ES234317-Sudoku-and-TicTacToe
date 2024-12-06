package src;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private Timer timer;
    private JLabel fastestTimeLabel;
    private JLabel currentTimeLabel;
    private long fastestTime = Long.MAX_VALUE; // Inisialisasi dengan nilai maksimum
    private long currentTime = 0;
    private String difficultyLevel = "Easy";
    private JPanel numberInput; // Deklarasikan variabel numberInput
    private long startTime; // Waktu mulai permainan
    private JLabel mistakeLabel;
    private Mistake mistakeTracker = new Mistake();

    public GameBoardPanel(Timer timer) {
        super.setLayout(new BorderLayout());

        // Create top panel for scores
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topRightPanel = new JPanel(new GridLayout(2, 1));
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Create fastest time label
        fastestTimeLabel = new JLabel("Fastest Time: N/A");
        topRightPanel.add(fastestTimeLabel);

        // Create current time label
        currentTimeLabel = new JLabel("Current Time: 0 seconds");
        topRightPanel.add(currentTimeLabel);

        topPanel.add(topRightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Create number input panel
        numberInput = new JPanel(); // Inisialisasi variabel numberInput
        bottomPanel.add(numberInput, BorderLayout.NORTH);

        // Create mistake label
        mistakeLabel = new JLabel("Mistakes: 0/3");
        topPanel.add(mistakeLabel);

        // Initialize the cells
        JPanel gridPanel = new JPanel(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                gridPanel.add(cells[row][col]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        CellInputListener listener = new CellInputListener();
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEnabled()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.timer = timer;
        this.startTime = System.currentTimeMillis(); // Set waktu mulai permainan

        // Create control panel with New Game, Easy Mode, Solve, and Hint buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBackground(Color.BLACK);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.addActionListener(e -> newGame());
        JButton easyModeButton = new JButton("Easy Mode");
        easyModeButton.setBackground(Color.BLACK);
        easyModeButton.setForeground(Color.WHITE);
        easyModeButton.addActionListener(e -> setDifficulty("Easy"));
        JButton solveButton = new JButton("Solve");
        solveButton.setBackground(Color.BLACK);
        solveButton.setForeground(Color.WHITE);
        solveButton.addActionListener(e -> solveGame());
        JButton hintButton = new JButton("Hint");
        hintButton.setBackground(Color.BLACK);
        hintButton.setForeground(Color.WHITE);
        hintButton.addActionListener(e -> giveHint());
        controlPanel.add(solveButton);
        controlPanel.add(hintButton);
        add(controlPanel, BorderLayout.SOUTH);
        
    }

    public void newGame() {
        puzzle.newPuzzle(this.difficultyLevel);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }

        // Reset timer
        this.startTime = System.currentTimeMillis();
        timer.start();

        // Reset mistake tracker
        mistakeTracker.reset();
        mistakeLabel.setText("Mistakes: " + mistakeTracker.getMistake() + "/3");
    }

    public void resetGame() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].status == CellStatus.CORRECT_GUESS || cells[i][j].status == CellStatus.WRONG_GUESS) {
                    cells[i][j].setText("");
                    cells[i][j].status = CellStatus.TO_GUESS;
                    cells[i][j].paint();
                    cells[i][j].setEnabled(true);
                }
                if (cells[i][j].status == CellStatus.CORRECT_GUESS || cells[i][j].status == CellStatus.GIVEN) {
                    cells[i][j].setEnabled(true);
                    timer.start();
                    if (cells[i][j].status == CellStatus.GIVEN) {
                        cells[i][j].setEnabled(false);
                    }
                }
            }
        }

        // Reset timer
        this.startTime = System.currentTimeMillis();
        timer.start();

        // Reset mistake tracker
        mistakeTracker.reset();
        mistakeLabel.setText("Mistakes: " + mistakeTracker.getMistake() + "/3");
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell) e.getSource();

            if (sourceCell.status == CellStatus.WRONG_GUESS || sourceCell.status == CellStatus.TO_GUESS) {
                int numberIn = Integer.parseInt(sourceCell.getText());
                System.out.println("You entered " + numberIn);

                if (numberIn == sourceCell.getNumber()) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    mistakeTracker.change(); // Update mistake counter when a wrong guess is made
                    mistakeLabel.setText("Mistakes: " + mistakeTracker.getMistake() + "/3");

                    // Play sound effect when mistake happens
                    mistakeTracker.playMistakeSound();
                }
                sourceCell.paint(); // re-paint this cell based on its status

                if (isSolved()) {
                    timer.stop();
                    long endTime = System.currentTimeMillis();
                    long timeTaken = (endTime - startTime) / 1000; // Waktu dalam detik
                    JOptionPane.showMessageDialog(null, "Congratulations! You have solved the game in " + timeTaken + " seconds!");
                    updateTimes(timeTaken);
                }

                if (mistakeTracker.getMistake() >= Mistake.MAX_MISTAKES) {
                    JOptionPane.showMessageDialog(null, "Game Over! You made too many mistakes.");
                    mistakeTracker.playGameOverSound();
                    timer.stop();
            }
        }
        }
    }

    public void setDifficulty(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDifficulty() {
        return difficultyLevel;
    }

    private void updateTimes(long timeTaken) {
        // Perbarui fastestTime jika waktu penyelesaian saat ini lebih pendek
        if (timeTaken < fastestTime) {
            fastestTime = timeTaken;
            fastestTimeLabel.setText("Fastest Time: " + fastestTime + " seconds");
        }
        currentTime = timeTaken;
        currentTimeLabel.setText("Current Time: " + currentTime + " seconds");
    }

    private void solveGame() {
        // Logika untuk menyelesaikan permainan secara otomatis
        solveSudoku();
        timer.stop();
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000; // Waktu dalam detik
        JOptionPane.showMessageDialog(null, "Game solved automatically in " + timeTaken + " seconds!");
        updateTimes(timeTaken);
    }

    private boolean solveSudoku() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].getText().isEmpty()) {
                    for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
                        if (isValid(row, col, num)) {
                            cells[row][col].setText(String.valueOf(num));
                            cells[row][col].status = CellStatus.CORRECT_GUESS;
                            if (solveSudoku()) {
                                return true;
                            } else {
                                cells[row][col].setText("");
                                cells[row][col].status = CellStatus.TO_GUESS;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, int num) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (cells[row][i].getText().equals(String.valueOf(num)) || cells[i][col].getText().equals(String.valueOf(num))) {
                return false;
            }
        }
        return true;
    }

    private void giveHint() {
        Random rand = new Random();
        int maxAttempts = 100; // Batasan jumlah percobaan
        int attempts = 0;
        while (attempts < maxAttempts) {
            int row = rand.nextInt(SudokuConstants.GRID_SIZE);
            int col = rand.nextInt(SudokuConstants.GRID_SIZE);
            if (cells[row][col].getText().isEmpty()) {
                for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
                    if (isValid(row, col, num)) {
                        cells[row][col].setText(String.valueOf(num));
                        cells[row][col].status = CellStatus.CORRECT_GUESS;
                        return;
                    }
                }
            }
            attempts++;
        }
        JOptionPane.showMessageDialog(null, "No valid cell found for hint.");
    }
}