package trabalho_1;

public class Canhao extends Elemento {
    
    private int pontos; 
    
    Canhao(int x, int y, char simbol, int vida) {
        super(x, y, simbol, vida); 
        pontos = 0;
    }
    
    // Método que retorna os pontos do canhão
    public int getPontos() {
        return pontos;
    }
    
    // Método que aumenta em uma unidade os pontos do jogador
    public void aumentaPontos() {
        pontos += 10;
    }
         
}
