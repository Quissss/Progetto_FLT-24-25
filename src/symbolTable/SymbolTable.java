package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {

	public static class Attributes {

		private LangType tipo;
		private String nome;
		private char registro;

		public Attributes(LangType tipo, String nome) {
			this.tipo = tipo;
			this.nome = nome;
		}
		
		/**
		 * Restituisce il tipo dell'attributo.
		 * 
		 * @return il tipo dell'attributo
		 */
		public LangType getType() {
			return tipo;
		}

		/**
		 * Restituisce il nome dell'attributo.
		 * 
		 * @return il nome dell'attributo
		 */
		public String getName() {
			return nome;
		}

		/**
		 * Restituisce il registro associato all'attributo.
		 * 
		 * @return il registro
		 */
		public char getRegister() {
			return registro;
		}

		/**
		 * Imposta il registro dell'attributo.
		 * 
		 * @param registro il registro da impostare
		 */
		public void setRegister(char registro) {
			this.registro = registro;
		}

	}

	private static HashMap<String, Attributes> symbolTable;

	public static void init() {
		symbolTable = new HashMap<>();
	}

	public static boolean enter(String id, Attributes entry) {
		if (symbolTable.containsKey(id))
			return false;
		symbolTable.put(id, entry);
		return true;
	}

	public static Attributes lookup(String id) {
		return symbolTable.get(id);
	}

	public static String toStr() {
		String str = "";

		for (var e : symbolTable.entrySet()) {
			str += "ID: " + e.getKey() + ", Tipo: " + e.getValue().getType() + "\n";
		}
		return str;
	}

	public static int size() {
		return symbolTable.size();
	}

}
