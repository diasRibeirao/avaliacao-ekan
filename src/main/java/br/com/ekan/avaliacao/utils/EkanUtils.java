package br.com.ekan.avaliacao.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária com métodos úteis para operações comuns.
 */
public class EkanUtils {

	EkanUtils() {

	}

	/**
	 * Remove todos os caracteres não numéricos de uma string.
	 * @param valor A string a ser limpa.
	 * @return A string contendo apenas os caracteres numéricos.
	 */
	public static String somenteNumeros(String valor) {
		if (valor == null) return "";
		return valor.replaceAll("\\D", "");
	}

	/**
	 * Verifica se um número de telefone é válido.
	 * @param telefone O número de telefone a ser verificado.
	 * @return true se o número de telefone for válido, caso contrário false.
	 */
	public static boolean isTelefoneValido(String telefone) {
		return telefone != null && telefone.matches("\\d{10,11}");
	}

	/**
	 * Formata um CPF.
	 * @param cpf O CPF a ser formatado.
	 * @return O CPF formatado.
	 */
	public static String formatarCPF(String cpf) {
		cpf = somenteNumeros(cpf);
		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}

	/**
	 * Verifica se um CPF é válido.
	 * @param cpf O CPF a ser verificado.
	 * @return true se o CPF for válido, caso contrário false.
	 */
	public static boolean isCpfValido(String cpf) {
		cpf = somenteNumeros(cpf);
		if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
			return false;
		}

		// Verificação dos dígitos verificadores
		return verificarDigito(cpf, 9) && verificarDigito(cpf, 10);
	}

	private static boolean verificarDigito(String cpf, int posicao) {
		int soma = 0;
		int peso = posicao + 1;
		for (int i = 0; i < posicao; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
		}

		int digito = 11 - (soma % 11);
		if (digito >= 10) {
			digito = 0;
		}

		return Character.getNumericValue(cpf.charAt(posicao)) == digito;
	}

	/**
	 * Verifica se um CNPJ é válido.
	 * @param cnpj O CNPJ a ser verificado.
	 * @return true se o CNPJ for válido, caso contrário false.
	 */
	public static boolean isCnpjValido(String cnpj) {
		cnpj = somenteNumeros(cnpj);
		if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
			return false;
		}

		// Verificação dos dígitos verificadores
		return verificarDigitoCnpj(cnpj, 12) && verificarDigitoCnpj(cnpj, 13);
	}

	private static boolean verificarDigitoCnpj(String cnpj, int posicao) {
		int soma = 0;
		int peso = 2;
		for (int i = posicao - 1; i >= 0; i--) {
			soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
			peso = (peso == 9) ? 2 : peso + 1;
		}

		int digito = 11 - (soma % 11);
		if (digito >= 10) {
			digito = 0;
		}

		return Character.getNumericValue(cnpj.charAt(posicao)) == digito;
	}

	/**
	 * Retorna a data hora atual.
	 * @return A data hora atual.
	 */
	public static LocalDateTime dataAtual() {
		return LocalDateTime.now();
	}

	/**
	 * Retorna a data como LocalDate.
	 * @return A data como LocalDate.
	 */
	public static LocalDate stringToLocalDate(String data) {
		return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
}
