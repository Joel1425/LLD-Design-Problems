package SnakesAndLaddersGame;

import java.util.Random;

public class Dice {
    Random rand;
    public Dice(){
        this.rand = new Random();
    }
    public int roll(){

        return this.rand.nextInt(6) + 1;
    }
}
