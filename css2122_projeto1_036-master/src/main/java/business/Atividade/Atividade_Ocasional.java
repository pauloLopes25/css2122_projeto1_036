package business.Atividade;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import business.Especialidade.Especialidade;
import business.Sessao.Sessao;

@Entity
public class Atividade_Ocasional extends Atividade{
    @OneToMany(targetEntity=Sessao.class) @JoinColumn
    private List<Sessao> ListaSessoes;
    private int NSessoes;
    public Atividade_Ocasional(){
        super();
    }

    public Atividade_Ocasional(Especialidade especialidade, String designacao, double preco,int duracao,int NSessoes) {
        super(especialidade, designacao, preco, duracao);
        this.NSessoes=NSessoes;
    }
    
    public Atividade_Ocasional(Especialidade especialidade, String designacao, double preco,int duracao,int NSessoes, List<Sessao> ListaSessoes) {
        super(especialidade, designacao, preco, duracao);
        this.ListaSessoes=ListaSessoes;
        this.NSessoes=NSessoes;
    }
    
    public List<Sessao> getListaSessoes() {
        return this.ListaSessoes;
    }
    
    public int getNSessoes() {
        return this.NSessoes;
    }
    
    
    public String atvOc_toString(String designacao, double preco, int duracao, int NSessoes) {
    	return "Designacao: " + designacao + "\nPreco: " + preco + "\nDuracao: " + duracao + "\nNSessoes: " + NSessoes + "\n";  
    }
    
}
