package com.mycompany.escola.model;

public class Professor extends Pessoa{
    
    private String cpf;
    private String turmas;
    
    public Professor(String nome, String sobrenome, int idade, String cpf, String turmas) {
        super(nome, sobrenome, idade);
        this.cpf = cpf;
        this.turmas = turmas;
    } 

    public Professor() {
        
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTurmas() {
        return turmas;
    }

    public void setTurmas(String turmas) {
        this.turmas = turmas;
    }

}
