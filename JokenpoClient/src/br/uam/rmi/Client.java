package br.uam.rmi;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        System.out.println("Carregando o cliente.");

        try {
            GameInterface objRemote =
                    (GameInterface) Naming.lookup("rmi://LOCALHOST:1099/battle");

            Scanner scan = new Scanner(System.in);

            System.out.print("******** Bem Vindo ao GAME [ JO-KEN-PO ] ******** \n");
            System.out.print("-> Escolha (1) SinglePlayer (2) MultiPlayer \n");

            var modeId = Integer.parseInt(scan.nextLine());

            //Instancia um novo jogador
            var playerId = objRemote.newPlayer();

            int weaponId;

            do {
                System.out.print("-> Escolha suas armas! ");
                System.out.print("(1) Pedra (2) Papel (3) Tesoura (0) Placar Geral ");
                weaponId = Integer.parseInt(scan.nextLine());

                if (ModeById(modeId) == GameInterface.GameMode.Single) {

                    Battle battle = objRemote.battleSingle(playerId,
                            WeaponById(weaponId));

                    var weapons = battle.getWeapons();

                    System.out.println("Batalha: " + weapons.get(0) + " X " + weapons.get(1));
                    System.out.println("Ganhador: " + battle.getWinner().toUpperCase());

                } else {
                    throw new Exception("Modo de jogo inválido!");
                }


            } while (weaponId == 0);

            // Closing Scanner after the use
            scan.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static GameInterface.Weapons WeaponById(int id) throws Exception {
        switch (id) {
            case 1:
                return GameInterface.Weapons.Pedra;
            case 2:
                return GameInterface.Weapons.Papel;
            case 3:
                return GameInterface.Weapons.Tesoura;
            default:
                throw new Exception("Arma inválida");
        }
    }

    private static GameInterface.GameMode ModeById(int id) throws Exception {
        switch (id) {
            case 1:
                return GameInterface.GameMode.Single;
            case 2:
                return GameInterface.GameMode.Multiplayer;
            default:
                throw new Exception("Modo de jogo inválido");
        }
    }

}
