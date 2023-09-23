package appconsole;

import model.Motorista;
import model.Usuario;
import model.Veiculo;
import model.Viagem;
import regras_negocio.Fachada;

public class Listar {

    public Listar() {
        try {
            Fachada.inicializar();
            
            System.out.println(" -> Lista de Viagens  <- ");
            for (Viagem viagem : Fachada.listarViagens()) {
                System.out.println(viagem);
            }
            
            System.out.println(" -> Lista de Veículos  <- ");
            for (Veiculo veiculo : Fachada.listarVeiculos()) {
                System.out.println(veiculo);
            }
            
            System.out.println(" -> Lista de Motoristas  <- ");
            for (Motorista motorista : Fachada.listarMotoristas()) {
                System.out.println(motorista);
            }
            
            System.out.println(" -> Lista de Usuários  <- ");
            for (Usuario usuario : Fachada.listarUsuarios()) {
                System.out.println(usuario);
            }
            
            System.out.println("Listagem encerrada!");
            
        } catch (Exception e) {
            System.out.println("Erro geral: " + e.getMessage());
        }

        Fachada.finalizar();
        System.out.println("\nfim do programa !");
    }

    public static void main(String[] args) {
        new Listar();
    }
}
