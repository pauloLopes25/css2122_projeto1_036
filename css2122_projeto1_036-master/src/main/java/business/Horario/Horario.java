package business.Horario;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import business.Instrutor.Instrutor;
import business.Sessao.Sessao;

@Entity
@NamedQuery(name=Horario.GET_BY_ID, query="SELECT h FROM Horario h WHERE h.id = :" + Horario.ID)
public class Horario {
	public static final String ID = "id";
	public static final String GET_BY_ID = "Horario.findById";

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Date DataInicio;
	private int NMesesInstrutor;
	private int NMeses;
	private List<Sessao> listaSessao;
	private Instrutor instrutor;
	
	public Horario() {
		
	}

	public Horario(Date DataInicio,int NMesesInstrutor, Instrutor instrutor, int NMeses, List<Sessao> listaSessao) {
		this.DataInicio=DataInicio;
		this.NMesesInstrutor=NMesesInstrutor;
		this.NMeses=NMeses;
		this.listaSessao=listaSessao;
		this.instrutor=instrutor;
		
	}
	
	public Date getDataInicio() {
		return this.DataInicio;
	}
	
	public int getNMesesInstrutor() {
		return this.NMesesInstrutor;
	}
	
	public int getNMeses() {
		return this.NMeses;
	}
	
	public List<Sessao> getListaSessao() {
		return this.listaSessao;
	}

	public Instrutor getInstrutor() {
		return this.instrutor;
	}
}