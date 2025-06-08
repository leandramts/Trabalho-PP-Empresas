import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TelaCadastroEmpresa extends JFrame {

    public TelaCadastroEmpresa() {
        setTitle("Cadastro de Empresa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null); // Centraliza

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);

        // Nome
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(labelFont);
        panel.add(lblNome, gbc);

        gbc.gridx = 1;
        JTextField nomeField = new JTextField(20);
        panel.add(nomeField, gbc);

        // Razão Social
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblRazao = new JLabel("Razão Social:");
        lblRazao.setFont(labelFont);
        panel.add(lblRazao, gbc);

        gbc.gridx = 1;
        JTextField razaoField = new JTextField(20);
        panel.add(razaoField, gbc);

        // CNPJ
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblCnpj = new JLabel("CNPJ:");
        lblCnpj.setFont(labelFont);
        panel.add(lblCnpj, gbc);

        gbc.gridx = 1;
        JTextField cnpjField = new JTextField(20);
        panel.add(cnpjField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        panel.add(lblEmail, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Botão
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnCadastrar = new JButton("Cadastrar");
        panel.add(btnCadastrar, gbc);

        // Ação do botão
        btnCadastrar.addActionListener(e -> {
            try {
                int cnpj = Integer.parseInt(cnpjField.getText().trim());
                String razao = razaoField.getText().trim();
                String nome = nomeField.getText().trim();
                String email = emailField.getText().trim();

                Empresa empresa = new Empresa(1, nome, email, cnpj, razao);

                salvarEmpresaEmArquivo(empresa);

                JOptionPane.showMessageDialog(this, "Empresa cadastrada e salva com sucesso!");

                // Limpa os campos
                nomeField.setText("");
                razaoField.setText("");
                cnpjField.setText("");
                emailField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "CNPJ inválido! Digite apenas números.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a empresa no arquivo.");
            }
        });

        add(panel);
        setVisible(true);
    }

    private void salvarEmpresaEmArquivo(Empresa empresa) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CadastrarEmpresas.txt", true))) {
            writer.write("Nome: " + empresa.getNome());
            writer.newLine();
            writer.write("Razão Social: " + empresa.getRazaoSocial());
            writer.newLine();
            writer.write("CNPJ: " + empresa.getCnpj());
            writer.newLine();
            writer.write("Email: " + empresa.getEmail());
            writer.newLine();
            writer.write("-----------");
            writer.newLine();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCadastroEmpresa::new);
    }
}
