package phs.com.circuitouniversitario;

public class evento {
    String nome, distancia;
    int background;

    public evento() {
    }

    public evento(String nome, String distancia, int background) {
        this.nome = nome;
        this.distancia = distancia;
        this.background = background;
    }

    public int getBackground() {
        return background;
    }
    public void setBackground(int background) {
        this.background = background;
    }

    public String getDistancia() {
        return distancia;
    }
    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }

}