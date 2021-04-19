package br.uam.rmi;

import com.sun.source.util.SourcePositions;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Game implements GameInterface {

    private static final ArrayList<Player> playersCache = new ArrayList<>();
    private static final Battle battle = new Battle();

    public Game() throws RemoteException {
        super();
        System.out.println("[ STG ] Novo Servidor Jokenpô disponível.");
    }

    @Override
    public int shutdown(String playerId) throws RemoteException {
        try {
            for (Player player : playersCache) {
                if (player.getId().contains(playerId)) {
                    return player.getBalance();
                }
            }
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

        try {
            System.out.println("Let's Go!");

            Battle.BattlePlayers winner = Battle.BattlePlayers.Anyone;
            Weapons counterattack = null;

            if (playersCache.isEmpty() && mode == GameMode.Multiplayer) {
                battle.setWeapons(weapon);
                battle.setAttacker(playersCache.get(0));
                System.out.println("Aguardando próximo jogador...");

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

                System.out.println("Ganhador da partida foi: " + winner);
                return (winner == Battle.BattlePlayers.Attacker) ?
                        battle.getAttacker().getId() :
                        battle.getDefender().getId();
            }else{

                //Modo de jogo SinglePlayer
                battle.setAttacker(playersCache.get(0));
                counterattack = Weapons.getRandom();

                System.out.println("Player Weapon: " + battle.getAttacker().getWeapon()
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
        return null;
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
