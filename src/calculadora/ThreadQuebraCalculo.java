package calculadora;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThreadQuebraCalculo extends Thread {

	private String arquivo;
	private String caminhoArquivo;

	public ThreadQuebraCalculo(String arquivo, String caminhoArquivo) {
		this.arquivo = arquivo;
		this.caminhoArquivo = caminhoArquivo;
	}

	@Override
	public void run() {
		lerString();

	}

	public void setarDadosOperacao(String dados) {
		Operacao o = new Operacao();
		if (!dados.trim().isEmpty()) {
			String idOperacao[] = dados.split(":");
			if (validarDados(idOperacao[0])) {
				o.setId(idOperacao[0]);
			}
			if (validarDados(idOperacao[1])) {
				o.setOperacao(idOperacao[1]);
			}
			ThreadCalculadora c = new ThreadCalculadora(o.getOperacao(),
					o.getId(), caminhoArquivo);
			c.start();
		}
	}

	public boolean validarDados(String valor) {
		boolean verificador = false;

		if (!valor.trim().isEmpty()) {
			if (valor != null && !valor.isEmpty()) {
				verificador = true;
			}
		}

		return verificador;
	}

	// Caso tenha que ler um arquivo
	public void lerArquivo() throws IOException {
		String linha = " ";
		BufferedReader br = new BufferedReader(new FileReader(arquivo));
		while ((linha = br.readLine()) != null) {
			String operacao = "";
			if (linha.equals("?")) {
				setarDadosOperacao(operacao);
				operacao = "";
			} else {
				operacao.concat(linha);
			}
		}
	}

	public void lerString() {
		String dados = "";
		for (int i = 0; i < arquivo.length(); i++) {
			if (!validarString(arquivo.charAt(i))) {
				dados.concat(arquivo).charAt(i);
			} else {
				setarDadosOperacao(dados);
				dados = "";
			}
		}
	}

	static boolean validarString(char letra) {
		if (letra == '?') {
			return true;
		}
		return false;
	}
}
