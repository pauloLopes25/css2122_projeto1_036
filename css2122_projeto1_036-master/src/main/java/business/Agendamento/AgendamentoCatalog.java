package business.Agendamento;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.Atividade.Atividade;
import business.Pagamento.Pagamento;
import business.Sessao.Sessao;
import facade.exceptions.ApplicationException;

public class AgendamentoCatalog {

	private EntityManager em;


	public AgendamentoCatalog(EntityManager em) {
		this.em = em;
	}

	public void addAgendamento (String email,Atividade atividade,Pagamento pagamento, List<Sessao> listaSessoes) {
		Agendamento agendamento = new Agendamento(email,atividade,pagamento,listaSessoes);
		em.getTransaction().begin();
		try {
			em.persist(agendamento);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}

	}

	public List<Agendamento> getListaAgendamentos()  throws ApplicationException{
		TypedQuery<Agendamento> query = em.createNamedQuery(Agendamento.LISTA_AGENDAMENTOS, Agendamento.class);
		return query.getResultList();
	}



}
