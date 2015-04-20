package calculadora;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ThreadCalculadora extends Thread {

	private String calculo;
	private String id;
	private String caminhoArquivo;
	private String status;

	public ThreadCalculadora(String calculo, String id, String caminhoArquivo) {
		this.calculo = calculo;
		this.id = id;
		this.caminhoArquivo = caminhoArquivo;
	}

	@Override
	public void run() {
		verificarOperacao();
	}

	public void verificarOperacao() {
		int resultado = 0;
		if (calculo.contains("+")) {
			resultado = somar();
		} else if (calculo.contains("-")) {
			resultado = subtrair();
		} else if (calculo.contains("*")) {
			resultado = multiplicar();
		} else if (calculo.contains("/")) {
			resultado = dividir();
		} else {
			status = "404";
		}

		try {
			salvarDados(resultado);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int somar() {
		String numeros[] = calculo.split("\\+");
		int soma = 0;
		if (validarTamanhoArray(numeros)) {
			for (int i = 0; i < numeros.length; i++) {
				if (validarDados(numeros[i])) {
					soma += Integer.parseInt(numeros[i]);
				}
			}
		}
		return soma;
	}

	public int subtrair() {
		String numeros[] = calculo.split("\\-");
		int subtracao = 0;
		if (validarTamanhoArray(numeros)) {
			for (int i = 0; i < numeros.length; i++) {
				if (validarDados(numeros[i])) {
					subtracao -= Integer.parseInt(numeros[i]);
				}
			}
		}
		return subtracao;
	}

	public int multiplicar() {
		String numeros[] = calculo.split("\\*");
		int multiplicacao = 1;
		if (validarTamanhoArray(numeros)) {
			for (int i = 0; i < numeros.length; i++) {
				if (validarDados(numeros[i])) {
					multiplicacao *= Integer.parseInt(numeros[i]);
				}
			}
		}
		return multiplicacao;
	}

	public int dividir() {
		String numeros[] = calculo.split("\\/");
		int div = 0;
		if (numeros[0] != null && !numeros[0].isEmpty()) {
			div = Integer.parseInt(numeros[0]);
			for (int i = 1; i < numeros.length; i++) {
				if (validarDados(numeros[i])) {
					if (numeros[i].equals("0")) {
						status = "506";
					} else {
						div /= Integer.parseInt(numeros[i]);
					}
				}
			}
		} else {
			status = "404";
		}
		return div;
	}

	public boolean validarDados(String valor) {
		boolean verificador = false;

		if (!valor.trim().isEmpty()) {
			verificador = true;
		} else {
			status = "404";
		}
		return verificador;
	}

	public boolean validarTamanhoArray(String dados[]) {
		boolean resultado = false;
		if (dados.length >= 2) {
			resultado = true;
		} else {
			status = "404";
		}
		return resultado;
	}

	public void salvarDados(int resultado) throws IOException {
		String texto = id + ":" + status + ":" + resultado;
		File fi = new File(caminhoArquivo);
		if (!fi.exists()) {
			fi.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(fi, true);
		out.write(texto.getBytes());
		out.flush();
		out.close();
	}

}
