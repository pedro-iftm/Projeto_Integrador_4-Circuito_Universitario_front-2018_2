package phs.com.circuitouniversitario;

public class evento {
    String nome;
    int background;

    public evento(String nome, int background) {
        this.nome = nome;
        this.background = background;
    }

    public int getBackground() {
        return background;
    }
    public void setBackground(int background) {
        this.background = background;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

}