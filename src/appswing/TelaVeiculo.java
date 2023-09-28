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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import model.Veiculo;
import regras_negocio.Fachada;

public class TelaVeiculo {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldPlaca;
    private JTextField textFieldCapacidade;
    private JButton button;
    private JButton button_2;
    private JLabel label;
    private JLabel labelCapacidade;
    private JLabel labelResultados;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    @SuppressWarnings("unused")
					TelaVeiculo tela = new TelaVeiculo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaVeiculo() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Cadastro de Veículos");
        frame.setBounds(100, 110, 729, 385);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Fachada.inicializar();
                listagem();
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
                label.setText("Selecionado: " + (String) table.getValueAt(table.getSelectedRow(), 0));
            }
        });
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.YELLOW);
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

        labelResultados = new JLabel("Resultados:");
        labelResultados.setBounds(21, 190, 431, 14);
        frame.getContentPane().add(labelResultados);

        textFieldPlaca = new JTextField();
        textFieldPlaca.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldPlaca.setColumns(10);
        textFieldPlaca.setBounds(68, 264, 195, 20);
        frame.getContentPane().add(textFieldPlaca);

        labelCapacidade = new JLabel("Placa:");
        labelCapacidade.setHorizontalAlignment(SwingConstants.LEFT);
        labelCapacidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelCapacidade.setBounds(21, 265, 46, 14);
        frame.getContentPane().add(labelCapacidade);

        textFieldCapacidade = new JTextField();
        textFieldCapacidade.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCapacidade.setColumns(10);
        textFieldCapacidade.setBounds(342, 264, 168, 20);
        frame.getContentPane().add(textFieldCapacidade);

        button = new JButton("Cadastrar Veículo");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textFieldPlaca.getText().isEmpty() || textFieldCapacidade.getText().isEmpty()) {
                        label.setText("Campos em branco");
                        return;
                    }
                    String placa = textFieldPlaca.getText();
                    int capacidade = Integer.parseInt(textFieldCapacidade.getText());

                    Veiculo veiculo = Fachada.cadastrarVeiculo(placa, capacidade);

                    if (veiculo != null) {
                        label.setText("Veículo cadastrado: " + placa);
                        listagem();
                    } else {
                        label.setText("Erro ao cadastrar veículo.");
                    }
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBounds(525, 262, 153, 23);
        frame.getContentPane().add(button);

        button = new JButton("Listar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        button.setBounds(308, 11, 89, 23);
        frame.getContentPane().add(button);

        labelCapacidade = new JLabel("Capacidade:");
        labelCapacidade.setHorizontalAlignment(SwingConstants.LEFT);
        labelCapacidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelCapacidade.setBounds(270, 265, 85, 14);
        frame.getContentPane().add(labelCapacidade);

        button_2 = new JButton("Apagar selecionado");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        label.setText("nao implementado");
                        String placa = (String) table.getValueAt(table.getSelectedRow(), 0);

                        Fachada.excluirVeiculoPorPlaca(placa);

                        label.setText("Veículo apagado");
                        listagem();
                    } else
                        label.setText("nao selecionado");
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_2.setBounds(281, 213, 171, 23);
        frame.getContentPane().add(button_2);
    }

    public void listagem() {
        try {
            List<Veiculo> lista = Fachada.listarVeiculos();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Placa");
            model.addColumn("Capacidade");
            model.addColumn("Viagens");

            for (Veiculo veiculo : lista) {
                int numViagens = veiculo.getLista().size();
                model.addRow(new Object[]{veiculo.getPlaca(), veiculo.getCapacidade(), numViagens});
            }

            table.setModel(model);
            labelResultados.setText("Resultados: " + lista.size() + " veículos");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
}
