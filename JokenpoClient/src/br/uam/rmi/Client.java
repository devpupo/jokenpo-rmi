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

            //Recebe o modo do jogo
            var modeId = Integer.parseInt(scan.nextLine());

            //Instancia um novo jogador
            var playerId = objRemote.newPlayer();

            int weaponId;

            do {
                System.out.print("-> Escolha suas armas! \n");
                System.out.print("(1) Pedra (2) Papel (3) Tesoura (0) Placar Geral \n");

                //Recebe o ID da Arma
                weaponId = Integer.parseInt(scan.nextLine());

               if(weaponId != 0){
                   //Envia para o servidor os dados de batalha
                   var winnerId = objRemote.battle(playerId,
                           WeaponById(weaponId),
                           ModeById(modeId));

                   //Retorna o ganhador da batalha atual
                   if (winnerId.contains("draw")) {
                       System.out.println("Empate! X_X \n");
                   } else if (winnerId.contains(playerId)) {
                       System.out.println("Você ganhou a batalha! =D \n");
                   } else {
                       System.out.println("Você perdeu a batalha! )= \n");
                   }
               }


            } while (weaponId != 0);

            //Encerra o jogo e retorna o ganhador final
            var finalWinner = objRemote.shutdown(playerId);

            if(finalWinner > 0){
                System.out.println("GANHOOOOOOOOOUUUUUUUUU");
            }else if(finalWinner < 0){
                System.out.println("FALHOUUUUUUUUUUUUUU");
            }else{
                System.out.println("EMPATEEEEEEEEEEE");
            }

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
