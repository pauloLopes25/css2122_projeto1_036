package facade.startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.handlers.AgendarAtividadeOcasionalHandler;
import business.handlers.ComprarParticipacaoMensalAtividadeRegularHandler;
import business.handlers.CriarAtividadeHandler;
import business.handlers.DefinirNovoHorarioAC_Regular_Handler;
import facade.exceptions.ApplicationException;
import facade.startup.servicos.AtividadeServico;

public class SaudeGesSys {

	private EntityManagerFactory emf;

	public void run() throws ApplicationException {
		try {
			emf = Persistence.createEntityManagerFactory("saudeges-jpa");
		} catch (Exception e) {
			throw new ApplicationException("Erro ao conectar com a data base");
		}
	}
	public AtividadeServico getAtividadeServico() {
		return new AtividadeServico(new CriarAtividadeHandler(emf),new DefinirNovoHorarioAC_Regular_Handler(emf), new ComprarParticipacaoMensalAtividadeRegularHandler(emf) , new AgendarAtividadeOcasionalHandler(emf));
	}
	
	
}