/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Elberg
 */
public class ServidorNodo extends Thread{
    private ServerSocket ss;
    private Socket s;
    
    private String dirIP;
    private String dirIpPropia;
    ArrayList<Recurso> array;
    private int id;
    private int puertocentral;
    private int puertopropio;
    private int i = 0;
    private int descargas = 0;
    
    public ServidorNodo(int id, String dirIP, int puertocentral, int puertopropio)
    {
        this.id = id;
        this.dirIP = dirIP;
        this.puertocentral = puertocentral;
        this.puertopropio = puertopropio;
    }
    
    /**
     * Metodo para correr el servidor del nodo 
     */
    public void correrServidor(ServidorNodo sn) throws IOException{
        /*Se conecta con servidor central*/
            this.s = new Socket(this.dirIP, this.puertocentral);
            this.ss = new ServerSocket(this.puertopropio);
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(),true);
            
            out.println("entrando");
            array = Global.recurso();
            out.println(array.get(0).getNombre());
            out.println(array.get(0).getDireccion());
            out.println(array.get(1).getNombre());
            out.println(array.get(1).getDireccion());
            out.println(array.get(2).getNombre());
            out.println(array.get(2).getDireccion());
            //Obtiene el ip del servidor dnoda y lo manda
             out.println(InetAddress.getLocalHost().getHostAddress());
            //Obtengo el puerto del servidor nodo y lo manda
             out.println(Integer.toString(ss.getLocalPort()));
             
             
            
             in.close();
             out.close();
             s.close();
             
             //Aqui empieza su funcion como cliente 
             new ConsolNodo(sn).start();
                     
             //Aqui empieza la funcion como servidor
             for(;;)
            {
                //Espera peticiones para aceptarlas
                Socket socket = ss.accept();
                if(ss.isClosed())
                {
                    break;
                }
                new ServidorManejadorNodo(socket).start();

                this.i++;
            }
    
    }
    
}
