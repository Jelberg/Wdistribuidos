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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static nodo.Global.positivoIP;


/**
 *
 * @author Elberg
 */
public class ConsolNodo extends Thread {
    private int i;
    private ServidorNodo sd;
    int intpu;
    public static String nombrerecursohash;
    private ArrayList<Finger> finger = new ArrayList<Finger>();

    public ConsolNodo(int id, ServidorNodo sd) {
        this.i = id;
        this.sd = sd;
    }

    public ConsolNodo(ServidorNodo sd) {
        this.sd = sd;
    }

    @Override
    public void run() {
        int valor;
        Scanner sc = new Scanner(System.in);
        for(;;)
        {
            System.out.println(" ");
            System.out.println("Bienvenido");

            System.out.println("1. Buscar Recurso");
            System.out.println("2. Estado de las solicitudes");
            System.out.println("3. Mostrar nodos anteriores y posteriores");
            System.out.println("0. Para salir");
            String opc = sc.nextLine();
            System.out.println("");
            
            //Para desloguearse
            if(opc.equalsIgnoreCase("3"))
            {
                //PARA DESCONECTAR EL NODO DEL SERVIDOR
                    //Conexion al nodo fantasma que se encuentra en al anillo
                    Socket s;
                try {
                    
                    s = new Socket(Global.IP, Global.ANILLO_PUERTO);                                                      
                    //Se establece comunicacion con el servidor anillo
                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(s.getOutputStream(),true);
                    
                    out.println("AP");
                    out.println(InetAddress.getLocalHost().getHostAddress());
                    
                } catch (IOException ex) {
                    Logger.getLogger(ConsolNodo.class.getName()).log(Level.SEVERE, null, ex);
                }
      
            }
            
            //Para desloguearse
            if(opc.equalsIgnoreCase("0"))
            {
                try {
                    //PARA DESCONECTAR EL NODO DEL SERVIDOR
                    //Conexion al nodo fantasma que se encuentra en al anillo
                    Socket s = new Socket(Global.IP, Global.ANILLO_PUERTO);
                                        
                    //Se establece comunicacion con el servidor anillo
                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(s.getOutputStream(),true);
                
                    out.println("EXIT");
                    //envia ip del que va a desloggear
                    out.println(Global.subcadena(InetAddress.getLocalHost().getHostAddress()));
                    
               
                        System.out.println("..bye");
                        break;
                
                    
                } catch (IOException ex) {
                    Logger.getLogger(ConsolNodo.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            
            if(opc.equalsIgnoreCase("1"))
            {
                System.out.println("Nombre del recurso: ");
                String op = sc.nextLine();
                
                try {
                    calculoTablaFinger();
                    op = Global.subcadena(op);
                    int hash= op.hashCode();
                    String valorpositivo = positivoIP(hash);
                    //valor positivo del recurso
                    nombrerecursohash = valorpositivo;
                    System.out.println("Valor que se pasa para calcular mayor de los menores: "+valorpositivo);
                    valor = mayorDeMenor(valorpositivo);
                    System.out.println("M de m "+valor);
            
                    //SE BUSCA ESE VALOR EN LAS TABLAS DE FINGER DEL FANTASMA
                    if (valor != 0){
                     System.out.println("..Buscando nodo que contenga el recurso..Recurso:"+nombrerecursohash);
           
                
                for (;;){      
                        
                    //Conexion al nodo fantasma que se encuentra en al anillo
                    Socket s = new Socket(Global.IP, Global.ANILLO_PUERTO);
                    
                    //Se establece comunicacion con el servidor anillo
                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(s.getOutputStream(),true);
                                       
                    //BR es para buscar en en servidor anilllo sobre el valor
                    out.println("BR");
                    out.println(valor);
                    
                    String result = in.readLine();
                    System.out.println("Este es el result: "+result);
                    
                    if (result.equals("NO")){//Respuesta del anillo
                        String ip = in.readLine(); //En hash
                        // Porwur lo que devuelve es el hash de la ip que cumple el mayor de nos menores 
                        nombrerecursohash = ip;
                        String puerto = in.readLine();
                        System.out.println("Buscando recurso en el nodo con el puerto:"+puerto);
                        intpu = Integer.parseInt(puerto);
                        s.close();
                        in.close();
                        out.close();
                    }
                   //Aqui forma la coneccion con el siguiente nodo 
                   //TODO: revisar la ip a la que se conecta el socket s1
                        Socket s1 = new Socket(Global.IP, intpu );
                        BufferedReader in1 = new BufferedReader(
                        new InputStreamReader(s1.getInputStream()));
                        PrintWriter out1 = new PrintWriter(s1.getOutputStream(),true);  
                                
                        out1.println("DR");
                        out1.println(nombrerecursohash);
                                
                        //nuevo valor encontrado de la tabla hash, y puerto o Rspuesta CHECK
                        String val = in1.readLine();
                        valor = Integer.parseInt(val);
                        //Posible respuesta del apuntaoor
                        String recibe = in1.readLine();
                              
                        out1.close();
                        in1.close();
                               
                           if(val.equals("CHECK")){
                               //la variable recibe tiene el apuntador en donde se encuentra
                               //Conaultar tabla hash de servior para que devuelva puerto y direccion
                               //Conexion al nodo fantasma que se encuentra en al anillo
                                Socket s3 = new Socket(Global.IP, Global.ANILLO_PUERTO);
                                //Se establece comunicacion con el servidor anillo
                                BufferedReader in3 = new BufferedReader(
                                new InputStreamReader(s.getInputStream()));
                                PrintWriter out3 = new PrintWriter(s.getOutputStream(),true);
                                
                                out3.println("checkData"); //paraentrar en el case final el anillo
                                out3.println(recibe);
                                
                                String a = in3.readLine();
                                String b = in3.readLine();
                                
                                System.out.println("RESPUESTA: "+a);
                                System.out.println("RESPUESTA: "+b);
                                // aqui ya se tiene las direcciones para hacerla descarga 
                                  
                       
                    
                          } //Termina el if del valor mayor de los menores y quiere decir que en la tabla se encuentra apuntando al recurso 
                          
                    
                    } 
                        
                    } //Cierre del for
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ConsolNodo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ConsolNodo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
           
        }
        
    }
    
    
    /**
     * METODO PARA CALCULAR TABLA DE FINGER DE CADA NODO
     */
    public void calculoTablaFinger() throws UnknownHostException{
       int i = 1;
       int val =0;
       String ip = Global.subcadena(InetAddress.getLocalHost().getHostAddress());
       System.out.println("Esta es la ip pata tabla finger: "+ip);
       int haship = ip.hashCode();
       System.out.println("Esta es la ip con hash: "+haship);
       String hashpositivo = Global.positivoIP(haship);
      // System.out.println("Valor positivo del hash "+hashpositivo);
       int hash = Integer.parseInt(hashpositivo);
       
       Finger fing = new Finger();
       fing.setPos(5);
       fing.setHaship(hash+1); 
       System.out.println (hash);
       
       for (int j=0; j < 6 ; j++){
           if (j == 5) finger.add(fing);
           else 
           {
               //Formula para definir los apuntadores de Chord
 
                   val = (int) (hash+Math.pow(2, j));
             
                System.out.println("Tabla finger de nodo : "+val);
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
        for(int i=0;i < finger.size()-1 ;i++){
            if ((i == 4) && (finger.get(i).getHaship() < hashInt)) return finger.get(i).getHaship();  
            else if ((finger.get(i).getHaship() < hashInt) && (hashInt < finger.get(i+1).getHaship()) )
                return finger.get(i).getHaship();
        }
        return 0;
    }

    
    /**
     * 
     *  GETTERS AND SETTERS
     * 
     */
    public int getI() {
        return i;
    }

    public void setI(int id) {
        this.i = id;
    }

    public ServidorNodo getSd() {
        return sd;
    }

    public void setSd(ServidorNodo sd) {
        this.sd = sd;
    }
    
    
    
}
