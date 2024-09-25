package modelo;

import app.OrdemServico;

// Tabela Hash usando tratamento de colisões por encadeamento exterior.
public class TabelaHash {
    private int M;
    private int qntRegistros;
    private No[] tabela; // Vetor de ponteiros para os registros

    // ----------------------------------------------------------------------------------------------------------
    // Construtor
    public TabelaHash(int tamanho) {
        M = tamanho;
        qntRegistros = 0;
        tabela = new No[M];
    }

    // ----------------------------------------------------------------------------------------------------------
    // Métodos auxiliares
    public No getNoAt(int indice) {
        return tabela[indice];
    }

    public void setNoAt(int indice, No no) {
        tabela[indice] = no;
    }

    public int getTamanho() {
        return M;
    }

    public int getQntRegistros() {
        return qntRegistros;
    }

    public double getFatorCarga() {
        return (double) qntRegistros / getTamanho();
    }

    public int hash(int codigo) {
        return codigo % M;
    }

    public boolean ehPrimo(int numero) {
        if (numero <= 1) return false;

        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }
    
    public int proximoPrimo(int numero) {
        int primo = numero + 1;
        while (!ehPrimo(primo)) {
            primo++;
        }
        return primo;
    }

    // ----------------------------------------------------------------------------------------------------------
    // CRUD da Tabela Hash
    public No buscar(int codigo) {
        int indice = hash(codigo);
        No no = tabela[indice];

        while (no != null) {
            if (no.os.getCodigo() == codigo) return no;
            no = no.proximo;
        }

        return null;
    }

    public void inserir(OrdemServico osNova) {
        int h = hash(osNova.getCodigo());
        No no = tabela[h];

        while (no != null) {
            if (no.os.getCodigo() == osNova.getCodigo()) break;
            no = no.proximo;
        }

        if (no == null) {
            No novoNo = new No();
            novoNo.os = osNova;
            novoNo.proximo = tabela[h];
            tabela[h] = novoNo;
            qntRegistros++;
        }
    }

    public void alterar(int codigo, OrdemServico osNova) {
        No no = buscar(codigo);
        if (no == null) return;
        no.os = osNova;
    }

    public void remover(int codigo) {
        int indice = hash(codigo);
        No no = tabela[indice];
        No anterior = null;

        while (no != null) {
            if (no.os.getCodigo() == codigo) {
                if (anterior == null) {tabela[indice] = no.proximo;} // Remover o primeiro nó
                else {anterior.proximo = no.proximo;} // Remover um nó no meio ou no final
                qntRegistros--;
                return;
            }
            anterior = no;
            no = no.proximo;
        }
    }
}
