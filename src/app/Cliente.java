package app;

import java.io.IOException;
import java.util.Scanner;

public class Cliente {
    private Servidor servidor;
    private CacheFIFO cache;
    private Scanner scanner;

    public Cliente(Servidor servidor) throws IOException {
        this.servidor = servidor;
        this.cache = new CacheFIFO(20);
        this.scanner = new Scanner(System.in);

    }

    public void buscar() {
        System.out.print("Digite o código da Ordem de Serviço a ser buscada: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        OrdemServico os = cache.getOrdemServico(codigo);
        System.out.println();
        if (os != null) {
            System.out.println("Ordem de Serviço encontrada na cache:");
            System.out.println(os);
            return;
        }

        os = servidor.buscar(codigo);
        if (os != null) {
            cache.adicionar(os);
            System.out.println("Ordem de Serviço encontrada no servidor:");
            System.out.println(os);
            return;
        }

        System.out.println("Ordem de Serviço não encontrada.");
    }

    public void adicionar() throws IOException {
        System.out.print("Digite o código da Ordem de Serviço a ser adicionado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println();


        if (isInCache(codigo)) {
            System.out.println("Ordem de serviço encontrada na cache.");
            return;
        }

        if (isInServidor(codigo)) {
            OrdemServico os = servidor.buscar(codigo);
            cache.adicionar(os);
            System.out.println("Ordem de serviço encontrada no servidor, adicionada na cache.");
            return;
        }

        System.out.print("Digite o nome da Ordem de Serviço: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a descrição da Ordem de Serviço: ");
        String descricao = scanner.nextLine();

        OrdemServico os = new OrdemServico(codigo, nome, descricao);
        cache.adicionar(os);
        servidor.inserir(os);
        System.out.println("Ordem de serviço adicionada no servidor e na cache.");
    }

    public void alterar() throws IOException {
        System.out.print("Digite o código da Ordem de Serviço a ser alterada: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (!isInCache(codigo) && !isInServidor(codigo)) {
            System.out.println("Ordem de Serviço não encontrada.");
            return;
        }

        System.out.print("Digite o novo nome da Ordem de Serviço: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a nova descrição da Ordem de Serviço: ");
        String descricao = scanner.nextLine();

        System.out.print("Digite a nova data e hora da Ordem de Serviço: ");
        String dataHora = scanner.nextLine();

        servidor.alterar(codigo, nome, descricao, dataHora);
        cache.remover(codigo);
        cache.adicionar(servidor.buscar(codigo));
        System.out.println("Ordem de Serviço alterada.");
    }

    public void remover() throws IOException {
        System.out.print("Digite o código da Ordem de Serviço a ser removida:");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (!isInCache(codigo) && !isInServidor(codigo)) {
            System.out.println("Ordem de Serviço não encontrada.");
            return;
        }

        servidor.remover(codigo);
        cache.remover(codigo);
        System.out.println("Ordem de Serviço removida.");
    }

    private boolean isInCache(int codigo) {
        return cache.getOrdemServico(codigo) != null;
    }

    private boolean isInServidor(int codigo) {
        return servidor.buscar(codigo) != null;
    }

    public void printCache() {
        cache.print();
    }
}
