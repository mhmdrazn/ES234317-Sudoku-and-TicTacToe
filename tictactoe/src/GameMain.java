/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026231012 - Zihni Aryanto Putra Buana
 * 2 - 5026231085 - Firmansyah Adi Prasetyo
 * 3 - 5026231174 - Muhamamd Razan Parisya Putra
 *   */
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

    private boolean isAIGame = true;
    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private AIPlayer currentAI;
    private JLabel statusBar;
    private AIPlayer easyAI;
    private AIPlayer mediumAI;
    private AIPlayer hardAI;
    private AIPlayer dynamicAI;
    private JButton restartButton;
    private JComboBox<String> difficultyDropdown;

   public GameMain() {
    initGame();

    // Inisialisasi AI (contoh AI)
    easyAI = new AIPLayerEasy(board);
    mediumAI = new AIPlayerMedium(board);
    hardAI = new AIPlayerHard(board);
    dynamicAI = new AIPlayerDynamic(board);

    // Set default AI
    currentAI = easyAI; // Default ke easy 
    currentAI.setSeed(Seed.NOUGHT);

    // Mouse Listener
    super.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            int row = mouseY / Cell.SIZE;
            int col = mouseX / Cell.SIZE;

            if (currentState == State.PLAYING) {
                SoundEffect.EAT_FOOD.play();
                if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                        && board.cells[row][col].content == Seed.NO_SEED) {

                    // Human move
                    currentState = board.stepGame(currentPlayer, row, col);
                    if (currentState == State.CROSS_WON || currentState == State.NOUGHT_WON) {
                        SoundEffect.EXPLODE.play();  // Play explosion sound on win
                    } else if (currentState == State.DRAW) {
                        SoundEffect.DIE.play();  // Play dying sound on draw
                    }

                    // AI Move
                    currentPlayer = Seed.NOUGHT;

                    if (currentState == State.PLAYING & isAIGame) {
                        Timer timer = new Timer(800, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                // AI move
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
                }
            } else {
                newGame();
            }
            repaint();
        }
    });

    // Set up the status bar (JLabel)
    statusBar = new JLabel();
    statusBar.setFont(FONT_STATUS);
    statusBar.setBackground(COLOR_BG_STATUS);
    statusBar.setOpaque(true);
    statusBar.setPreferredSize(new Dimension(300, 30));
    statusBar.setHorizontalAlignment(JLabel.LEFT);
    statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

    // Panel untuk menggabungkan status bar dan kontrol panel
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
    bottomPanel.add(statusBar);

    // Panel untuk kontrol (dropdown dan tombol restart)
    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    String[] difficulties = {"Easy", "Medium", "Hard", "Dynamic"};
    difficultyDropdown = new JComboBox<>(difficulties);
    difficultyDropdown.addActionListener(e -> {
        String selected = (String) difficultyDropdown.getSelectedItem();
        switch (selected) {
            case "Easy":
                currentAI = easyAI;
                break;
            case "Medium":
                currentAI = mediumAI;
                break;
            case "Hard":
                currentAI = hardAI;
                break;
            case "Dynamic":
                currentAI = dynamicAI;
                break;
        }
        restartGame();
        repaint();
    });
    controlPanel.add(difficultyDropdown);

    // Tombol Restart
    restartButton = new JButton("Restart Game");
    restartButton.setPreferredSize(new Dimension(150, 30));
    restartButton.addActionListener(e -> restartGame());
    controlPanel.add(restartButton);

    bottomPanel.add(controlPanel);  // Add control panel below the status bar

    // Tambahkan bottomPanel ke posisi PAGE_END
    super.setLayout(new BorderLayout());
    super.add(bottomPanel, BorderLayout.SOUTH);
    super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 80)); // Sesuaikan ukuran

    newGame();
}

    // Inisialisasi game
    public void initGame() {
        board = new Board();
        SoundEffect.initGame(); // Pre-load all sound effects
        SoundEffect.playBackgroundMusic();  // Start playing background music
    }

    // Memulai permainan baru
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.CROSS;    // cross plays first
        currentState = State.PLAYING;  // ready to play
        SoundEffect.playBackgroundMusic();
        repaint();
    }

    // Mengulang permainan
    public void restartGame() {
        newGame();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint(g);

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
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

    // Main method untuk menjalankan game
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new GameMain());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(390, 460); // Atur ukuran
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
