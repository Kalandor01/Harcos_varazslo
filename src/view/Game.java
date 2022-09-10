/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.Timer;
import model.Play_field;
import model.Player;

/**
 *
 * @author Gigabyte
 */
public class Game extends javax.swing.JFrame {

    /**
     * Creates new form Game
     */
    //settings
    int delay = 50;
    int fiels_size = 15;
    int fighters = 5;
    int wizards = 5;
    //vars
    boolean alive = true;
    boolean updating = false;
    boolean paused = true;
    Play_field game;
    JLabel[][] field_labels;
    Player[] players;
    JSpinner delay_spinner = new javax.swing.JSpinner();
    
    public Game() {
        initComponents();
        game = new Play_field(fiels_size, fighters, wizards);
        make_field();
        update_field();
        warning_label.setText("");
        info_label.setText("Waiting for start.");
    }
    
    private void make_field() {
        //set labels/settings
        info_label.setText("Setting up.");
        field_size_spinner.setValue(fiels_size);
        fighters_spinner.setValue(fighters);
        wizards_spinner.setValue(wizards);
        //setup delay
        delay_spinner.setModel(new javax.swing.SpinnerNumberModel(0.2d, -0.1d, null, 0.1d));
        delay_spinner_panel.add(delay_spinner);
        delay_spinner.setValue(delay/1000.0);
        //setup field
        players = game.getPlayers();
        field_labels = new JLabel[game.getSize()][3];
        play_field.removeAll();
        play_field.setLayout(new java.awt.GridLayout());
        play_field.setLayout(new java.awt.GridLayout(1, players.length));
        for (int x = 0; x < game.getSize(); x++) {
            //setup panel
            JPanel field_panel = new JPanel();
            field_panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            field_panel.setLayout(new java.awt.GridLayout());
            field_panel.setLayout(new java.awt.GridLayout(3, 1));
            //setup hp
            JLabel life = new JLabel();
            field_labels[x][0] = life;
            life.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            life.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            life.setVerticalTextPosition(JLabel.CENTER);
            life.setForeground(Color.BLACK);
            //setup player
            JLabel player = new JLabel();
            field_labels[x][1] = player;
            player.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            player.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            player.setVerticalTextPosition(JLabel.CENTER);
            player.setForeground(Color.BLACK);
            //setup attack
            JLabel attack = new JLabel();
            field_labels[x][2] = attack;
            attack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            attack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            attack.setVerticalTextPosition(JLabel.CENTER);
            attack.setForeground(Color.BLACK);
            //add
            field_panel.add(life);
            field_panel.add(player);
            field_panel.add(attack);
            play_field.add(field_panel);
        }
    }
    
    private void update_field() {
        //clear
        for (int x = 0; x < game.getSize(); x++) {
            field_labels[x][0].setText("");
            field_labels[x][1].setText("");
            field_labels[x][2].setText("");
        }
        //update
        players = game.getPlayers();
        for (int x = 0; x < players.length; x++) {
            if(players[x].isAlive()) {
                //get vars
                int player_pos = players[x].getPos();
                JLabel life = field_labels[player_pos][0];
                JLabel player = field_labels[player_pos][1];
                JLabel attack = field_labels[player_pos][2];
    //            System.out.printf("%s: %s: %s, %s, %s\n", x, players[x].getPos(), life, player, attack);
                System.out.printf("%s: %s, %s, %s\n", players[x].getLetter(), players[x].getLife(), players[x].getPos(), players[x].getAttack());
                //setup hp
                life.setText(Integer.toString(players[x].getLife()));
                //setup player
                player.setText(Character.toString(players[x].getLetter()));
                //setup attack
                attack.setText(Integer.toString(players[x].getAttack()));
            }
        }
        System.out.println();
        revalidate();
    }
    
    private void update() {
        if(alive)
        {
            updating = true;
            game.move_players();
            alive = game.step();
            update_field();
            if(alive && !paused) {
                if(delay >= 0)
                {
                    Timer timer = new Timer(delay, e -> {
                        update();
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                else
                    update();
            }
            else if(!alive)
                info_label.setText("Finished!");
        }
        else
            info_label.setText("Finished!");
    }
    
    private void set_settings() {
        if((int)field_size_spinner.getValue() > 0)
            fiels_size = (int)field_size_spinner.getValue();
        else
            field_size_spinner.setValue(fiels_size);
        if((int)fighters_spinner.getValue() >= 0)
            fighters = (int)fighters_spinner.getValue();
        else
            fighters_spinner.setValue(fighters);
        if((int)wizards_spinner.getValue() >= 0)
            wizards = (int)wizards_spinner.getValue();
        else
            wizards_spinner.setValue(wizards);
        if((double)delay_spinner.getValue() >= (double)(-0.1))
        {
            if((double)delay_spinner.getValue() >= (double)0.0)
                warning_label.setText("");
            else
                warning_label.setText("NO DELAY (WILL FREZE WINDOW DURING FIGHT)");
            delay = (int)((double)(delay_spinner.getValue())*1000);
        }
        else
            delay_spinner.setValue(delay/1000.0);
    }
    
    private void restart() {
        //set settings
        set_settings();
        //restart
        updating = false;
        alive = true;
        paused = true;
        game = new Play_field(fiels_size, fighters, wizards);
        make_field();
        update_field();
        info_label.setText("Waiting for start.");
        start_button.setText("Start");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        play_field = new javax.swing.JPanel();
        setting_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        field_size_spinner = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        fighters_spinner = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        wizards_spinner = new javax.swing.JSpinner();
        delay_spinner_panel = new javax.swing.JPanel();
        info_panel = new javax.swing.JPanel();
        info_label = new javax.swing.JLabel();
        start_button = new javax.swing.JButton();
        restart_button = new javax.swing.JButton();
        warning_label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        play_field.setLayout(new java.awt.GridLayout(1, 5));

        setting_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Settings", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        setting_panel.setMinimumSize(new java.awt.Dimension(300, 32));
        setting_panel.setLayout(new java.awt.GridLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Field size"));
        jPanel2.setMinimumSize(new java.awt.Dimension(2, 2));
        jPanel2.setLayout(new java.awt.GridLayout());

        field_size_spinner.setModel(new javax.swing.SpinnerNumberModel(3, 1, null, 1));
        jPanel2.add(field_size_spinner);

        setting_panel.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Fighters"));
        jPanel3.setMinimumSize(new java.awt.Dimension(10, 10));
        jPanel3.setLayout(new java.awt.GridLayout());

        fighters_spinner.setModel(new javax.swing.SpinnerNumberModel(1, 0, null, 1));
        jPanel3.add(fighters_spinner);

        setting_panel.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Wizards"));
        jPanel4.setMinimumSize(new java.awt.Dimension(10, 10));
        jPanel4.setLayout(new java.awt.GridLayout());

        wizards_spinner.setModel(new javax.swing.SpinnerNumberModel(1, 0, null, 1));
        jPanel4.add(wizards_spinner);

        setting_panel.add(jPanel4);

        delay_spinner_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Delay (s)"));
        delay_spinner_panel.setLayout(new java.awt.GridLayout());
        setting_panel.add(delay_spinner_panel);

        info_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info_label.setText("PLACEHOLDER");

        start_button.setText("Start");
        start_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_buttonActionPerformed(evt);
            }
        });

        restart_button.setText("Restart");
        restart_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restart_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout info_panelLayout = new javax.swing.GroupLayout(info_panel);
        info_panel.setLayout(info_panelLayout);
        info_panelLayout.setHorizontalGroup(
            info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(info_panelLayout.createSequentialGroup()
                .addComponent(info_label, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(info_panelLayout.createSequentialGroup()
                .addComponent(start_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(restart_button))
        );
        info_panelLayout.setVerticalGroup(
            info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(info_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(info_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(restart_button)
                    .addComponent(start_button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        warning_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        warning_label.setText("GOOD");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(play_field, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(info_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(warning_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(setting_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(play_field, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(warning_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(info_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setting_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void start_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_buttonActionPerformed
        if(paused)
        {
            start_button.setText("Pause");
            info_label.setText("Fighting...");
            paused = false;
            update();
        }
        else
        {
            start_button.setText("Resume");
            info_label.setText("Paused.");
            paused = true;
        }
    }//GEN-LAST:event_start_buttonActionPerformed

    private void restart_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restart_buttonActionPerformed
        paused = true;
        restart();
    }//GEN-LAST:event_restart_buttonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel delay_spinner_panel;
    private javax.swing.JSpinner field_size_spinner;
    private javax.swing.JSpinner fighters_spinner;
    private javax.swing.JLabel info_label;
    private javax.swing.JPanel info_panel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel play_field;
    private javax.swing.JButton restart_button;
    private javax.swing.JPanel setting_panel;
    private javax.swing.JButton start_button;
    private javax.swing.JLabel warning_label;
    private javax.swing.JSpinner wizards_spinner;
    // End of variables declaration//GEN-END:variables

}
