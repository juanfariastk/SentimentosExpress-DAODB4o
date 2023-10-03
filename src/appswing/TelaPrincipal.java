package appswing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

import regras_negocio.Fachada;

public class TelaPrincipal {
    private JFrame frame;
    private JMenu mnMotorista;
    private JMenu mnVeiculo;
    private JMenu mnViagem;
    private JMenu mnConsulta;
    private JLabel label;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaPrincipal window = new TelaPrincipal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaPrincipal() {
        initialize();
        frame.setTitle("Sentimentos Express - Usuário: " + Fachada.logado.getNome());
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Viagens - Sentimentos Express");
        frame.setBounds(100, 100, 450, 363);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        label = new JLabel("");
        label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("Inicializando...");
        label.setBounds(0, 0, 467, 302);
        ImageIcon imagem = new ImageIcon(getClass().getResource("/arquivos/imagem.png"));
        imagem = new ImageIcon(imagem.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        label.setIcon(imagem);
        frame.getContentPane().add(label);
        frame.setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        

        mnMotorista = new JMenu("Motorista");
        mnMotorista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                @SuppressWarnings("unused")
				TelaMotorista tela = new TelaMotorista();
            }
        });
        menuBar.add(mnMotorista);

        mnVeiculo = new JMenu("Veículo");
        mnVeiculo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                @SuppressWarnings("unused")
				TelaVeiculo tela = new TelaVeiculo();
            }
        });
        menuBar.add(mnVeiculo);

        mnViagem = new JMenu("Viagem");
        mnViagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                @SuppressWarnings("unused")
				TelaViagem tela = new TelaViagem();
            }
        });
        menuBar.add(mnViagem);
        
        mnConsulta = new JMenu("Consulta");
        mnConsulta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                @SuppressWarnings("unused")
				TelaConsulta tela = new TelaConsulta();
            }
        });
        menuBar.add(mnConsulta);
    }
}
