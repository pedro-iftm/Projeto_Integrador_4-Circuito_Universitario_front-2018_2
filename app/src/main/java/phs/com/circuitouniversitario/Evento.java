package phs.com.circuitouniversitario;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Evento implements Serializable {
    @SerializedName("idEvento")
    Integer idEvento;

    @SerializedName("nomeEvento")
    String nome;
    @SerializedName("descricaoEvento")
    String endereco;
    @SerializedName("dataEvento")
    String data;
    @SerializedName("enderecoEvento")
    String descricao;

    public Evento(Integer idEvento, String nomeEvento, String descricaoEvento, String dataEvento, String enderecoEvento) {
        this.idEvento = idEvento;
        this.nome = nomeEvento;
        this.descricao = descricaoEvento;
        this.data = dataEvento;
        this.endereco = enderecoEvento;

    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }
}