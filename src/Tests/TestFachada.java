package Tests;

import java.util.List;

import model.Motorista;
import model.Veiculo;
import model.Viagem;
import regras_negocio.Fachada;

public class TestFachada {

    public static void main(String[] args) {
        Fachada.inicializar();

        try {
        	// Teste de cadastro de veículo
            Veiculo veiculo1 = Fachada.cadastrarVeiculo("ABC123", 5);
            System.out.println("Veículo cadastrado: " + veiculo1);

            // Teste de cadastro de motorista
            Motorista motorista1 = Fachada.cadastrarMotorista("12345", "João");
            System.out.println("Motorista cadastrado: " + motorista1);

            // Teste de cadastro de viagens adicionais ao motorista
            Viagem viagem1 = Fachada.cadastrarViagem("2023-09-23", veiculo1, motorista1, "Destino A", 3);
            Viagem viagem2 = Fachada.cadastrarViagem("2023-09-24", veiculo1, motorista1, "Destino B", 2);
            Viagem viagem3 = Fachada.cadastrarViagem("2023-09-25", veiculo1, motorista1, "Destino C", 1);
            Viagem viagem4 = Fachada.cadastrarViagem("2023-09-26", veiculo1, motorista1, "Destino D", 4);
            
            System.out.println("Viagem cadastrada: " + viagem1);
            System.out.println("Viagem cadastrada: " + viagem2);
            System.out.println("Viagem cadastrada: " + viagem3);
            System.out.println("Viagem cadastrada: " + viagem4);

            // Teste de listar veículos
            List<Veiculo> veiculos = Fachada.listarVeiculos();
            System.out.println("Lista de veículos: " + veiculos);

            // Teste de listar motoristas
            List<Motorista> motoristas = Fachada.listarMotoristas();
            System.out.println("Lista de motoristas: " + motoristas);

            // Teste de listar viagens
            List<Viagem> viagens = Fachada.listarViagens();
            System.out.println("Lista de viagens: " + viagens);

            // Teste de exclusão de viagem por destino
            Fachada.excluirViagemPorDestino("Destino A");

            // Teste de localização de motorista por nome
            Motorista motoristaEncontrado = Fachada.localizarMotoristaPorNome("João");
            System.out.println("Motorista encontrado: " + motoristaEncontrado);

            // Teste de localização de viagens por destino
            List<Viagem> viagensPorDestino = Fachada.localizarViagemPorDestino("Destino A");
            if(viagensPorDestino.isEmpty()) {
            	System.out.println("Este destino não existe, nenhuma viagem foi encontrada " );
            }

            // Teste de localização de veículo por placa
            Veiculo veiculoEncontrado = Fachada.localizarVeiculoPorPlaca("ABC123");
            System.out.println("Veículo encontrado: " + veiculoEncontrado);

            //Teste filtro
            
            
            List<Motorista> motoristasComMaisDeDuasViagens = Fachada.motoristasComMaisDeDuasViagens();
            System.out.println(motoristasComMaisDeDuasViagens);
            System.out.println(Fachada.localizarMotoristaPorNome("João"));
//            if (motoristasComMaisDeDuasViagens.isEmpty()) {
//                System.out.println("Não existem motoristas com mais de duas viagens.");
//            } else {
//                System.out.println("Motoristas com mais de duas viagens:");
//                for (Motorista motorista : motoristasComMaisDeDuasViagens) {
//                    System.out.println("Nome: " + motorista.getNome());
//                    System.out.println("CPF: " + motorista.getCnh());
//                }
//            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }

        Fachada.finalizar();
    }
}
