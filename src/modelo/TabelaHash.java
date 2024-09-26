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

    public int getQuantidade() {
        return qntRegistros;
    }

    public double getFatorCarga() {
        return (double) qntRegistros / getTamanho();
    }

    public TabelaHash redimensionar() {
        int novoTamanho = M * 2; // Dobra o tamanho da tabela
        TabelaHash novaTabela = new TabelaHash(novoTamanho);
        
        for (int i = 0; i < M; i++) {
            No no = tabela[i];
            while (no != null) {
                novaTabela.inserir(no.os);
                no = no.proximo;
            }
        }

        return novaTabela;
    }

    private int hash(int codigo) {
        // Método da multiplicação
        double A = (Math.sqrt(5) - 1) / 2; // Número áureo (0.6180339887...)
        return (int) (M * ((codigo * A) % 1)); // Função de hash (h(k) = m * (k * A % 1))
    }

    private boolean ehPrimo(int numero) {
        if (numero <= 1) return false;

        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }
    
    private int proximoPrimo(int numero) {
        int primo = numero + 1;
        while (!ehPrimo(primo)) {
            primo++;
        }
        return primo;
    }

    private No buscarNo(int codigo) {
        int indice = hash(codigo);
        No no = tabela[indice];

        while (no != null) {
            if (no.os.getCodigo() == codigo) return no;
            no = no.proximo;
        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------------------
    // CRUD da Tabela Hash

    public OrdemServico buscar(int codigo) {
        No no = buscarNo(codigo); // Busca o nó que contém o registro
        if (no == null) return null; // Se não encontrar, retorna nulo
        return no.os; // Retorna o registro
    }
    
    public void inserir(OrdemServico osNova) {
        int h = hash(osNova.getCodigo()); // Índice da tabela onde o registro será inserido
        No no = tabela[h]; // Ponteiro para o primeiro nó da lista

        while (no != null) { // Percorre a lista encadeada até encontrar o fim (no == null)
            if (no.os.getCodigo() == osNova.getCodigo()) break; // Verifica se o registro já existe, impedindo duplicatas
            no = no.proximo; // Avança para o próximo nó
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
        No no = buscarNo(codigo);
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

    // ----------------------------------------------------------------------------------------------------------
    // Métodos de impressão
    public void imprimir() {
        for (int i = 0; i < M; i++) {
            No no = tabela[i];
            System.out.println("Endereço " + i + " da tabela hash encadeada:");
            while (no != null) {
                System.out.print("Código " + no.os.getCodigo() + " | " + no.os.getNome() + " | " + no.os.getDescricao() + " | " + no.os.getDataHora() + "\n");
                no = no.proximo;
            }
            System.out.println();
        }
    }
}
