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
public class Finger {
    int pos;
    int haship;
    int puertofin;

    public Finger() {
    }
    
    

    public Finger(int pos, int haship) {
        this.pos = pos;
        this.haship = haship;
    }

    public Finger(int pos, int haship, int puertofin) {
        this.pos = pos;
        this.haship = haship;
        this.puertofin = puertofin;
    }
    
    

    public int getPuertofin() {
        return puertofin;
    }

    public void setPuertofin(int puertofin) {
        this.puertofin = puertofin;
    }

    
    
    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getHaship() {
        return haship;
    }

    public void setHaship(int haship) {
        this.haship = haship;
    }
    
    
    
}
