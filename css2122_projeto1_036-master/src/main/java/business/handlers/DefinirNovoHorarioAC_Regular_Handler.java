package business.handlers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import business.Atividade.Atividade;
import business.Atividade.Atividade_Regular;
import business.DiasSemana.DiasSemana;
import business.Horario.Horario;
import business.Instrutor.Instrutor;
import business.Sessao.Sessao;
import client.DataCorrente;
import facade.exceptions.ApplicationException;

public class DefinirNovoHorarioAC_Regular_Handler {

	private EntityManagerFactory emf;

	public DefinirNovoHorarioAC_Regular_Handler(EntityManagerFactory emf) {
		this.emf = emf;
	}
	

	public List<String> definirHorarioRegular() {
		EntityManager em = emf.createEntityManager();
		try {
		TypedQuery<Atividade_Regular> query = em.createNamedQuery(Atividade.LISTA_ATIVIDADES_REGULARES, Atividade_Regular.class);
		List<Atividade_Regular> lista = query.getResultList();
		List<String> listaRegular = new ArrayList<>();
		for(int i = 0; i<lista.size();i++) {
			listaRegular.add(lista.get(i).getDesignacao());
		}
		return listaRegular;
		}catch (Exception e) {
			return null;
		}finally {
			em.close();
		}
	}
	private int getDiaSemanaNum(String dia) {
        if(dia.equals(DiasSemana.SEGUNDA.getString()))
            return 2;
        else if(dia.equals(DiasSemana.TERCA.getString()))
            return 3;
        else if(dia.equals(DiasSemana.QUARTA.getString()))
            return 4;
        else if(dia.equals(DiasSemana.QUINTA.getString()))
            return 5;
        else if(dia.equals(DiasSemana.SEXTA.getString()))
            return 6;
        else if(dia.equals(DiasSemana.SABADO.getString()))
            return 7;
        else
            return 1;
    }
	private DiasSemana getDiaSemana(String dia) {
        if(dia.equals(DiasSemana.SEGUNDA.getString()))
            return DiasSemana.SEGUNDA;
        else if(dia.equals(DiasSemana.TERCA.getString()))
            return DiasSemana.TERCA;
        else if(dia.equals(DiasSemana.QUARTA.getString()))
            return DiasSemana.QUARTA;
        else if(dia.equals(DiasSemana.QUINTA.getString()))
            return DiasSemana.QUINTA;
        else if(dia.equals(DiasSemana.SEXTA.getString()))
            return DiasSemana.SEXTA;
        else if(dia.equals(DiasSemana.SABADO.getString()))
            return DiasSemana.SABADO;
        else
            return DiasSemana.DOMINGO;
    }
	public void adicionarNovoHorarioRegular(String designacao, List<String> diasSemana, List<Date> listaDatas, Date dataInicio,int duracao,int instrutorNum,int nMeses) throws ApplicationException {
		
		EntityManager em = emf.createEntityManager();
		Instrutor instrutor;
		Atividade_Regular atividade;
		
		try {
			TypedQuery<Instrutor> query = em.createNamedQuery(Instrutor.FIND_BY_NUM, Instrutor.class);
			query.setParameter(Instrutor.INSTRUTOR_NUM, instrutorNum);
			instrutor = query.getSingleResult();
		}catch (Exception e) {
			em.close();
			throw new ApplicationException("O instrutor fornecido nao existe");
		}
		
		try {
			TypedQuery<Atividade_Regular> query = em.createNamedQuery(Atividade.FIND_BY_DESIGNATION, Atividade_Regular.class);
			query.setParameter(Atividade.ATIVIDADE_DESIGNATION, designacao);
			atividade = query.getSingleResult();
		}catch (Exception e) {
			em.close();
			throw new ApplicationException("Atividade fornecida nao existe");
		}
		
		if (listaDatas.size()!=diasSemana.size() || listaDatas.size()>7 || listaDatas.size()<1 ) {
			em.close();
			throw new ApplicationException("Os horarios fornecidos nao sao entre 1 e 7 ou o numero de dias de semana e o numero de horarios nao sao iguais.");
		}
		
		if(instrutor.getCertificado(atividade.getEspecialidade().GetCertificacao())==null) {
			em.close();
			throw new ApplicationException("O instrutor nao tem a certificacao necessaria para a atividade");
		}
		
		for(int i = 0; i< diasSemana.size();i++) {		
			for(int j = 0; j< diasSemana.size();j++) {
				if(i!=j && ( diasSemana.get(i).equals(diasSemana.get(j)) && listaDatas.get(i).equals(listaDatas.get(j)) ) ) {
					em.close();
					throw new ApplicationException("Nos horarios dados existe repeticoes.");
				}
			}
		}
		
		if(duracao<1) {
			em.close();
			throw new ApplicationException("Numero de meses que a atividade fica disponivel eh inferior a zero.");		
		}
		
		if(dataInicio.before(DataCorrente.get())) {
			em.close();
			throw new ApplicationException("Data de inicio do horario nao eh no futuro.");			
		}
		
		if(nMeses>duracao) {
			em.close();
			throw new ApplicationException("Numero de meses atribuidos ao instrutor superior ao numero de meses que o horario esta disponivel.");			
		}
		List<Sessao> sessoesInstrutor=null;
		try {
		TypedQuery<Sessao> query = em.createNamedQuery(Sessao.FIND_BY_INSTRUTOR, Sessao.class);
		query.setParameter(Sessao.INSTRUTOR_NUM, instrutorNum);
		sessoesInstrutor = query.getResultList();
		}catch (Exception e) {
		}
		

		List<Sessao> listaSessoes = new ArrayList<>();
		//percorer o numero diferente de dias na semana que pode ter aulas
		for(int i = 0;i<diasSemana.size();i++) {
			
			//temporario para obter as horas sem usar o get do date
			Calendar temp = Calendar.getInstance();
			temp.setTime(listaDatas.get(i));
			
			// dia utilizado para guardar o dia que avanca
			Calendar dia = Calendar.getInstance();
			dia.setTime(dataInicio);
			dia.set(dia.get(Calendar.YEAR),dia.get(Calendar.MONTH), dia.get(Calendar.DATE), temp.get(Calendar.HOUR_OF_DAY), temp.get(Calendar.MINUTE));
			
			//obter o dia da semana no formato de 1(domingo) 7(sexta)
			int diaSemana = getDiaSemanaNum(diasSemana.get(i));
			
			// obter a data maxima que o horario pode ser implementado
			Calendar fim = Calendar.getInstance();
			fim.setTime(dataInicio);
			fim.add(Calendar.MONTH, duracao);
			
			// obter a data maxima que o instrutor pode dar aulas
			Calendar fimInstrutor = Calendar.getInstance();
			fimInstrutor.setTime(dataInicio);
			fimInstrutor.add(Calendar.MONTH, nMeses);
			
			// percore dia a dia para saber se o dia da semana eh valido
			while(dia.before(fim)) {
				if(dia.get(Calendar.DAY_OF_WEEK)==diaSemana) {
					
					Calendar horaFim = Calendar.getInstance();
					Date init = dia.getTime();
					horaFim.setTime(init);
					horaFim.add(Calendar.MINUTE, atividade.getDuracao());

					Date fin = horaFim.getTime();
					Sessao sessao;
					// se o instrutor ainda pode dar a aula deste dia ou deve ficar vazio
					if(dia.before(fimInstrutor))
						sessao = new Sessao(instrutor,getDiaSemana(diasSemana.get(i)), init, fin);
					else
						sessao = new Sessao(null,getDiaSemana(diasSemana.get(i)), init, fin);
					if(sessoesInstrutor!=null) {
						for(int j = 0;j<sessoesInstrutor.size(); j++ ) {
							if(sessao.getInstrutor()!=null && !(
									sessoesInstrutor.get(j).getHoraInit().before(sessao.getHoraInit()) && sessoesInstrutor.get(j).getHoraFim().before(sessao.getHoraInit()) || 
									sessoesInstrutor.get(j).getHoraInit().after(sessao.getHoraFim()) && sessoesInstrutor.get(j).getHoraFim().after(sessao.getHoraFim()) ) )  {
								em.close();
								throw new ApplicationException("O instrutor tem sobreposicao nas sessoes.");
							}
						}
					}
					listaSessoes.add(sessao);
				}
				dia.add(Calendar.DATE, 1);
			}
		}
		// percorrer a lista de sessoes para fazer a persistence de cada uma e se exister horario com a mesma data de inicio e adicionar ao seu horario 
		for(int i = 0; i<listaSessoes.size();i++) {
			em.getTransaction().begin();
			try {
				em.persist(listaSessoes.get(i));
				em.getTransaction().commit();
			}catch (Exception e) {
				em.getTransaction().rollback();
				em.close();
				throw new ApplicationException("Erro numa transassao.");
			}
			// verifica o horario e se possivel adiciona
			if(atividade.getHorario() != null) { 
				for(int j =0; j<atividade.getHorario().size();j++) {
					if(atividade.getHorario().get(j).getDataInicio().equals(dataInicio)) {
						atividade.getHorario().get(j).getListaSessao().add(listaSessoes.get(i));
					}
				}
			}
		}
		List<Horario> listaHorario;
		Horario horario;
		// para a situacao em que nao existe a lista de horarios ou entao se existir nao existe um horario onde as sessoes facam sentido fazer parte
		if(atividade.getHorario() == null || !checkHorario(atividade.getHorario(),dataInicio)) {
			horario = new Horario(dataInicio, nMeses, instrutor, duracao, listaSessoes); 
			if(atividade.getHorario()==null) {
			listaHorario = new ArrayList<>();
			listaHorario.add(horario);
			atividade.setHorario(listaHorario);
			}else {
				atividade.getHorario().add(horario);
			}
			em.getTransaction().begin();
			try {
				em.persist(horario);
				em.getTransaction().commit();
			}catch (Exception e) {
				em.getTransaction().rollback();
				em.close();
				throw new ApplicationException("Erro numa transassao.");
			}		
		}
		em.getTransaction().begin();
		try {
			em.persist(atividade);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			em.close();
			throw new ApplicationException("Erro numa transassao.");
		}finally {
			em.close();
		}
		
	}
	private boolean checkHorario( List<Horario> horarios, Date dataInicio) {
		for(int i = 0;i<horarios.size();i++) {
			if(horarios.get(i).getDataInicio().equals(dataInicio))
				return true;
		}
		return false;
	}
}
