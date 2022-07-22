package business.handlers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import business.Atividade.Atividade;
import business.Atividade.Atividade_Ocasional;
import business.Atividade.Atividade_Regular;
import business.Especialidade.Especialidade;
import business.Especialidade.EspecialidadeCatalog;
import facade.exceptions.ApplicationException;

public class CriarAtividadeHandler {

	private EntityManagerFactory emf;

	public CriarAtividadeHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public List<String> listaEspecialidades() throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		List<String> lista;
		try{
			lista = new EspecialidadeCatalog(em).getListaEspecialidades();
		}catch (Exception e) {
			em.close();
			throw e;
		}
		return lista;
	}
	
	private boolean checkAtividade(String designacao) {
		EntityManager em = emf.createEntityManager();
		try{	
		TypedQuery<Atividade> query = em.createNamedQuery(Atividade.FIND_BY_DESIGNATION, Atividade.class);
		query.setParameter(Atividade.ATIVIDADE_DESIGNATION, designacao);
		query.getSingleResult();
		em.close();
		return true;
		}catch (Exception e) {
			em.close();
			return false;
		}
	}

	public void criarAtividade(String especialidade, String designacao, boolean regular, int nSessoes, int preco, int duracao) throws ApplicationException {
		if(checkAtividade(designacao)) {
			throw new ApplicationException("Atividade ja existe na base de dados");// TODO: handle exception
		}
		StringBuilder sb = new StringBuilder(designacao);
		EntityManager em = emf.createEntityManager();
		List<String> listaEspecialidades = listaEspecialidades();
		if(!listaEspecialidades.contains(especialidade)) {
			em.close();
			throw new ApplicationException ("Especialidade " + especialidade + " nao consta na lista.");
		}
		for(int i =0;i<designacao.length();i++) {				
			if(!Character.isLetterOrDigit(sb.charAt(i)) && sb.charAt(i)!=' ') {
				em.close();
				throw new ApplicationException ("Especialidade " + designacao + " contem caracteres nao alfanumericos.");
			}
		}
		if(!Character.isLetter(sb.charAt(0))) {
			em.close();
			throw new ApplicationException ("Especialidade " + designacao + " nao comeca com uma letra");
		}
		if( (regular && (nSessoes>5 || nSessoes<1) ) || (!regular && (nSessoes>20 || nSessoes<1) ) ) {
			em.close();
			throw new ApplicationException ("Atividade nao respeita o numero maximo de sessoes por semana/mes");
		}
		if(duracao<=0) {
			em.close();
			throw new ApplicationException ("Duracao da atividade nao pode ser menor ou igual a zero");
		}
		if(preco<=0) {
			em.close();
			throw new ApplicationException ("Preco da atividade nao pode ser menor ou igual a zero");
		}
		if(regular) {
			em.close();
			throw new ApplicationException ("Atividades regulares devem ter numero maximo de participantes");		
		}
		EspecialidadeCatalog EspCat = new EspecialidadeCatalog(em);
		Especialidade dados = EspCat.getEspecialidade(especialidade);
		if(dados.GetDuracao()>duracao) {
			em.close();
			throw new ApplicationException ("Duracao minima da sessao nao cumprida");					
		}	
		Especialidade esp = new EspecialidadeCatalog(em).getEspecialidade(especialidade);
		Atividade_Ocasional atividade = new Atividade_Ocasional(esp, designacao, preco, duracao, nSessoes);
		em.getTransaction().begin();
		try {
		em.persist(atividade);
		em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			throw new ApplicationException("Erro no registo da atividade na base de dados");// TODO: handle exception
		}finally {
			em.close();
		}
	}
	
	public void criarAtividade(String especialidade, String designacao, boolean regular, int nSessoes, int preco, int duracao,int nMaxParticipantes) throws ApplicationException {
		if(checkAtividade(designacao)) {
			throw new ApplicationException("Atividade ja existe na base de dados");// TODO: handle exception
		}
		StringBuilder sb = new StringBuilder(designacao);
		EntityManager em = emf.createEntityManager();
		List<String> listaEspecialidades = listaEspecialidades();
		
		if(!listaEspecialidades.contains(especialidade)) {
			em.close();
			throw new ApplicationException ("Especialidade " + especialidade + " nao consta na lista.");
		}
		for(int i =0;i<designacao.length();i++) {
				
			if(!Character.isLetterOrDigit(sb.charAt(i)) && sb.charAt(i)!=' ') {
				em.close();
				throw new ApplicationException ("Especialidade " + designacao + " contem caracteres nao alfanumericos.");
			}
		}
		if(!Character.isLetter(sb.charAt(0))) {
			em.close();
			throw new ApplicationException ("Especialidade " + designacao + " nao comeca com uma letra");
		}
		if( (regular && (nSessoes>5 || nSessoes<1) ) || (!regular && (nSessoes>20 || nSessoes<1) ) ) {
			em.close();
			throw new ApplicationException ("Atividade nao respeita o numero maximo de sessoes por semana/mes");
		}
		if(duracao<=0) {
			em.close();
			throw new ApplicationException ("Duracao da atividade nao pode ser menor ou igual a zero");
		}
		if(preco<=0) {
			em.close();
			throw new ApplicationException ("Preco da atividade nao pode ser menor ou igual a zero");
		}
		if(regular && nMaxParticipantes<=0) {
			em.close();
			throw new ApplicationException ("Numero maximo de participantes numa ativade regular deve ser superior a zero");
		}
		EspecialidadeCatalog EspCat = new EspecialidadeCatalog(em);
		Especialidade dados = EspCat.getEspecialidade(especialidade);
		if(dados.GetDuracao()>duracao) {
			em.close();
			throw new ApplicationException ("Duracao minima da sessao nao cumprida");					
		}
		Especialidade esp = new EspecialidadeCatalog(em).getEspecialidade(especialidade);

		if(regular) {
			Atividade_Regular atividade = new Atividade_Regular(esp, designacao, preco, duracao,nSessoes,nMaxParticipantes);
			em.getTransaction().begin();
			try {
			em.persist(atividade);
			em.getTransaction().commit();
			}catch (Exception e) {
				em.getTransaction().rollback();
				throw new ApplicationException("Erro no registo da atividade na base de dados");// TODO: handle exception
			}finally {
				em.close();
			}
		}else {
			Atividade_Ocasional atividade = new Atividade_Ocasional(esp, designacao, preco, duracao, nSessoes);
			em.getTransaction().begin();
			try {
			em.persist(atividade);
			em.getTransaction().commit();
			}catch (Exception e) {
				em.getTransaction().rollback();
				throw new ApplicationException("Erro no registo da atividade na base de dados");// TODO: handle exception
			}finally {
				em.close();
			}
		}
	}
}