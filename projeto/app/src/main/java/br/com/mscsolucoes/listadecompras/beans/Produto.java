package br.com.mscsolucoes.listadecompras.beans;

import br.com.mscsolucoes.listadecompras.DAO.anotacoes.Campo;
import br.com.mscsolucoes.listadecompras.DAO.anotacoes.Tabela;

@Tabela(nome="produtos", prefixo="pdt")
public class Produto {


    @Campo(nome = "id", Inteiro = true, set = "setId", get = "getId", Id=true)
    private int id;

    @Campo(nome = "nome", set = "setNome", get = "getNome")
    private String nome;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
