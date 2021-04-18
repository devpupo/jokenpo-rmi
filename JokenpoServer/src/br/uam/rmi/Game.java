package br.uam.rmi;

import com.sun.source.util.SourcePositions;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Game implements GameInterface {

    private static ArrayList<Player> playersCache = new ArrayList<>();

    public Game() throws RemoteException {
        super();
        System.out.println("[ STG ] Novo Servidor Jokenpô disponível.");
    }

    @Override
    public int shutdown(String playerId) throws RemoteException {
        for (Player player : playersCache) {
            if (player.getId().contains(playerId)) {
                return player.getBalance();
            }
        }
        return 404;
    }

    @Override
    public String newPlayer() throws RemoteException {
        try {
            Player player = new Player();
            playersCache.add(player);
            return player.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Battle battleSingle(String playerId, Weapons weapon) throws RemoteException {

        System.out.println("Let's Go!");

        var npcWeapon = Weapons.getRandom();

        var weapons = new ArrayList<Weapons>();

        Battle battle = new Battle(weapons);

        System.out.println("Player Weapon: " + weapon + " X NPC Weapon: " + npcWeapon);

        for (Player player : playersCache) {
            if (player.getId().contains(playerId)) {
                if (weapon != npcWeapon) {

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

                    System.out.println((player.getWon() > winnings) ? "Player Venceu! =) "
                            : "NPC Venceu! =( ");

                    System.out.println("Placar Vitórias: " + player.getWon() + " Balance: " + player.getBalance());

                    battle.setWinner(winner);

                } else {
                    System.out.println("Empate Ô_Ô");
                }
                System.out.println(" ---------------------------------- ");

                return battle;
            }
        }
        return null;
    }
}
