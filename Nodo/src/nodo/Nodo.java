/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

import java.io.IOException;

/**
 *
 * @author Elberg
 */
public class Nodo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ServidorNodo sd = new ServidorNodo(1,"localhost",Global.ANILLO_PUERTO,Global.PUERTO);
        sd.correrServidor(sd);
        /*ServidorDescarga sd = new ServidorDescarga();
        sd.start();
    */}
    
}
