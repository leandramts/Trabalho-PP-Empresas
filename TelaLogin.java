import java.awt.*;
import javax.swing.*;

public class TelaLogin extends JFrame {

    public TelaLogin() {
        setTitle("Sistema de Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton btnGerente = new JButton("Entrar como Gerente");
        JButton btnPessoaFisica = new JButton("Entrar como Pessoa Física");

        btnGerente.addActionListener(e -> {
            new TelaLoginGerente();
            dispose();
        });

        btnPessoaFisica.addActionListener(e -> {
            new TelaCadastroPessoaFisica();
            dispose();
        });

        add(btnGerente);
        add(btnPessoaFisica);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}
