package modelo;

import app.OrdemServico;

public class ArvoreAVL {
    private NoAVL raiz;
    private String tipoRotacao;

    private int altura(NoAVL N) {
        if (N == null)
            return 0;
        return N.altura;
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerdo;
        NoAVL T2 = x.direito;
    
        x.direito = y;
        y.esquerdo = T2;
    
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
    
        tipoRotacao = "Rotação direita simples"; // Atualizado
        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direito;
        NoAVL T2 = y.esquerdo;
    
        y.esquerdo = x;
        x.direito = T2;
    
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
    
        tipoRotacao = "Rotação esquerda simples"; // Atualizado
        return y;
    }

    private int getBalanceamento(NoAVL N) {
        if (N == null)
            return 0;
        return altura(N.esquerdo) - altura(N.direito);
    }

    public boolean inserir(OrdemServico dado) {
        boolean[] rotacionou = { false };
        raiz = inserir(raiz, dado, rotacionou);
        return rotacionou[0];
    }

    private NoAVL inserir(NoAVL no, OrdemServico dado, boolean[] rotacionou) {
        if (no == null) {
            return new NoAVL(dado);
        }
    
        if (dado.getCodigo() < no.dado.getCodigo()) {
            no.esquerdo = inserir(no.esquerdo, dado, rotacionou);
        } else if (dado.getCodigo() > no.dado.getCodigo()) {
            no.direito = inserir(no.direito, dado, rotacionou);
        } else {
            return no; // Duplicates are not allowed
        }
    
        no.altura = 1 + Math.max(altura(no.esquerdo), altura(no.direito));
    
        int balanceamento = getBalanceamento(no);
    
        // Rotação à direita
        if (balanceamento > 1 && dado.getCodigo() < no.esquerdo.dado.getCodigo()) {
            rotacionou[0] = true;
            return rotacaoDireita(no);
        }
    
        // Rotação à esquerda
        if (balanceamento < -1 && dado.getCodigo() > no.direito.dado.getCodigo()) {
            rotacionou[0] = true;
            return rotacaoEsquerda(no);
        }
    
        // Rotação dupla à direita
        if (balanceamento > 1 && dado.getCodigo() > no.esquerdo.dado.getCodigo()) {
            rotacionou[0] = true;
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }
    
        // Rotação dupla à esquerda
        if (balanceamento < -1 && dado.getCodigo() < no.direito.dado.getCodigo()) {
            rotacionou[0] = true;
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }
    
        return no;
    }

    public boolean remover(int codigo) {
        boolean[] rotacionou = { false };
        raiz = remover(raiz, codigo, rotacionou);
        return rotacionou[0];
    }

    private NoAVL remover(NoAVL no, int codigo, boolean[] rotacionou) {
        if (no == null) {
            return no;
        }

        if (codigo < no.dado.getCodigo()) {
            no.esquerdo = remover(no.esquerdo, codigo, rotacionou);
        } else if (codigo > no.dado.getCodigo()) {
            no.direito = remover(no.direito, codigo, rotacionou);
        } else {
            if ((no.esquerdo == null) || (no.direito == null)) {
                NoAVL temp = null;
                if (temp == no.esquerdo) {
                    temp = no.direito;
                } else {
                    temp = no.esquerdo;
                }

                if (temp == null) {
                    temp = no;
                    no = null;
                } else {
                    no = temp;
                }
            } else {
                NoAVL temp = getMenorNoAVL(no.direito);
                no.dado = temp.dado;
                no.direito = remover(no.direito, temp.dado.getCodigo(), rotacionou);
            }
        }

        if (no == null) {
            return no;
        }

        no.altura = Math.max(altura(no.esquerdo), altura(no.direito)) + 1;

        int balanceamento = getBalanceamento(no);

        // Rotação à direita
        if (balanceamento > 1 && getBalanceamento(no.esquerdo) >= 0) {
            rotacionou[0] = true;
            return rotacaoDireita(no);
        }

        // Rotação dupla à direita
        if (balanceamento > 1 && getBalanceamento(no.esquerdo) < 0) {
            rotacionou[0] = true;
            no.esquerdo = rotacaoEsquerda(no.esquerdo);
            return rotacaoDireita(no);
        }

        // Rotação à esquerda
        if (balanceamento < -1 && getBalanceamento(no.direito) <= 0) {
            rotacionou[0] = true;
            return rotacaoEsquerda(no);
        }

        // Rotação dupla à esquerda
        if (balanceamento < -1 && getBalanceamento(no.direito) > 0) {
            rotacionou[0] = true;
            no.direito = rotacaoDireita(no.direito);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public String getTipoRotacao() {
        return tipoRotacao;
    }

    public void resetarTipoRotacao() {
        tipoRotacao = "Nenhuma rotação";
    }

    private NoAVL getMenorNoAVL(NoAVL no) {
        NoAVL atual = no;
        while (atual.esquerdo != null) {
            atual = atual.esquerdo;
        }
        return atual;
    }

    public OrdemServico buscar(int codigo) {
        return buscar(raiz, codigo);
    }

    private OrdemServico buscar(NoAVL no, int codigo) {
        if (no == null) {
            return null;
        }

        if (no.dado.getCodigo() == codigo) {
            return no.dado;
        }

        if (no.dado.getCodigo() > codigo) {
            return buscar(no.esquerdo, codigo);
        }

        return buscar(no.direito, codigo);
    }

    public void emOrdem() {
        emOrdem(raiz);
    }

    private void emOrdem(NoAVL no) {
        if (no != null) {
            emOrdem(no.esquerdo);
            System.out.println(no.dado);
            emOrdem(no.direito);
        }
    }

    public int getAltura() {
        return altura(raiz);
    }

    private int getAltura(NoAVL no) {
        if (no == null) {
            return -1;
        }
        return no.altura;
    }

    public String getQuantidade() {
        return String.valueOf(contarNos(raiz));
    }

    private int contarNos(NoAVL no) {
        if (no == null) {
            return 0;
        }
        return 1 + contarNos(no.esquerdo) + contarNos(no.direito);
    }
}