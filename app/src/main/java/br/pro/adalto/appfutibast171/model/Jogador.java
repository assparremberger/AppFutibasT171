package br.pro.adalto.appfutibast171.model;

/**
 * Created by assparremberger on 25/09/2018.
 */

public class Jogador {

    private String id, nome;
    private int idade;

    @Override
    public String toString() {
        return nome + "\n" + idade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
