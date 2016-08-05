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
    









private boolean pode_comer(ArrayList<ArrayList<Dama>> tabu, int i, int j, int i2, int j2) {
        
        int dif_i = (i2 - i) / 2;
        int dif_j = (j2 - j) / 2;
        int x, y;    
        
        if(!limite(i2, j2)){
        	return false;
        }
        
        // Posição não vazia
        if(!pos_vazia(tabu,i2,j2)){
            return false;
        }
        
        // Mesma cor
        if(tabu.get(i).get(j) == Dama.BRANCA || tabu.get(i).get(j) == Dama.PRETA){
            if(tabu.get(i+dif_i).get(j+dif_j) == tabu.get(i).get(j)){
                return false;
            }
        }
        //Posição em que quer comer vazia
        if(tabu.get(i).get(j) == Dama.PRETA || tabu.get(i).get(j) == Dama.BRANCA){
            if(pos_vazia(tabu,i+dif_i, j+dif_j)){
                return false;
            }
        }
        // Limite do tamanho da comida (Peça normal)
        if(tabu.get(i).get(j) == Dama.PRETA || tabu.get(i).get(j) == Dama.BRANCA){    
            if(tabu.get(i+dif_i).get(j+dif_j) != tabu.get(i).get(j)){
                if(Math.abs(i2 -i ) != 2 || Math.abs(j - j2) != 2){
                    return false;
                }
            }
        }
        
       
       if(tabu.get(i).get(j) == Dama.DPRETA || tabu.get(i).get(j) == Dama.DBRANCA){    
        //Direita pra cima
            x = i - 1; 
            y = j + 1;
            while(x>i2 && y<j2 && x>0 && y<8){
                if(!pos_vazia(tabu,x,y) && pos_vazia(tabu,x-1,y+1)){
                    return true;
                }
                x--;
                y++;    
            }

           // Direita pra baixo 
            x = i + 1;
            y = j + 1;
                while(x<i2 && y<j2 && x<8 && y<8){
                if((!pos_vazia(tabu,x,y)) && (pos_vazia(tabu,x+1,y+1))){
                    return true;
                }
                x++;
                y++;    
            }
         
        //Esquerda pra cima  
            x = i - 1; 
            y = j - 1;

            while(x>i2 && y>j2 && x>0 && y>0){
                if((!pos_vazia(tabu,x,y)) && (pos_vazia(tabu,x-1,y-1))){
                    return true;
                }
                x--;
                y--;
            }        
            //Esquerda pra baixo
            x = i + 1; 
            y = j - 1;

            while(x<i2 && y>j2 && x<8 && y>0){
                 if((!pos_vazia(tabu,x,y)) && (pos_vazia(tabu,x+1, y-1))){
                    return true;
                 }
                x++;
                y--;    
            }
        }
             
        if(tabu.get(i).get(j) == Dama.BRANCA || tabu.get(i).get(j) == Dama.PRETA){
            if(tabu.get(i+dif_i).get(j+dif_j) != tabu.get(i).get(j))
                return true;
        }
    return false;
}

private boolean pode_movimentar(ArrayList<ArrayList<Dama>> tabu, int i, int j, int i2, int j2) {
        
        int x = 0, y = 0;
        
        if(!limite(i2, j2)){
            return false;
        }
        
        //Posição de destino cheia
        if(!pos_vazia(tabu, i2, j2)){
            return false;
        }
        //Posição de origem vazia
        if(pos_vazia(tabu, i, j)){
            return false;
        }
        ///Jogada fora das diagonais
        if(tabu.get(i).get(j)!= Dama.VAZIA){
            if(Math.abs(i - i2) != Math.abs(j - j2)){
                return false;
            }
        }
        // limite do tamanho do passo
        if (Math.abs(i - i2) != 1 || Math.abs(j - j2) != 1) {
            if (tabu.get(i).get(j) == Dama.BRANCA || tabu.get(i).get(j) == Dama.PRETA) {
                return false;
            }
        }
        
        //Direção do passo peça branca
        if(tabu.get(i).get(j) == Dama.BRANCA){
            if(i2 > i){
                return false;
            }
        }
        
        //Direção do passo peça preta
        if(tabu.get(i).get(j) == Dama.PRETA){
            if(i2 < i){
                return false;
            }
        }
        // Diagonal direita para baixo
        if(tabu.get(i).get(j) == Dama.DBRANCA || tabu.get(i).get(j) == Dama.DPRETA){
            x = i + 1; 
            y = j + 1;
            while(x<=i2 && y<=j2 && x<8 && y<8 ){
                if(!pos_vazia(tabu, x, y)){
                    return false;
                }
                else if(x == i2 && y == j2)
                        return true;
                x++;
                y++;
            }
        
        // Diagonal direita para cima
        
            x = i - 1;
            y = j + 1;
            
            while(x>=i2 && y<=j2 && x>=0 && y<8){
                if(!pos_vazia(tabu, x,y)){
                    return false;
                }
                else if(x == i2 && y == j2)
                        return true;    
                x--;
                y++;    
            }
        
        // Diagonal esquerda para cima
        
            x = i - 1; 
            y = j - 1;
            while(x>=i2 && y>=j2 && x>=0 && y>=0){
                if(!pos_vazia(tabu,x,y)){
                    return false;
                }
                else if(x == i2 && y == j2)
                    return true;
                x--;
                y--;
            }
        
            // Diagonal esquerda para baixo
        
            x = i + 1; 
            y = j - 1;
            
            while(x<=i2 && y>=j2 && x<8 && y>=0){
                if(!pos_vazia(tabu,x,y)){
                    return false;
                }
                else if(x == i2 && y == j2)
                    return true;
                x++;
                y--;    
            }
        }   
        return true;
    }
