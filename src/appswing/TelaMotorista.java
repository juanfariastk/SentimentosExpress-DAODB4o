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

import regras_negocio.Fachada;
import model.Motorista;

public class TelaMotorista {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldCNH;
    private JTextField textFieldNome;
    private JButton button;
    private JButton button_1;
    private JButton button_2;
    private JLabel label;
    private JLabel labelResultados;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    @SuppressWarnings("unused")
					TelaMotorista tela = new TelaMotorista();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaMotorista() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Cadastro de Motoristas");
        frame.setBounds(100, 100, 729, 385);
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
        table.setBackground(Color.ORANGE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        label = new JLabel(""); // label de mensagem
        label.setForeground(Color.BLUE);
        label.setBounds(21, 321, 688, 14);
        frame.getContentPane().add(label);

        labelResultados = new JLabel("Resultados:");
        labelResultados.setBounds(21, 190, 431, 14);
        frame.getContentPane().add(labelResultados);

        JLabel label_2 = new JLabel("CNH:");
        label_2.setHorizontalAlignment(SwingConstants.LEFT);
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_2.setBounds(21, 267, 63, 14);
        frame.getContentPane().add(label_2);

        textFieldCNH = new JTextField();
        textFieldCNH.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCNH.setColumns(10);
        textFieldCNH.setBounds(68, 264, 195, 20);
        frame.getContentPane().add(textFieldCNH);

        JLabel label_3 = new JLabel("Nome:");
        label_3.setHorizontalAlignment(SwingConstants.LEFT);
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_3.setBounds(281, 267, 63, 14);
        frame.getContentPane().add(label_3);

        textFieldNome = new JTextField();
        textFieldNome.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldNome.setColumns(10);
        textFieldNome.setBounds(336, 264, 168, 20);
        frame.getContentPane().add(textFieldNome);

        button_1 = new JButton("Cadastrar Motorista");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textFieldCNH.getText().isEmpty() || textFieldNome.getText().isEmpty()) {
                        label.setText("Campos em branco");
                        return;
                    }
                    String cnh = textFieldCNH.getText();
                    String nome = textFieldNome.getText();

                    Motorista motorista = Fachada.cadastrarMotorista(cnh, nome);

                    if (motorista != null) {
                        label.setText("Motorista cadastrado: " + nome);
                        listagem();
                    } else {
                        label.setText("Erro ao cadastrar motorista.");
                    }
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_1.setBounds(525, 265, 153, 23);
        frame.getContentPane().add(button_1);

        button = new JButton("Listar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        button.setBounds(308, 11, 89, 23);
        frame.getContentPane().add(button);

        button_2 = new JButton("Apagar selecionado");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        label.setText("Não implementado");
                        String cnh = (String) table.getValueAt(table.getSelectedRow(), 0);

                        Fachada.excluirMotoristaPorCNH(cnh);

                        label.setText("Motorista apagado");
                        listagem();
                    } else
                        label.setText("Não selecionado");
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
            List<Motorista> lista = Fachada.listarMotoristas();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("CNH");
            model.addColumn("Nome");
            model.addColumn("Viagens");

            for (Motorista motorista : lista) {
                int numViagens = motorista.getLista().size();
                model.addRow(new Object[]{motorista.getCnh(), motorista.getNome(), numViagens});
            }

            table.setModel(model);
            labelResultados.setText("Resultados: " + lista.size() + " motoristas");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
}
