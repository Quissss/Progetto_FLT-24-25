package symbolTable;

import java.util.HashMap;

import ast.LangType;

/**
 * Implementa la Sybol Table, che contiene i nomi utilizzati dal programma .
 * 
 * */
public class SymbolTable {

	/**
	 * Implementa gli attributi che saranno inseriti nella tabella
	 */
	public static class Attributes {

		private LangType tipo;
		private String nome;
		private char registro;

		/**
		 * Costruttore dell'attributo.
		 * 
		 * @param tipo     tipo dell'attributo
		 * @param nome     nome dell'attributo
		 */
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
	
	/**
	 * Inizializza l'hashmap della SymbolTable.
	 */
	public static void init() {
		symbolTable = new HashMap<>();
	}

	/**
	 * Inserisce un attributo nella SymbolTable.
	 * 
	 * @param id l'id dell'attributo da inserire all' interno
	 * @param entry l'attributo da inserire all'interno
	 * @return true se l'attributo è stato inserito correttamente, se no ritorna false
	 */
	public static boolean enter(String id, Attributes entry) {
		if (symbolTable.containsKey(id))
			return false;
		symbolTable.put(id, entry);
		return true;
	}

	/**
	 * Restituisce un attributo.
	 * 
	 * @param id   id dell'attributo da cercare
	 * @return attributo cercato
	 */
	public static Attributes lookup(String id) {
		return symbolTable.get(id);
	}

	/**
	 * Restituisce una rappresentazione sotto forma di testo della SymbolTable.
	 * 
	 * @return rappresentazione testuale della SymbolTable
	 */
	public static String toStr() {
		String str = "";

		for (var e : symbolTable.entrySet()) {
			str += "ID: " + e.getKey() + ", Tipo: " + e.getValue().getType() + "\n";
		}
		return str;
	}

	/**
	 * Restituisce la grandezza.
	 * 
	 * @return restituisce la grandezza della SymbolTable
	 */
	public static int size() {
		return symbolTable.size();
	}

}
