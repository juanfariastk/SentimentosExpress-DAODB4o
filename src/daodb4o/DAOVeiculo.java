package daodb4o;

import java.util.List;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import model.Veiculo;
import model.Viagem;

public class DAOVeiculo extends DAO<Veiculo> {

    public Veiculo read(Object chave) {
        String placa = (String) chave;
        Query q = manager.query();
        q.constrain(Veiculo.class);
        q.descend("placa").constrain(placa);
        List<Veiculo> resultados = q.execute();
        if (resultados.size() > 0)
            return resultados.get(0);
        else
            return null;
    }

    public Veiculo veiculoPorMotorista(String nomeMotorista) {
        Query q = manager.query();
        q.constrain(Veiculo.class);
        q.descend("lista").descend("motorista").descend("nome").constrain(nomeMotorista);
        ObjectSet<Veiculo> resultados = q.execute();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        } else {
            return null;
        }
    }

    public List<Viagem> listaViagensVeiculo(String placaVeiculo) {
        Query q = manager.query();
        q.constrain(Veiculo.class);
        q.descend("placa").constrain(placaVeiculo);
        ObjectSet<Veiculo> resultados = q.execute();
        if (!resultados.isEmpty()) {
            Veiculo veiculo = resultados.get(0);
            return veiculo.getLista();
        } else {
            return null;
        }
    }

    public Veiculo veiculoPorPlaca(String placa) {
        Query q = manager.query();
        q.constrain(Veiculo.class);
        q.descend("placa").constrain(placa);
        ObjectSet<Veiculo> resultados = q.execute();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        } else {
            return null;
        }
    }

    public void atualizarAtributosVeiculo(Veiculo veiculo) {
        try {
            begin();
            update(veiculo);
            commit();
        } catch (Exception e) {
            rollback();
            throw new RuntimeException("Erro ao atualizar o veículo: " + e.getMessage());
        } finally {
            close();
        }
    }
}