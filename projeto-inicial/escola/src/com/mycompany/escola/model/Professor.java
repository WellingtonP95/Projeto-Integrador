package com.mycompany.escola.model;

public class Professor extends Pessoa{
    
    private final String cpf;
    private String turmas;
    
    public Professor(String nome, String sobrenome, int idade, String cpf, String turmas) {
        super(nome, sobrenome, idade);
        this.cpf = cpf;
        this.turmas = turmas;
    } 
    
    public String getCpf() {
        return cpf;
    }

    public String getTurmas() {
        return turmas;
    }

    public void setTurmas(String turmas) {
        this.turmas = turmas;
    }

}
