package app;

import java.io.FileWriter;
import java.io.IOException;

import modelo.ArvoreAVL;

public class Servidor {
    private ArvoreAVL bancoDeDados;
    private CacheFIFO cache;
    private FileWriter arquivoLog;

    public Servidor(int tamanhoCache) throws IOException {
        bancoDeDados = new ArvoreAVL();
        cache = new CacheFIFO(tamanhoCache);
        arquivoLog = new FileWriter("servidor_log.txt", true);
    }

    public OrdemServico buscar(int codigo) {
        OrdemServico os = cache.obter(codigo);
        if (os != null) {
            return os;
        }
        os = bancoDeDados.buscar(codigo);
        if (os != null) {
            cache.adicionar(os);
        }
        return os;
    }

    public void inserir(OrdemServico os) throws IOException {
        bancoDeDados.resetarTipoRotacao();
        bancoDeDados.inserir(os);
        cache.adicionar(os);
        registrarOperacao("Inserção", os.getCodigo());
    }

    public void remover(int codigo) throws IOException {
        bancoDeDados.remover(codigo);
        registrarOperacao("Remoção", codigo);
    }

    public void alterar(int codigo, String nome, String descricao, String dataHora) throws IOException {
        remover(codigo);
        OrdemServico novaOs = new OrdemServico(codigo, nome, descricao, dataHora);
        inserir(novaOs);
        registrarOperacao("Alteração", codigo);
    }

    public void listarOrdemServicos() {
        bancoDeDados.emOrdem();
    }

    private void registrarOperacao(String operacao, int codigo) throws IOException {
        int alturaArvore = bancoDeDados.getAltura();
        String tipoRotacao = bancoDeDados.getTipoRotacao();
        String log = operacao + " - Código: " + codigo + " - Altura da Árvore: " + alturaArvore + " - " + tipoRotacao + "\n";
        arquivoLog.write(log);
        arquivoLog.flush();
        System.out.print(log);
        cache.mostrarCache();
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