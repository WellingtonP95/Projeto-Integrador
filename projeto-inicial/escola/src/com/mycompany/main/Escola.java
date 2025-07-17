package com.mycompany.main;

import com.mycompany.escola.model.Aluno;
import com.mycompany.escola.model.Gerenciador;
import com.mycompany.escola.model.Professor;

public class Escola {

    public static void main(String[] args) {
        
        Gerenciador gerenciador = new Gerenciador();
        
        Aluno aluno = new Aluno("João", "Silva", 16, 'A');
        Aluno aluno2 = new Aluno("Maria", "Joana", 14, 'B');
        
        gerenciador.adicionarPessoa(aluno);
        gerenciador.adicionarPessoa(aluno2);
        
        Professor prof = new Professor("Alberto", "Pereira", 38, "0540005698", "A, B");
        Professor prof2 = new Professor("Ednaldo", "Manga", 32, "0650006892", "A, B, C");
       
        gerenciador.adicionarPessoa(prof);
        gerenciador.adicionarPessoa(prof2);
       
        gerenciador.listarPessoas();
    }
}
