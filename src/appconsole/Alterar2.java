package appconsole;

import regras_negocio.Fachada;
import model.Veiculo;

public class Alterar2 {
    
    public Alterar2() {
        try {
            Fachada.inicializar();

            // Atualizando capacidade de veiculo de placa KBU-0214 para 5
            Fachada.atualizarCapacidadeDeVeiculo("KBU-0214", 5);
            Veiculo veiculoAtualizado = Fachada.localizarVeiculoPorPlaca("KBU-0214");
            System.out.println("Veiculo : " + veiculoAtualizado + " - Teve a capacidade alterada para 5");
        
        } catch (Exception e) {
            System.out.println(e);
        }

        Fachada.finalizar();
        System.out.println("\nfim do programa !");
    }

    public static void main(String[] args) {
        new Alterar2();
    }
}