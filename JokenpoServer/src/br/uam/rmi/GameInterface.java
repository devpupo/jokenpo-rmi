package br.uam.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote{

    public enum Weapons {
        Pedra, Papel, Tesoura;

        public static Weapons getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    public enum GameMode {
        Single, Multiplayer;
    }

    //Desliga o jogo e retorna resultado do player
    public int shutdown(String playerId) throws RemoteException;

    //Instancia um novo jogador
    public String newPlayer() throws RemoteException;

    //Batalha modo Single
    public Battle battleSingle(String playerId, Weapons weapon) throws RemoteException;
}
