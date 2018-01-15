/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Elberg
 */
public class DescargaArchivo {
    private Connection conn = Sql.getConInstance();
    Recurso tab = new Recurso(); 
    
    
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
    
    
    
    
}
