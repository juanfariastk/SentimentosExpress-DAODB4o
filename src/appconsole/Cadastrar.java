package appconsole;

import daodb4o.Util;
import model.Motorista;
import model.Veiculo;
import regras_negocio.Fachada;

public class Cadastrar {

    public Cadastrar() {
        try {
            Fachada.inicializar();
            System.out.println("Realizando cadastros...");

            // Motoristas
            Motorista motorista1, motorista2, motorista3, motorista4;

            motorista1 = Fachada.cadastrarMotorista("68418818452", "Carlinho do Financeiro");
            motorista2 = Fachada.cadastrarMotorista("24030964862", "Leozinho do Borel");
            motorista3 = Fachada.cadastrarMotorista("95752634310", "Zezão Pé Grande");
            motorista4 = Fachada.cadastrarMotorista("51704264906", "Juquinha da Esquina");

            // Veiculos
            Veiculo veiculo1, veiculo2, veiculo3, veiculo4;

            veiculo1 = Fachada.cadastrarVeiculo("KBU-0214", 4);
            veiculo2 = Fachada.cadastrarVeiculo("NEM-9988", 7);
            veiculo3 = Fachada.cadastrarVeiculo("JYQ-1219", 6);
            veiculo4 = Fachada.cadastrarVeiculo("HYU-7848", 7);

            // Viagens
            Fachada.cadastrarViagem("22/10/2022", veiculo1, motorista1, "Recife", 4);
            Fachada.cadastrarViagem("10/12/2022", veiculo2, motorista2, "Guarapari", 3);
            Fachada.cadastrarViagem("01/09/2022", veiculo3, motorista3, "Bariloche", 7);
            Fachada.cadastrarViagem("01/09/2022", veiculo4, motorista4, "Bariloche", 4);
            Fachada.cadastrarViagem("11/01/2023", veiculo2, motorista3, "Augusta", 5);
            Fachada.cadastrarViagem("18/01/2023", veiculo1, motorista2, "Feira de Santada", 3);
            Fachada.cadastrarViagem("18/01/2023", veiculo4, motorista1, "Areia", 5);
            Fachada.cadastrarViagem("04/02/2023", veiculo2, motorista1, "Rio Branco", 3);
            Fachada.cadastrarViagem("17/02/2023", veiculo3, motorista4, "Santa Catarina", 4);

            Util.desconectar();
            System.out.println("\nCadastros realizados com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Cadastrar();
    }
}
