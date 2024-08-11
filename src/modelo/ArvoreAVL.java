package modelo;

import app.OrdemServico;

public class ArvoreAVL {
    private NoAVL raiz;

    private int altura(NoAVL N) {
        if (N == null) return 0;
        return N.altura;
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerdo;
        NoAVL T2 = x.direito;

        x.direito = y;
        y.esquerdo = T2;

        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;
        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;

        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direito;
        NoAVL T2 = y.esquerdo;

        y.esquerdo = x;
        x.direito = T2;

        x.altura = Math.max(altura(x.esquerdo), altura(x.direito)) + 1;
        y.altura = Math.max(altura(y.esquerdo), altura(y.direito)) + 1;

        return y;
    }

    private int getBalanceamento(NoAVL N) {
        if (N == null) return 0;
        return altura(N.esquerdo) - altura(N.direito);
    }

    public void inserir(OrdemServico dado) {
        raiz = inserir(raiz, dado);
    }

    private NoAVL inserir(NoAVL nodo, OrdemServico dado) {
        if (nodo == null) return new NoAVL(dado);

        if (dado.getCodigo() < nodo.dado.getCodigo()) {
            nodo.esquerdo = inserir(nodo.esquerdo, dado);
        } else if (dado.getCodigo() > nodo.dado.getCodigo()) {
            nodo.direito = inserir(nodo.direito, dado);
        } else {
            return nodo;
        }

        nodo.altura = 1 + Math.max(altura(nodo.esquerdo), altura(nodo.direito));

        int balance = getBalanceamento(nodo);

        // Caso Esquerdo-Esquerdo
        if (balance > 1 && dado.getCodigo() < nodo.esquerdo.dado.getCodigo()) {
            return rotacaoDireita(nodo);
        }

        // Caso Direito-Direito
        if (balance < -1 && dado.getCodigo() > nodo.direito.dado.getCodigo()) {
            return rotacaoEsquerda(nodo);
        }

        // Caso Esquerdo-Direito
        if (balance > 1 && dado.getCodigo() > nodo.esquerdo.dado.getCodigo()) {
            nodo.esquerdo = rotacaoEsquerda(nodo.esquerdo);
            return rotacaoDireita(nodo);
        }

        // Caso Direito-Esquerdo
        if (balance < -1 && dado.getCodigo() < nodo.direito.dado.getCodigo()) {
            nodo.direito = rotacaoDireita(nodo.direito);
            return rotacaoEsquerda(nodo);
        }

        return nodo;
    }

    public void remover(int codigo) {
        raiz = removerNodo(raiz, codigo);
    }

    private NoAVL removerNodo(NoAVL raiz, int codigo) {
        if (raiz == null) return raiz;

        if (codigo < raiz.dado.getCodigo()) {
            raiz.esquerdo = removerNodo(raiz.esquerdo, codigo);
        } else if (codigo > raiz.dado.getCodigo()) {
            raiz.direito = removerNodo(raiz.direito, codigo);
        } else {
            if ((raiz.esquerdo == null) || (raiz.direito == null)) {
                NoAVL temp = null;
                if (temp == raiz.esquerdo) temp = raiz.direito;
                else temp = raiz.esquerdo;

                if (temp == null) {
                    temp = raiz;
                    raiz = null;
                } else raiz = temp;
            } else {
                NoAVL temp = minValorNodo(raiz.direito);
                raiz.dado = temp.dado;
                raiz.direito = removerNodo(raiz.direito, temp.dado.getCodigo());
            }
        }

        if (raiz == null) return raiz;

        raiz.altura = Math.max(altura(raiz.esquerdo), altura(raiz.direito)) + 1;

        int balance = getBalanceamento(raiz);

        if (balance > 1 && getBalanceamento(raiz.esquerdo) >= 0) {
            return rotacaoDireita(raiz);
        }

        if (balance > 1 && getBalanceamento(raiz.esquerdo) < 0) {
            raiz.esquerdo = rotacaoEsquerda(raiz.esquerdo);
            return rotacaoDireita(raiz);
        }

        if (balance < -1 && getBalanceamento(raiz.direito) <= 0) {
            return rotacaoEsquerda(raiz);
        }

        if (balance < -1 && getBalanceamento(raiz.direito) > 0) {
            raiz.direito = rotacaoDireita(raiz.direito);
            return rotacaoEsquerda(raiz);
        }

        return raiz;
    }

    private NoAVL minValorNodo(NoAVL nodo) {
        NoAVL atual = nodo;
        while (atual.esquerdo != null) atual = atual.esquerdo;
        return atual;
    }

    public OrdemServico buscar(int codigo) {
        return buscar(raiz, codigo);
    }

    private OrdemServico buscar(NoAVL nodo, int codigo) {
        if (nodo == null) return null;
        if (nodo.dado.getCodigo() == codigo) return nodo.dado;
        if (nodo.dado.getCodigo() > codigo) return buscar(nodo.esquerdo, codigo);
        return buscar(nodo.direito, codigo);
    }

    public void emOrdem() {
        emOrdem(raiz);
    }

    private void emOrdem(NoAVL nodo) {
        if (nodo != null) {
            emOrdem(nodo.esquerdo);
            System.out.println(nodo.dado);
            emOrdem(nodo.direito);
        }
    }

    public int getAltura() {
        return altura(raiz);
    }

	public String getQuantidade() {
		return String.valueOf(contarNos(raiz));
	}

	private int contarNos(NoAVL nodo) {
		if (nodo == null) {
			return 0;
		}
		return 1 + contarNos(nodo.esquerdo) + contarNos(nodo.direito);
	}
}
