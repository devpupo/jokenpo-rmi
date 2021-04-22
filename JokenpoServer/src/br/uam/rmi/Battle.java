package br.uam.rmi;

import java.util.ArrayList;


public class Battle {

    enum BattlePlayers {
        Attacker, Defender, Anyone;
    }

    private static String winner;
    private static Player attacker;
    private static Player defender;
    private static ArrayList<GameInterface.Weapons> weapons;

    public ArrayList<GameInterface.Weapons> getWeapons() {
        return weapons;
    }

    public void setWeapons(GameInterface.Weapons weapon) {
        weapons.add(weapon);
    }

    public void setDrawMatch() {
        attacker.setDraw();
        defender.setDraw();
    }

    public void setWonMatch(BattlePlayers player) {
        if (player == BattlePlayers.Attacker) {
            attacker.setWon();
            defender.setDefeats();
        } else {
            defender.setWon();
            attacker.setDefeats();
        }
    }

    public void setWinner(String player) {
        winner = player;
    }

    public void setAttacker(Player player) {
        attacker = player;
    }

    public void setDefender(Player player) {
        defender = player;
    }

    public String getWinner() {
        return winner;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

}
