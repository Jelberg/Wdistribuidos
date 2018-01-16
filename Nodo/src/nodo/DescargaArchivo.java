/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elberg
 */
public class DescargaArchivo extends Thread{
    private Connection conn = Sql.getConInstance();
    Recurso tab = new Recurso(); 
    String dato ;
    private Socket sd;
    private String mensaje;
    private Socket socket;
    private FileOutputStream fos;
    private ServidorManejadorNodo sdp;
     ServerSocket server;
    Socket connection;
    DataOutputStream output;
    BufferedInputStream bis;
    BufferedOutputStream bos;

    public DescargaArchivo() {
    }


    public DescargaArchivo(Socket socket, ServidorManejadorNodo sd)
    {
        this.socket = socket;
        this.sdp = sd;
    }
    
    /**
     * Metodo para obtener la direccion del recurso
     * @param nombre 
     */
    public void buscarArchivo(String nombre){
        String query = "select direccion from direccionrecursos where nombre='"+nombre+"'";
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            Recurso tab = null;        
            
            while (rs.next()) {
                String name = rs.getString("nombre");
                String dir = rs.getString("direccion");


                tab = new Recurso(name,dir);
                
            }

        } catch (Exception e) {
        
        }
    
    }

 
    @Override
    public void run() {

                byte[] receivedData;
                 int in;
                 String file;

                try{
                 //Servidor Socket en el puerto 5000
                 server = new ServerSocket( 5000 );
                 
                 while ( true ) {
                     
                 //Aceptar conexiones
                 connection = server.accept();
                 //Buffer de 1024 bytes
                 
                 receivedData = new byte[1024];
                 bis = new BufferedInputStream(connection.getInputStream());
                 DataInputStream dis=new DataInputStream(connection.getInputStream());
                 
                 //Recibimos el nombre del fichero
                 file = dis.readUTF();
                 file = file.substring(file.indexOf('\\')+1,file.length());
                 
                 //Para guardar fichero recibido
                 bos = new BufferedOutputStream(new FileOutputStream(file));
                 
                 while ((in = bis.read(receivedData)) != -1){
                     
                 bos.write(receivedData,0,in);
                 }
                 
                 bos.close();
                 dis.close();
                 }
                 
                 }catch (Exception e ) {
                     
                 System.err.println(e);
                 }
                 }
    
    
}
