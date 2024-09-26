package modelo;

import app.OrdemServico;

public class TabelaEnderecamentoAberto {
    int M;
    int menorPrimo;
    No[] tabela;
    int qntRegistros;

    // ----------------------------------------------------------------------------------------------------------
    // Construtor
    public TabelaEnderecamentoAberto(int tamanho) {
        M = tamanho;
        tabela = new No[tamanho]; // Será utilziado na cache, então o limite será 20
        qntRegistros = 0;
    }

    // ----------------------------------------------------------------------------------------------------------
    // Métodos auxiliares
    public boolean isCheia() { return qntRegistros == M; };

    public boolean isVazia() { return qntRegistros == 0; };

    public int getTamanho() { return M; };

    private int hash(int codigo) {
       // Método da multiplicação
        double A = (Math.sqrt(5) - 1) / 2; // Número áureo (0.6180339887...)
        return (int) (M * ((codigo * A) % 1)); // Função de hash (h(k) = m * (k * A % 1))
    }

    private int tentativaLinear(int codigo, int tentativa) {
        return (hash(codigo) + tentativa) % M;
    }

    private No buscarNo(int codigo) {
        int tentativa = 0; // Tentativa de busca
        int h = tentativaLinear(codigo, tentativa); // Índice da tabela onde o registro será inserido

        while (tabela[h] != null && tentativa < M) {
            if (tabela[h].os == null) {return null;} // Se encontrar uma posição vazia, retorna null
            if (tabela[h].os.getCodigo() == codigo) {return tabela[h];} // Se encontrar o registro, retorna
            h = tentativaLinear(codigo, ++tentativa); // Senão, tenta a próxima posição
        }

        return null; // Se não encontrar o registro, retorna null
    }
    // ----------------------------------------------------------------------------------------------------------
    // CRUD da Tabela Hash
    public OrdemServico buscar(int codigo) {
        No no = buscarNo(codigo); // Busca o nó que contém o registro
        if (no == null) return null; // Se não encontrar, retorna nulo
        return no.os; // Retorna o registro
    }

    public void inserir(OrdemServico osNova) {
        int tentativa = 0; // Tentativa de inserção
        int h = tentativaLinear(osNova.getCodigo(), tentativa); // Índice da tabela onde o registro será inserido

        while (tabela[h] != null && tentativa < M) {
            if (tabela[h].os.getCodigo() == osNova.getCodigo()) {
                System.out.println("Ordem de Serviço já cadastrada.");
                return;
            }
            h = tentativaLinear(osNova.getCodigo(), ++tentativa);
        }

        if (tentativa >= M) {
            System.out.println("Quantidade de tentativas excedida.");
            return;
        }

        // Se a posição estiver vazia, insere o registro
        tabela[h] = new No();
        tabela[h].os = osNova;
        qntRegistros++;
    }

    public void remover(int codigo) {
        No no = buscarNo(codigo);
        if (no != null) {
            no.os = null;
            tabela[hash(codigo)] = null;
            qntRegistros--;
        }
    }

    public void alterar(int codigo, OrdemServico osNova) {
        No no = buscarNo(codigo);
        if (no != null) {
            no.os = osNova;
        }
    }

    // ----------------------------------------------------------------------------------------------------------
    // Métodos de impressão
    public void imprimir() {
        for (int i = 0; i < M; i++) {
            if (tabela[i] != null) {
                System.out.println("Índice " + i + " da tabela hash: " + tabela[i].os);
            }
        }
    }

}
