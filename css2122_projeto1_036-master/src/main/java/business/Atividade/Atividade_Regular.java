package business.Atividade;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import business.Especialidade.Especialidade;
import business.Horario.Horario;

@Entity
public class Atividade_Regular extends Atividade{
    
	
    private int NSessoesSemanais;
    private int NMaxParticipantes;
    @OneToMany(fetch = FetchType.EAGER ,targetEntity=Horario.class) @JoinColumn
    private List<Horario> horario;
    public Atividade_Regular(){
        super();
    }

    public Atividade_Regular(Especialidade especialidade, String designacao, double preco,int duracao,int NSessoesSemanais,int NMaxParticipantes) {
        super(especialidade, designacao, preco, duracao);
        this.NSessoesSemanais=NSessoesSemanais;
        this.NMaxParticipantes=NMaxParticipantes;

    }
    
    public Atividade_Regular(Especialidade especialidade, String designacao, double preco,int duracao,int NSessoesSemanais,int NMaxParticipantes, List<Horario> horario) {
        super(especialidade, designacao, preco, duracao);
        this.NSessoesSemanais=NSessoesSemanais;
        this.NMaxParticipantes=NMaxParticipantes;
        this.horario=horario;
    }
    
    public int getNSessoesSemanais() {
        return this.NSessoesSemanais;
    }
    public int getNMaxParticipantes() {
        return this.NMaxParticipantes;
    }
    public List<Horario> getHorario() {
        return this.horario;
    }
    public void setHorario(List<Horario> horario) {
    	this.horario=horario;
    }
    
}
