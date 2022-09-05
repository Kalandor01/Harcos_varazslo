package main;

import java.util.Random;

public class Harcos_varazslo
{
    
    public static String kiir(int stage, int tabla, String name[], int life[], int attack[], int pos[])
    {
        boolean none;
        int x, y, line_n, line_i;
        String line = "";
        for(x=0;x<tabla;x++)
        {
            none = true;
            line_n = 0;
            line_i = 0;
            for(y=0;y<pos.length;y++)
            {
                if(pos[y] == x && life[y] > 0)
                {
                    none = false;
                    line_i = y;
                    switch (stage) {
                        case 1 -> line_n += life[y];
                        case 2 -> line_n++;
                        default -> line_n += attack[y];
                    }
                }
            }
            if(none == true)
            {
                if(stage == 2)
                    line += "_";
                else
                    line += " ";
            }
            else
            {
                if(stage == 2)
                {
                    if(line_n == 1)
                        line += name[line_i];
                    else
                        line += "X";
                }
                else
                    if(line_n < 10)
                        line += line_n;
                    else
                        line += "X";
            }            
        }
        return line;
    }
    
    
    public static void move(int tabla, int life[], int pos[])
    {
        Random r = new Random();
        int x, lep;
        for(x=0;x<pos.length;x++)
        {
            if(life[x] > 0)
            {
                if(pos[x] == 0)
                {
                    lep = r.nextInt(2);
                    if(lep == 0)
                        pos[x]++;
                }
                else if(pos[x] == tabla - 1)
                {
                    lep = r.nextInt(2);
                    if(lep == 0)
                        pos[x]--;
                }
                else
                {
                    lep = r.nextInt(3);
                    if(lep == 0)
                        pos[x]--;
                    else if(lep == 1)
                        pos[x]++;
                }
            }
        }
    }
    
    
    public static void fight(int tabla, int life[], int attack[], int pos[])
    {
        int x, y, z, at = 0;
        z = 0;
        for(x=0;x<tabla;x++)
        {
            for(y=0;y<pos.length;y++)
                if(life[y] > 0 && pos[y] == x)
                    at++;
            int pos_a[] = new int[at];
            for(y=0;y<pos.length;y++)
            {
                if(life[y] > 0 && pos[y] == x)
                {
                    pos_a[z] = y;
                    z++;
                }
            }
            for(y=0;y<pos_a.length;y++)
            {
                for(z=0;z<pos_a.length;z++)
                {
                    if(y != z)
                    {
                        life[pos_a[y]] -= attack[pos_a[z]];
                        if(life[pos_a[y]] <= 0)
                            life[pos_a[y]] = 0;
                    }
                }
            }
        }
    }
    
    
    public static void main(String[] args) throws InterruptedException
    {
        //var
        Random r = new Random();
        boolean shrink = true;
        int tabla = 20, delay = 200, scale_multy = 2;
        int x, sum, ded, ded_p = 0;
        //variables
        String name[] = {"H", "V", "A", "B", "C", "D", "E", "F"};
        int life[] = new int[name.length];
        int attack[] = new int[name.length];
        int pos[] = new int[name.length];
        for(x=0;x<name.length;x++)
        {
            life[x] = 9;
            attack[x] = r.nextInt(3) + 1;
            pos[x] = r.nextInt(tabla - 1);
        }
        
        //tábla
        sum = 0;
        for(x=0;x<life.length;x++)
        {
            if(life[x] > 0)
            {
                sum ++;
            }
        }
        while (sum > 1)
        {
            move(tabla, life, pos);
            fight(tabla, life, attack, pos);
            //kiír
            System.out.println();
            for(x=0;x<3;x++)
                System.out.println(kiir(x+1, tabla, name, life, attack, pos) + "\n");
            for(x=0;x<name.length;x++)
            {
                if(life[x] > 0)
                    System.out.println(name[x] + ": " + "pos:" + pos[x] + ", life:" + life[x] + ", attack:" + attack[x]);
                else
                    System.out.println(name[x] + " died.");
            }
            System.out.println();
            //szum
            sum = 0;
            ded = 0;
            for(x=0;x<life.length;x++)
            {
                if(life[x] > 0)
                    sum ++;
                else
                    ded++;
            }
            //shrinking board
            if(ded > ded_p && shrink)
            {
                tabla -=scale_multy * (ded-ded_p);
                if(tabla<1)
                    tabla = 1;
            }
            ded_p = ded;
            //Showe players
            for(x=0;x<pos.length;x++)
            {
                if(pos[x]>tabla-1)
                    pos[x] = tabla-1;
            }
            //slower
            Thread.sleep(delay);
        }
    }
}
