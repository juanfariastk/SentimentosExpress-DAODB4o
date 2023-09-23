package appconsole;

import regras_negocio.Fachada;
import model.Viagem;
import model.Veiculo;
import model.Motorista;

public class Consultar {

    public Consultar() {
        try {
            Fachada.inicializar();

            System.out.println("Consultas...\n");

            // Consultar viagens da data de 18/01/2023
            System.out.println("Viagens da data de 18/01/2023:");
            for (Viagem viagem : Fachada.listarViagensPorData("18/01/2023")) {
                System.out.println(viagem);
            }

            // Consultar viagens do veículo de placa JYQ-1219
            System.out.println("\nViagens do veículo JYQ-1219:");
            Veiculo veiculo = Fachada.localizarVeiculoPorPlaca("JYQ-1219");
            if (veiculo != null) {
                for (Viagem viagem : veiculo.getLista()) {
                    System.out.println(viagem);
                }
            }

            // Consultar motoristas com mais de 2 viagens
            System.out.println("\nMotoristas com mais de 2 viagens:");
            for (Motorista motorista : Fachada.motoristasComMaisDeDuasViagens()) {
                System.out.println(motorista.getNome());
                System.out.println(motorista);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Fachada.finalizar();
        System.out.println("\nFim do programa!");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}
