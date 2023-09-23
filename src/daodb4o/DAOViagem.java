package daodb4o;

import java.util.List;

import com.db4o.ObjectSet;
import com.db4o.query.Query;

import model.Motorista;
import model.Veiculo;
import model.Viagem;

public class DAOViagem  extends DAO<Viagem>{

	public Viagem read (Object chave){
		int id = (int) chave;	
		Query q = manager.query();
		q.constrain(Viagem.class);
		q.descend("id").constrain(id);
		List<Viagem> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	//criar "id" sequencial 
	public void create(Viagem obj){
		int novoid = super.gerarId();  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}

	//--------------------------------------------
	//  consultas de Motorista e de Veiculo
	//--------------------------------------------

	public ObjectSet<Object> viagemMotorista(String nomeMotorista){
		Query q;
		q = manager.query();
		q.constrain(Viagem.class);
		q.descend("motorista").descend("nome").constrain(nomeMotorista);
		return q.execute();
	}

	public ObjectSet<Object> veiculoViagem(String placaVeiculo){
		Query q = manager.query();
		q.constrain(Viagem.class);
		q.descend("veiculo").descend("placa").constrain(placaVeiculo);
		return q.execute();
	}
	
	public List<Viagem> viagensPorDestino(String destino) {
	    Query q = manager.query();
	    q.constrain(Viagem.class);
	    q.descend("destino").constrain(destino);
	    ObjectSet<Viagem> resultados = q.execute();
	    return resultados;
	}
	
	public Viagem viagemPorId(int id) {
	    Query q = manager.query();
	    q.constrain(Viagem.class);
	    q.descend("id").constrain(id);
	    ObjectSet<Viagem> resultados = q.execute();
	    if (!resultados.isEmpty()) {
	        return resultados.get(0);
	    } else {
	        return null;
	    }
	}
	
	 public List<Viagem> viagensPorData(String data) {
	        Query q = manager.query();
	        q.constrain(Viagem.class);
	        q.descend("data").constrain(data);
	        List<Viagem> resultados = q.execute();
	        return resultados;
	 }
	 
	 public void atualizarAtributosViagem(Viagem viagem) {
	        try {
	            begin();
	            update(viagem);
	            commit();
	        } catch (Exception e) {
	            rollback();
	            throw new RuntimeException("Erro ao atualizar a viagem: " + e.getMessage());
	        } finally {
	            close();
	        }
	 }

}
