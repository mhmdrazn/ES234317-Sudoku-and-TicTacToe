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
    public static final Font FONT_STATUS = new Font("Figtree", Font.PLAIN, 14);

    private boolean isAIGame;
    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;
    private AIPlayer easyAI;
    private AIPlayer mediumAI;
    private AIPlayer hardAI;
    private AIPlayer dynamicAI;
    private JButton restartButton;
    private AIPlayer currentAI;

    public GameMain(boolean isAIGame, String difficulty) {
        this.isAIGame = isAIGame;
        initGame();

        if (isAIGame) {
            easyAI = new AIPLayerEasy(board);
            mediumAI = new AIPlayerMedium(board);
            hardAI = new AIPlayerHard(board);
            dynamicAI = new AIPlayerDynamic(board);

            easyAI.setSeed(Seed.NOUGHT);
            mediumAI.setSeed(Seed.NOUGHT);
            hardAI.setSeed(Seed.NOUGHT);
            dynamicAI.setSeed(Seed.NOUGHT);

            if (difficulty != null) {
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
                    default:
                        currentAI = easyAI;
                }
            } else {
                currentAI = easyAI;
            }
        }

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
                        
                        // Make move for current player
                        currentState = board.stepGame(currentPlayer, row, col);
                        
                        // Check for win/draw conditions
                        if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
                            SoundEffect.EXPLODE.play();
                        } else if (currentState == State.DRAW) {
                            SoundEffect.DIE.play();
                        }

                        // Handle next turn
                        if (isAIGame) {
                            if (currentState == State.PLAYING) {
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
                            }
                        } else {
                            // PvP mode: switch players
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        // Status bar setup
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END);
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 80));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Button panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 15, 4, 15));

        restartButton = new JButton("Restart Game");
        restartButton.setForeground(new Color(255, 255, 255));
        restartButton.setFocusPainted(false);
        restartButton.setBackground(new Color(33, 37, 49));
        restartButton.setFont(new Font("Figtree", Font.BOLD, 14));
        restartButton.setPreferredSize(new Dimension(150, 30));
        restartButton.addActionListener(_ -> restartGame());
        buttonPanel.add(restartButton);

        add(buttonPanel, BorderLayout.PAGE_END);
        
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
        setBackground(COLOR_BG);
        board.paint(g);

        String playerIndicator = isAIGame ? 
            (currentPlayer == Seed.CROSS ? "Your Turn" : "Computer's Turn") :
            (currentPlayer == Seed.CROSS ? "X's Turn" : "O's Turn");

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText(playerIndicator);
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    public static void main(String[] args) {
        boolean isAIGame = args[0].equals("aiGame");
        String difficulty = args.length > 1 ? args[1] : null;
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new GameMain(isAIGame, difficulty));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}