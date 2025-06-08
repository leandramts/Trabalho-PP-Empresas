import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaCadastroTarefa extends JFrame {

    public TelaCadastroTarefa() {
        setTitle("Cadastro de Tarefa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        JTextField tituloField = new JTextField(20);
        panel.add(tituloField, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        JTextField descricaoField = new JTextField(20);
        panel.add(descricaoField, gbc);

        // Data
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Data (DD/MM/AAAA):"), gbc);
        gbc.gridx = 1;
        JTextField dataField = new JTextField(20);
        panel.add(dataField, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        JTextField statusField = new JTextField(20);
        panel.add(statusField, gbc);

        // Botão Cadastrar
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton cadastrarButton = new JButton("Cadastrar");
        panel.add(cadastrarButton, gbc);

        cadastrarButton.addActionListener(e -> {
            try {
                String titulo = tituloField.getText().trim();
                String descricao = descricaoField.getText().trim();
                String status = statusField.getText().trim();

                String[] dataParts = dataField.getText().trim().split("/");
                int dia = Integer.parseInt(dataParts[0]);
                int mes = Integer.parseInt(dataParts[1]);
                int ano = Integer.parseInt(dataParts[2]);

                Tarefa tarefa = new Tarefa(1, titulo, descricao, new Data(dia, mes, ano), status);
                salvarTarefaEmArquivo(tarefa);

                JOptionPane.showMessageDialog(this, "Tarefa cadastrada com sucesso!");
                tituloField.setText("");
                descricaoField.setText("");
                dataField.setText("");
                statusField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados! Verifique o formato.");
            }
        });

        add(panel);
        setVisible(true);
    }

    private void salvarTarefaEmArquivo(Tarefa tarefa) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CadastrarTarefas.txt", true))) {
            writer.write("Título: " + tarefa.getTitulo());
            writer.newLine();
            writer.write("Descrição: " + tarefa.getDescricao());
            writer.newLine();
            writer.write("Data: " + tarefa.getData());
            writer.newLine();
            writer.write("Status: " + tarefa.getStatus());
            writer.newLine();
            writer.write("-----------");
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCadastroTarefa::new);
    }
}
