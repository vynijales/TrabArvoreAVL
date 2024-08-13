package app;

import java.util.LinkedList;

public class CacheFIFO {
    private LinkedList<OrdemServico> cache;
    private int capacidade;

    public CacheFIFO(int capacidade) {
        this.capacidade = capacidade;
        this.cache = new LinkedList<>();
    }

    public void adicionar(OrdemServico os) {
        if (cache.size() >= capacidade) {
            cache.removeFirst();
        }
        cache.addLast(os);
    }

    public OrdemServico getOrdemServico(int codigo) {
        for (OrdemServico os : cache) {
            if (os.getCodigo() == codigo) {
                return os;
            }
        }
        return null;
    }

    public boolean remover(int codigo) {
        for (OrdemServico os : cache) {
            if (os.getCodigo() == codigo) {
                cache.remove(os);
                return true;
            }
        }
        return false;
    }

    public void print() {
        System.out.println("Conteúdo da Cache:");
        if (cache.isEmpty()) {
            System.out.println("Cache vazia.");

        } else {
            for (OrdemServico os : cache) {
                System.out.println(os);
            }
        }
        System.out.println();
    }
}
