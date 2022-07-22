package business.Especialidade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

public class EspecialidadeCatalog {
	/**
	 * Entity manager factory for accessing the persistence service 
	 */
	private EntityManager em;
	/**
	 * 
	 * @return 
	 */
	public EspecialidadeCatalog(EntityManager em) {
		this.em = em;
	}
	
	public Especialidade getEspecialidade (String  designacao) throws ApplicationException {
		try {
			TypedQuery<Especialidade> query = em.createNamedQuery(Especialidade.FIND_BY_DESIGNACAO, Especialidade.class);
			query.setParameter(Especialidade.DESIGNACAO, designacao);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new ApplicationException ("Especialidade " + designacao + " not found.", e);
		}
	}

	public List<String> getListaEspecialidades() throws ApplicationException {
		TypedQuery<String> query = em.createNamedQuery(Especialidade.LISTA_ESPECIALIDADES, String.class);
		return query.getResultList();
	}
}
