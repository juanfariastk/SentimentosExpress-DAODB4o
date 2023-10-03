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
    public static Usuario logado;

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
        Viagem viagem = new Viagem(data, veiculo, motorista, destino, quantidade);
        motorista.addViagem(viagem);
        veiculo.addViagem(viagem);
        daoMotorista.update(motorista);
        daoVeiculo.update(veiculo);
        daoViagem.create(viagem);
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

    public static List<Usuario> listarUsuarios() {
        DAO.begin();
        List<Usuario> resultados = daousuario.readAll();
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

    public static void atualizarCapacidadeDeVeiculo(String placa, int novaCapacidade) {
        DAO.begin();
        Veiculo veiculo = localizarVeiculoPorPlaca(placa);
        if (veiculo != null) {
            veiculo.setCapacidade(novaCapacidade);
            daoVeiculo.atualizarAtributosVeiculo(veiculo);
            System.out.println("Veiculo : " + veiculo.getPlaca() + " - Teve a capacidade alterada para " + novaCapacidade);
            DAO.commit();
        } else {
            System.out.println("Veículo com placa '" + placa + "' não encontrado.");
            DAO.rollback();
        }
    }

    public static void atualizarCapacidadeDaViagemPorDestino(String destino, int novaCapacidade) {
        DAO.begin();
        List<Viagem> viagens = localizarViagemPorDestino(destino);
        if (!viagens.isEmpty()) {
            for (Viagem viagem : viagens) {
                viagem.setQuantidade(novaCapacidade);
                daoViagem.atualizarAtributosViagem(viagem);
                System.out.println("Viagem com destino '" + destino + "' atualizada com nova capacidade: " + novaCapacidade);
            }
            DAO.commit();
        } else {
            System.out.println("Nenhuma viagem encontrada com destino '" + destino + "'.");
            DAO.rollback();
        }
    }
    
    public static void atualizarMotoristaDaViagem(int viagemId, String novoMotoristaCnh) {
        DAOViagem.begin();
        try {
            Viagem viagem = daoViagem.viagemPorId(viagemId);
            if (viagem != null) {
                Motorista novoMotorista = daoMotorista.motoristaPorCNH(novoMotoristaCnh);
                if (novoMotorista != null) {
                    Motorista motoristaAntigo = viagem.getMotorista();
                    if (motoristaAntigo != null) {
                        motoristaAntigo.removeViagem(viagem);

                        daoMotorista.update(motoristaAntigo);
                    }
                    viagem.setMotorista(novoMotorista);
                    novoMotorista.addViagem(viagem);
                   
                    
                    daoMotorista.update(novoMotorista);
                    daoViagem.atualizarAtributosViagem(viagem); 
                    System.out.println("Motorista da viagem com ID " + viagemId + " atualizado para " + novoMotorista.getNome());
                    DAOViagem.commit();
                } else {
                    System.out.println("Novo motorista com CNH " + novoMotoristaCnh + " não encontrado.");
                    DAOViagem.rollback();
                }
            } else {
                System.out.println("Viagem com ID " + viagemId + " não encontrada.");
                DAOViagem.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DAOViagem.rollback();
            System.err.println("Erro ao atualizar o motorista da viagem com ID " + viagemId + ": " + e.getMessage());
        } finally {
            DAOViagem.close();
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

    public static void excluirViagemPorId(int id) {
        try {
            DAO.begin();
            Viagem viagem = daoViagem.viagemPorId(id);

            if (viagem != null) {
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
                System.out.println("Viagem com ID " + id + " excluída com sucesso.");
            } else {
                System.out.println("Nenhuma viagem encontrada com ID " + id + ".");
            }

            DAO.commit();
        } catch (Exception e) {
            e.printStackTrace();
            DAO.rollback();
            System.err.println("Erro ao excluir a viagem com ID " + id + ": " + e.getMessage());
        } finally {
            DAO.commit();
        }
    }

    public static void excluirVeiculoPorPlaca(String placa) {
        try {
            DAO.begin();
            Veiculo veiculo = daoVeiculo.veiculoPorPlaca(placa);

            if (veiculo != null) {
                List<Viagem> viagens = veiculo.getLista();

                for (Viagem viagem : viagens) {
                    Motorista motorista = viagem.getMotorista();
                    if (motorista != null) {
                        motorista.removeViagem(viagem);
                        daoMotorista.update(motorista);
                    }

                    daoViagem.delete(viagem);
                }

                daoVeiculo.delete(veiculo);
                System.out.println("Veículo com placa " + placa + " excluído com sucesso.");
            } else {
                System.out.println("Veículo com placa " + placa + " não encontrado.");
            }

            DAO.commit();
        } catch (Exception e) {
            e.printStackTrace();
            DAO.rollback();
            System.err.println("Erro ao excluir o veículo com placa " + placa + ": " + e.getMessage());
        } finally {
            DAO.commit();
        }
    }
    
    public static void excluirMotoristaPorCNH(String cnh) {
        try {
            DAO.begin();
            Motorista motorista = daoMotorista.motoristaPorCNH(cnh); // Usar o método motoristaPorCNH para buscar o motorista

            if (motorista != null) {
                List<Viagem> viagens = motorista.getLista();

                for (Viagem viagem : viagens) {
                    Viagem viagemNoVeiculo = daoViagem.viagemPorId(viagem.getId());

                    if (viagemNoVeiculo != null) {
                        Veiculo veiculo = viagemNoVeiculo.getVeiculo();
                        if (veiculo != null) {
                            veiculo.removeViagem(viagemNoVeiculo);
                            daoVeiculo.update(veiculo);
                        }
                        daoViagem.delete(viagemNoVeiculo);
                    }
                }

                daoMotorista.delete(motorista);
                System.out.println("Motorista com CNH " + cnh + " excluído com sucesso.");
            } else {
                System.out.println("Motorista com CNH " + cnh + " não encontrado.");
            }

            DAO.commit();
        } catch (Exception e) {
            e.printStackTrace();
            DAO.rollback();
            System.err.println("Erro ao excluir o motorista com CNH " + cnh + ": " + e.getMessage());
        } finally {
            DAO.commit();
        }
    }



    public static Usuario cadastrarUsuario(String nome, String senha) throws Exception {
        DAO.begin();
        Usuario usu = daousuario.read(nome);
        if (usu != null)
            throw new Exception("Usuario ja cadastrado:" + nome);
        usu = new Usuario(nome, senha);

        daousuario.create(usu);
        DAO.commit();
        return usu;
    }

    public static Usuario localizarUsuario(String nome, String senha) {
        Usuario usu = daousuario.read(nome);
        if (usu == null)
            return null;
        if (!usu.getSenha().equals(senha))
            return null;
        return usu;
    }

	
}
