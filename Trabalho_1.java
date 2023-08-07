package trabalho_1;

/**
 *
 * @author Guilh
 */
public class Trabalho_1 {
    public static void main(String[] args) {
    
       TelaJogo it = new TelaJogo();
        
        try {
            it.iniciaJogo();
        }
        catch(InterruptedException e) {
            System.out.println("Erro: "+ e.getMessage());
        }
        catch(Error e) {
            System.out.println("Erro ocorreu na execução do jogo: "+ e.getMessage());
        }
    }
    
}
