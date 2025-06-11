import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class TelaLoginGerente extends JFrame {

    private static final String SENHA_MESTRE = "admin123";

    private JTextField emailField;
    private JPasswordField senhaField;

    public TelaLoginGerente() {
        setTitle("Login Gerente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        senhaField = new JPasswordField(20);
        panel.add(senhaField, gbc);

        // Botão Login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnLogin = new JButton("Entrar");
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
    String email = emailField.getText().trim();
    String senha = new String(senhaField.getPassword()).trim();

    if (email.isEmpty() || senha.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
        return;
    }

    if (autenticarGerente(email, senha)) {
        JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");

        // Cria um objeto Usuario para o gerente
        // Aqui você pode modificar para pegar o nome do gerente do arquivo ou fixo por enquanto
        Usuario gerente = new Usuario(1, "Gerente", email);

        // Abre o menu do gerente
        new TelaMenuGerente();

        // Fecha a tela de login
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Email ou senha incorretos.");
    }
});

        // Botão para cadastro protegido por senha mestre
        gbc.gridy = 3;
        JButton btnCadastrarGerente = new JButton("Cadastrar novo gerente");
        panel.add(btnCadastrarGerente, gbc);

        btnCadastrarGerente.addActionListener(e -> {
            String senhaMestre = JOptionPane.showInputDialog(this, "Digite a senha mestre para cadastrar gerente:");
            if (senhaMestre != null && senhaMestre.equals(SENHA_MESTRE)) {
                new TelaCadastroGerente();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Senha mestre incorreta!");
            }
        });

        add(panel);
        setVisible(true);
    }

    private boolean autenticarGerente(String email, String senha) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Gerentes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    String emailArquivo = partes[1];
                    String senhaArquivo = partes[2];

                    if (emailArquivo.equals(email) && senhaArquivo.equals(senha)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar arquivo de gerentes.");
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLoginGerente::new);
    }
}
