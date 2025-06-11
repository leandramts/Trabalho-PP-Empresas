public class Tarefa {
    private int id;
    private String titulo;
    private String descricao;
    private Data data;
    private String status;
    private Usuario responsavel;

    public Tarefa() {
        // construtor vazio
    }

    public Tarefa(int id, String titulo, String descricao, Data data, String status, Usuario responsavel) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.responsavel = responsavel;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Usuario getResponsavel() { return responsavel; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }

    @Override
    public String toString() {
        return "Tarefa{" +
            "id=" + id +
            ", titulo='" + titulo + '\'' +
            ", descricao='" + descricao + '\'' +
            ", data=" + data +
            ", status='" + status + '\'' +
            ", responsavel=" + (responsavel != null ? responsavel.getNome() : "N/A") +
            '}';
    }
}
