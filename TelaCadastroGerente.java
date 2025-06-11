import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class TelaCadastroGerente extends JFrame {

    public TelaCadastroGerente() {
        setTitle("Cadastro de Gerente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        JTextField nomeField = new JTextField(20);
        panel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        JPasswordField senhaField = new JPasswordField(20);
        panel.add(senhaField, gbc);

        // Botão cadastrar
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnCadastrar = new JButton("Cadastrar");
        panel.add(btnCadastrar, gbc);

        btnCadastrar.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Gerentes.txt", true))) {
                writer.write(nome + ";" + email + ";" + senha);
                writer.newLine();
                //JOptionPane.showMessageDialog(this, "Gerente cadastrado com sucesso!");
                new TelaLoginGerente();
                dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar dados.");
            }
        });

        add(panel);
        setVisible(true);
    }
}
