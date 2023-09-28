package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import model.Motorista;

public class DAOCliente extends DAO<Motorista>{

	public Motorista read (Object chave){
		String cpf = (String) chave;	
		Query q = manager.query();
		q.constrain(Motorista.class);
		q.descend("cpf").constrain(cpf);
		List<Motorista> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
}