package app;

import java.io.FileWriter;
import java.io.IOException;

// import modelo.ArvoreAVL;
import modelo.TabelaHash;

public class Servidor {
    // private ArvoreAVL bancoDeDados;
    private TabelaHash bancoDeDados;
    private FileWriter arquivoLog;

    // ----------------------------------------------------------------------------------------------------------
    // Construtor
    public Servidor() throws IOException {
        // bancoDeDados = new ArvoreAVL();
        bancoDeDados = new TabelaHash(11); // Tamanho inicial da tabela hash
        arquivoLog = new FileWriter("servidor_log.txt", true);
    }

    // ----------------------------------------------------------------------------------------------------------
    // Métodos auxiliares
    public void expandirBD(){
        TabelaHash expandida = bancoDeDados.redimensionar(); // Dobra o tamanho da tabela
        bancoDeDados = expandida; // Atualiza a referência
    }

    // ----------------------------------------------------------------------------------------------------------
    // CRUD do servidor
    public OrdemServico buscar(int codigo) {
        OrdemServico os = bancoDeDados.buscar(codigo);
        if (os == null) {
            System.out.println("Ordem de Serviço não encontrada no servidor.");
        } 
        System.out.println("Orden de Serviço encontrada: " + os);
        return os;
    }

    public void inserir(OrdemServico os) throws IOException {
        if (bancoDeDados.getFatorCarga() > 0.9){ // Se o fator de carga for maior que 0.9
            expandirBD();
        }
        // bancoDeDados.resetarTipoRotacao();
        bancoDeDados.inserir(os); // Insere a ordem de serviço no banco de dados
        registrarOperacao("Inserção", os.getCodigo()); // Registra no log
    }

    public void remover(int codigo) throws IOException {
        if (bancoDeDados.buscar(codigo) == null) {
            System.out.println("Ordem de Serviço não encontrada no servidor.");
            return;
        }
        registrarOperacao("Remoção", codigo);
        bancoDeDados.remover(codigo);
    }

    public void alterar(int codigo, OrdemServico novaOs) throws IOException {
        if (bancoDeDados.buscar(codigo) == null) {
            System.out.println("Ordem de Serviço não encontrada no servidor.");
            return;
        }
        bancoDeDados.alterar(codigo, novaOs);
        registrarOperacao("Alteração", codigo);
    }

    public void listar() {
        // bancoDeDados.emOrdem();
        System.out.println("=====================================");
        System.out.println("Conteúdo do servidor:");
        bancoDeDados.imprimir();
    }

    // ----------------------------------------------------------------------------------------------------------
    // Métodos de registro e impressão

    private void registrarOperacao(String operacao, int codigo) throws IOException {
        // int alturaArvore = bancoDeDados.getAltura();
        // String tipoRotacao = bancoDeDados.getTipoRotacao();
        // String log = operacao + " - Código: " + codigo + " - Altura da Árvore: " + (alturaArvore - 1) + " - " + tipoRotacao + "\n";

        OrdemServico os = bancoDeDados.buscar(codigo);
        String descricao = os.getDescricao();
        String data = os.getDataHora();
        // Código com no mínimo 2 caracteres

        String log = operacao + " - Código " + String.format("%02d", os.getCodigo()) + ", Descrição " + descricao +  ", Data " + data + "\n";

        arquivoLog.write(log);
        arquivoLog.flush();
    }

    public void fechar() throws IOException {
        if (arquivoLog != null) {
            arquivoLog.close();
        }
    }

    public String quantidadeOrdensServico() {
        return "Quantidade de Ordens de Serviço: " + bancoDeDados.getQuantidade();
    }
}
