package business.Instrutor;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.Certificado.Certificado;


/**
 *Entity para a implementacao do Instrutor
 */
@Entity
@NamedQueries({
@NamedQuery(name = Instrutor.FIND_BY_NUM, query = "SELECT i FROM Instrutor i WHERE i.num = :" + Instrutor.INSTRUTOR_NUM),
@NamedQuery(name = Instrutor.GET_LIST, query = "SELECT i FROM Instrutor i")
})
public class Instrutor {
	
	public static final String FIND_BY_NUM = "Instrutor.findById";
	public static final String INSTRUTOR_NUM = "Instrutor";
	
	public static final String GET_LIST = "Instrutor.getList";
	
	@Id
	private int num;
	@Column (nullable = false, unique = true)
	private String nome;
	//private Certificado cert;
	@Column (nullable = false, unique = false) @Enumerated(EnumType.STRING)
	private List<Certificado> listCert;
	
	//Construtor necessario para o modelo JPA
	public Instrutor() {//Do Nothing
	}
	
	
	//Criar um novo instrutor
	public Instrutor(int num, String nome, List<Certificado> certfs) {  
		this.num = num;
		this.nome = nome;
		this.listCert = certfs;
	}
	
	
	/**
	 *
	 * @return Devolve o nome do instrutor
	 */
	public String getNome() {
		return this.nome;
	}
	
	
	/**
	 * 
	 * @return devolve a indentifica��o do Instrutor
	 */
	public int getId() {
		return this.num;
	}
	
	/**
	 * 
	 * @return Devolve o tipo de Certificado que o instrutor possui
	 */
	public Certificado getCertificado(Certificado cert) {	
		return this.listCert.contains(cert) ? this.listCert.get(this.listCert.indexOf(cert)) : null;
	}
	
	/**
	 * 
	 * @return Devolve uma lista dos certificados que o instrutor possui
	 */
	public List<Certificado> getCertificados(){
		return this.listCert;
	}
	
	
	/**
	 * 
	 * @return Devolve a informacao do instrutor
	 */
	public String InstrutorToString() {
		return "nome: " + this.nome + "\nId de registro: " + this.num + "\nCertificado(s) : " + this.listCert;
	}	
}
