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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elberg
 */
public class ServidorManejadorNodo extends Thread {
private Socket socket;
public static String recursohash;
private ArrayList<Finger> finger = new ArrayList<Finger>();

    public ServidorManejadorNodo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        
    try {
        
      
        
         BufferedReader in1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter out1 = new PrintWriter(socket.getOutputStream(),true);
         
         String paso = in1.readLine();
         
         if (paso.equals("DR")){
             //Recurso calculado en hash, CONECTA CON NODO CLIENTE      
             
             recursohash = in1.readLine();
               System.out.println("Nodo solicita busqueda de recurso con hash: "+recursohash);
             calculoTablaFinger();
             int valor = mayorDeMenor(recursohash);
             
             if (valor != 0){
                  //Conexion al nodo fantasma que se encuentra en al anillo
                    Socket s = new Socket(Global.IP, Global.ANILLO_PUERTO);
                    
                    //Se establece comunicacion con el servidor anillo
                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(s.getOutputStream(),true);
                    
                    //Para decirle al servidor anillo que esta e busca de recursos, y pasa dicho valor
                     out.println("BR");
                     out.println(valor);
                     
                     String result = in.readLine();
                    
                    if (result.equals("NO")){
                        String iphash = in.readLine();
                        String puerto = in.readLine();
                        int intpu = Integer.parseInt(puerto);
                        s.close();
                        in.close();
                        out.close();
                        
                        // Pasa las ip y los puertos al nodo que pregunta
                        out1.println(iphash);
             

                    }
             
             }
             // Cuando devuelva 0 quiere decir que en su tabla esta apuntando al nodo que posee el archivo a descargar
             else {
             // quiere decir que en finger que es un array list al la posicion 0, esta el apuntador del documento 
                Finger fing = finger.get(0);
                out1.println(fing.haship);
                out1.println("CKECK");
                
                
             }
         }
         
                
    } catch (IOException ex) {
        Logger.getLogger(ServidorManejadorNodo.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    
    
    
    /**
     * METODO PARA CALCULAR TABLA DE FINGER DE CADA NODO
     * @throws java.net.UnknownHostException
     */
    public void calculoTablaFinger() throws UnknownHostException{
       int i = 0;
        int val =0;
       String ip = Global.subcadena(InetAddress.getLocalHost().getHostAddress());
       int haship = ip.hashCode();
       String hashpositivo = Global.positivoIP(haship);
       int hash = Integer.parseInt(hashpositivo);
       
       Finger fing = new Finger();
       fing.setPos(5);
       fing.setHaship(hash+1); 
       
       
       for (int j=1; j <= 6 ; j++){
           if (j == 6) finger.add(fing);
           else 
           {
                //Formula para definir los apuntadores de Chord
 
                   val = (int) (hash+Math.pow(2, j));
             
                System.out.println("Tabla finger Servidor : "+val);
                i=i++;
                Finger fin = new Finger(i,val);
                finger.add(fin);
           }          
          
       }
    }
    
     /**
     * Metodo que devuelve el mayor de los menores
     * @param hash
     * @return 
     */
    public int mayorDeMenor(String hash){
        int hashInt = Integer.parseInt(hash);
        for(int i=0;i < finger.size() ;i++){
            if ((finger.get(i).getHaship() < hashInt) && (hashInt < finger.get(i+1).getHaship()) )
                return finger.get(i).getHaship();
        }
        return 0;
    }
    
}
