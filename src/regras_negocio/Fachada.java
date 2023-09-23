package regras_negocio;

import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOVeiculo;
import daodb4o.DAOMotorista;
import daodb4o.DAOViagem;
import daodb4o.DAOUsuario;

import model.Viagem;
import model.Veiculo;
import model.Motorista;
import model.Usuario;


public class Fachada {
    private static DAOVeiculo daoVeiculo = new DAOVeiculo();
    private static DAOMotorista daoMotorista = new DAOMotorista();
    private static DAOViagem daoViagem = new DAOViagem();
    private static DAOUsuario daousuario = new DAOUsuario(); 
	public static Usuario logado;	//contem o objeto Usuario logado em TelaLogin.java

    public static void inicializar() {
        DAO.open();
    }

    public static void finalizar() {
        DAO.close();
    }

    public static Veiculo cadastrarVeiculo(String placa, int capacidade) {
        try {
        	DAO.begin();
            Veiculo veiculoExistente = daoVeiculo.read(placa);
            if (veiculoExistente != null) {
                throw new Exception("Veículo com placa já cadastrada: " + placa);
            }

            Veiculo veiculo = new Veiculo(placa, capacidade);
            daoVeiculo.create(veiculo);
            DAO.commit();
            return veiculo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Motorista cadastrarMotorista(String cnh, String nome) throws Exception {
        Motorista motoristaExistente = daoMotorista.read(cnh);
        if (motoristaExistente != null) {
            throw new Exception("Motorista com CNH já cadastrada: " + cnh);
        }
        DAO.begin();
        Motorista motorista = new Motorista(cnh, nome);
        daoMotorista.create(motorista);
        DAO.commit();
        return motorista;
    }

    public static Viagem cadastrarViagem(String data, Veiculo veiculo, Motorista motorista, String destino, int quantidade) {
    	DAO.begin();
    	int novoId = daoViagem.gerarId(); 
        Viagem viagem = new Viagem(novoId, data, veiculo, motorista, destino, quantidade); 
        viagem.setId(novoId); 
        daoViagem.create(viagem); 
        motorista.addViagem(viagem); 
        veiculo.addViagem(viagem); 
        DAO.commit();
        return viagem;
    }


    public static List<Veiculo> listarVeiculos() {
    	DAO.begin();
    	List<Veiculo> listaVeiculos = daoVeiculo.readAll();
    	DAO.commit();
        return listaVeiculos;
    }

    public static List<Motorista> listarMotoristas() {
    	DAO.begin();
    	List<Motorista> listaMotoristas = daoMotorista.readAll();
    	DAO.commit();
        return listaMotoristas;
    }

    public static List<Viagem> listarViagens() {
    	DAO.begin();
    	List<Viagem> listaViagens = daoViagem.readAll();
    	DAO.commit();
        return listaViagens;
    }
    
    public static List<Usuario>  listarUsuarios(){
		DAO.begin();
		List<Usuario> resultados =  daousuario.readAll();
		DAO.commit();
		return resultados;
	} 
    
    public static List<Viagem> listarViagensPorData(String data) {
        DAOViagem.begin();
        List<Viagem> viagens = daoViagem.viagensPorData(data);
        DAOViagem.commit();
        return viagens;
    }
    
    public static List<Motorista> motoristasComMaisDeDuasViagens() {
        DAO.begin();
        List<Motorista> motoristas = daoMotorista.motoristasComMaisDeDuasViagens();
        DAO.commit();
        return motoristas;
    }
    
    public static void excluirViagemPorDestino(String destino) {
        DAO.begin();
        List<Viagem> viagens = daoViagem.viagensPorDestino(destino);

        for (Viagem viagem : viagens) {
            Motorista motorista = viagem.getMotorista();
            if (motorista != null) {
                motorista.removeViagem(viagem); 
                daoMotorista.update(motorista); 
            }

            Veiculo veiculo = viagem.getVeiculo();
            if (veiculo != null) {
                veiculo.removeViagem(viagem); 
                daoVeiculo.update(veiculo); 
            }

            daoViagem.delete(viagem);
        }

        DAO.commit();
    }


    public static Motorista localizarMotoristaPorNome(String nome) {
        Motorista motorista = daoMotorista.motoristaPorNome(nome);
        if (motorista == null) {
            System.out.println("Motorista com nome '" + nome + "' não encontrado.");
        }
        return motorista;
    }

    public static List<Viagem> localizarViagemPorDestino(String destino) {
        List<Viagem> viagens = daoViagem.viagensPorDestino(destino);
        if (viagens.isEmpty()) {
            System.out.println("Nenhuma viagem encontrada com destino '" + destino + "'.");
        }
        return viagens;
    }


    public static Veiculo localizarVeiculoPorPlaca(String placa) {
        Veiculo veiculo = daoVeiculo.veiculoPorPlaca(placa);
        if (veiculo == null) {
            System.out.println("Veículo com placa '" + placa + "' não encontrado.");
        }
        return veiculo;
    }
    
    public static void atualizarCapacidaDeVeiculo(String placa, int novaCapacidade) {
        Veiculo veiculo = localizarVeiculoPorPlaca(placa);
        if (veiculo != null) {
            veiculo.setCapacidade(novaCapacidade);
            daoVeiculo.atualizarAtributosVeiculo(veiculo);
            System.out.println("Veiculo : " + veiculo.getPlaca() + " - Teve a capacidade alterada para " + novaCapacidade);
        } else {
            System.out.println("Veículo com placa '" + placa + "' não encontrado.");
        }
    }
    
    public static void atualizarCapacidadeDaViagemPorDestino(String destino, int novaCapacidade) {
        List<Viagem> viagens = localizarViagemPorDestino(destino);
        
        if (!viagens.isEmpty()) {
            for (Viagem viagem : viagens) {
                viagem.setQuantidade(novaCapacidade);
                daoViagem.atualizarAtributosViagem(viagem);
                System.out.println("Viagem com destino '" + destino + "' atualizada com nova capacidade: " + novaCapacidade);
            }
        } else {
            System.out.println("Nenhuma viagem encontrada com destino '" + destino + "'.");
        }
    }



    public static void excluirViagemDeMotoristaPorDestino(String destino) {
    	DAO.begin();
        List<Viagem> viagensComDestino = daoViagem.viagensPorDestino(destino);

        if (viagensComDestino != null && !viagensComDestino.isEmpty()) {
        	
            for (Viagem viagem : viagensComDestino) {
                Motorista motorista = viagem.getMotorista();
                Veiculo veiculo = viagem.getVeiculo();

                if (motorista != null) {
                    motorista.removeViagem(viagem);
                    daoMotorista.update(motorista);
                }

                if (veiculo != null) {
                    veiculo.removeViagem(viagem);
                    daoVeiculo.update(veiculo);
                }

                daoViagem.delete(viagem);
            }
           
        }
        DAO.commit();
    }
    
    //-- Metodos Usuario
    
    public static Usuario cadastrarUsuario(String nome, String senha) throws Exception{
		DAO.begin();
		Usuario usu = daousuario.read(nome);
		if (usu!=null)
			throw new Exception("Usuario ja cadastrado:" + nome);
		usu = new Usuario(nome, senha);

		daousuario.create(usu);
		DAO.commit();
		return usu;
	}
	public static Usuario localizarUsuario(String nome, String senha) {
		Usuario usu = daousuario.read(nome);
		if (usu==null)
			return null;
		if (! usu.getSenha().equals(senha))
			return null;
		return usu;
	}

}