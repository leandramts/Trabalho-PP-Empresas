import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TelaMenuGerente extends JFrame {

    private JList<String> listaPessoas;
    private DefaultListModel<String> modelPessoas;
    private JTextArea areaTarefas;

    private List<PessoaFisica> pessoasFisicas; // para mapear nomes ao objeto PessoaFisica

    public TelaMenuGerente() {
        setTitle("Menu do Gerente - Visualizar Pessoas e Tarefas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        modelPessoas = new DefaultListModel<>();
        listaPessoas = new JList<>(modelPessoas);
        listaPessoas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        areaTarefas = new JTextArea();
        areaTarefas.setEditable(false);
        JScrollPane scrollTarefas = new JScrollPane(areaTarefas);

        JScrollPane scrollPessoas = new JScrollPane(listaPessoas);
        scrollPessoas.setPreferredSize(new Dimension(250, 0));

        add(scrollPessoas, BorderLayout.WEST);
        add(scrollTarefas, BorderLayout.CENTER);

        pessoasFisicas = carregarPessoas();

        for (PessoaFisica p : pessoasFisicas) {
            modelPessoas.addElement(p.getNome());
        }

        listaPessoas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = listaPessoas.getSelectedIndex();
                if (idx >= 0) {
                    PessoaFisica selecionada = pessoasFisicas.get(idx);
                    mostrarTarefas(selecionada);
                }
            }
        });

        setVisible(true);
    }

    private List<PessoaFisica> carregarPessoas() {
        List<PessoaFisica> pessoas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("CadastrarPessoas.txt"))) {
            String linha;
            String nome = "", email = "", cpf = "", dataNasc = "";
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Nome: ")) nome = linha.substring(6);
                else if (linha.startsWith("Email: ")) email = linha.substring(7);
                else if (linha.startsWith("CPF: ")) cpf = linha.substring(5);
                else if (linha.startsWith("Data de Nascimento: ")) dataNasc = linha.substring(20);
                else if (linha.startsWith("-----------")) {
                    // Criar PessoaFisica com dados lidos
                    String[] d = dataNasc.split("/");
                    PessoaFisica p = new PessoaFisica(0, nome, email, cpf, new Data(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2])));
                    pessoas.add(p);
                    nome = email = cpf = dataNasc = "";
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pessoas físicas:\n" + e.getMessage());
            e.printStackTrace();
        }
        return pessoas;
    }

    private void mostrarTarefas(PessoaFisica pessoa) {
        List<Tarefa> tarefas = carregarTarefasDeUsuario(pessoa);

        if (tarefas.isEmpty()) {
            areaTarefas.setText("Nenhuma tarefa cadastrada para " + pessoa.getNome());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Tarefas de ").append(pessoa.getNome()).append(":\n\n");
            for (Tarefa t : tarefas) {
                sb.append("Título: ").append(t.getTitulo()).append("\n");
                sb.append("Descrição: ").append(t.getDescricao()).append("\n");
                sb.append("Data: ").append(t.getData()).append("\n");
                sb.append("Status: ").append(t.getStatus()).append("\n");
                sb.append("------------------------------\n");
            }
            areaTarefas.setText(sb.toString());
        }
    }

    private List<Tarefa> carregarTarefasDeUsuario(Usuario usuario) {
        List<Tarefa> tarefas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("CadastrarTarefas.txt"))) {
            String linha;
            String titulo = "", descricao = "", data = "", status = "", responsavel = "";
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Título: ")) titulo = linha.substring(8);
                else if (linha.startsWith("Descrição: ")) descricao = linha.substring(11);
                else if (linha.startsWith("Data: ")) data = linha.substring(6);
                else if (linha.startsWith("Status: ")) status = linha.substring(8);
                else if (linha.startsWith("Responsável: ")) responsavel = linha.substring(12);
                else if (linha.startsWith("-----------")) {
                    if (responsavel.trim().equalsIgnoreCase(usuario.getNome().trim())) {
                        String[] d = data.split("/");
                        Tarefa t = new Tarefa(0, titulo, descricao, new Data(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2])), status, usuario);
                        tarefas.add(t);
                    }
                    titulo = descricao = data = status = responsavel = "";
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas:\n" + e.getMessage());
            e.printStackTrace();
        }
        return tarefas;
    }

    // Para teste rápido:
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaMenuGerente::new);
    }
}
