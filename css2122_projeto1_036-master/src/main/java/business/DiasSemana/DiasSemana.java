package business.DiasSemana;

	
	/**
	 * Todos os tipos de certificados
	 */
	public enum DiasSemana {
	    SEGUNDA("Segunda-feira"), 
	    TERCA("Terca-feira"), 
	    QUARTA("Quarta-feira"), 
	    QUINTA("Quinta-feira"),
	    SEXTA("Sexta-feira"), 
	    SABADO("Sabado"), 
	    DOMINGO("Domingo");	 
	    private String DiaSemana;
	 
	    DiasSemana(String dia) {
	        this.DiaSemana = dia;
	    }
	 
	    public String getString() {
	        return DiaSemana;
	    }
	

}