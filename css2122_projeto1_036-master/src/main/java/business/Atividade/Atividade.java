package business.Atividade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.Especialidade.Especialidade;



@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@NamedQueries({
@NamedQuery(name=Atividade.FIND_BY_DESIGNATION, query="SELECT a FROM Atividade a WHERE a.designacao = :" + Atividade.ATIVIDADE_DESIGNATION),
@NamedQuery(name=Atividade.LISTA_ATIVIDADES_REGULARES, query="SELECT a FROM Atividade a WHERE a.dtype = 'Atividade_Regular'"),
@NamedQuery(name=Atividade.GET_BY_DESIGNACAO_ATIVIDADES_REGULARES, query="SELECT a FROM Atividade a WHERE a.dtype = 'Atividade_Regular' AND a.designacao = :" + Atividade.ATIVIDADE_DESIGNATION),
@NamedQuery(name=Atividade.LISTA_ATIVIDADE_OCASIONAIS, query = "SELECT a FROM Atividade a WHERE a.dtype = 'Atividade_Ocasional' ORDER BY a.preco ASC"),
@NamedQuery(name=Atividade.FIND_BY_ESPECIALIDADE, query = "SELECT a FROM Atividade a Where a.especialidade = :" + Atividade.ATIVIDADE_ESPECIALIDADE)
})
public class Atividade {
	public static final String FIND_BY_ESPECIALIDADE = "Atividade.findByEspecialidade";
	public static final String ATIVIDADE_ESPECIALIDADE = "especialidade";
	
	public static final String GET_BY_DESIGNACAO_ATIVIDADES_REGULARES = "Atividade.RegularesDesignacao";
	
	public static final String LISTA_ATIVIDADES_REGULARES = "Atividade.Regulares";
	public static final String LISTA_ATIVIDADE_OCASIONAIS = "Atividade.Ocasionais";

	public static final String FIND_BY_DESIGNATION = "Atividade.findByDesignation";
	public static final String ATIVIDADE_DESIGNATION = "designacao";
	
	@Column(insertable = false, updatable = false) 
	private String dtype;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn (nullable = false)
	private String designacao;
	
	@JoinColumn (nullable = false)
	private double preco;
	
	@JoinColumn (nullable = false)
	private int duracao;
	
	@JoinColumn (nullable = false)
	private Especialidade especialidade;
	
	//private boolean regular;
	
	public Atividade() {
		
	}
	
	//Criar uma noca atividade
	public Atividade(Especialidade especialidade, String designacao, double preco, int duracao) {
		this.designacao = designacao;
		this.preco = preco;
		this.duracao = duracao;
		this.especialidade = especialidade;
	}
	
	public int getId() {
		return this.id;
	}

	public double getPreco() {
		return this.preco;
	}

	public String getDesignacao() {
		return this.designacao;
	}
	
	public Especialidade getEspecialidade() {
		return this.especialidade;
	}
	
	public int getDuracao() {
		return this.duracao;
	}

 //maybe um toString com a info da atividade.
}
