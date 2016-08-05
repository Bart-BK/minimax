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
public class Tabuleiro {
    final int tamanho = 8;
//    ArrayList<ArrayList<PECA>> tabuleiro;
    
    public enum PECA {PRETA, BRANCA, VAZIA};
    
    public ArrayList<ArrayList<PECA>> inicializaTabuleiro(){
        ArrayList<ArrayList<PECA>> tabuleiro = new ArrayList<>();
        
        try{
            for(int i = 0; i < tamanho; i++){
                ArrayList<PECA> linha = new ArrayList();

                for(int j = 0; j < tamanho; j++){

                    if(i < 3){

                        if(i % 2 == 0){
                            if(j % 2 == 0){
                                linha.add(PECA.VAZIA);
                            }else{
                                linha.add(PECA.PRETA);
                            }
                        }else{
                            if(j % 2 == 0){
                                linha.add(PECA.PRETA);
                            }else{
                                linha.add(PECA.VAZIA);
                            }                        
                        }

                    }else if(i > 4){
                        if(i % 2 == 0){
                            if(j % 2 == 0){
                                linha.add(PECA.VAZIA);
                            }else{
                                linha.add(PECA.BRANCA);
                            }
                        }else{
                            if(j % 2 == 0){
                                linha.add(PECA.BRANCA);
                            }else{
                                linha.add(PECA.VAZIA);
                            }                        
                        }
                    }else{
                        linha.add(PECA.VAZIA);
                    }
                }
                tabuleiro.add(linha);
            }    
        
        }catch(Exception ex){
            System.out.println("inicializaTabuleiro: " +ex.getMessage());
        }
//        this.tabuleiro = tabuleiro;
        
        return tabuleiro;
    }

    public void mostrarTabuleiro(ArrayList<ArrayList<PECA>> tabuleiro){
        try{
            System.out.print("   ");
            for (char i = '0'; i <= '7'; i++) {
                System.out.print(i + "   ");
            }
            System.out.println("");
           for(int i = 0; i < tamanho; i++){
               System.out.print(i + ""); //Numera as linhas
                System.out.print("|");
                for(int j = 0; j < tamanho; j++){
                    if(null != tabuleiro.get(i).get(j))switch (tabuleiro.get(i).get(j)) {
                       case VAZIA:
                           System.out.print("   |");
                           break;
                       case PRETA:
                           System.out.print(" x |");
                           break;
                       case BRANCA:
                           System.out.print(" o |");
                           break;
                       default:
                           break;
                   }
                }
                System.out.println();
            }
            System.out.println();
        }catch(Exception ex){
            System.out.println("mostrarTabuleiro: " + ex.getMessage());
        }
    }

    public ArrayList<ArrayList<PECA>> duplicarTabuleiro(ArrayList<ArrayList<PECA>> tabuleiro) {
        ArrayList<ArrayList<PECA>> novoTabuleiro = new ArrayList();
        
        try{
            for(int i = 0; i < tabuleiro.size(); i++){
                ArrayList<PECA> linha = new ArrayList();
                
                for(int j = 0; j < tabuleiro.get(i).size(); j++){
                    linha.add(tabuleiro.get(i).get(j));
                }
                
                novoTabuleiro.add(linha);
            }
        }catch(Exception e){
            System.out.println("duplicarTabuleiro: " + e.getMessage());
        }
        return novoTabuleiro;
    }
    
    public int custo(ArrayList<ArrayList<PECA>> tab2, ArrayList<ArrayList<PECA>> tab1) {
        
        if(tab2 == null){
            return 0;
        }
        
        if(tab1 == null)
            return 0;
        
        return pecas(tab2) - pecas(tab1);
    }
    
    private int pecas(ArrayList<ArrayList<PECA>> tabuleiro) {
        int soma = 0;
        
        try{
            for(int i = 0; i < tabuleiro.size(); i++){
                for(int j = 0; j < tabuleiro.get(i).size(); j++){
                    if(tabuleiro.get(i).get(j) != PECA.VAZIA){
                        soma++;
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("pecas: " +ex.getMessage());
        }
        return soma;
    }
    
    
    
}
