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

            var mode = Integer.parseInt(scan.nextLine());

            //Instancia um novo jogador
            var playerId = objRemote.newPlayer();


            if (mode == 1) {


                Battle battle = objRemote.battleSingle(playerId,
                        GameInterface.Weapons.Pedra);

                var weapons = battle.getWeapons();

                System.out.println("Batalha: " + weapons.get(0) + " X " + weapons.get(1));
                System.out.println("Ganhador: " + battle.getWinner().toUpperCase());
            }else{
                System.out.print("******** Jogo Finalizado ********");
                return;
            }

            // Closing Scanner after the use
            scan.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
