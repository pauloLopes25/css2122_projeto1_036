package business.Instrutor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

public class InstrutorCatalog {

	//Entity manager para aceder ao servico de persistencia
	private EntityManager em;

	/**
	 * 
	 * @param em
	 */
	public InstrutorCatalog(EntityManager em) {
		this.em = em;
	}

	/**
	 * 
	 * @param instructorID
	 * @return
	 */
	public Instrutor getInstrutor(int instructorID) throws ApplicationException{ 
		try {
		TypedQuery<Instrutor> query = em.createNamedQuery(Instrutor.FIND_BY_NUM, Instrutor.class);
		query.setParameter(Instrutor.INSTRUTOR_NUM, instructorID);
		return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("Nao foi possivel encontrar o instrutor com o id: " + instructorID);
		}	
	}
	
	public List<Instrutor> getInstrutores() throws ApplicationException{
		try {
			TypedQuery<Instrutor> query = em.createNamedQuery(Instrutor.GET_LIST, Instrutor.class);
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new ApplicationException("Nao foi encontrado nenhum instrutor");
		}
	}
}
