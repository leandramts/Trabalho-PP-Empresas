import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class TelaCadastroTarefa extends JFrame {

    private Usuario usuarioLogado;

    public TelaCadastroTarefa(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

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
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnNaoIniciado = new JButton("Não inicializado");
        JButton btnEmAndamento = new JButton("Em andamento");
        JButton btnFinalizado = new JButton("Finalizado");

        statusPanel.add(btnNaoIniciado);
        statusPanel.add(btnEmAndamento);
        statusPanel.add(btnFinalizado);
        panel.add(statusPanel, gbc);

        final String[] statusSelecionado = {""};  //vai armazenar os status escolhido

        // Ações dos botões de statuss
        btnNaoIniciado.addActionListener(e -> {
            statusSelecionado[0] = "Não inicializado";
            btnNaoIniciado.setBackground(Color.red);
            btnEmAndamento.setBackground(null);
            btnFinalizado.setBackground(null);
        });

        btnEmAndamento.addActionListener(e -> {
            statusSelecionado[0] = "Em andamento";
            btnEmAndamento.setBackground(Color.yellow);
            btnNaoIniciado.setBackground(null);
            btnFinalizado.setBackground(null);
        });

        btnFinalizado.addActionListener(e -> {
            statusSelecionado[0] = "Finalizado";
            btnFinalizado.setBackground(Color.GREEN);
            btnNaoIniciado.setBackground(null);
            btnEmAndamento.setBackground(null);
        });

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
                String[] dataParts = dataField.getText().trim().split("/");
                if (statusSelecionado[0].isEmpty()) {
                    throw new IllegalArgumentException("Selecione um status antes de cadastrar!");
                }
                int dia = Integer.parseInt(dataParts[0]);
                int mes = Integer.parseInt(dataParts[1]);
                int ano = Integer.parseInt(dataParts[2]);

                Tarefa tarefa = new Tarefa(1, titulo, descricao, new Data(dia, mes, ano), statusSelecionado[0], usuarioLogado);
                salvarTarefaEmArquivo(tarefa);

                JOptionPane.showMessageDialog(this, "Tarefa cadastrada com sucesso!");

                
                Object[] opcoes = {"Sim", "Não"};
                int resposta = JOptionPane.showOptionDialog(
                this,
                "Deseja cadastrar outra tarefa?",
                "Confirmação",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                 opcoes,
                 opcoes[0]
                );


                if (resposta == JOptionPane.YES_OPTION) {
                    // Limpa os campos para novo cadastro
                    tituloField.setText("");
                    descricaoField.setText("");
                    dataField.setText("");
                    statusSelecionado[0] = "";
                    btnNaoIniciado.setBackground(null);
                    btnEmAndamento.setBackground(null);
                    btnFinalizado.setBackground(null);
                } else {
                    // Fecha a tela de cadastro e retorna ao login
                    dispose(); // Fecha a tela de cadastro
                    new TelaLogin();
                }

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
            writer.write("Responsável: " + tarefa.getResponsavel().getNome());
            writer.newLine();
            writer.write("-----------");
            writer.newLine();
        }
    }
}
