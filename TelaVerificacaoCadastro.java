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
    String cpfDigitado = JOptionPane.showInputDialog(this, "Digite seu CPF:");

    if (cpfDigitado == null || cpfDigitado.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "CPF não pode estar vazio.");
        return;
    }

    // Limpa o CPF digitado para formato só números
    cpfDigitado = cpfDigitado.replace(".", "").replace("-", "").trim();

    PessoaFisica usuarioEncontrado = null;

    try (BufferedReader reader = new BufferedReader(new FileReader("CadastrarPessoas.txt"))) {
        String linha;
        String nome = "", email = "", cpf = "";
        int dia = 0, mes = 0, ano = 0;

        while ((linha = reader.readLine()) != null) {
            if (linha.startsWith("Nome: ")) {
                nome = linha.substring(6).trim();
            } else if (linha.startsWith("Email: ")) {
                email = linha.substring(7).trim();
            } else if (linha.startsWith("CPF: ")) {
                cpf = linha.substring(5).replace(".", "").replace("-", "").trim();
            } else if (linha.startsWith("Data de nascimento: ")) {
                String[] partes = linha.substring(20).trim().split("/");
                if (partes.length == 3) {
                    try {
                        dia = Integer.parseInt(partes[0]);
                        mes = Integer.parseInt(partes[1]);
                        ano = Integer.parseInt(partes[2]);
                    } catch (NumberFormatException e) {
                        dia = mes = ano = 0; // padrão caso erro
                    }
                }
            } else if (linha.equals("----------")) {
                // Verifica se CPF bate
                if (cpf.equals(cpfDigitado)) {
                    usuarioEncontrado = new PessoaFisica(1, nome, email, cpf, new Data(dia, mes, ano));
                    break;
                }
                // limpa para próximo cadastro
                nome = "";
                email = "";
                cpf = "";
                dia = mes = ano = 0;
            }
        }

        // Caso o arquivo não tenha "----------" após o último usuário, verifica também após a leitura
        if (usuarioEncontrado == null && !cpf.isEmpty()) {
            if (cpf.equals(cpfDigitado)) {
                usuarioEncontrado = new PessoaFisica(1, nome, email, cpf, new Data(dia, mes, ano));
            }
        }

    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo: " + ex.getMessage());
        return;
    }

    if (usuarioEncontrado != null) {
        new TelaCadastroTarefa(usuarioEncontrado);
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "CPF não encontrado. Faça o cadastro.");
        new TelaVerificacaoCadastro();
        dispose();
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaVerificacaoCadastro::new);
    }
}
