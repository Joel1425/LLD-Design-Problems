package SnakesAndLaddersGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    int boardSize;
    HashMap<Integer, Snake> snakes;
    HashMap<Integer, Ladder> ladders;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
    }

    public int getBoardSize(){
        return this.boardSize;
    }

    public Ladder getLadderValue( int position ){
        if (this.ladders.containsKey(position)){
            return this.ladders.get(position);
        }
        return null;
    }

    public Snake getSnakeValue( int position ){
        if (this.snakes.containsKey(position)){
            return this.snakes.get(position);
        }
        return null;
    }

    public void addSnake( Snake snake ){
        this.snakes.put(snake.getStart(), snake);
    }

    public void addLadder( Ladder ladder ){
        this.ladders.put(ladder.getStart(), ladder);
    }

    public void initBoard(){
        System.out.println("INIT_BOARD");
        addSnake(new Snake(63,18));
        addSnake(new Snake(97,78));
        addSnake(new Snake(95,56));
        addSnake(new Snake(88,24));
        addSnake(new Snake(36,6));
        addSnake(new Snake(48,26));
        addLadder(new Ladder(4, 14));
        addLadder(new Ladder(8, 30));
        addLadder(new Ladder(38, 59));
        addLadder(new Ladder(42, 63));
        addLadder(new Ladder(76, 89));
        addLadder(new Ladder(92, 98));
        addLadder(new Ladder(99, 100));


    }
}
