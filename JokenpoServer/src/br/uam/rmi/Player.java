package br.uam.rmi;

public class Player {
    private  int won = 0;
    private  int defeats = 0;
    private GameInterface.Weapons weapon;

    public int getBalance() { return won - defeats; }

    public GameInterface.Weapons getWeapon() { return weapon; }

    public int getWon() { return won; }

    public void setWon() {
        this.won++;
    }

    public void setDefeats() {
        this.defeats++;
    }

    public void setWeapon(GameInterface.Weapons weapon) { this.weapon = weapon; }
}
