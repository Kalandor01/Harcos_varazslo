package model;
public class Player
{
    protected String name;
    protected int life;
    protected int attack;
    protected int pos;
    protected int board_size;
    
    public Player(int board_size, String name) {
        this(board_size, name, (int)(Math.random() * 10 + 3), (int)(Math.random() * 5 + 1));
    }
    public Player(int board_size, String name, int life, int attack) {
        this(board_size, name, life, attack, (int)(Math.random() * board_size));
    }
    public Player(int board_size, String name, int life, int attack, int pos) {
        this.name = name;
        this.life = life;
        this.attack = attack;
        this.pos = pos;
        this.board_size = board_size;
    }
    
    public boolean take_dmg(int dmg) {
        if(this.life > 0)
            this.life -= dmg;
        if(this.life > 0)
            return true;
        else
        {
            this.pos = -1;
            return false;
        }
    }
    
    public void move() {
        if(this.isAlive())
        {
            int move = (int)(Math.random() * 3);
            if(move == 1 && this.pos != 0)
                pos--;
            if(move == 2 && this.pos != board_size - 1)
                pos++;
        }
    }

    public boolean isAlive() {
        return this.life > 0;
    }
    
    public int getLife() {
        return life;
    }

    public int getAttack() {
        return attack;
    }

    public int getPos() {
        return pos;
    }

    @Override
    public String toString() {
        return "Player: " + name + ", hp: " + life + ", attack=" + attack + ", pos=" + pos;
    }
    
}
