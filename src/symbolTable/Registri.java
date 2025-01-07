package symbolTable;

import java.util.ArrayList;

import exception.CodeGeneratorException;

public class Registri {

	static ArrayList<Character> caratteri;

	/**
	 * Inizializza i registri, uno per lettera dell'alfabeto (inglese).
	 */
	public static void inizializza() {
		caratteri = new ArrayList<Character>();
		for (char c = 'a'; c <= 'z'; c++) {
			caratteri.add(c);
		}
	}

	/**
	 * Restituisce un nuovo registro.
	 * 
	 * @return un nuovo registro
	 * @throws CodeGeneratorException se tutti i registri sono in uso
	 */
	public static char newRegister() throws CodeGeneratorException {
		if (caratteri.isEmpty())
			throw new CodeGeneratorException("Numero massimo di registri superato.");
		return caratteri.remove(0);
	}
}
