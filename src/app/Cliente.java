package app;

import java.io.IOException;
import java.util.Scanner;

public class Cliente {
    private Servidor servidor;

    public Cliente(Servidor servidor) {
        this.servidor = servidor;
    }

    public void iniciar() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Buscar Ordem de Serviço");
            System.out.println("2. Cadastrar Ordem de Serviço");
            System.out.println("3. Alterar Ordem de Serviço");
            System.out.println("4. Remover Ordem de Serviço");
            System.out.println("5. Listar Ordens de Serviço");
            System.out.println("6. Quantidade de Ordens de Serviço");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Digite o código da Ordem de Serviço: ");
                    int codigoBusca = scanner.nextInt();
                    OrdemServico osBusca = servidor.buscar(codigoBusca);
                    if (osBusca != null) {
                        System.out.println(osBusca);
                    } else {
                        System.out.println("Ordem de Serviço não encontrada.");
                    }
                    break;
                case 2:
                    System.out.print("Digite o código: ");
                    int codigoCadastro = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite a descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Digite a data e hora: ");
                    String dataHora = scanner.nextLine();
                    OrdemServico osCadastro = new OrdemServico(codigoCadastro, nome, descricao, dataHora);
                    servidor.inserir(osCadastro);
                    break;
                case 3:
                    System.out.print("Digite o código da Ordem de Serviço a ser alterada: ");
                    int codigoAlteracao = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Digite o novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Digite a nova descrição: ");
                    String novaDescricao = scanner.nextLine();
                    System.out.print("Digite a nova data e hora: ");
                    String novaDataHora = scanner.nextLine();
                    servidor.alterar(codigoAlteracao, novoNome, novaDescricao, novaDataHora);
                    break;
                case 4:
                    System.out.print("Digite o código da Ordem de Serviço a ser removida: ");
                    int codigoRemocao = scanner.nextInt();
                    servidor.remover(codigoRemocao);
                    break;
                case 5:
                    servidor.listarOrdemServicos();
                    break;

                case 6:
                    System.out.println("Quantidade de Ordens de Serviço: " + servidor.quantidadeOrdensServico());
                    break;

                case 0:
                    servidor.fechar();
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
