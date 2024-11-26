public class Main {
    public static void main(String[] args) {
        // using CLI
        GameMain gameCLI = new GameMain();
        gameCLI.play();

        // uisng GUI
        TTTGraphics gameGUI = new TTTGraphics();
        gameGUI.play();
    }
}
