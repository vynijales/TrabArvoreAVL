package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrdemServico {
        
        private int codigo;
        private String nome;
        private String descricao;
        private String dataHora;
    
        public OrdemServico() {
            this.codigo = 0;
            this.nome = "";
            this.descricao = "";
            this.dataHora = "";
        }
    
        public OrdemServico(int codigo, String nome, String descricao) {
            this.codigo = codigo;
            this.nome = nome;
            this.descricao = descricao;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            this.dataHora = LocalDateTime.now().format(formatter);
        }
   
        public OrdemServico(int codigo, String nome, String descricao, String dataHora) {
            this.codigo = codigo;
            this.nome = nome;
            this.descricao = descricao;
            this.dataHora = dataHora;
        }

        public int getCodigo() {
            return codigo;
        }
    
        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }
    
        public String getNome() {
            return nome;
        }
    
        public void setNome(String nome) {
            this.nome = nome;
        }
    
        public String getDescricao() {
            return descricao;
        }
    
        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    
        public String getDataHora() {
            return dataHora;
        }
    
        public void setDataHora(String dataHora) {
            this.dataHora = dataHora;
        }
    
        @Override
        public String toString() {
            return "OrdemServico:" + "\n"
                + "[codigo=" + codigo + ",\n"
                + "descricao=" + descricao + ",\n"
                + "dataHora=" + dataHora+ ",\n"
                + "nome=" + nome + "]\n";
        }
    
}
