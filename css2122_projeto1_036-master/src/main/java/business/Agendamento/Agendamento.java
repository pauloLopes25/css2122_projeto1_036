package business.Agendamento;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.Atividade.Atividade;
import business.Pagamento.Pagamento;
import business.Sessao.Sessao;

@Entity
@NamedQueries({
@NamedQuery(name=Agendamento.LISTA_AGENDAMENTOS, query="SELECT e FROM Agendamento e "),
})
public class Agendamento {
	
	public static final String LISTA_AGENDAMENTOS = "Agendamento.listaAgendamentos";
	
	@Column(nullable = false, unique = false)
	private String email;
	@JoinColumn(nullable = false, unique = false)
	private Atividade atividade;
	@JoinColumn(nullable = false, unique = true)
	private Pagamento pagamento;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(nullable = false, unique = false)    @ManyToMany(fetch = FetchType.EAGER)
	private List<Sessao> listaSessoes;

	public Agendamento() {
	}
	
	public Agendamento(String email,Atividade atividade,Pagamento pagamento, List<Sessao> listaSessoes) {
		this.email = email;
		this.atividade = atividade;
		this.pagamento = pagamento;
		this.listaSessoes = listaSessoes;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Atividade getAtividade() {
		return this.atividade;
	}
	
	public Pagamento getPagamento() {
		return this.pagamento;
	}
	
	public List<Sessao> getListaSessao(){
		return this.listaSessoes;
	}
	
}
