package SnakesAndLaddersGame;

public class SnakesAndLadders {
    public static void main(String[] args) {
        Game game = new Game(100);
        game.addPlayer(new Player("1"));
        game.addPlayer(new Player("2"));
        game.startGame();
    }
}
