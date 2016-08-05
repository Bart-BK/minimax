/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dama.minimax;

import java.util.ArrayList;

/**
 *
 * @author Prabhát
 */
public class Main {
    
    static Tabuleiro tab = new Tabuleiro();
    public static ArrayList<ArrayList<Tabuleiro.PECA>> tabuleiro = tab.inicializaTabuleiro();
    static Minimax minimax = new Minimax();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            tab.mostrarTabuleiro(tabuleiro);
            
        } catch (Exception e) {
            System.out.println("main :"+ e.getMessage());
        }
    }
    
}
