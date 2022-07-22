package business.Sessao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;

import business.DiasSemana.DiasSemana;
import business.Instrutor.Instrutor;

@Entity
@NamedQuery(name = Sessao.FIND_BY_INSTRUTOR, query = "SELECT s FROM Sessao s WHERE s.instrutor.num = :" + Sessao.INSTRUTOR_NUM)
public class Sessao {
	
	public static final String FIND_BY_INSTRUTOR = "Sessao.findByInstrutorId";
	public static final String INSTRUTOR_NUM = "Instrutor";
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, unique = false)
	private DiasSemana dia;
	@Column(nullable = false, unique = false)
	private Date horaInit;
	@Column(nullable = false, unique = false)
	private Date horaFim;
	@JoinColumn(nullable = true, unique = false)
	private Instrutor instrutor=null;
	
	public Sessao() {
	}
	
	public Sessao(Instrutor instrutor,DiasSemana dia, Date horaInit, Date horaFim) {
		this.instrutor= instrutor;
		this.dia=dia;
		this.horaInit=horaInit;
		this.horaFim=horaFim;
	}
	
	public DiasSemana getDia() {
		return this.dia;
	}
	
	public Date getHoraInit() {
		return this.horaInit;
	}
	
	public Date getHoraFim() {
		return this.horaFim;
	}
	
	public Instrutor getInstrutor() {
		return this.instrutor;
	}
}
