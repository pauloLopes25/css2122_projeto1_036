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
import business.Agendamento.AgendamentoCatalog;
import business.Atividade.Atividade;
import business.Atividade.Atividade_Regular;
import business.Pagamento.Pagamento;
import business.Sessao.Sessao;
import client.DataCorrente;
import facade.exceptions.ApplicationException;

public class ComprarParticipacaoMensalAtividadeRegularHandler {

	private static EntityManagerFactory emf;


	public ComprarParticipacaoMensalAtividadeRegularHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public String comprarParticipacaoMensalAtividadeRegular(String designacao, Date diaInicio, int numHorario, int nMeses, String email) throws ApplicationException {

		EntityManager em = emf.createEntityManager();
		Atividade atividade;
		AgendamentoCatalog agenCat = new AgendamentoCatalog(em);
		List<Agendamento> listaAgendamentos = agenCat.getListaAgendamentos();

		try {
			TypedQuery<Atividade> query = em.createNamedQuery(Atividade.GET_BY_DESIGNACAO_ATIVIDADES_REGULARES, Atividade.class);
			query.setParameter(Atividade.ATIVIDADE_DESIGNATION, designacao);
			atividade = query.getSingleResult();
		}catch (Exception e) {
			em.close();
			throw new ApplicationException("Atividade fornecida nao existe");
		}

		Atividade_Regular atividadeRegular = (Atividade_Regular) atividade;

		List<Sessao> listaSessoes = construirListaDatas(diaInicio, atividadeRegular.getHorario().get(numHorario).getListaSessao(),nMeses);

		if(listaAgendamentos == null) {

		}else {
			int contadorAgendamentos=0;
			for (int i = 0; i < listaAgendamentos.size(); i++) {
				if(listaAgendamentos.get(i).getAtividade().getDesignacao().equals(designacao)) {
					try {
						for(int j = 0;j<listaAgendamentos.get(i).getListaSessao().size();j++) {
							// comparar os valores de sessao e se algum coincidir dar throw mas apanhar e incrementar contadorAgendamentos	
							if(listaSessoes.contains(listaAgendamentos.get(i).getListaSessao().get(j))) {
								throw new ApplicationException("");
							}

						}
					}catch (ApplicationException e) {
						contadorAgendamentos++;
					}
				}
			}

			if(contadorAgendamentos>=atividadeRegular.getNMaxParticipantes()) {
				throw new ApplicationException("Esta sessao atingiu o numero maximo de participantes");
			}

		}

		double precoAtividade = atividade.getPreco() * nMeses;

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

		Agendamento agendamento = new Agendamento(email, atividade, detalhesPagamento, listaSessoes);

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

	public List<String> obterListaDeHorario(String designacao) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		Atividade_Regular atividade_regular;

		try {
			TypedQuery<Atividade_Regular> query = em.createNamedQuery(Atividade.GET_BY_DESIGNACAO_ATIVIDADES_REGULARES, Atividade_Regular.class);
			query.setParameter(Atividade.ATIVIDADE_DESIGNATION, designacao);
			atividade_regular = query.getSingleResult();
		}catch (Exception e) {
			em.close();
			throw new ApplicationException("Atividade fornecida nao existe");
		}
		List<String> listaDeHorarios = new ArrayList<>();
		if(atividade_regular.getHorario() == null) {
			throw new ApplicationException("Atividade fornecida nao tem Horario");
		}else {
			for (int i = 0; i < atividade_regular.getHorario().size(); i++) {
				for (int j = 0; j < atividade_regular.getHorario().get(i).getListaSessao().size(); j++) {
					if(atividade_regular.getHorario().get(i).getDataInicio().after(DataCorrente.get()) || atividade_regular.getHorario().get(i).getListaSessao().get(j).getHoraInit().after(DataCorrente.get())) {

						Calendar dataInicio = Calendar.getInstance();
						dataInicio.setTime(atividade_regular.getHorario().get(i).getDataInicio());

						Calendar horaInicio = Calendar.getInstance();
						horaInicio.setTime(atividade_regular.getHorario().get(i).getListaSessao().get(j).getHoraInit());

						StringBuilder sb = new StringBuilder();
						sb.append("\nDia da semana: " + atividade_regular.getHorario().get(i).getListaSessao().get(j).getDia().getString()
								+ "\nPreco: " + atividade_regular.getPreco() 
								+ "\nData de Inicio: " + dataInicio.get(Calendar.DATE) + "/" +dataInicio.get(Calendar.MONTH)+1 + "/"+ dataInicio.get(Calendar.YEAR) 
								+ "\nHora de inicio: " + horaInicio.get(Calendar.HOUR_OF_DAY) + ":" + horaInicio.get(Calendar.MINUTE));

						if(!listaDeHorarios.contains(sb.toString())) {
							listaDeHorarios.add(sb.toString());

						}
					}
				}
			}
			return listaDeHorarios;
		}
	}

	private List<Sessao> construirListaDatas(Date inicio,List<Sessao> sessoes,int meses) throws ApplicationException{
		List<Sessao> lista = new ArrayList<>();
		Calendar dataFim = Calendar.getInstance(); 
		dataFim.setTime(inicio);
		dataFim.add(Calendar.MONTH, meses);
		for( int i =0;i<sessoes.size();i++) {
			if(i>0 && i == sessoes.size()-1 && dataFim.after(sessoes.get(i).getHoraInit())){
				throw new ApplicationException("Nao existem sessoes suficientes para cumprir o numero de meses pedido");
			}
			if(inicio.before(sessoes.get(i).getHoraInit()) && sessoes.get(i).getHoraInit().before(dataFim.getTime())) {
				lista.add(sessoes.get(i));
			}
		}
		return lista;
	}
}
