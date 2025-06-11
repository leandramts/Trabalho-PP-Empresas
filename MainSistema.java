import javax.swing.SwingUtilities;

public class MainSistema {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin();  // Abre a tela inicial de login
        });
    }
}
