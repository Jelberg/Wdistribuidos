/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

/**
 *
 * @author Elberg
 */
public class Global {

    public Global() {
    }
    
    /**
     * Global para conexion sql
     */
    public static String BD_USER = "postgres";
    public static String BD_PASSWORD = "123";
    public static String BD_URL = "jdbc:postgresql://localhost/postgres";
    public static String BD_CLASS_FOR_NAME = "org.postgresql.Driver";
    
    /**
     * Puertos
     */
    public static int ANILLO_PUERTO = 44444;
    public static int PUERTO = 47524;
    
    
    /**
     * 
     * METODOS GLOBALES
     * 
     */
    
    public static String positivoIP (int ip){
    int i = ip;
       if (i < 0) i = i*(-1);
     return String.valueOf(i);
    }
}
