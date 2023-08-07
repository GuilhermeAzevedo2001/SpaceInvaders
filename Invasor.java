package trabalho_1;

import java.util.ArrayList;

public class Invasor extends Elemento {
    
    // Velocidade do movimento de todos os invasores (todos possuem a mesma velocidade)
    private static int velocidade;
    
    /*
    * 0: mover para direita
    * 1: mover para esquerda
    */
    private int direcao;
    
    /*
    * 0: invasor anda em linha reta
    * 1: invasor desce 1 linha
    */
    private int descer;
    
    // Controla a mudança do caractere do invasor 
    private boolean alt_simbol = false;
    private final char simbol2 = '$';    // Caractere do invasor
    
    Invasor(int x, int y, char simbol, int velocidade, int direcao) {
        super(x, y, simbol);
        this.direcao = direcao; 
        Invasor.velocidade = velocidade; 
    }
    
     // Método que retorna a atual direção do invasor
     // direcao - Se 0, o invasor está andando para a direita; Se 1, o invasor está andando para a esquerda
    public int getDirecao() {
        return this.direcao;
    }
    
    // Método que retorna um símbolo para o invasor
    @Override
    public char getSimbol() {
        this.alt_simbol = !this.alt_simbol;
        if(this.alt_simbol) 
            return simbol;
        else
            return simbol2;
    }
    
    // Método que retorna a velocidade dos invasores
    public static int getVelocidade() {
        return Invasor.velocidade;
    }
    
    // Método que inverte o sentido de movimento do invasor ao inverter o valor da variável direcao
    public void inverteSentido() {
        descer = 1;        
        if(direcao == 1) 
            direcao = 0;
        else
            direcao = 1;
    }    
    
    // Método que atualiza a posição do invasor 
    public void move(int tamY) {   
        if(descer == 1) {
            x++;
            descer = 0;
        }
        else {
            switch (direcao) {
                case 0: 
                    if(y < tamY && (y + Invasor.velocidade >= tamY))   
                        y++;                    
                    else                        
                        y += Invasor.velocidade;
                    break;
            
                case 1: 
                    if(y >= 1 && (y - Invasor.velocidade < 0))
                        y--;
                    else
                        y -= Invasor.velocidade;
                    break;
            } 
        } 
    }
    
    // Método responsável por aumentar a velocidade dos invasores
    public static void aumentaVelocidade(ArrayList<Invasor> invaders) {
        // Temos 11x5 invasores = 55
        // Devo aumentar a velocidade de movimento dos aliens quando abater mais de 15
        if(invaders.size() == 35) {
            Invasor.velocidade++;
        }
        else if(invaders.size() == 20) {
            Invasor.velocidade++;
        }
    }
}
