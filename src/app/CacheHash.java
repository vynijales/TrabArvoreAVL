package app;

import java.util.Random;

import modelo.TabelaEnderecamentoAberto;

public class CacheHash {
    private TabelaEnderecamentoAberto tabela;

    // ----------------------------------------------------------------------------------------------------------
    // Construtor
    public CacheHash() {
        tabela = new TabelaEnderecamentoAberto(20); // Será utilziado na cache, então o limite será 20
    }

    // ----------------------------------------------------------------------------------------------------------
    // Métodos auxiliares
    private boolean possueOS(int codigo) { return tabela.buscar(codigo) != null; }

    private void randomRemove() {
        Random random = new Random();
        int indice = random.nextInt(tabela.getTamanho());
        tabela.remover(indice);
        System.out.println("Os de índice " + indice + " removida aleatoriamente.");
    }
    // ----------------------------------------------------------------------------------------------------------
    // CRUD da Cache

    public OrdemServico buscar(int codigo) {
        OrdemServico os = tabela.buscar(codigo);
        if (os == null) {
            System.out.println("Ordem de Serviço não encontrada na cache.");
        }
        return os;
    }

    public void inserir(OrdemServico osNova) {
        if (possueOS(osNova.getCodigo())) {
            System.out.println("Ordem de Serviço já cadastrada.");
            return;
        }

        if (tabela.isCheia()) { randomRemove(); }

        tabela.inserir(osNova);
    }

    public void alterar(int codigo, OrdemServico osNova) {
        if (!possueOS(codigo)) {
            System.out.println("Ordem de Serviço não encontrada na cache.");
            return;
        }

        tabela.alterar(codigo, osNova);
    }

    public void remover(int codigo) {
        if (!possueOS(codigo)) {
            System.out.println("Ordem de Serviço não encontrada na cache.");
            return;
        }

        tabela.remover(codigo);
    }

    // ----------------------------------------------------------------------------------------------------------
    // Métodos de impressão
    public void imprimir() {
        System.out.println("Conteúdo da Cache:");
        tabela.imprimir();
    }
}
