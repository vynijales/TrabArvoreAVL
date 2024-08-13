package app;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();

            // Adiciona 60 ordens de serviço na base de dados
            for (int i = 1; i <= 10; i++) {
                OrdemServico os = new OrdemServico(i, "Ordem " + i, "Descrição " + i);
                servidor.inserir(os);
            }

            Cliente cliente = new Cliente(servidor);

            Scanner scanner = new Scanner(System.in);

            System.out.println("\033[2J\033[1;1H");

            while (true) {

                cliente.printCache();

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
                System.out.println("\033[2J\033[1;1H"); // Limpar a tela

                switch (opcao) {
                    case 1:
                        cliente.buscar();
                        break;
                    case 2:
                        cliente.adicionar();
                        break;
                    case 3:
                        cliente.alterar();
                        break;
                    case 4:
                        cliente.remover();
                        break;
                    case 5:
                        servidor.listar();
                        break;

                    case 6:
                        servidor.quantidadeOrdensServico();
                        break;

                    case 0:
                        servidor.fechar();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
