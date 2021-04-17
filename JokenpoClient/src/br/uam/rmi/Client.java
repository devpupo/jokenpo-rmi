package br.uam.rmi;

import java.rmi.Naming;

public class Client {

    public static void main(String[] args) {
        System.out.println("Carregando o cliente.");

        try{
            GameInterface objRemote =
                    (GameInterface) Naming.lookup("rmi://LOCALHOST:1099/battle");

            Battle battle = objRemote.battleSingle(GameInterface.Weapons.Pedra);

            var weapons = battle.getWeapons();

            System.out.println("Batalha: " + weapons.get(0) + " X " + weapons.get(1));
            System.out.println("Ganhador: " + battle.getWinner().toUpperCase());

        }catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
}
