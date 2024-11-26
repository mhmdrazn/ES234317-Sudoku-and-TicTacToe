package tictactoe;
public class Main {
    public static void main(String[] args) {
        System.out.println("Playing Tic Tac Toe");
        TTTConsoleNonOO game = new TTTConsoleNonOO();
        game.play();

        TTTGraphics gameGUI = new TTTGraphics();
        gameGUI.play();
    }
}
