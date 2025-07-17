package com.mycompany.escola.model;

public abstract class Pessoa {
    
    protected String nome;
    protected String sobrenome;
    protected int idade;

    public Pessoa(String nome, String sobrenome, int idade) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
    }
    
    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
    
     public int getIdade() {
        return idade;
    }
}
