package br.uam.rmi;

import java.util.UUID;

public class Player {
    private  String id;
    private  int won = 0;
    private  int defeats = 0;
    private GameInterface.Weapons weapon;

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public int getBalance() { return won - defeats; }

    public GameInterface.Weapons getWeapon() { return weapon; }

    public String getId(){ return id; }

    public int getWon() { return won; }

    public void setWon() {
        won++;
    }

    public void setDefeats() {
        defeats++;
    }

    public void setWeapon(GameInterface.Weapons weapon) { this.weapon = weapon; }
}
