package SnakesAndLaddersGame;

public class Player {
    String id;
    int currentPosition;
    boolean hasWon;

    public Player(String id ) {
        this.id = id;
        this.currentPosition = 1;
        this.hasWon = false;
    }

    public String getID(){
        return this.id;
    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }

    public boolean isHasWon( int boardSize ){
        return this.currentPosition == boardSize;
    }

    public void setCurrentPosition( int position ){
        this.currentPosition = position;
    }
}
