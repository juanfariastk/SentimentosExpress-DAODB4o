package appconsole;

import java.util.List;
import model.Viagem;
import regras_negocio.Fachada;

public class AlterarRelacionamento {
    public AlterarRelacionamento() {
        try {
            Fachada.inicializar();

            // alterando relacionamento da viagem de id 1 para o motorista de CNH 95752634310 - Zezão
            Fachada.atualizarMotoristaDaViagem(1, "95752634310");

            List<Viagem> viagemAlterada = Fachada.localizarViagemPorDestino("Recife");
            if (!viagemAlterada.isEmpty()) {
                System.out.println("Viagem de ID " + viagemAlterada.get(0).getId() + " - Motorista: " + viagemAlterada.get(0).getMotorista().getNome());
            } else {
                System.out.println("Nenhuma viagem encontrada com destino 'Recife'.");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante a execução: " + e.getMessage());
        } finally {
            Fachada.finalizar();
            System.out.println("\nfim do programa !");
        }
    }

    public static void main(String[] args) {
        new AlterarRelacionamento();
    }
}

