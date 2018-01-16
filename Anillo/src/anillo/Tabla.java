/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anillo;

import java.util.ArrayList;

/**
 *
 * @author Elberg
 */
public class Tabla {
    private int orden;
    private String ip;
    private String ihash;
    private int puerto;
    private ArrayList<Tabla> all;

    public Tabla(int orden, String ihash) {
        this.orden = orden;
        this.ihash = ihash;
    }

    public Tabla(int orden, String ip, String ihash) {
        this.orden = orden;
        this.ip = ip;
        this.ihash = ihash;
    }

    public Tabla(int orden, String ip, String ihash, int puerto) {
        this.orden = orden;
        this.ip = ip;
        this.ihash = ihash;
        this.puerto = puerto;
    }

    public Tabla(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }
    
    

    
    
    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    
    

    
    public int getOrden() {
        return orden;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    
    
    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getIhash() {
        return ihash;
    }

    public void setIhash(String ihash) {
        this.ihash = ihash;
    }

    public ArrayList<Tabla> getAll() {
        return all;
    }

    public void setAll(ArrayList<Tabla> all) {
        this.all = all;
    }
    
    
    
}
