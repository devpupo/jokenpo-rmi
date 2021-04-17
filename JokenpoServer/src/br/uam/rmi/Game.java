package br.uam.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Game implements GameInterface {

    private static Player playerCache;

    public Game() throws RemoteException {
        super();
        System.out.println("[ STG ] Novo Servidor Jokenpô disponível.");
    }

    @Override
    public int shutdown() throws RemoteException {
        return 1;
    }

    @Override
    public Battle battleSingle(Weapons weapon) throws RemoteException {

        System.out.println("Let's Go!");

        var npcWeapon = Weapons.getRandom();

        var weapons = new ArrayList<Weapons>();

        Battle battle = new Battle(weapons);
        Player player = new Player();



        System.out.println(npcWeapon + " X " + weapon);

        var winnings = player.getWon();

        switch (weapon) {
            case Papel:
                if (npcWeapon == Weapons.Pedra) {
                    player.setWon();
                } else {
                    player.setDefeats();
                }
                break;
            case Pedra:
                if (npcWeapon == Weapons.Tesoura) {
                    player.setWon();
                } else {
                    player.setDefeats();
                }
                break;
            case Tesoura:
                if (npcWeapon == Weapons.Papel) {
                    player.setWon();
                } else {
                    player.setDefeats();
                }
                break;
        }

        var winner = (player.getWon() > winnings) ? "player" : "npc";

        System.out.println("Player Weapon: " + weapon + "   X   NPC Weapon: " + npcWeapon);
        System.out.println((player.getWon() > winnings) ? "Player Venceu! =) " : "NPC Venceu! =( ");
        System.out.println(" ---------------------------------- ");

        battle.setWinner(winner);

        return battle;
    }
}
