package client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import facade.exceptions.ApplicationException;
import facade.startup.SaudeGesSys;
import facade.startup.servicos.AtividadeServico;

public class SimpleClient {
	public static void main(String[] args) {

	SaudeGesSys app = new SaudeGesSys();
		try {
			app.run();

		AtividadeServico es = app.getAtividadeServico();
		List<String> listaEspecialidades;
		List<String> listaHorarios;
		
		
			try {
				listaEspecialidades = es.criarAtividade();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				es.adicionaAtividade("Fisioterapia","Fisioterapia 1",false,1,43, 45);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			try {
				listaEspecialidades = es.criarAtividade();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				es.adicionaAtividade("Fisioterapia","Fisioterapia 5",false,5,195, 45);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			try {
				listaEspecialidades = es.criarAtividade();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				es.adicionaAtividade("Massagem Desportiva","Massagem 3x60",false,3,200, 60);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			try {
				listaEspecialidades = es.criarAtividade();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				es.adicionaAtividade("Massagem Desportiva","Massagem 3x20",false,3,100, 20);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			try {
				listaEspecialidades = es.criarAtividade();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				es.adicionaAtividade("Pilates Clinico","Pilates 2x",true,2,60, 45,2);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			try {
				listaEspecialidades = es.criarAtividade();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				es.adicionaAtividade("Pilates Clinico","Pilates 1x",true,1,40, 45,2);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			try {
				listaEspecialidades = es.criarAtividade();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				es.adicionaAtividade("Pos Parto","Pos Parto 3x",true,3,40, 30);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			// 8
			try {
				listaEspecialidades = es.definirNovoHorarioRegular();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				
				Calendar in = Calendar.getInstance();
				in.set(2022,3,1,1, 0,0);
				Date inicio = (Date) in.getTime().clone();
				
				List<Date> listaDias = new ArrayList<>();
				List<String> diasSemana = new ArrayList<>();
				
				diasSemana.add("Terca-feira");
				in.set(0,0,0,14,00,0);
				listaDias.add( (Date) in.getTime().clone() );
				
				diasSemana.add("Quinta-feira");
				in.set(0,0,0,14,00,0);
				listaDias.add( (Date) in.getTime().clone() );
				
				es.adicionarNovoHorarioRegular("Pilates 2x", diasSemana, listaDias, inicio, 6, 2, 2);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			// 9
			try {
				listaEspecialidades = es.definirNovoHorarioRegular();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				
				Calendar in = Calendar.getInstance();
				in.set(2022,3,1,0, 0,0);
				Date inicio = (Date) in.getTime().clone();
				
				List<Date> listaDias = new ArrayList<>();
				List<String> diasSemana = new ArrayList<>();
				
				diasSemana.add("Terca-feira");
				in.set(0,0,0,14,00,0);
				listaDias.add( (Date) in.getTime().clone() );
				
				diasSemana.add("Quinta-feira");
				in.set(0,0,0,14,00,0);
				listaDias.add( (Date) in.getTime().clone() );
				
				es.adicionarNovoHorarioRegular("Pilates 2x", diasSemana, listaDias, inicio, 6, 1, 2);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			// 10
			try {
				listaEspecialidades = es.definirNovoHorarioRegular();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				
				Calendar in = Calendar.getInstance();
				in.set(2022,3,18,0, 0,0);
				Date inicio = (Date) in.getTime().clone();
				
				List<Date> listaDias = new ArrayList<>();
				List<String> diasSemana = new ArrayList<>();
				
				diasSemana.add("Segunda-feira");
				in.set(0,0,0,14,30,0);
				listaDias.add( (Date) in.getTime().clone() );
				
				diasSemana.add("Quinta-feira");
				in.set(0,0,0,14,30,0);
				listaDias.add( (Date) in.getTime().clone() );
				
				es.adicionarNovoHorarioRegular("Pilates 2x", diasSemana, listaDias, inicio, 6, 1, 1);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			// 11
			try {
				listaEspecialidades = es.definirNovoHorarioRegular();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				
				Calendar in = Calendar.getInstance();
				in.set(2022,3,1,0, 0,0);
				Date inicio = (Date) in.getTime().clone();
				
				List<Date> listaDias = new ArrayList<>();
				List<String> diasSemana = new ArrayList<>();
				
				diasSemana.add("Segunda-feira");
				in.set(0,0,0,14,00,0);
				listaDias.add( (Date) in.getTime().clone() );

				
				es.adicionarNovoHorarioRegular("Pilates 1x", diasSemana, listaDias, inicio, 3, 1, 3);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			
			// 12
			try {
				listaEspecialidades = es.definirNovoHorarioRegular();
				for (int i = 0;i<listaEspecialidades.size();i++) {
					System.out.println(listaEspecialidades.get(i));
				}
				
				Calendar in = Calendar.getInstance();
				in.set(2022,3,18,0, 0,0);
				Date inicio = (Date) in.getTime().clone();
				
				List<Date> listaDias = new ArrayList<>();
				List<String> diasSemana = new ArrayList<>();
				
				diasSemana.add("Sexta-feira");
				in.set(0,0,0,14,30,0);
				listaDias.add( (Date) in.getTime().clone() );

				
				es.adicionarNovoHorarioRegular("Pilates 1x", diasSemana, listaDias, inicio, 3, 1, 3);
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
			//13
			try {
				listaHorarios = es.obterListaDeHorarios("Pilates 1x");
				for (int i = 0;i<listaHorarios.size();i++) {
					System.out.println(listaHorarios.get(i));
				}
				//junta a hora do horario com a data de inicio do horario escolhido
				Calendar dia = Calendar.getInstance();
				dia.set(2022,3,1); 
				Date diaInicio = dia.getTime();
				
				System.out.println(es.comprarParticipacaoMensalAtividadeRegular("Pilates 1x", diaInicio ,0, 3, "u1@gmail.com"));
				
				
			}catch (ApplicationException e) {
				e.printStackTrace();
			}
			//14
			try {
				listaHorarios = es.obterListaDeHorarios("Pilates 1x");
				for (int i = 0;i<listaHorarios.size();i++) {
					System.out.println(listaHorarios.get(i));
				}
				//junta a hora do horario com a data de inicio do horario escolhido
				Calendar dia = Calendar.getInstance();
				dia.set(2022,3,11); 
				Date diaInicio = dia.getTime();
				
				System.out.println(es.comprarParticipacaoMensalAtividadeRegular("Pilates 1x", diaInicio ,0, 1, "u2@gmail.com"));
				
				
			}catch (ApplicationException e) {
				e.printStackTrace();
			}
			//15
			try {
				listaHorarios = es.obterListaDeHorarios("Pilates 1x");
				for (int i = 0;i<listaHorarios.size();i++) {
					System.out.println(listaHorarios.get(i));
				}
				//junta a hora do horario com a data de inicio do horario escolhido
				Calendar dia = Calendar.getInstance();
				dia.set(2022,3,18); 
				Date diaInicio = dia.getTime();
				
				System.out.println(es.comprarParticipacaoMensalAtividadeRegular("Pilates 1x", diaInicio ,0, 1, "u3@gmail.com"));
				
				
			}catch (ApplicationException e) {
				e.printStackTrace();
			}
			//16
			try {
				listaHorarios = es.obterListaDeHorarios("Pilates 1x");
				for (int i = 0;i<listaHorarios.size();i++) {
					System.out.println(listaHorarios.get(i));
				}
				//junta a hora do horario com a data de inicio do horario escolhido
				Calendar dia = Calendar.getInstance();
				dia.set(2022,4,16); 
				Date diaInicio = dia.getTime();
				System.out.println(es.comprarParticipacaoMensalAtividadeRegular("Pilates 1x", diaInicio ,0, 3, "u3@gmail.com"));
				
			}catch (ApplicationException e) {
				e.printStackTrace();
			}
			//17
			try {
				List<Date> listaHorasDatas = new ArrayList<>();

				Calendar dia = Calendar.getInstance();
				dia.set(2022,2,28,10,0,0); 
				
				listaHorasDatas.add((Date) dia.getTime().clone());
				
				dia.set(2022,3,4,14,0,0); 
				listaHorasDatas.add((Date) dia.getTime().clone());

				dia.set(2022,3,11,12,0,0); 
				listaHorasDatas.add((Date) dia.getTime().clone());
				
				List<String> listaAtividades=es.agendarAtividadeOcasional("Massagem Desportiva");
				
				for(int i = 0;i<listaAtividades.size();i++) {
					System.out.println(listaAtividades.get(i));
				}
				
				System.out.println(es.getInstrutoresAtividadeOcasional("Massagem 3x60", listaHorasDatas));
				
				System.out.println(es.comfirmarAtividadeOcasional(2,"u4@gmail.com",listaHorasDatas,"Massagem 3x60"));
				
			}catch (ApplicationException e) {
				e.printStackTrace();
			}
			
		} catch (ApplicationException e1) {
			e1.printStackTrace();
		}
	}
}
