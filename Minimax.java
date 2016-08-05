/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dama.minimax;

/**
 *
 * @author Prabhát
 */
public class Minimax {
    Tabuleiro tab = new Tabuleiro();
    
    
}


    private boolean limite(int i, int j){
    	// Limite de Tamanho
        if(i >= tam || i < 0){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
               //System.out.println("Essa linha não existe");
            return false;
        }
        // Limite de Tamanho
        if(j >= tam || j < 0){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                //System.out.println("Essa coluna não existe");
            return false;
        }
    	return true;
    }
    
    private boolean pos_vazia(ArrayList<ArrayList<Dama>>tabu, int i, int j){
        if(tabu.get(i).get(j) == Dama.VAZIA){
            return true;
        }
        return false;
    }
    
