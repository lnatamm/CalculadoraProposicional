public class Proposicao {
    private Character nome;
    private boolean valor;


    public Proposicao(Character nome) {
        this.nome = nome;
        valor = false;
    }

    public Proposicao(Character nome, boolean valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Proposicao(boolean valor) {
        this.nome = '0';
        this.valor = valor;
    }

    public Character getNome() {
        return nome;
    }

    public void setNome(Character nome) {
        this.nome = nome;
    }

    public boolean getValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return String.format("%b", valor);
    }
}