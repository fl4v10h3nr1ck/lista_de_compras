package br.com.mscsolucoes.listadecompras.beans;

import br.com.mscsolucoes.listadecompras.DAO.anotacoes.Campo;
import br.com.mscsolucoes.listadecompras.DAO.anotacoes.Tabela;

import java.io.Serializable;
import java.util.Comparator;

@Tabela(nome="compras", prefixo="cmp")
public class Compra {


    @Campo(nome = "id", Inteiro = true, set = "setId", get = "getId", Id=true)
    private int id;

    @Campo(nome = "data", set = "setData", get = "getData")
    private String data;


    @Campo(nome = "valor_total", set = "setValor_total", get = "getValor_total")
    private String valor_total;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }
}
