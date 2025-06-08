public class PessoaFisica extends Usuario {
    private int cpf;
    private Data dataNascimento;

    public PessoaFisica(int id, String nome, String email, int cpf, Data dataNascimento) {
        super(id, nome, email); 
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }


    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public Data getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Data dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
