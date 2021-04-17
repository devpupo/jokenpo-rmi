package br.uam.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor {

    public static void main(String args[]) throws RemoteException{
        Registry registro=null;
        try {

            // Instanciação
            Game obj = new Game();

            // Exportando o objeto para o stub
            Remote stub = UnicastRemoteObject.exportObject(obj,0);

            // Binding o objeto remoto (stub) no registro
            registro = LocateRegistry.createRegistry(1099);
            registro.rebind("battle", stub);

        } catch (Exception e) {
            System.out.println("Erro no Servidor:" + e.getMessage());
        }
    }
}
