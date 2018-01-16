/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anillo;

import static anillo.Global.positivoIP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elberg
 */
public class ManejadorAnillo extends Thread{
    private Socket socket;
    private Tabla infodata;
    private String ipnodo, ipnodohash;
    private String user;
    String puerto;
    private int numUsu;
    ArrayList<Tabla> ArrayTab = new ArrayList<Tabla>();
    private Connection conn = Sql.getConInstance();

    public ManejadorAnillo(Socket socket) {
        this.socket = socket;
    }

    public ManejadorAnillo(Socket socket, String ipnodo) {
        this.socket = socket;
        this.ipnodo = ipnodo;
    }

   

    @Override
    public void run() {
        try {
            /*Se abren los canales de comunicación
            de entrada y salida de datos
            */
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));      
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
           //opcion para entrar en un case
            String data = in.readLine();
            
            //if para actualizar tabla finger fantasma
            if (data.contains("entrando")){
            System.out.println("Usuario Conectado");
            numUsu = numUsu++;
            //Resibe ip
            ipnodo = in.readLine();
            System.out.println(ipnodo);
            ipnodohash = String.valueOf(ipnodo.hashCode());
          
           //Puero de nodo
             puerto = in.readLine();       
            addTablaFinger();
            
            }
            else
            //If para la busqueda de recursos
            if (data.contains("BR")){
                //lugar al que va a buscar
                String recurso = in.readLine();
                System.out.println("Recurso:" + recurso);
                ordenaNodos();
                Tabla tabmn = mayorDeMenor(recurso);
                if (tabmn != null){
                    //Devuelve ip y puerto del siguiente nodo
                    out.println("NO");
                    out.println(tabmn.getIhash());
                    out.println(tabmn.getPuerto());
                   
                }
                else if (data.contains("checkData")){ //
                    String Data = in.readLine(); // Este es la referencia que debe devolver os datos del usuario par aque pueda iniciar conexion y descargar archivo
                    getDataNodo(Data);
                    out.print(infodata.getIp());
                    out.println(infodata.getPuerto());
                }
                else // If para eslogear el usuario del anillo
                    if (data.contains("EXIT")){
                        String logout = in.readLine();
                        deleteNodo(logout);
                        System.out.println("Usuario deslogeado: "+logout);
                        out.println("MUERTO");
                    }
            }
            else if (data.contains("AP")){
               String ip = in.readLine();
               //Anterior
                out.println();
                //Porterior
                out.println();
            }
            
            
            
            in.close();
            out.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ManejadorAnillo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Metodo que añade a la ip a la tabla finger del nodo fantasma
     */
    public void addTablaFinger(){
        
         String query = "insert into fingeranillo (ip,hash,puerto) values ('"+ ipnodo+"',"+ positivoIP(ipnodohash)
                 +","+puerto+")";
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

        } catch (Exception e) {
        
        }
    }
    
    public void getDataNodo(String hash){
        
         String query = "select distinct ip, puerto from fingeranillo where hash="+hash;
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
                String ip = rs.getString("ip");
                int puerto = rs.getInt("puerto");
             
                infodata.setIp(ip);
                infodata.setPuerto(puerto);
                

        } catch (Exception e) {
        
        }
    }
    
    public void anteriornodo(String ipb){
        String query = "select distinct ip from fingeranillo where ip='"+ipb+"'";
        
        
    }
    
     public void deleteNodo(String ipd){
        String query = "delete from fingeranillo where ip='"+ipd+"'";
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            Tabla tab = null;
            ArrayTab = null;         
            int puesto = 0;
            
            while (rs.next()) {
                String ip = rs.getString("ip");
                String ihash = rs.getString("hash");
                int puerto = rs.getInt("puerto");
                puesto = puesto++;

                tab = new Tabla(puesto,ip,ihash,puerto);
                
                //Lllena el array list con la informacion ordenada
                ArrayTab.add(tab);
            }

        } catch (Exception e) {
        
        }
    
    }
    
    public void ordenaNodos(){
        String query = "select distinct hash, ip,puerto from fingeranillo order by hash";
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            Tabla tab = null;
            ArrayTab = null;         
            int puesto = 0;
            
            while (rs.next()) {
                String ip = rs.getString("ip");
                String ihash = rs.getString("hash");
                int puerto = rs.getInt("puerto");
                puesto = puesto++;

                tab = new Tabla(puesto,ip,ihash,puerto);
                
                //Lllena el array list con la informacion ordenada
                ArrayTab.add(tab);
            }

        } catch (Exception e) {
        
        }
    
    }
    
    /**
     * Devuelve el objeto que tiene el menor de los mayores
     * @param hash
     * @return 
     */
    public Tabla mayorDeMenor(String hash){
        int hashInt = Integer.parseInt(hash);
        System.out.println("mayor menor de fantasma: "+hashInt);
        for(int i=0;i < ArrayTab.size() ;i++){
            // Este if es para cundo llegue al final de la tabla y el valor sea menor a la llave que se tiene
            if (i == ArrayTab.size() && Integer.parseInt(ArrayTab.get(i).getIhash()) < hashInt) 
                return ArrayTab.get(i);
            else if ((Integer.parseInt(ArrayTab.get(i).getIhash()) < hashInt) && 
                    (hashInt < Integer.parseInt(ArrayTab.get(i+1).getIhash())) )
                return ArrayTab.get(i);
            
        }
        return null;
    }
    
    /**
     * GETTERS AND SETTERS
     */
    
     public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getIpnodo() {
        return ipnodo;
    }

    public void setIpnodo(String ipnodo) {
        this.ipnodo = ipnodo;
    }

   
    
}
