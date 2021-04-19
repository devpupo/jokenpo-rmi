package br.uam.rmi;

import java.util.UUID;

public class Player {
    private String id;
    private static int won = 0;
    private static int defeats = 0;
    private static int draw = 0;
    private GameInterface.Weapons weapon;

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public int getBalance() {
        return won - defeats;
    }

    public GameInterface.Weapons getWeapon() {
        return this.weapon;
    }

    public String getId() {
        return this.id;
    }

    public int getWon() {
        return won;
    }

    public int getDraw() {
        return draw;
    }

    public void setWon() {
        won++;
    }

    public void setDefeats() {
        defeats++;
    }

    public void setDraw() {
        draw++;
    }

    public void setWeapon(GameInterface.Weapons weapon) {
        this.weapon = weapon;
    }
}
