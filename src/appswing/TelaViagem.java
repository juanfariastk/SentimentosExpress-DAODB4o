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

import model.Motorista;
import model.Veiculo;
import model.Viagem;
import regras_negocio.Fachada;

public class TelaViagem {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldData;
    private JTextField textFieldPlacaCarro;
    private JTextField textFieldNomeMotorista;
    private JTextField textFieldDestino;
    private JTextField textFieldQuantidade;
    private JButton buttonCadastrar;
    private JButton buttonApagar;
    private JLabel label;
    private JLabel labelResultados;
    private JButton buttonListar;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    @SuppressWarnings("unused")
					TelaViagem tela = new TelaViagem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaViagem() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Viagem");
        frame.setBounds(100, 100, 729, 480);
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
                labelResultados.setText("selecionado=" + (int) table.getValueAt(table.getSelectedRow(), 0));
            }
        });
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(new Color(144, 238, 144));
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
        label.setBounds(12, 395, 688, 14);
        frame.getContentPane().add(label);

        labelResultados = new JLabel("resultados:");
        labelResultados.setBounds(21, 210, 431, 14);
        frame.getContentPane().add(labelResultados);

        JLabel labelData = new JLabel("Data:");
        labelData.setHorizontalAlignment(SwingConstants.LEFT);
        labelData.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelData.setBounds(12, 269, 89, 14);
        frame.getContentPane().add(labelData);

        textFieldData = new JTextField();
        textFieldData.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldData.setColumns(10);
        textFieldData.setBounds(103, 266, 195, 20);
        frame.getContentPane().add(textFieldData);

        buttonCadastrar = new JButton("Cadastrar Nova Viagem");
        buttonCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textFieldData.getText().isEmpty() || textFieldPlacaCarro.getText().isEmpty() || textFieldNomeMotorista.getText().isEmpty()
                            || textFieldDestino.getText().isEmpty() || textFieldQuantidade.getText().isEmpty()) {
                        label.setText("Campo vazio");
                        return;
                    }
                    String data = textFieldData.getText();
                    String placaCarro = textFieldPlacaCarro.getText();
                    String nomeMotorista = textFieldNomeMotorista.getText();
                    String destino = textFieldDestino.getText();
                    int quantidade = Integer.parseInt(textFieldQuantidade.getText());

                    Veiculo veiculo = Fachada.localizarVeiculoPorPlaca(placaCarro);
                    Motorista motorista = Fachada.localizarMotoristaPorNome(nomeMotorista);

                    if (veiculo == null) {
                        label.setText("Veículo não encontrado");
                        return;
                    }

                    if (motorista == null) {
                        label.setText("Motorista não encontrado");
                        return;
                    }

                    Viagem viagem = Fachada.cadastrarViagem(data, veiculo, motorista, destino, quantidade);

                    if (viagem != null) {
                        label.setText("Viagem criada");
                        listagem();
                    } else {
                        label.setText("Erro ao criar viagem");
                    }
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });

        buttonCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonCadastrar.setBounds(275, 355, 175, 23);
        frame.getContentPane().add(buttonCadastrar);

        buttonListar = new JButton("Listar");
        buttonListar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        buttonListar.setBounds(315, 11, 89, 23);
        frame.getContentPane().add(buttonListar);

        JLabel labelPlacaCarro = new JLabel("Placa do carro:");
        labelPlacaCarro.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelPlacaCarro.setBounds(12, 295, 89, 16);
        frame.getContentPane().add(labelPlacaCarro);

        textFieldPlacaCarro = new JTextField();
        textFieldPlacaCarro.setBounds(130, 295, 130, 19);
        frame.getContentPane().add(textFieldPlacaCarro);
        textFieldPlacaCarro.setColumns(10);

        JLabel labelNomeMotorista = new JLabel("Nome do Motorista:");
        labelNomeMotorista.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelNomeMotorista.setBounds(12, 323, 130, 16);
        frame.getContentPane().add(labelNomeMotorista);

        textFieldNomeMotorista = new JTextField();
        textFieldNomeMotorista.setBounds(147, 323, 130, 19);
        frame.getContentPane().add(textFieldNomeMotorista);
        textFieldNomeMotorista.setColumns(10);

        JLabel labelDestino = new JLabel("Destino:");
        labelDestino.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelDestino.setBounds(318, 265, 89, 16);
        frame.getContentPane().add(labelDestino);

        textFieldDestino = new JTextField();
        textFieldDestino.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldDestino.setColumns(10);
        textFieldDestino.setBounds(392, 265, 130, 19);
        frame.getContentPane().add(textFieldDestino);

        JLabel labelQuantidade = new JLabel("Quantidade de Pessoas:");
        labelQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
        labelQuantidade.setBounds(300, 295, 140, 16);
        frame.getContentPane().add(labelQuantidade);

        textFieldQuantidade = new JTextField();
        textFieldQuantidade.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldQuantidade.setColumns(10);
        textFieldQuantidade.setBounds(435, 295, 97, 19);
        frame.getContentPane().add(textFieldQuantidade);

        buttonApagar = new JButton("Apagar selecionado");
        buttonApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {
                        int idViagem = (int) table.getValueAt(table.getSelectedRow(), 0);

                        Fachada.excluirViagemPorId(idViagem);
                        label.setText("Viagem apagada");
                    } else {
                        label.setText("Nenhuma viagem selecionada");
                    }
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });

        buttonApagar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        buttonApagar.setBounds(275, 215, 171, 23);
        frame.getContentPane().add(buttonApagar);
    }

    public void listagem() {
        try {
            List<Viagem> lista = Fachada.listarViagens();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Data");
            model.addColumn("Veículo");
            model.addColumn("Motorista");
            model.addColumn("Destino");
            model.addColumn("Quantidade");

            for (Viagem viagem : lista) {
                model.addRow(new Object[]{
                        viagem.getId(),
                        viagem.getData(),
                        viagem.getVeiculo().getPlaca(),
                        viagem.getMotorista().getNome(),
                        viagem.getDestino(),
                        viagem.getQuantidade()
                });
            }

            table.setModel(model);

            labelResultados.setText("resultados: " + lista.size() + " viagens");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
}
