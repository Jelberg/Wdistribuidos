/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

import java.util.ArrayList;

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
     * IP
     */
    
    public static String IP = "192.168.250.13"; // Ip para conectar con el servidor
    
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
    
    /**
     * Funcion que devuelve los ultimos 3 caracteres de un String
     */

     public static String subcadena(String str){
         return str.substring(str.length()-2, str.length());
     }
     
     
     
     
     
     
     
     public static ArrayList<Recurso> recurso (){
         ArrayList<Recurso> ret = new   ArrayList<Recurso>();
         //Modificarlo con los nombres y las direcciones de los recursos
            Recurso A = new Recurso();
            A.setNombre(BD_URL);
            A.setDireccion(IP);
            Recurso B = new Recurso();
            A.setNombre(BD_URL);
            A.setDireccion(IP);
            Recurso C = new Recurso();
            A.setNombre(BD_URL);
            A.setDireccion(IP);
            ret.add(A);
            ret.add(B);
            ret.add(C);
            return ret;
     }
     
    
   
}
