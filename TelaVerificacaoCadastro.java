import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class TelaVerificacaoCadastro extends JFrame {

    public TelaVerificacaoCadastro() {
        setTitle("Verificação de Cadastro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JButton btnSim = new JButton("Já tenho cadastro");
        JButton btnNao = new JButton("Ainda não tenho cadastro");

        btnSim.addActionListener(e -> {
            verificarCPF();
        });

        btnNao.addActionListener(e -> {
            new TelaCadastroPessoaFisica(); // vai para o cadastro direto
            dispose();
        });

        add(new JLabel("Você já possui cadastro?"));
        add(btnSim);
        add(btnNao);

        setVisible(true);
    }

    private void verificarCPF() {
        String cpf = JOptionPane.showInputDialog(this, "Digite seu CPF:");

        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "CPF não pode estar vazio.");
            return;
        }

        boolean encontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("CadastrarPessoas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains("CPF: " + cpf)) {
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo.");
        }

        if (encontrado) {
            //JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            new TelaCadastroTarefa(new PessoaFisica(0, "Usuário", "email@email.com", cpf, null)); // substitua por dados reais se desejar
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "CPF não encontrado. Faça o cadastro.");
            new TelaVerificacaoCadastro(); // volta para a tela de verificação
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaVerificacaoCadastro::new);
    }
}
