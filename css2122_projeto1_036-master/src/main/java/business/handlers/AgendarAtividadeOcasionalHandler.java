package business.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import business.Agendamento.Agendamento;
import business.Atividade.Atividade;
import business.Atividade.Atividade_Ocasional;
import business.DiasSemana.DiasSemana;
import business.Instrutor.Instrutor;
import business.Instrutor.InstrutorCatalog;
import business.Pagamento.Pagamento;
import business.Sessao.Sessao;
import client.DataCorrente;
import facade.exceptions.ApplicationException;


public class AgendarAtividadeOcasionalHandler {

	private EntityManagerFactory emf;

	public AgendarAtividadeOcasionalHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public List<String> agendarAtividadeOcasional(String especialidade) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		//InstrutorCatalog catalogoInstrutor = new InstrutorCatalog(em);
		List<Atividade_Ocasional> listaAtividades;
		try {
			TypedQuery<Atividade_Ocasional> query = em.createNamedQuery(Atividade.LISTA_ATIVIDADE_OCASIONAIS, Atividade_Ocasional.class);
			listaAtividades = query.getResultList();
		}catch (Exception e) {
			em.close();
			throw new ApplicationException("Especialidade fornecida nao existe");
		}
		List<String> lista = new ArrayList<String>();
		for(int i =0;i<listaAtividades.size();i++) {
			if(listaAtividades.get(i).getEspecialidade().GetDesignacao().equals(especialidade)) {

				lista.add("Designacao: "+listaAtividades.get(i).getDesignacao()+ " Duracao: "+listaAtividades.get(i).getDuracao()+ " Preco: "+listaAtividades.get(i).getPreco());
			}
		}
		return lista;
		
	}

	public String AgendarAtividadeOcasional(String designacao, List<Date> listaDatas) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		StringBuilder sb = new StringBuilder();
		
		InstrutorCatalog catalogoInstrutor = new InstrutorCatalog(em);
		
		List<Instrutor> listaInstrutores = catalogoInstrutor.getInstrutores();
		Atividade_Ocasional atividadeOcasional;
		try {
			TypedQuery<Atividade_Ocasional> query = em.createNamedQuery(Atividade.FIND_BY_DESIGNATION, Atividade_Ocasional.class);
			query.setParameter(Atividade.ATIVIDADE_DESIGNATION, designacao);
			atividadeOcasional = query.getSingleResult();
		}catch (Exception e) {
			em.close();
			throw new ApplicationException("Especialidade fornecida nao existe");
		}
		
		for(int i =0;i<listaInstrutores.size();i++) {
			if(!listaInstrutores.get(i).getCertificados().contains(atividadeOcasional.getEspecialidade().GetCertificacao())) {
				listaInstrutores.remove(i);
			}
		}
		
		List<Sessao> sessoes;
		for(int i=0;i<listaInstrutores.size();i++) {
			try {
				TypedQuery<Sessao> query = em.createNamedQuery(Sessao.FIND_BY_INSTRUTOR, Sessao.class);
				query.setParameter(Sessao.INSTRUTOR_NUM, listaInstrutores.get(i).getId());
				sessoes = query.getResultList();
				for(int j =0;j<sessoes.size();j++) {
					for(int dia=0;dia<listaDatas.size();dia++) {
						
						Calendar dataFim = Calendar.getInstance();
						
						dataFim.setTime(listaDatas.get(dia));
						dataFim.add(Calendar.MINUTE, atividadeOcasional.getDuracao());
						
						if(!( sessoes.get(j).getHoraInit().before(listaDatas.get(dia)) && sessoes.get(j).getHoraFim().before(dataFim.getTime()) ) || 
								!( sessoes.get(j).getHoraInit().after(listaDatas.get(dia)) && sessoes.get(j).getHoraFim().after(dataFim.getTime() ) )) {
							throw new ApplicationException("");
						}
					}
				}
			sb.append("Instrutor: "+listaInstrutores.get(i).getNome() +" id: "+listaInstrutores.get(i).getId()+"\n");
			}catch (Exception e) {
			}
		}
		return sb.toString();
	}
	
	private DiasSemana getDiaSemana(int dia) {
        if(dia==2)
            return DiasSemana.SEGUNDA;
        else if(dia==3)
            return DiasSemana.TERCA;
        else if(dia==4)
            return DiasSemana.QUARTA;
        else if(dia==5)
            return DiasSemana.QUINTA;
        else if(dia==6)
            return DiasSemana.SEXTA;
        else if(dia==7)
            return DiasSemana.SABADO;
        else
            return DiasSemana.DOMINGO;
    }

	public String comfirmarAtividadeOcasional(int instrutorId, String email, List<Date> listaHorasDatas, String designacao) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		InstrutorCatalog catalogoInstrutor = new InstrutorCatalog(em);
		for(int i =0;i<listaHorasDatas.size();i++) {
			for(int j =0;j<listaHorasDatas.size();j++) {
				if(i!=j && listaHorasDatas.get(i).equals(listaHorasDatas.get(j)))
					throw new ApplicationException("Repeticao de horarios");
			}
		}
		Atividade_Ocasional atividadeOcasional;
		try {
			TypedQuery<Atividade_Ocasional> query = em.createNamedQuery(Atividade.FIND_BY_DESIGNATION, Atividade_Ocasional.class);
			query.setParameter(Atividade.ATIVIDADE_DESIGNATION, designacao);
			atividadeOcasional = query.getSingleResult();
		}catch (Exception e) {
			em.close();
			throw new ApplicationException("Especialidade fornecida nao existe");
		}
		Instrutor instrutor = catalogoInstrutor.getInstrutor(instrutorId);

		List<Sessao> sessoes = new ArrayList<Sessao>();
		for(int i =0;i<listaHorasDatas.size();i++) {
			
			Calendar dia = Calendar.getInstance();
			dia.setTime(listaHorasDatas.get(i));
			Calendar diaFim = Calendar.getInstance();
			diaFim.setTime(listaHorasDatas.get(i));
			diaFim.add(Calendar.MINUTE, atividadeOcasional.getDuracao());
			
			Sessao sessao = new Sessao(instrutor, getDiaSemana(dia.get(Calendar.DAY_OF_WEEK)), listaHorasDatas.get(i),(Date) diaFim.getTime().clone());
			sessoes.add(sessao);
			
			em.getTransaction().begin();
			try {
				em.persist(sessao);
				em.getTransaction().commit();
			}catch (Exception e) {
				em.getTransaction().rollback();
				em.close();
				throw new ApplicationException("Erro numa transassao.");
			}
			
			atividadeOcasional.getListaSessoes().add(sessao);
		}
		
		em.getTransaction().begin();
		try {
			em.persist(atividadeOcasional);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
			throw new ApplicationException("Erro numa transassao.");
		}
		
		double precoAtividade = atividadeOcasional.getPreco();

		Calendar dataPagamento = Calendar.getInstance();
		dataPagamento.setTime(DataCorrente.get());
		dataPagamento.add(Calendar.DATE, 1);

		Random ref = new Random();
		int referencia = ref.nextInt(999999);

		Pagamento detalhesPagamento = new Pagamento(12345, referencia, precoAtividade, dataPagamento.getTime());
		em.getTransaction().begin();
		try {
			em.persist(detalhesPagamento);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
			throw new ApplicationException("Erro no registo do pagamento");
		}
		
		Agendamento agendamento = new Agendamento(email, atividadeOcasional, detalhesPagamento, sessoes);

		em.getTransaction().begin();
		try {
			em.persist(agendamento);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			throw new ApplicationException("Erro no registo do agendamento");
		}finally {
			em.close();
		}
		return detalhesPagamento.toString();
		
		
	}

}
