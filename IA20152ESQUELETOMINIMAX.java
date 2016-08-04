package ia.pkg2015.pkg2.esqueleto.minimax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
public class IA20152ESQUELETOMINIMAX {
    
    final int tam = 8;

    /* Dado uma matriz de representação de um tabuleiro, esta função retorna uma
    cópia exata desta matriz. Para tanto, este algoritmo percorre todas as 
    linhas e todas as colunas da matriz, copiando um elemento de cada vez. 
    */
    private ArrayList<ArrayList<Dama>> duplicar_tabu(ArrayList<ArrayList<Dama>> tabu) {
        ArrayList<ArrayList<Dama>> novo_tabu = new ArrayList();
        
        try{
            for(int i = 0; i < tabu.size(); i++){
                ArrayList<Dama> linha = new ArrayList();
                
                for(int j = 0; j < tabu.get(i).size(); j++){
                    linha.add(tabu.get(i).get(j));
                }
                
                novo_tabu.add(linha);
            }
        }catch(Exception e){
            System.out.println("duplicar_tabu: " + e.getMessage());
        }
        return novo_tabu;
    }

    /* O custo é a quantidade de peças do tabuleiro antigo menos a quantidade
    de peças do tabuleiro novo. Esta função deve a cada jogada ser maximizada
    ou minimizada, a depender de quem estiver jogando, ou seja, a máquina
    ou você. 
    */
    private int custo(ArrayList<ArrayList<Dama>> tab2, ArrayList<ArrayList<Dama>> tab1) {
        
        if(tab2 == null){
            return 0;
        }
        
        if(tab1 == null)
            return 0;
        
        return pecas(tab2) - pecas(tab1);
    }

    /* Para calcular a quantidade de peças de um tabuleiro percorre-se
    todas as linhas e todas as colunas do tabuleiro, e se verifica se 
    cada posição possui uma peça. Caso afirmativo, a soma é incrementada
    em uma unidade. 
    */
    private int pecas(ArrayList<ArrayList<Dama>> tab) {
        int soma = 0;
        
        try{
            for(int i = 0; i < tab.size(); i++){
                for(int j = 0; j < tab.get(i).size(); j++){
                    if(tab.get(i).get(j) != Dama.VAZIA){
                        soma++;
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("pecas: " +ex.getMessage());
        }
        return soma;
    }
    
    /* Atualmente existem dois tipos de peças: preta e branca. Porém, 
    o aluno deverá aperfeiçoar esta parte do código para que existam mais
    dois casos: dama preta e dama branca. 
    */
    public enum Dama {PRETA, BRANCA, DPRETA, DBRANCA, VAZIA};


    /* Este método faz com que a máquina coma recursivamente, ou seja, que
    a máquina coma várias peças de uma vez só. Haja vista que uma peça pode 
    comer em quatro direções diferentes, então 4 possibilidades precisam 
    ser verificadas. Na parte inicial do código as variáveis dif_i e dif_j
    armazenam o vetor direção do movimento, e mas primeiras linhas do try 
    limpam a posição antiga e a posição da peça que foi comida, e ajusta
    a nova posição da peça que está comendo. 
    */
    private ArrayList<ArrayList<Dama>> come_recursivamente(ArrayList<ArrayList<Dama>> tabu, int i, int j, int i2, int j2) {
        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
        
        int dif_i = (i2 - i) / 2, dif_j = (j2 - j)/ 2;
        Dama d = tabu.get(i).get(j);
        int aux_x = 0;
        int aux_y = 0;
 
        try{
            novo_tabu.get(i).set(j, Dama.VAZIA);
            novo_tabu.get(i + dif_i).set(j + dif_j, Dama.VAZIA);
            novo_tabu.get(i2).set(j2, d);
            //Verifica os possiveis movimentos para comer uma peça
            
            //Direita e baixo
            if(tabu.get(i).get(j) == Dama.DPRETA || tabu.get(i).get(j) == Dama.DBRANCA ){
                if(i2>i && j2>j ){
                    aux_x = i;
                    aux_y = j;
                    while(aux_x < 8 && aux_y < 8){
                        if (pode_comer(tabu, i2, j2, aux_x, aux_y)){
                            novo_tabu = come_recursivamente(novo_tabu, i2, j2, aux_x, aux_y);
                        }
                        aux_x++;
                        aux_y++;
                    }
                }
                //Diagonal esquerda e cima
                if(i2<i && j2<j){
                    aux_x = i;
                    aux_y = j;
                    while(aux_x >= 0 && aux_y >= 0){
                        if (pode_comer(tabu, i2, j2, aux_x, aux_y)){
                                novo_tabu = come_recursivamente(novo_tabu, i2, j2, aux_x, aux_y);
                        }
                        aux_x--;
                        aux_y--;
                    }
                }
                //Diagonal esqueda para baixo
                if(i2>i && j2<j){
                    aux_x = i;
                    aux_y = j;
                    
                    while(aux_x < 8 && aux_y >= 0){
                        if (pode_comer(tabu, i2, j2, aux_x, aux_y)){
                                novo_tabu = come_recursivamente(novo_tabu, i2, j2, aux_x, aux_y);
                        }
                        aux_x++;
                        aux_y--;
                    }
                }
                //Diagonal direita para cima
                if(i2<i && j2>j){
                    aux_x = i;
                    aux_y = j;

                    while(aux_x >= 0 && aux_y < 8){
                        if (pode_comer(tabu, i2, j2, aux_x, aux_y)){
                            novo_tabu = come_recursivamente(novo_tabu, i2, j2, aux_x, aux_y);
                        }
                        aux_x--;
                        aux_y++;
                    }
                }
            }else if(pode_comer(novo_tabu, i2, j2, i2+2, j2+2)){
                novo_tabu = come_recursivamente(novo_tabu, i2, j2, i2+2, j2+2);
            //Esquerda e Baixo  
            }else if(pode_comer(novo_tabu, i2, j2, i2-2, j2-2)){
                novo_tabu = come_recursivamente(novo_tabu, i2, j2, i2-2, j2-2);
            //Direita e Baixo
            }else if(pode_comer(novo_tabu, i2, j2, i2-2, j2+2)){
                novo_tabu = come_recursivamente(novo_tabu, i2, j2, i2-2, j2+2);
            //Esquerda e Cima
            }else if(pode_comer(novo_tabu, i2, j2, i2+2, j2-2)){
                novo_tabu = come_recursivamente(novo_tabu, i2, j2, i2+2, j2-2);
            }
        }catch(Exception ex){
            System.out.println("come_recursivamente: " + ex.getMessage());
        }
        return novo_tabu;
    }

    /* Verifica-se se uma peça pode comer verificando-se primeiramente 
    por exclusão os casos em que ela não pode comer. Isso tipicamente aconte
    ce quando a peça está se movimentando para fora do tabuleiro, quando a 
    nova posição não está vazia, e quando a peça que está sendo comida na verdade
    não existe (posição vazia). Caso contrário, a peça pode comer se a cor da 
    peça que está comendo for diferente da cor da peça que está sendo comida. 
    */
    private boolean pode_comer(ArrayList<ArrayList<Dama>> tabu, int i, int j, int i2, int j2) {
        
        int dif_i = (i2 - i) / 2;
        int dif_j = (j2 - j) / 2;
        int x, y;    
        
        // Limite de Tamanho
        if(i2 >= tam || i2 < 0){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                //System.out.println("Linha não existe");
            return false;
        }
        // Limite de Tamanho
        if(j2 >= tam || j2 < 0){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                //System.out.println("Coluna não existe");
            return false;
        }
        // Posição não vazia
        if(tabu.get(i2).get(j2) != Dama.VAZIA){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                //System.out.println("Posição de destino não vazia");
            return false;
        }
        
        // Mesma cor
        if(tabu.get(i).get(j) == Dama.BRANCA || tabu.get(i).get(j) == Dama.PRETA){
            if(tabu.get(i+dif_i).get(j+dif_j) == tabu.get(i).get(j)){
                //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                    //System.out.println("Não pode comer suas próprias peças");
                return false;
            }
        }
        //Posição em que quer comer vazia
        if(tabu.get(i).get(j) == Dama.PRETA || tabu.get(i).get(j) == Dama.BRANCA){
            if(tabu.get(i+dif_i).get(j+dif_j) == Dama.VAZIA){
                //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                    //System.out.println("Jogada não permitida");
                return false;
            }
        }
        // Limite do tamanho da comida (Peça normal)
        if(tabu.get(i).get(j) == Dama.PRETA || tabu.get(i).get(j) == Dama.BRANCA){    
            if(tabu.get(i+dif_i).get(j+dif_j) != tabu.get(i).get(j)){
                if(Math.abs(i2 -i ) != 2 || Math.abs(j - j2) != 2){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Jogada não permitida para peças normais");
                    return false;
                }
            }
        }
        
       
       if(tabu.get(i).get(j) == Dama.DPRETA || tabu.get(i).get(j) == Dama.DBRANCA){
            
        //Direita pra cima
        
            x = i - 1; 
            y = j + 1;

            while(x>i2 && y<j2 && x>0 && y<8){
                if(tabu.get(x).get(y) != Dama.VAZIA && tabu.get(x-1).get(y+1) == Dama.VAZIA){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Só pode comer 1 peça por vez");
                    return true;
                }
                x--;
                y++;    
            }

           // Direita pra baixo 
            x = i + 1;
            y = j + 1;
                while(x<i2 && y<j2 && x<8 && y<8){
                if((tabu.get(x).get(y) != Dama.VAZIA) && (tabu.get(x-1).get(y+1) == Dama.VAZIA)){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Só pode comer 1 peça por vez");
                    return true;
                }
                x++;
                y++;    
            }
        
        
        //Esquerda pra cima
        
        
            x = i - 1; 
            y = j - 1;

            while(x>i2 && y>j2 && x>0 && y>0){
                if((tabu.get(x).get(y) != Dama.VAZIA) && (tabu.get(x-1).get(y-1) == Dama.VAZIA)){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Só pode comer 1 peça por vez");
                    return true;
                }
                x--;
                y--;
            }
        
        
            //Esquerda pra baixo

            x = i + 1; 
            y = j - 1;

            while(x<i2 && y>j2 && x<8 && y>0){
                 if((tabu.get(x).get(y) != Dama.VAZIA) && (tabu.get(x+1).get(y-1) == Dama.VAZIA )){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Só pode comer 1 peça por vez");
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
    
    
    
    /* Este método recebe duas posições e um tabuleiro, e diz se é possível
    movimentar uma peça da posição antiga para a posição nova. Isso acontece
    quando a posição antiga não está vazia e a posição nova está vazia, e quando
    a peça não está tentando se movimentar para fora do tabuleiro. 
    */
    private boolean pode_movimentar(ArrayList<ArrayList<Dama>> tabu, int i, int j, int i2, int j2) {
        
        int x = 0, y = 0;

        // Limite de Tamanho
        if(i2 >= tam || i2 < 0){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
               //System.out.println("Essa linha não existe");
            return false;
        }
        // Limite de Tamanho
        if(j2 >= tam || j2 < 0){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                //System.out.println("Essa coluna não existe");
            return false;
        }
        //Posição de destino cheia
        if(tabu.get(i2).get(j2) != Dama.VAZIA){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                //System.out.println("Existe uma peça na posição de destino");
            return false;
        }
        //Posição de origem vazia
        if(tabu.get(i).get(j) == Dama.VAZIA){
            //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                //System.out.println("Posição de origem vazia");
            return false;
        }
        ///Jogada fora das diagonais
        if(tabu.get(i).get(j)!= Dama.VAZIA){
            if(Math.abs(i - i2) != Math.abs(j - j2)){
                //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                    //System.out.println("Jogada fora da diagonal");
                return false;
            }
        }
        // limite do tamanho do passo
        if (Math.abs(i - i2) != 1 || Math.abs(j - j2) != 1) {
            if (tabu.get(i).get(j) == Dama.BRANCA || tabu.get(i).get(j) == Dama.PRETA) {
                //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                    //System.out.println("Peça normal só pode pular uma casa por vez");
                return false;
            }
        }
        
        //Direção do passo peça branca
        if(tabu.get(i).get(j) == Dama.BRANCA){
            if(i2 > i){
                //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                    //System.out.println("Não pode andar pra trás");
                return false;
            }
        }
        
        //Direção do passo peça preta
        if(tabu.get(i).get(j) == Dama.PRETA){
            if(i2 < i){
                //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                    //System.out.println("Não pode andar pra trás");
                return false;
            }
        }
        // Diagonal direita para baixo
        if(tabu.get(i).get(j) == Dama.DBRANCA || tabu.get(i).get(j) == Dama.DPRETA){
            x = i + 1; 
            y = j + 1;
            while(x<=i2 && y<=j2 && x<8 && y<8 ){
                if(tabu.get(x).get(y) != Dama.VAZIA){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Existem peças no caminho");
                    return false;
                }else if(x == i2 && y == j2)
                        return true;
                x++;
                y++;
            }
        
        // Diagonal direita para cima
        
            x = i - 1;
            y = j + 1;
            
            while(x>=i2 && y<=j2 && x>=0 && y<8){
                if(tabu.get(x).get(y) != Dama.VAZIA){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Existem peças no caminho");
                    return false;
                }else if(x == i2 && y == j2)
                        return true;    
                x--;
                y++;    
            }
        
        // Diagonal esquerda para cima
        
            x = i - 1; 
            y = j - 1;
            while(x>=i2 && y>=j2 && x>=0 && y>=0){
                if(tabu.get(x).get(y) != Dama.VAZIA){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Existem peças no caminho");
                    return false;
                }else if(x == i2 && y == j2)
                    return true;
                x--;
                y--;
            }
        
            // Diagonal esquerda para baixo
        
            x = i + 1; 
            y = j - 1;
            
            while(x<=i2 && y>=j2 && x<8 && y>=0){
                 if(tabu.get(x).get(y) != Dama.VAZIA ){
                    //if(tabu.get(i).get(j) == Dama.DBRANCA|| tabu.get(i).get(j) == Dama.BRANCA)
                        //System.out.println("Existem peças no caminho");
                    return false;
                }else if(x == i2 && y == j2)
                    return true;
                x++;
                y--;    
            }
        }   
        return true;
    }
    
    /* Este método mostra o tabuleiro na tela. Para isso, ele percorre todas
    as linhas e todas as colunas da matriz que representa o tabuleiro, e mostra
    um x (Minusculo) quando a pedra for preta, e um o (Minusculo) quando a peça for branca
    um X (Maisculo) quando for uma dama preta, e um O (Maiusculo) quando for uma dama branca. 
    */
    public void mostrar_tabuleiro(ArrayList<ArrayList<Dama>> tabu){
        try{
            System.out.print("   ");
            for (char i = '0'; i <= '7'; i++) {
                System.out.print(i + "   ");
            }
            System.out.println("");
           for(int i = 0; i < tam; i++){
               System.out.print(i + ""); //Numera as linhas
                System.out.print("|");
                for(int j = 0; j < tam; j++){
                    if(tabu.get(i).get(j) == Dama.VAZIA){
                        System.out.print("   |");
                    }else if(tabu.get(i).get(j) == Dama.PRETA){
                        System.out.print(" x |");
                    }else if(tabu.get(i).get(j) == Dama.BRANCA){
                        System.out.print(" o |");
                    }else if(tabu.get(i).get(j) == Dama.DBRANCA){
                        System.out.print(" O |");
                    }else if(tabu.get(i).get(j) == Dama.DPRETA){
                        System.out.print(" X |");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }catch(Exception ex){
            System.out.println("mostrar_tabuleiro: " + ex.getMessage());
        }
    }
    
    /* Esta peça cria um tabueiro. Para tanto, é necessário alternar as posições
    entre vazia ou não vazia, considerando-se também a cor da peça. Para isso,
    percorre-se cada linha e cada coluna da matriz que representa o tabuleiro, 
    e verifica-se se o processamento está percorrendo a parte superior 
    ou inferior do tabuleiro (isso é suficiente para se verificar a cor do 
    jogador). 
    */
    
        public ArrayList<ArrayList<Dama>> init_tabuleiro(){
        
        ArrayList<ArrayList<Dama>> tabu = new ArrayList();
        
        try{
            for(int i = 0; i < tam; i++){
                ArrayList<Dama> linha = new ArrayList();

                for(int j = 0; j < tam; j++){

                    if(i < 3){

                        if(i % 2 == 0){
                            if(j % 2 == 0){
                                linha.add(Dama.VAZIA);
                            }else{
                                linha.add(Dama.PRETA);
                            }
                        }else{
                            if(j % 2 == 0){
                                linha.add(Dama.PRETA);
                            }else{
                                linha.add(Dama.VAZIA);
                            }                        
                        }

                    }else if(i > 4){
                        if(i % 2 == 0){
                            if(j % 2 == 0){
                                linha.add(Dama.VAZIA);
                            }else{
                                linha.add(Dama.BRANCA);
                            }
                        }else{
                            if(j % 2 == 0){
                                linha.add(Dama.BRANCA);
                            }else{
                                linha.add(Dama.VAZIA);
                            }                        
                        }
                    }else{
                        linha.add(Dama.VAZIA);
                    }
                }
                tabu.add(linha);
            }    
        
        }catch(Exception ex){
            System.out.println("init_tabuleiro: " +ex.getMessage());
        }
        
        return tabu;
    }
    
    /* Este método retorna um conjunto de novos tabuleiros que podem surgir 
    a partir de um tabuleiro analizando-se um conjunto de jogadas possíveis. 
    Porém, as jogadas devem obrigatoriamente comer pelo menos uma peça. Como
    é possível comer em quatro direções, então quatro possibilidades são 
    verificadas. 
    */
       public ArrayList<ArrayList<ArrayList<Dama>>> retalhacoes_possiveis(ArrayList<ArrayList<Dama>> tabu, Dama cor_da_vez){
        ArrayList<ArrayList<ArrayList<Dama>>> lista_tabuleiros = new ArrayList();
        int aux_x = 0;  
        int aux_y = 0;
       try{
                    
            for(int i = 0; i < tam; i++){
                for(int j = 0; j < tam; j++){
                    if(tabu.get(i).get(j) == cor_da_vez || tabu.get(i).get(j) == Dama.DPRETA || tabu.get(i).get(j) == Dama.DBRANCA){
                        if(tabu.get(i).get(j) == Dama.DPRETA || tabu.get(i).get(j) == Dama.DBRANCA){
                            aux_x = i;
                            aux_y = j;
                            while(aux_x < 8 && aux_y < 8){
                                if(pode_comer(tabu, i, j, aux_x, aux_y)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, aux_x, aux_y);
                                    lista_tabuleiros.add(novo_tabu);
                                }
                                aux_x++;
                                aux_y++;
                            }
                            aux_x = i;
                            aux_y = j;
                            while(aux_x >= 0 && aux_y >= 0){
                                if(pode_comer(tabu, i, j, aux_x, aux_y)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, aux_x, aux_y);
                                    lista_tabuleiros.add(novo_tabu);
                                }
                                aux_x--;
                                aux_y--;
                            }
                            aux_x = i;
                            aux_y = j;
                            while(aux_x >= 0 && aux_y < 8){
                                if(pode_comer(tabu, i, j, aux_x, aux_y)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, aux_x, aux_y);
                                    lista_tabuleiros.add(novo_tabu);
                                }
                                aux_x--;
                                aux_y++;
                            }
                            aux_x = i;
                            aux_y = j;
                            while(aux_x < 8 && aux_y >= 0){
                                if(pode_comer(tabu, i, j, aux_x, aux_y)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, aux_x, aux_y);
                                    lista_tabuleiros.add(novo_tabu);
                                }
                                aux_x++;
                                aux_y--;
                            }
                        }else{
                            if(pode_comer(tabu, i, j, i+2, j+2)){
                                if(i+2 == 7 && tabu.get(i).get(j) == Dama.PRETA)
                                    tabu.get(i+2).set(j+2, Dama.DPRETA);
                                ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, i+2, j+2);
                                lista_tabuleiros.add(novo_tabu);
                            }else if(pode_comer(tabu, i, j, i-2, j-2)){
                                if(i+2 == 7 && tabu.get(i).get(j) == Dama.PRETA)
                                    tabu.get(i+2).set(j+2, Dama.DPRETA);
                                ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, i-2, j-2);
                                lista_tabuleiros.add(novo_tabu);                            
                            }else if(pode_comer(tabu, i, j, i+2, j-2)){
                                if(i+2 == 7 && tabu.get(i).get(j) == Dama.PRETA)
                                    tabu.get(i+2).set(j+2, Dama.DPRETA);
                                ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, i+2, j-2);
                                lista_tabuleiros.add(novo_tabu);                            
                            }else if(pode_comer(tabu, i, j, i-2, j+2)){
                                if(i+2 == 7 && tabu.get(i).get(j) == Dama.PRETA)
                                    tabu.get(i+2).set(j+2, Dama.DPRETA);
                                ArrayList<ArrayList<Dama>> novo_tabu = come_recursivamente(tabu, i, j, i-2, j+2);
                                lista_tabuleiros.add(novo_tabu);                            
                            }
                        }
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("Retalhaçoes Possiveis" + ex.getMessage());
        }
       
       return lista_tabuleiros;
    }
    
    
    /* Este método retorna um conjunto de tabuleiros que podem surgir
    movimentando-se peças do tabuleiro, mas aqui os movimentos não 
    produzem pontos nem para o adversário nem para o jogador. Uma peça apenas
    pode se movimentar para a frente (quando ela não é rainha). Por isso, 
    apenas duas possibilidades são verificadas para cada caso. 
    */
    public ArrayList<ArrayList<ArrayList<Dama>>> movimentos_possiveis(ArrayList<ArrayList<Dama>> tabu, Dama cor_da_vez){
        ArrayList<ArrayList<ArrayList<Dama>>> lista_tabuleiros = new ArrayList();
        int aux_x = 0;
        int aux_y = 0;
        try{
                    
            for(int i = 0; i < tam; i++){
                for(int j = 0; j < tam; j++){
                    if(tabu.get(i).get(j) == cor_da_vez || tabu.get(i).get(j) == Dama.DPRETA || tabu.get(i).get(j) == Dama.DBRANCA){
                        if(cor_da_vez == Dama.PRETA){
                            if(tabu.get(i).get(j) == Dama.DPRETA){
                                //Direita pra baixo
                                aux_x = i;
                                aux_y = j;
                                while(aux_x < 8 && aux_y < 8){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DPRETA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x++;
                                    aux_y++;
                                }
                                //Esquerda para cima
                                aux_x = i;
                                aux_y = j;
                                while(aux_x >= 0 && aux_y >= 0){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DPRETA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x--;
                                    aux_y--;
                                }
                                //Direita pra Cima
                                aux_x = i;
                                aux_y = j;
                                while(aux_x >= 0 && aux_y < 8){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DPRETA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x--;
                                    aux_y++;
                                }
                                //Esquerda pra baixo
                                aux_x = i;
                                aux_y = j;
                                while(aux_x < 8 && aux_y >= 0){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DPRETA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x++;
                                    aux_y--;
                                }
                            }else if(tabu.get(i).get(j) == Dama.PRETA){
                                if(pode_movimentar(tabu, i, j, i+1, j+1)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                    if(i+1==7){
                                        novo_tabu.get(i+1).set(j+1, Dama.DPRETA);
                                    }else{
                                        novo_tabu.get(i+1).set(j+1, Dama.PRETA);
                                    }
                                    novo_tabu.get(i).set(j, Dama.VAZIA);
                                    lista_tabuleiros.add(novo_tabu);
                                }

                                if(pode_movimentar(tabu, i, j, i+1, j-1)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                     if(i+1==7){
                                        novo_tabu.get(i+1).set(j-1, Dama.DPRETA);
                                    }else{
                                        novo_tabu.get(i+1).set(j-1, Dama.PRETA);
                                     }
                                    novo_tabu.get(i).set(j, Dama.VAZIA);
                                    lista_tabuleiros.add(novo_tabu);
                                }                            
                            }

                        }else if(cor_da_vez == Dama.BRANCA){
                            if(tabu.get(i).get(j) == Dama.DBRANCA){
                                aux_x = i;
                                aux_y = j;
                                while(aux_x < 8 && aux_y < 8){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DBRANCA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x++;
                                    aux_y++;
                                }
                                aux_x = i;
                                aux_y = j;
                                while(aux_x >= 0 && aux_y >= 0){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DBRANCA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x--;
                                    aux_y--;
                                }
                                aux_x = i;
                                aux_y = j;
                                while(aux_x >= 0 && aux_y < 8){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DBRANCA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x--;
                                    aux_y++;
                                }
                                aux_x = i;
                                aux_y = j;
                                while(aux_x < 8 && aux_y >= 0){
                                    if(pode_movimentar(tabu, i, j, aux_x, aux_y)){
                                        ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                        novo_tabu.get(aux_x).set(aux_y, Dama.DBRANCA);
                                        novo_tabu.get(i).set(j, Dama.VAZIA);
                                        lista_tabuleiros.add(novo_tabu);
                                    }
                                    aux_x++;
                                    aux_y--;
                                }
                            }else if (tabu.get(i).get(j) == Dama.BRANCA){
                                if(pode_movimentar(tabu, i, j, i-1, j+1)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                    if(i+1==0){
                                        novo_tabu.get(i-1).set(j+1, Dama.DBRANCA);
                                    }else
                                        novo_tabu.get(i-1).set(j+1, Dama.BRANCA);
                                    novo_tabu.get(i).set(j, Dama.VAZIA);
                                    lista_tabuleiros.add(novo_tabu);
                                }

                                if(pode_movimentar(tabu, i, j, i-1, j-1)){
                                    ArrayList<ArrayList<Dama>> novo_tabu = duplicar_tabu(tabu);
                                     if(i+1==0){
                                        novo_tabu.get(i-1).set(j-1, Dama.DBRANCA);
                                    }else
                                        novo_tabu.get(i-1).set(j-1, Dama.BRANCA);
                                    
                                    novo_tabu.get(i).set(j, Dama.VAZIA);
                                    lista_tabuleiros.add(novo_tabu);
                                }                            
                            }
                        }
                    }
                }
            }
        }catch(Exception ex){
            System.out.println("movimentos_possiveis: " +ex.getMessage());
        }
        return lista_tabuleiros;
    }
    
        public ArrayList<ArrayList<Dama>> jogar_via_minimax(ArrayList<ArrayList<Dama>> tabu, Dama cor_da_vez, int profundidade){
        ArrayList<ArrayList<Dama>> novo_tabu = null;
        ArrayList<ArrayList<ArrayList<Dama>>> tabu_filhos = null;
            
        try{
            //Caso base da recursão que pega o melhor tabuleiro para peça preta e o 
            //cenário com maior custo para o jogador
            if(profundidade == 0 || this.pecas(tabu) == 1){
                if(cor_da_vez == Dama.PRETA){
                    tabu_filhos = this.movimentos_possiveis(tabu, cor_da_vez);
                    tabu_filhos.addAll(this.retalhacoes_possiveis(tabu, cor_da_vez));
                    Collections.shuffle(tabu_filhos);
                    novo_tabu = tabu_filhos.get(0);
                    
                    for(int i = 1; i < tabu_filhos.size(); i++){
                        if(this.custo(novo_tabu, tabu) > this.custo(tabu_filhos.get(i), tabu)){
                            novo_tabu = tabu_filhos.get(i);
                        }
                    }

                }else{
                    tabu_filhos = this.movimentos_possiveis(tabu, cor_da_vez);
                    tabu_filhos.addAll(this.retalhacoes_possiveis(tabu, cor_da_vez));
                    Collections.shuffle(tabu_filhos);
                    novo_tabu = tabu_filhos.get(0);
                    
                    for(int i = 1; i < tabu_filhos.size(); i++){
                        if(this.custo(novo_tabu, tabu) < this.custo(tabu_filhos.get(i), tabu)){
                            novo_tabu = tabu_filhos.get(i);
                        }
                    }
                }
            }
            //Melhor jogada para o computador
            else if(cor_da_vez == Dama.PRETA){
                
                tabu_filhos = this.movimentos_possiveis(tabu, cor_da_vez);
                tabu_filhos.addAll(this.retalhacoes_possiveis(tabu, cor_da_vez));
                Collections.shuffle(tabu_filhos);
                novo_tabu = tabu_filhos.get(0);
                
                for(int i = 1; i < tabu_filhos.size(); i++){
                    if(this.custo(this.jogar_via_minimax(novo_tabu, Dama.BRANCA, profundidade-1), tabu) >
                            this.custo(this.jogar_via_minimax(tabu_filhos.get(i), Dama.BRANCA, profundidade-1), tabu)){
                        novo_tabu = tabu_filhos.get(i);
                    }
                }                
            }
            //Pior jogada para o jogador adversário
            else if(cor_da_vez == Dama.BRANCA){
                
                tabu_filhos = this.movimentos_possiveis(tabu, cor_da_vez);
                tabu_filhos.addAll(this.retalhacoes_possiveis(tabu, cor_da_vez));
                Collections.shuffle(tabu_filhos);
                novo_tabu = tabu_filhos.get(0);
                
                for(int i = 1; i < tabu_filhos.size(); i++){
                    if(this.custo(this.jogar_via_minimax(novo_tabu, Dama.PRETA, profundidade-1), tabu) < this.custo(this.jogar_via_minimax(tabu_filhos.get(i), Dama.PRETA, profundidade-1), tabu)){
                        novo_tabu = tabu_filhos.get(i);
                    }
                }                                
            }
        }catch(Exception e){
            //System.out.println("jogar_viaMinMax: " +e.getMessage());
        }
        return novo_tabu;
    }
    
    public static void main(String[] args) {
        IA20152ESQUELETOMINIMAX damas = new IA20152ESQUELETOMINIMAX();
        Dama cor_da_vez = Dama.BRANCA;
        boolean comer_novamente = false;
        
        
        try{
            ArrayList<ArrayList<Dama>> tabu = damas.init_tabuleiro();
            Scanner ler =new Scanner(System.in);
            while(damas.pecas(tabu) != 1){
                if(cor_da_vez == Dama.BRANCA){
                    if(comer_novamente == true){
                        System.out.println("Deseja continua jogando? Se sim - 's', Se não 'n'");
                        String op = ler.nextLine();
                        
                        if(op == "N" || op == "n" ){
                            cor_da_vez = Dama.PRETA;
                            comer_novamente = false;
                            continue;
                        }
                        comer_novamente = false; 
                    }
                    damas.mostrar_tabuleiro(tabu);
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Posição x atual: ");
                    int x = reader.read() - 48;
                    reader.skip(1);

                    System.out.print("Posição y atual: ");
                    int y = reader.read() - 48;
                    reader.skip(1);
                                        
                    System.out.print("Nova posição x: ");
                    int x2 = reader.read()  - 48;
                    reader.skip(1);

                    System.out.print("Nova posição y: ");
                    int y2 = reader.read() - 48;
                    reader.skip(1);
                    
                    int aux_x = 0;
                    int aux_y = 0;

                    if(damas.pode_comer(tabu, x, y, x2, y2)){
                        int dif_x = (x2 - x) / 2, dif_y = (y2 - y)/ 2;
                        //tabu.get(x).set(y, Dama.VAZIA);
                        // Garantir que a dama não volte a ser peça normal
                        if(tabu.get(x).get(y) == Dama.DBRANCA){
                            //Apagar peça comida Direita baixo
                                aux_x = x;
                                aux_y = y;
                                tabu.get(x).set(y, Dama.VAZIA);
                                while(aux_x < x2 && aux_y < y2 && aux_x < 8 && aux_y < 8 ){
                                    if(tabu.get(aux_x).get(aux_y)!= Dama.DPRETA || tabu.get(aux_x).get(aux_y)!= Dama.PRETA){
                                        tabu.get(aux_x).set(aux_y, Dama.VAZIA);
                                    }
                                    aux_x++;
                                    aux_y++;
                                }
                                
                                tabu.get(aux_x).set(aux_y, Dama.VAZIA);
                                
                            //Apagar peça comida Esquerda baixo
                                aux_x = x;
                                aux_y = y;
                                tabu.get(x).set(y, Dama.VAZIA);
                                    while(aux_x < x2 && aux_y > y2 && aux_x < 8 && aux_y > 0){
                                        if(tabu.get(aux_x).get(aux_y)!= Dama.DPRETA || tabu.get(aux_x).get(aux_y)!= Dama.PRETA){
                                            tabu.get(aux_x).set(aux_y, Dama.VAZIA);
                                        }
                                        aux_x++;
                                        aux_y--;
                                    }
                            
                            
                            //Apagar peça comida Direita cima
                                aux_x = x;
                                aux_y = y;
                                tabu.get(x).set(y, Dama.VAZIA);
                                    while(aux_x > x2 && aux_y < y2 && aux_x > 0 && aux_y < 8){
                                        if(tabu.get(aux_x).get(aux_y)!= Dama.DPRETA || tabu.get(aux_x).get(aux_y)!= Dama.PRETA){
                                            tabu.get(aux_x).set(aux_y, Dama.VAZIA);
                                        }
                                        aux_x--;
                                        aux_y++;
                                    }
                            
                            //Apagar peça comida Esquerda cima
                                
                                aux_x = x;
                                aux_y = y;
                                tabu.get(x).set(y, Dama.VAZIA);
                                    while(aux_x > x2 && aux_y > y2 && aux_x > 0 && aux_y > 0){
                                        if(tabu.get(aux_x).get(aux_y)!= Dama.DPRETA || tabu.get(aux_x).get(aux_y)!= Dama.PRETA){
                                            tabu.get(aux_x).set(aux_y, Dama.VAZIA);
                                        }
                                        aux_x--;
                                        aux_y--;
                                    }
                                    
                                
                            tabu.get(x2).set(y2,Dama.DBRANCA);
                            //----------------- Comer novamente -----------------------------------
                                comer_novamente = false;
                            //Diagonal esquerda para cima

                            aux_x = x2 - 1;
                            aux_y = y2 - 1;    
                            while(aux_x >= 0 && aux_y >= 0){

                                if (damas.pode_comer(tabu, x2, y2, aux_x, aux_y)){
                                    comer_novamente = true;
                                }
                                if(comer_novamente == true)
                                    break;
                                aux_x--;
                                aux_y--;
                            }

                            //Diagonal direita para cima
                            aux_x = x2 - 1;
                            aux_y = y2 + 1;

                            while(aux_x >= 0 && aux_y < 8){

                                if (damas.pode_comer(tabu, x2, y2, aux_x, aux_y)){
                                    comer_novamente = true;
                                }
                                if(comer_novamente == true)
                                    break;
                                aux_x--;
                                aux_y++;
                            }

                            //Diagonal esqueda para baixo
                            aux_x = x2 + 1;
                            aux_y = y2 - 1;    
                            while(aux_x < 8 && aux_y >= 0){
                                if (damas.pode_comer(tabu, x2, y2, aux_x, aux_y)){
                                    comer_novamente = true;
                                }
                                if(comer_novamente == true)
                                    break;
                                aux_x++;
                                aux_y--;
                            }

                            //Diagonal direita para baixo

                            aux_x = x2 + 1;
                            aux_y = y2 + 1;
                            while(aux_x < 8 && aux_y < 8){
                                if (damas.pode_comer(tabu, x2, y2, aux_x, aux_y)){
                                    comer_novamente = true;
                                }
                                if(comer_novamente == true)
                                    break;
                                aux_x++;
                                aux_y++;
                            }

                            if(comer_novamente == true){
                                
                                continue;
                            }  
                    
                        }else if(tabu.get(x).get(y) == Dama.BRANCA){

                            tabu.get(x + dif_x).set(y + dif_y, Dama.VAZIA);
                            tabu.get(x2).set(y2, Dama.BRANCA);
                        }
                        
                        tabu.get(x).set(y, Dama.VAZIA);

                    
                    
                            
                    
                        
                    //Comer novamente                        
                    comer_novamente = false;
                        
                    if(tabu.get(x).get(y) == Dama.BRANCA || tabu.get(x).get(y) == Dama.PRETA){
                        if (damas.pode_comer(tabu, x2, y2, x2 - 2, y2 - 2)
                            || damas.pode_comer(tabu, x2, y2, x2 - 2, y2 + 2)
                            || damas.pode_comer(tabu, x2, y2, x2 + 2, y2 - 2)
                            || damas.pode_comer(tabu, x2, y2, x2 + 2, y2 + 2)) {
                                comer_novamente = true;
                                continue;
                            }
                    }
                    
                    if(comer_novamente == true){
                          continue;
                    }  
                        
                    if(x2 == 0)
                        tabu.get(x2).set(y2, Dama.DBRANCA);
    
                    }else if(damas.pode_movimentar(tabu, x, y, x2, y2)){
                       
                        // Garantir que a dama não volte a ser peça normal
                        if(tabu.get(x).get(y) == Dama.DBRANCA){
                            tabu.get(x).set(y, Dama.VAZIA);
                            tabu.get(x2).set(y2, Dama.DBRANCA);
                        }else{
                            tabu.get(x).set(y, Dama.VAZIA); 
                            tabu.get(x2).set(y2, Dama.BRANCA);
                        }
                        
                        if(x2 == 0){
                            tabu.get(x2).set(y2, Dama.DBRANCA);
                        }
                        
                    }else{
                        System.out.println("Erro de entrada! Tentando novamente...");
                        continue;
                    }
                    
                    cor_da_vez = Dama.PRETA;
                    
                }else{
                    tabu = damas.jogar_via_minimax(tabu, Dama.PRETA, 3);
                    
                    cor_da_vez = Dama.BRANCA;
                    
                }
                
                //damas.mostrar_tabuleiro(tabu);
                
            }
        }catch(Exception e){
            System.out.println("main: " +e.getMessage());
        }
        
    }
    
}
