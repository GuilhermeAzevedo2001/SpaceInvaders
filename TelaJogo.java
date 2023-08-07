package trabalho_1;

import java.util.ArrayList;

public class TelaJogo {
    
    // O campo será uma matriz de 15 linhas por 20 colunas
    private final int tamX = 20;
    private final int tamY = 40;
    
    // Matriz de caracteres que será printada, simulando a tela do jogo 
    private char tela[][];
    
    // Lista e objetos de Elementos que serão usados 
    private ArrayList<Invasor> invaders;
    private ArrayList<Barreira> base;
    private ArrayList<Tiro> tiroPlayer;
    
    // Canhao corresponde ao proprio jogador
    private Canhao jogador;
    
    // Controla o tempo do tiro
    private long tempo_tiro; 
    
    // Indica se o jogo acabou ou não
    private boolean jogo = true; 
    
    TelaJogo() {
        tela = new char[tamX][tamY];
        invaders = new ArrayList<>();
        base = new ArrayList<>();
        tiroPlayer = new ArrayList<>();
    }
    
    // Método responsável por deixar o jogo em loop, atualizando a matriz do jogo e travando o Output console
    public void iniciaJogo() throws InterruptedException {
       
        // Inicia o jogo na primeira fase de dificuldade(só existe este nível nesse primeiro momento)
        setElementos(0);
        
        // Variáveis de controle do canhão 
        int mov = 1;
        int cont_mov = 0;
        
        while(jogo) {
           escreve_tela();
           processaMovimento(mov);
           cls();
           
           //Reseta a variavel de movimentos do canhao (Inverter o sentido do canhao)
           cont_mov++;
           if(cont_mov == 16) {
               cont_mov = 0;
               if(mov == 1)
                   mov = -1;
               else
                   mov = 1;
           }           
        }     
        System.out.println("\nVocê foi derrotado, sua pontuação foi de: " + jogador.getPontos());
    }

    // Método que instancia todos os elementos do jogo
    private void setElementos(int nFase) {
        // Os invasores estão divididos em 11 colunas e 5 linhas
        int i, j;
        for (i = 0; i <= 4; i++) 
            for (j = tamY/2 - 5; j <= tamY/2 + 5; j++) 
                invaders.add(new Invasor(i + nFase, j, '&', 1, 0));
            
        // As barreiras ficarão na linha 27 da matriz      
        for (j = 4; j < 37; j += 8) {
            base.add(new Barreira(tamX - 3, j, '@', 2));
            base.add(new Barreira(tamX - 3, j+1, '@', 2 ));
        }
        // Inicio proximo ao canto esquerdo
        jogador = new Canhao(tamX - 1, tamY/3, (char) 177, 1);
    }

    //Método responsável por escrever os objetos na tela em suas posições corretas
    private void escreve_tela() {
        Elemento aux;
        int i, j;
        
        // Atualiza a posição dos invasores
        i = 0;
        while(i != invaders.size()) {
            aux = invaders.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        // Atualiza a posição dos tiros
        i = 0;
        while(i != tiroPlayer.size()) {
            aux = tiroPlayer.get(i);
            
            // Efeito de explosão
            if(tela[aux.x][aux.y] == '&' || tela[aux.x][aux.y] == '$')
                tela[aux.x][aux.y] = (char) 37;
            else
                tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        // Atualiza a posição barreiras
        i = 0;
        while(i != base.size()) {
            aux = base.get(i);
            tela[aux.x][aux.y] = aux.getSimbol();
            i++;
        }
        
        // Atualiza a posição do jogador
        tela[jogador.x][jogador.y] = jogador.getSimbol();
        
        // Escreve todo o conteúdo no Console
        for(i = 0; i < tamX; i++) {
            for(j = 0; j < tamY; j++) {
                if(tela[i][j] == 0) 
                    System.out.print(" ");
                 else
                    System.out.print(tela[i][j]);
            }
            System.out.print("\n");
        }
    }
    
    // Método responsável por processar o movimento dos elementos
    private void processaMovimento(int mov) {        
        Invasor auxInv;
        Tiro tiro;
        Barreira auxBase;
        int i,j;  
        
        processa_colisoes();
        
        // Tiros_jogador 
        // Atirar apenas em intervalo de tempo de 1 segundo
        if(System.nanoTime() - tempo_tiro >= 800000000 ) {
            tiro = new Tiro(jogador.x, jogador.y, (char) 42);
            tiroPlayer.add(tiro);
            
            tempo_tiro = System.nanoTime();                        
        }
        
        // Avançar com todos os tiros
        i = 0;
        while(i < tiroPlayer.size()) {
            tiro = tiroPlayer.get(i);
            tiro.x--;
            
            if(tiro.x < 0) //Tiro inválido, deve ser retirado
                tiroPlayer.remove(tiro);
            i++;
        }
       
        // Invasores 
        // Pesquisar a localização atual dos invasores e setar o seu proximo movimento
        i = 0;
        while(i != invaders.size()) {
            auxInv = invaders.get(i);
                     
            // Caso algum dos invasores esteja prestes a sair da borda, mudar o sentido do movimento
            if((auxInv.getDirecao() == 0 && auxInv.y == tamY -1) || (auxInv.getDirecao() == 1 && auxInv.y == 0)
                || (auxInv.y + Invasor.getVelocidade() > tamY - 1 && auxInv.getDirecao()== 0)
                ||(auxInv.getDirecao() == 1 && auxInv.y - Invasor.getVelocidade() < 0)) {              
                j = 0;
                while(j != invaders.size()) {
                    auxInv = invaders.get(j);
                    auxInv.inverteSentido(); //Inverter a direção
                    auxInv.move(tamY);
                    j++;
                }
                return;
            }
            else if(auxInv.x == tamX -1) { // Fim da partida
                jogo = false;
                return;
            }
            i++;
        }
        
        // Movimenta os invasores 
        i = 0;
        while(i != invaders.size() && jogo) {
            auxInv = invaders.get(i);
            auxInv.move(tamY);
            i++;
        }

        // Canhão
        // Mivimenta canhao
        jogador.y += mov;
        
        //Remover todas as barreiras cuja vida seja 0
        i = 0;
        while(i < base.size() && jogo) {
            auxBase = base.get(i);            
            if(auxBase.getVidas() == 0)
                base.remove(auxBase);
            i++;
        }
    }
    
    // Método responsável por processar todas as colisões do jogo
    private void processa_colisoes() {
        Barreira auxBase;
        Invasor auxInv;
        Tiro tiro;
        int i, j;
        
        // Colisão tiro-invasor 
        i = 0;
        while(i < tiroPlayer.size()) {
            j = 0;
            tiro = tiroPlayer.get(i);
            
            while(j < invaders.size()) {
                auxInv = invaders.get(j);
                
                //Testar colisão entre todos os tiros e todos os invasores
                if(tiro.x == auxInv.x && tiro.y == auxInv.y) {
                    //Colisão!!                                  
                    tiroPlayer.remove(tiro);
                    invaders.remove(auxInv);
                    
                    //O jogador ganha pontos!!
                    jogador.aumentaPontos();

                    Invasor.aumentaVelocidade(invaders);
                }
                j++;
            }
            i++;
        }

        // Colisão Barreira-Tiro
        i = 0;
        while(i < base.size()) {
            j = 0;
            auxBase = base.get(i);
            
            while(j < tiroPlayer.size()) {
                tiro = tiroPlayer.get(j);
                
                //Testar colisão entre todos os tiros e todos os invasores
                if(tiro.x == auxBase.x && tiro.y == auxBase.y) {
                    //Colisão!!
                    //Devo retirar uma vida da barreira e remover o tiro
                    tiroPlayer.remove(tiro);
                    auxBase.removeVida();
                }
                j++;
            }
            i++;
        }
    }
    
    // Método responsável por limpar o console
    private void cls() throws InterruptedException {
        int i, j;
        
        //Dando este sleep causa o efeito de refresh
        Thread.sleep(250); 
        
        //Além disso devo limpar a matriz tela
        for(i = 0; i < tamX; i++)
            for(j = 0; j< tamY; j++)
                tela[i][j] = (char) 0;
    }
}