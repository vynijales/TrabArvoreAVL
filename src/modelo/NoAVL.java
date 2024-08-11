package modelo;

import app.OrdemServico;

public class NoAVL {

	OrdemServico dado;
	NoAVL esquerdo, direito;
	
	int altura;

	public NoAVL(OrdemServico dado) {
		this.dado = dado;
		this.altura = 1;
	}
}
