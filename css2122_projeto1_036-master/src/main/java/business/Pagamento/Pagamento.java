package business.Pagamento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(name = Pagamento.FIND_BY_REFERENCIA, query = "SELECT r FROM Pagamento r WHERE r.referencia = :" + Pagamento.PAGAMENTO_REFERENCIA )
public class Pagamento {

	public static final String FIND_BY_REFERENCIA = "Pagamento.findByReferencia";
	public static final String PAGAMENTO_REFERENCIA = "Pagamento";
	
	@Column (nullable = false)
	private int entidade;
	
	@Id @Column (nullable = false) @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int referencia;
	
	@Column (nullable = false)
	private double valor;
	
	@Column (nullable = false)
	private Date dataLimite;
	
	public Pagamento(){
		
	}
	
	/**
	 * 
	 * @param ativ
	 * @param entidade
	 * @param referencia
	 * @param dataLimite
	 */
	public Pagamento (int entidade, int referencia, double valor, Date dataLimite) {
		this.valor =valor;
		this.entidade = entidade;
		this.referencia = referencia;
		this.dataLimite = dataLimite;
	}
	
	/**
	 * 
	 * @return entidade
	 */
	public int getEntidade() {
		return this.entidade;
	}

	/**
	 * 
	 * @return referencia para pagamento
	 */
	public int getReferencia() {
		return this.referencia;
	}

	/**
	 * 
	 * @return valor da atividade
	 */
	public double getValor() {
		return this.valor;
	}
	
	/**
	 * 
	 * @return data limite de pagamento
	 */
	public Date getDataLimite() {
		return this.dataLimite;
	}
	
	@Override
	public String toString() {
		return "Entidade:"+ entidade + ", Referencia: " + referencia + ", Valor: " + valor + ", Data Limite: " + dataLimite;
	}
	
}
