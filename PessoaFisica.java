
public class PessoaFisica extends Usuario {
    private String cpf;
    private Data dataNascimento;

    public PessoaFisica(int id, String nome, String email, String cpf, Data dataNascimento) {
        super(id, nome, email); 
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Data getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Data dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
