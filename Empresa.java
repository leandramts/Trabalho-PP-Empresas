public class Empresa extends Usuario {
    private int cnpj;
    private String razaoSocial;

    public Empresa(int id, String nome, String email, int cnpj, String razaoSocial) {
        super(id, nome, email); 
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

  
    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}

