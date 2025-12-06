package SnakesAndLaddersGame;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List<Player> players;
    Board board;
    Dice dice;

    public Game( int boardSize ) {
        this.players = new ArrayList<>();
        this.dice = new Dice();
        this.board = new Board(100 );
        this.board.initBoard();
    }

    public void addPlayer( Player player ){
        this.players.add(player);
    }

    public String Log(Player player, int x, int y ){
        return "Moving Player " + player.getID() + " from " + x + " to " + y;
    }

    private void processSpecialPiece(Player player, Piece piece, String pieceType) {
        System.out.println(pieceType + " found at " + player.getCurrentPosition() + " for PLAYER: " + player.getID());
        int oldPos = player.getCurrentPosition();
        piece.move(player);
        System.out.println(Log(player, oldPos, player.getCurrentPosition()));
    }

    public void movePlayer(Player player, int diceValue) {
        int nextPosition = Math.min(player.getCurrentPosition() + diceValue, board.getBoardSize());
        System.out.println(Log(player, player.getCurrentPosition(), nextPosition));
        player.setCurrentPosition(nextPosition);

        Ladder ladder = board.getLadderValue(player.getCurrentPosition());
        if (ladder != null) {
            processSpecialPiece(player, ladder, "LADDER");
        }

        Snake snake = board.getSnakeValue(player.getCurrentPosition());
        if (snake != null) {
            processSpecialPiece(player, snake, "SNAKE");
        }
    }

    public void startGame(){
        Deque<Player> playerTurn = new LinkedList<>( this.players );
        while( true ){
            Player currentPlayer = playerTurn.poll();
            int diceValue = this.dice.roll();
            assert currentPlayer != null;
            System.out.println("DICE value for PLAYER: "+currentPlayer.getID()+" is "+diceValue);
            movePlayer( currentPlayer, diceValue );
            if (currentPlayer.isHasWon(this.board.getBoardSize())){
                System.out.println("PLAYER: "+currentPlayer.getID()+" WON!");
                break;
            }
            playerTurn.addLast(currentPlayer);
        }
    }
}
