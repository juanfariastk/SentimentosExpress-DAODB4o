package appconsole;

import java.util.List;

import regras_negocio.Fachada;
import model.*;

public class Alterar {
	
	public Alterar() {
		try {
			Fachada.inicializar();
			// Atualizando capacidade da viagem
            Fachada.atualizarCapacidadeDaViagemPorDestino("Recife", 6);
            List<Viagem> viagemAtualizada = Fachada.localizarViagemPorDestino("Recife");
            System.out.println("A viagem de ID: " + viagemAtualizada.get(0).getId() + " e Destino " + viagemAtualizada.get(0).getDestino() + " foi atualizada com sucesso!"); 
            
            // Atualizando capacidade de veiculo de placa KBU-0214 para 5
            Fachada.atualizarCapacidadeDaViagemPorDestino("KBU-0214", 5);
            Veiculo veiculoAtualizado = Fachada.localizarVeiculoPorPlaca("KBU-0214");
            System.out.println("Veiculo : " + veiculoAtualizado + " - Teve a capacidade alterada para 5");
		
		} catch (Exception e) {
			System.out.println(e);
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa !");
	}

	public static void main(String[] args) {
		new Alterar();
	}
}
