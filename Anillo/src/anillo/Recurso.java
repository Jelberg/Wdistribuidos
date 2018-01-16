/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anillo;

/**
 *
 * @author Elberg
 */
public class Recurso {
    private String nombre;
    private String direccion;

    public Recurso(String direccion) {
        this.direccion = direccion;
    }

    public Recurso(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Recurso() {
    }

    
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
