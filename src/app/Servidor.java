package app;

import java.io.FileWriter;
import java.io.IOException;

import modelo.ArvoreAVL;

public class Servidor {
    private ArvoreAVL bancoDeDados;
    private FileWriter arquivoLog;

    public Servidor() throws IOException {
        bancoDeDados = new ArvoreAVL();
        arquivoLog = new FileWriter("servidor_log.txt", true);
    }

    public OrdemServico buscar(int codigo) {
        OrdemServico os = bancoDeDados.buscar(codigo);
        return os;
    }

    public void inserir(OrdemServico os) throws IOException {
        bancoDeDados.resetarTipoRotacao();
        bancoDeDados.inserir(os);
        registrarOperacao("Inserção", os.getCodigo());
    }

    public void remover(int codigo) throws IOException {
        if (bancoDeDados.buscar(codigo) == null) {
            System.out.println("Ordem de Serviço não encontrada.");
            return;
        }
        bancoDeDados.remover(codigo);
        registrarOperacao("Remoção", codigo);
    }

    public void alterar(int codigo, String nome, String descricao, String dataHora) throws IOException {
        if (bancoDeDados.buscar(codigo) == null) {
            System.out.println("Ordem de Serviço não encontrada.");
            return;
        }
        bancoDeDados.remover(codigo);
        OrdemServico novaOs = new OrdemServico(codigo, nome, descricao);
        inserir(novaOs);
        registrarOperacao("Alteração", codigo);
    }

    public void listar() {
        bancoDeDados.emOrdem();
    }

    private void registrarOperacao(String operacao, int codigo) throws IOException {
        int alturaArvore = bancoDeDados.getAltura();
        String tipoRotacao = bancoDeDados.getTipoRotacao();
        String log = operacao + " - Código: " + codigo + " - Altura da Árvore: " + (alturaArvore - 1) + " - " + tipoRotacao + "\n";

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