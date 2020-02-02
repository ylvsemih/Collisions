public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.readRules("simulation.in.txt");
        game.startGame();
    }
}
