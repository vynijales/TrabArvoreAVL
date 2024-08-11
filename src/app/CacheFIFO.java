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

    public OrdemServico obter(int codigo) {
        for (OrdemServico os : cache) {
            if (os.getCodigo() == codigo) {
                return os;
            }
        }
        return null;
    }

    public void mostrarCache() {
        System.out.println("Conteúdo da Cache:");
        for (OrdemServico os : cache) {
            System.out.println(os);
        }
        System.out.println();
    }
}
