package app;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(20);

            // Adiciona 60 ordens de serviço na base de dados
            for (int i = 1; i <= 60; i++) {
                OrdemServico os = new OrdemServico(i, "Ordem " + i, "Descrição " + i, "2024-08-11 12:00");
                servidor.inserir(os);
            }

            Cliente cliente = new Cliente(servidor);
            cliente.iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
