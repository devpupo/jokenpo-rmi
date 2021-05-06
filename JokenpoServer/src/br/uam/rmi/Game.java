package br.uam.rmi;

import com.sun.source.util.SourcePositions;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Game implements GameInterface {

    private static ArrayList<Player> playersCache = new ArrayList<>();
    private static Battle battle = new Battle();
    private static boolean conclude = true;

    public Game() throws RemoteException {
        super();
        System.out.println("[ STG ] Novo Servidor Jokenpô disponível.");
    }

    @Override
    public int shutdown(String playerId) throws RemoteException {

        var balance = 0;

        try {
            for (Player player : playersCache) {
                if (player.getId().contains(playerId)) {

                    System.out.println(player.getWon());
                    System.out.println(player.getDraw());

                    playersCache.remove(player);

                    balance = player.getBalance();
                }
            }

            //playersCache.clear();

            return balance;

        } catch (Exception e) {
            e.printStackTrace();
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
    public String battle(String playerId, Weapons weapon, GameMode mode) throws RemoteException {
        String winnerId = null;

        try {
            System.out.println("Let's Go!");

            Battle.BattlePlayers winner = Battle.BattlePlayers.Anyone;
            Weapons counterattack = null;

            if ((conclude || playersCache.isEmpty()) && mode == GameMode.Multiplayer) {
                battle.setAttacker(playersCache.get(0));
                battle.getAttacker().setWeapon(weapon);
                System.out.println("Aguardando próximo jogador...");
                conclude = false;

            } else if (!playersCache.isEmpty() && mode == GameMode.Multiplayer) {
                if (battle.getDefender() == null) {
                    battle.setDefender(playersCache.get(0));
                }

                counterattack = weapon;

                System.out.println("Player Weapon: " + battle.getAttacker().getWeapon()
                        + " X Player2 Weapon: " + counterattack);

                weapon = battle.getAttacker().getWeapon();

                if (weapon != counterattack) {
                    if (runBattle(weapon, counterattack)) {
                        battle.setWonMatch(Battle.BattlePlayers.Attacker);
                        winner = Battle.BattlePlayers.Attacker;
                    } else {
                        battle.setWonMatch(Battle.BattlePlayers.Defender);
                        winner = Battle.BattlePlayers.Defender;
                    }
                } else {
                    battle.setDrawMatch();
                    System.out.println("Empate Ô_Ô");
                }

                conclude = true;

                System.out.println("Ganhador da partida foi: " + winner);
                winnerId = (winner == Battle.BattlePlayers.Attacker) ?
                        battle.getAttacker().getId() :
                        battle.getDefender().getId();
            } else {
                if (battle.getDefender() == null) {
                    battle.setDefender(new Player());
                }

                //Modo de jogo SinglePlayer
                battle.setAttacker(playersCache.get(0));


                counterattack = Weapons.getRandom();

                System.out.println("Player Weapon: " + weapon
                        + " X NPC Weapon: " + counterattack);

                if (weapon != counterattack) {
                    if (runBattle(weapon, counterattack)) {
                        battle.setWonMatch(Battle.BattlePlayers.Attacker);
                        winner = Battle.BattlePlayers.Attacker;
                    } else {
                        battle.setWonMatch(Battle.BattlePlayers.Defender);
                        winner = Battle.BattlePlayers.Defender;
                    }
                } else {
                    battle.setDrawMatch();
                    System.out.println("Empate Ô_Ô");
                    return "draw";
                }

                System.out.println("Ganhador da partida foi: " + winner);

                return (winner == Battle.BattlePlayers.Attacker) ?
                        battle.getAttacker().getId() :
                        "NPC";
            }
            System.out.println(" ---------------------------------- ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try { Thread.sleep (50000); } catch (InterruptedException ignored) {}

        return winnerId;
    }

    //Verifica se o attack ganha
    private boolean runBattle(Weapons attack, Weapons counterattack) {
        switch (attack) {
            case Papel:
                return (counterattack == Weapons.Pedra);
            case Pedra:
                return (counterattack == Weapons.Tesoura);
            case Tesoura:
                return (counterattack == Weapons.Papel);
            default:
                return false;
        }
    }

}
