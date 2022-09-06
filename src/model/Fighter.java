package model;
public class Fighter extends Player
{
    public Fighter(int board_size) {
        super(board_size, "F");
        this.attack *= 2;
        this.life = (int)(Math.ceil(this.life * (2.0/3)));
    } 
}
