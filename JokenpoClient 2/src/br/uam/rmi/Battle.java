package br.uam.rmi;

import java.util.ArrayList;

public class Battle {
    private String winner;
    private  ArrayList<GameInterface.Weapons> weapons;

    public Battle(ArrayList<GameInterface.Weapons> weapons) {
        this.weapons = weapons;
    }

    public String getWinner() { return winner; }

    public ArrayList<GameInterface.Weapons> getWeapons() { return this.weapons; }

    public void setWinner(String winner) { this.winner = winner; }
}
