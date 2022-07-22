package facade.startup.servicos;

import java.util.Date;
import java.util.List;

import business.handlers.AgendarAtividadeOcasionalHandler;
import business.handlers.ComprarParticipacaoMensalAtividadeRegularHandler;
import business.handlers.CriarAtividadeHandler;
import business.handlers.DefinirNovoHorarioAC_Regular_Handler;
import facade.exceptions.ApplicationException;

public class AtividadeServico {
	private  DefinirNovoHorarioAC_Regular_Handler novoHorarioRegularHandler;
	private CriarAtividadeHandler AtividadeHandler;
	private ComprarParticipacaoMensalAtividadeRegularHandler comprarParticipacaoMensalAtividadeRegularHandler;
	private AgendarAtividadeOcasionalHandler agendarAtividadeOcasionalHandler;
	
	public AtividadeServico(CriarAtividadeHandler AtividadeHandler, DefinirNovoHorarioAC_Regular_Handler definirNovoHorarioAC_Regular_Handler, 
			ComprarParticipacaoMensalAtividadeRegularHandler comprarParticipacaoMensalAtividadeRegularHandler, AgendarAtividadeOcasionalHandler agendarAtividadeOcasionalHandler) {
		this.AtividadeHandler = AtividadeHandler;
		this.novoHorarioRegularHandler = definirNovoHorarioAC_Regular_Handler;
		this.comprarParticipacaoMensalAtividadeRegularHandler = comprarParticipacaoMensalAtividadeRegularHandler;
		this.agendarAtividadeOcasionalHandler=agendarAtividadeOcasionalHandler;
		
	}
	
	public List<String> criarAtividade() throws ApplicationException {
		return AtividadeHandler.listaEspecialidades();
	}

	public void adicionaAtividade(String especialidade, String designacao, boolean regular, int nSessoes, int preco,int duracao) throws ApplicationException {
		AtividadeHandler.criarAtividade(especialidade,designacao,regular,nSessoes,preco,duracao);
	}
	public void adicionaAtividade(String especialidade, String designacao, boolean regular, int nSessoes, int preco,int duracao, int nMaxParticipantes) throws ApplicationException {
		AtividadeHandler.criarAtividade(especialidade,designacao,regular,nSessoes,preco,duracao,nMaxParticipantes);
	}

	public List<String> definirNovoHorarioRegular() throws ApplicationException {
		return novoHorarioRegularHandler.definirHorarioRegular();
	}	
	
	public void adicionarNovoHorarioRegular(String designacao,List<String> diasSemana, List<Date> listaDatas, Date dataInicio,int duracao,int instrutorNum,int nMeses) throws ApplicationException{
		novoHorarioRegularHandler.adicionarNovoHorarioRegular(designacao ,diasSemana , listaDatas, dataInicio, duracao, instrutorNum, nMeses);
	}

	public String comprarParticipacaoMensalAtividadeRegular(String designacao, Date diaInicio, int numHorario, int nMeses, String email) throws ApplicationException {
		return comprarParticipacaoMensalAtividadeRegularHandler.comprarParticipacaoMensalAtividadeRegular(designacao, diaInicio, numHorario, nMeses, email);
	}
	
	public List<String> obterListaDeHorarios(String designacao) throws ApplicationException {
		return comprarParticipacaoMensalAtividadeRegularHandler.obterListaDeHorario(designacao);	
	}

	public List<String> agendarAtividadeOcasional(String especialidade) throws ApplicationException {
		return agendarAtividadeOcasionalHandler.agendarAtividadeOcasional(especialidade);
	}
	
	public String getInstrutoresAtividadeOcasional(String designacao, List<Date> diaInicio) throws ApplicationException {
		return agendarAtividadeOcasionalHandler.AgendarAtividadeOcasional( designacao, diaInicio);
	}

	public String comfirmarAtividadeOcasional(int instrutorId, String email, List<Date> listaHorasDatas, String designacao) throws ApplicationException {
		return agendarAtividadeOcasionalHandler.comfirmarAtividadeOcasional(instrutorId,email,listaHorasDatas,designacao);
	}
}
