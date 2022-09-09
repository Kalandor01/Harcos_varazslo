package model;
public class Play_field
{
    private int size;
    private Player[] players;
    
    public Play_field(int size, int fighters, int wizards) {
        this.size = size;
        this.generate(fighters, wizards);
    }
    
    public void generate(int fighters, int wizards) {
        this.players = new Player[wizards + fighters];
        int x = 0;
        for (;x < wizards; x++) {
            players[x] = new Wizard(size);
        }
        for (; x < wizards + fighters; x++) {
            players[x] = new Fighter(size);
        }
    }
    
    public void move_players() {
        for (Player player : players) {
            player.move();
        }
    }
    
    public boolean step() {
        //anyone alive
        int p = 0, alive = 0;
        while (p < players.length && alive < 2) {
            if(players[p].isAlive())
                alive++;
            p++;
        }
        if(alive > 1) {
            int[] p_this_pos;
            int p_same_pos;
            for (int x = 0; x < size; x++) {
                //get this pos player num
                p_same_pos = 0;
                for (Player player : players) {
                    if (player.pos == x && player.isAlive()) {
                        p_same_pos++;
                    }
                }
                //get this spot players pos
                p_this_pos = new int[p_same_pos];
                p_same_pos = 0;
                for (int y = 0; y < players.length; y++) {
                    if(players[y].pos == x && players[y].isAlive()) {
                        p_this_pos[p_same_pos] = y;
                        p_same_pos++;
                    }
                }
                //fight
                for (int pos1 : p_this_pos) {
                    if(players[pos1].isAlive()) {
                        for (int pos2 : p_this_pos) {
                            if(pos1 != pos2 && players[pos2].isAlive()) {
                                players[pos2].take_dmg(players[pos1].getAttack());
                            }
                        }
                    }
                }
            }
            //still alive?
            p = 0;
            alive = 0;
            while (p < players.length && alive < 2) {
                if(players[p].isAlive())
                    alive++;
                p++;
            }
            if(alive > 1)
                return true;
            else
                return false;
        }
        else
            return false;
    }
    
    public void write_board() {
        System.out.print("|");
        for (int x = 0; x < size; x++) {
            int p_num = 0;
            while (p_num < players.length && players[p_num].pos != x) {
                p_num++;
            }
            if(p_num == players.length)
                System.out.print("_");
            else
                System.out.print(players[p_num].letter);
        }
        System.out.println("|\n");
    }

    public void write_players() {
        System.out.println("Players:");
        for (Player player : players) {
            System.out.println(player);
        }
    }
    
    public void write_alive_players() {
        System.out.println("Players:");
        for (Player player : players) {
            if(player.isAlive())
                System.out.println(player);
        }
    }

    public int getSize() {
        return size;
    }

    public Player[] getPlayers() {
        return players;
    }
    
    
}
