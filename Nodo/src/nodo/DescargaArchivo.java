/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
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
    
    private Socket socket;
    private ServidorManejadorNodo sd;
    


    public DescargaArchivo(Socket socket, ServidorManejadorNodo sd)
    {
        this.socket = socket;
        this.sd = sd;
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
        
    }
    
    
    
}
