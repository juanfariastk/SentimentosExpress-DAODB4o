package appswing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.Motorista;
import model.Veiculo;
import model.Viagem;
import regras_negocio.Fachada;

public class TelaConsulta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton button;
    private JLabel label;
    private JLabel label_4;

    private JComboBox<String> comboBox;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    @SuppressWarnings("unused")
                    TelaConsulta tela = new TelaConsulta();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaConsulta() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);

        frame.setResizable(false);
        frame.setTitle("Consulta");
        frame.setBounds(100, 100, 729, 385);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Fachada.inicializar();
            }
            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 43, 674, 148);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                label_4.setText("selecionado="+ (String) table.getValueAt(table.getSelectedRow(), 0));
            }
        });
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.LIGHT_GRAY);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setBounds(21, 321, 688, 14);
        frame.getContentPane().add(label);

        label_4 = new JLabel("resultados:");
        label_4.setBounds(21, 190, 431, 14);
        frame.getContentPane().add(label_4);

        button = new JButton("Consultar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                if(index < 0) {
                    label_4.setText("consulta não selecionada");
                } else {
                    switch(index) {
                        case 0:
                            List<Viagem> resultado1 = Fachada.listarViagensPorData("18/01/2023");
                            listagemViagens(resultado1);
                            break;
                        case 1:
                            Veiculo veiculo = Fachada.localizarVeiculoPorPlaca("JYQ-1219");
                            if (veiculo != null) {
                                List<Viagem> resultado2 = veiculo.getLista();
                                listagemViagens(resultado2);
                            } else {
                                label_4.setText("Veículo não encontrado.");
                            }
                            break;
                        case 2:
                            List<Motorista> resultado3 = Fachada.motoristasComMaisDeDuasViagens();
                            listagemMotoristas(resultado3);
                            break;
                    }
                }
            }
        });
        button.setBounds(606, 10, 89, 23);
        frame.getContentPane().add(button);

        comboBox = new JComboBox<>();
        comboBox.setToolTipText("selecione a consulta");
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Quais as viagens para a data 18/01/2023", "Quais as viagens com o veículo de placa JYQ-1219", "Quais os motoristas que têm mais de 2 viagens"}));
        comboBox.setBounds(21, 10, 513, 22);
        frame.getContentPane().add(comboBox);
    }

    public void listagemViagens(List<Viagem> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Data");
            model.addColumn("Veículo");
            model.addColumn("Motorista");
            model.addColumn("Destino");
            model.addColumn("Quantidade");

            for (Viagem viagem : lista) {
                String placaVeiculo = viagem.getVeiculo() != null ? viagem.getVeiculo().getPlaca() : "N/A";
                String nomeMotorista = viagem.getMotorista() != null ? viagem.getMotorista().getNome() : "N/A";
                model.addRow(new Object[]{viagem.getData(), placaVeiculo, nomeMotorista, viagem.getDestino(), viagem.getQuantidade()});
            }

            table.setModel(model);
            label_4.setText("Resultados: " + lista.size() + " viagens");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }

    public void listagemMotoristas(List<Motorista> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("CNH");
            model.addColumn("Nome");
            model.addColumn("Viagens");

            for (Motorista motorista : lista) {
                int numViagens = motorista.getLista().size();
                model.addRow(new Object[]{motorista.getCnh(), motorista.getNome(), numViagens});
            }

            table.setModel(model);
            label_4.setText("Resultados: " + lista.size() + " motoristas");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
}
