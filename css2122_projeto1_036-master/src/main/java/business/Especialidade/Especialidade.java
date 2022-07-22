package business.Especialidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.Certificado.Certificado;

@Entity
@NamedQueries({
@NamedQuery(name=Especialidade.FIND_BY_DESIGNACAO, query="SELECT p FROM Especialidade p WHERE p.designacao = :" + Especialidade.DESIGNACAO),
@NamedQuery(name=Especialidade.FIND_BY_NUM, query="SELECT p FROM Especialidade p WHERE p.num = :" + Especialidade.NUM),
@NamedQuery(name=Especialidade.LISTA_ESPECIALIDADES, query="SELECT e.designacao FROM Especialidade e ")
})
public class Especialidade {
	public static final String FIND_BY_DESIGNACAO = "Especialidade.findByDesignacao";
	public static final String FIND_BY_NUM = "Especialidade.findByNum";
	public static final String LISTA_ESPECIALIDADES = "Especialidade.listaEspecialidade";
	
	public static final String DESIGNACAO = "designacao";
	public static final String NUM = "num";
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int num;
	@Column(nullable = false, unique = false)
	private String designacao;
	@Column(nullable = false, unique = false)
	private int duracao;
	@Enumerated(EnumType.STRING) 
	private Certificado certificacao;
	
	public Especialidade() {
	}
	
	public Especialidade(int num, String designacao,int duracao,Certificado certificacao) {
		this.num=num;
		this.designacao=designacao;
		this.duracao=duracao;
		this.certificacao=certificacao;
	}
	
	public Certificado GetCertificacao() {
		return this.certificacao;
	}
	
	public String GetDesignacao() {
		return this.designacao;
	}
	
	public int GetNum() {
		return this.num;
	}
	
	public int GetDuracao() {
		return this.duracao;
	}
}
