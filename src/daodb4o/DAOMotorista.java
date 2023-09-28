package daodb4o;

import java.util.List;
import java.util.ArrayList;
import com.db4o.ObjectSet;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;
import model.Motorista;
import model.Viagem;

public class DAOMotorista extends DAO<Motorista> {
    public Motorista read(Object chave) {
        String cpf = (String) chave;
        Query q = manager.query();
        q.constrain(Motorista.class);
        q.descend("cpf").constrain(cpf);
        List<Motorista> resultados = q.execute();
        if (resultados.size() > 0)
            return resultados.get(0);
        else
            return null;
    }

    public List<Viagem> viagensMotorista(String nomeMotorista) {
        Query q = manager.query();
        q.constrain(Motorista.class);
        q.descend("nome").constrain(nomeMotorista);
        ObjectSet<Motorista> resultados = q.execute();
        if (!resultados.isEmpty()) {
            Motorista motorista = resultados.get(0);
            return motorista.getLista();
        } else {
            return null;
        }
    }

    public Viagem viagemMotoristaPorNome(String nomeMotorista, String nomeViagem) {
        Query q = manager.query();
        q.constrain(Motorista.class);
        q.descend("nome").constrain(nomeMotorista);
        ObjectSet<Motorista> resultados = q.execute();
        if (!resultados.isEmpty()) {
            Motorista motorista = resultados.get(0);
            for (Viagem viagem : motorista.getLista()) {
                if (viagem.getDestino().equals(nomeViagem)) {
                    return viagem;
                }
            }
        }
        return null;
    }

    public Motorista motoristaPorPlacaVeiculo(String placaVeiculo) {
        Query q = manager.query();
        q.constrain(Motorista.class);
        q.descend("veiculo").descend("placa").constrain(placaVeiculo);
        ObjectSet<Motorista> resultados = q.execute();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        } else {
            return null;
        }
    }

    public Motorista motoristaPorNome(String nome) {
        Query q = manager.query();
        q.constrain(Motorista.class);
        q.descend("nome").constrain(nome);
        ObjectSet<Motorista> resultados = q.execute();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        } else {
            return null;
        }
    }

    public List<Motorista> motoristasComMaisDeDuasViagens() {
        Query q = manager.query();
        q.constrain(Motorista.class);
        q.constrain(new FiltroQuantidadeViagensMotorista());
        ObjectSet<Motorista> resultados = q.execute();
        List<Motorista> motoristasComMaisDeDuasViagens = new ArrayList<>();
        for (Motorista motorista : resultados) {
            motoristasComMaisDeDuasViagens.add(motorista);
        }
        return motoristasComMaisDeDuasViagens;
    }

    public Motorista motoristaPorCNH(String cnh) {
        Query q = manager.query();
        q.constrain(Motorista.class);
        q.descend("cnh").constrain(cnh);
        ObjectSet<Motorista> resultados = q.execute();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        } else {
            return null;
        }
    }
}

@SuppressWarnings("serial")
class FiltroQuantidadeViagensMotorista implements Evaluation {
    public void evaluate(Candidate candidate) {
        Motorista motorista = (Motorista) candidate.getObject();
        if (motorista.getLista().size() > 2) {
            candidate.include(true);
        } else {
            candidate.include(false);
        }
    }
}
