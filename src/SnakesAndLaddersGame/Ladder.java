package SnakesAndLaddersGame;

public class Ladder implements Piece{
    int start;
    int end;
    public Ladder( int start, int end ){
        this.start = start;
        this.end = end;
    }
    @Override
    public void move( Player player ) {
        player.setCurrentPosition( this.end );
    }
    @Override
    public int getStart(){
        return this.start;
    }
}
