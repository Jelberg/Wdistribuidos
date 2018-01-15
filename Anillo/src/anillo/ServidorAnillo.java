/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anillo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Elberg
 */
public class ServidorAnillo extends Thread{
    private ServerSocket Servidor;
    private int puerto; 
    private String OS = System.getProperty("os.name");
    
    public ServidorAnillo(int puerto)
    {
        this.puerto = puerto;
    }
    
    /**
     * Inicia el Servidor para el anillo
     */
    public void correrServidor(){
         try
        {
            this.Servidor = new ServerSocket(this.puerto);
       
            for(;;)
            {
                //Espera que un cliente se oonecte
                System.out.println("Esperando que cliente se conecte....");
                Socket socket = Servidor.accept();
                
                //Abre el hilo con el manejador del servidor
                new ManejadorAnillo(socket).start();
            }
        }
        catch(IOException ioe)
        {
            System.err.println(ioe);
        }
    }
    
}
