package facade.exceptions;

public class InstrutorNotFoundException extends ApplicationException{
	
	
	private static final long serialVersionUID = -3984382774372236951L;

	/**
	 * Cria uma excecao dado uma msg de erro
	 * @param msg msg de erro dada
	 */
	public InstrutorNotFoundException(String msg) {
		super(msg);
	}
	
	/**
	 * Cria uma exceçao dado um wrapping 
	 * @param msg Msg de erro
	 * @param e excecao wrapped
	 */
	public InstrutorNotFoundException(String msg, Exception e) {
		super(msg, e);
	}
	
}
