import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaCadastroPessoaFisica extends JFrame {

    public TelaCadastroPessoaFisica() {
        setTitle("Cadastro de Pessoa Física");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 320);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        JTextField nomeField = new JTextField(20);
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        JTextField cpfField = new JTextField(20);
        panel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Data de Nascimento:"), gbc);
        gbc.gridx = 1;
        JTextField dataField = new JTextField(20);
        panel.add(dataField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnCadastrar = new JButton("Cadastrar");
        panel.add(btnCadastrar, gbc);

        btnCadastrar.addActionListener(e -> {
            try {
                String nome = nomeField.getText().trim();
                String email = emailField.getText().trim();
                String cpf = cpfField.getText().trim();

                String[] partes = dataField.getText().trim().split("/");
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int ano = Integer.parseInt(partes[2]);

                PessoaFisica pf = new PessoaFisica(1, nome, email, cpf, new Data(dia, mes, ano));

                salvarPessoaEmArquivo(pf);
                JOptionPane.showMessageDialog(this, "Pessoa cadastrada com sucesso!");

                nomeField.setText("");
                emailField.setText("");
                cpfField.setText("");
                dataField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados! Verifique o formato.");
            }
        });

        add(panel);
        setVisible(true);
    }

    private void salvarPessoaEmArquivo(PessoaFisica pf) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CadastrarPessoas.txt", true))) {
            writer.write("Nome: " + pf.getNome());
            writer.newLine();
            writer.write("Email: " + pf.getEmail());
            writer.newLine();
            writer.write("CPF: " + pf.getCpf());
            writer.newLine();
            writer.write("Data de Nascimento: " + pf.getDataNascimento());
            writer.newLine();
            writer.write("-----------");
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCadastroPessoaFisica::new);
    }
}
