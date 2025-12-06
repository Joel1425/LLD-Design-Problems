package SnakesAndLaddersGame;

public class Snake implements Piece{
    int start;
    int end;
    public Snake( int start, int end ){
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
