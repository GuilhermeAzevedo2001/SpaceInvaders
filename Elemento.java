package trabalho_1;

public class Elemento {
    
    public int x, y;
    protected char simbol;
    protected int vida;
    
    /*
    * Existem elementos que não possuem um numero de vidas maior que 1, como 
    * uma instancia "Tiro" ou como o proprio "Invasor", por este motivo 
    * existem dois contrutores, porém com uma redução no numero de parametros
    */
    Elemento(int x, int y, char simbol, int vida) {
        this.x = x;
        this.y = y;
        this.simbol = simbol;
        this.vida = vida;
    }
    
    Elemento(int x, int y, char simbol) {
        this.x = x;
        this.y = y;
        this.simbol = simbol;
        vida = 0;
    }
    
    /**
     * Método que seta a atual posição do elemento
     * x - Posição X do elemento na matriz da tela 
     * y - Posição Y do elemento na matriz da tela
     */
    public final void setPos(int x, int y) {
        this.x = x;
        this.x = y;
    }
    
    // Método que retorna a quantidade de vidas do elemento
    public int getVidas() {
        return vida;
    }
    
    // Método que retorna o símbolo
    public char getSimbol() {
        return simbol;     
    }
   
    // Método que remove uma vida deste elemento
    public void removeVida() {
        this.vida--;
    }
}
