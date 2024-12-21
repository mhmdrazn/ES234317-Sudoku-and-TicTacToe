/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 */

package tictactoe.src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameMain extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80);  // Red #EF6950
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    private boolean isAIGame;
    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private AIPlayer currentAI;
    private JLabel statusBar;
    private AIPlayer easyAI;
    private AIPlayer mediumAI;
    private AIPlayer hardAI;
    private JButton restartButton;
    private JButton exitButton;
    private JComboBox<String> difficultyDropdown;
    private JLabel difficultyLabel;
    private JPanel difficultyPanel;

    private void setAIDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                currentAI = easyAI;
                break;
            case "medium":
                currentAI = mediumAI;
                break;
            case "hard":
                currentAI = hardAI;
                break;
        }
        if (currentAI != null) {
            currentAI.setSeed(Seed.NOUGHT);
        }
    }

    public void runGame(boolean isAIGame) {
        this.isAIGame = isAIGame;
        initGame();

        if (isAIGame) {
            // Initialize AI players only in AI mode
            easyAI = new AIPLayerEasy(board);
            mediumAI = new AIPlayerMedium(board);
            hardAI = new AIPlayerHard(board);
            currentAI = easyAI;
            currentAI.setSeed(Seed.NOUGHT);
        }

        // Mouse Listener
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        
                        SoundEffect.EAT_FOOD.play();
                        // Make move and update game state
                        currentState = board.stepGame(currentPlayer, row, col);
                        
                        // Check for game end conditions
                        if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
                            SoundEffect.EXPLODE.play();
                        } else if (currentState == State.DRAW) {
                            SoundEffect.DIE.play();
                        }

                        // Handle next turn
                        if (isAIGame && currentState == State.PLAYING) {
                            currentPlayer = Seed.NOUGHT;
                            // AI move with delay
                            Timer timer = new Timer(800, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt) {
                                    int[] aiMove = currentAI.move();
                                    if (aiMove != null) {
                                        currentState = board.stepGame(currentPlayer, aiMove[0], aiMove[1]);
                                        if (currentState == State.PLAYING) {
                                            SoundEffect.EAT_FOOD.play();
                                        } else {
                                            SoundEffect.DIE.play();
                                        }
                                        currentPlayer = Seed.CROSS;
                                        repaint();
                                    }
                                }
                            });
                            timer.setRepeats(false);
                            timer.start();
                        } else {
                            // Player vs Player: switch turns
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        // Status Bar
        statusBar = new JLabel();
        statusBar.setFont(new Font("Figtree", Font.PLAIN, 14));
        statusBar.setBackground(new Color(33, 37, 49));
        statusBar.setOpaque(true);
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Create panels
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(33, 37, 49));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JPanel topControlPanel = new JPanel(new BorderLayout(5, 5));
        topControlPanel.setBackground(new Color(33, 37, 49));
        topControlPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JPanel bottomControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomControlPanel.setBackground(new Color(33, 37, 49));
        bottomControlPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        // Difficulty controls (only shown in AI mode)
        difficultyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        difficultyPanel.setBackground(new Color(33, 37, 49));

        difficultyLabel = new JLabel("Difficulty: Easy");
        difficultyLabel.setFont(new Font("Figtree", Font.PLAIN, 14));
        difficultyLabel.setForeground(Color.WHITE);

        String[] difficulties = {"Easy", "Medium", "Hard"};
        difficultyDropdown = new JComboBox<>(difficulties);
        difficultyDropdown.setBackground(new Color(33, 37, 49));
        difficultyDropdown.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 10));
        difficultyDropdown.setForeground(Color.WHITE);
        difficultyDropdown.setFocusable(false);
        difficultyDropdown.setFont(new Font("Figtree", Font.PLAIN, 14));
        difficultyDropdown.addActionListener(e -> {
            String selected = (String) difficultyDropdown.getSelectedItem();
            difficultyLabel.setText("Difficulty: " + selected);
            setAIDifficulty(selected);
            restartGame();
            repaint();
        });

        // Only add difficulty controls in AI mode
        if (isAIGame) {
            topControlPanel.add(difficultyLabel);
            bottomControlPanel.add(difficultyDropdown, BorderLayout.EAST);
            topControlPanel.add(difficultyPanel, BorderLayout.WEST);
        }

        // Control buttons
        restartButton = new JButton("Restart Game");
        restartButton.setBackground(new Color(33, 37, 49));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setFont(new Font("Figtree", Font.PLAIN, 14));
        restartButton.addActionListener(e -> restartGame());

        exitButton = new JButton("Exit Game");
        exitButton.setBackground(new Color(33, 37, 49));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Figtree", Font.PLAIN, 14));
        exitButton.addActionListener(e -> System.exit(0));

        topControlPanel.add(statusBar, BorderLayout.EAST);
        bottomControlPanel.add(restartButton);
        bottomControlPanel.add(exitButton);

        bottomPanel.add(topControlPanel, BorderLayout.NORTH);
        bottomPanel.add(bottomControlPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 80));

        newGame();
    }

    public void initGame() {
        board = new Board();
        SoundEffect.initGame();
        SoundEffect.playBackgroundMusic();
    }

    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        SoundEffect.playBackgroundMusic();
        repaint();
    }

    public void restartGame() {
        newGame();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        board.paint(g);

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                GameMain game = new GameMain();
                
                // Check if we're running an AI game
                boolean isAIGame = args.length > 0 && args[0].equals("aiGame");
                
                // Set up the game with the appropriate mode
                game.runGame(isAIGame);
                
                // If it's an AI game and difficulty is specified
                if (isAIGame && args.length > 1) {
                    String difficulty = args[1].toLowerCase();
                    switch (difficulty) {
                        case "easy":
                            game.difficultyDropdown.setSelectedItem("Easy");
                            game.currentAI = game.easyAI;
                            break;
                        case "medium":
                            game.difficultyDropdown.setSelectedItem("Medium");
                            game.currentAI = game.mediumAI;
                            break;
                        case "hard":
                            game.difficultyDropdown.setSelectedItem("Hard");
                            game.currentAI = game.hardAI;
                            break;
                    }
                    if (game.currentAI != null) {
                        game.currentAI.setSeed(Seed.NOUGHT);
                    }
                }
                
                frame.setContentPane(game);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(390, 460);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}