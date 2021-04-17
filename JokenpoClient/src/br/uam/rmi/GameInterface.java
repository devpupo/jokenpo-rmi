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

    public int shutdown() throws RemoteException;
    public Battle battleSingle(Weapons weapon) throws RemoteException;
}
