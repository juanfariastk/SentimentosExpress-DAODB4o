package daodb4o;

import java.util.List;

import com.db4o.query.Query;

import model.Usuario;

public class DAOUsuario extends DAO<Usuario>{

	public Usuario read (Object chave){
		String nome = (String) chave;	//casting para o tipo da chave
		Query q = manager.query();
		q.constrain(Usuario.class);
		q.descend("nome").constrain(nome);
		List<Usuario> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}

	//--------------------------------------------
	//  consultas
	//--------------------------------------------
	
}

