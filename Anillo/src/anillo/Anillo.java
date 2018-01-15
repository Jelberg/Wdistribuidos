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
public class Anillo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServidorAnillo s = new ServidorAnillo(44444);
        s.correrServidor();
    }
    
}
