package model;
public class Wizard extends Player
{

    public Wizard(int board_size) {
        super(board_size, "Wizard");
        this.attack = (int)(Math.ceil( this.attack * (2.0/3)));
        this.life *= 2;
    }    
}
