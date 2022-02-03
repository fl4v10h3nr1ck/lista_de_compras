package br.com.mscsolucoes.listadecompras.beans;

import br.com.mscsolucoes.listadecompras.DAO.anotacoes.Campo;
import br.com.mscsolucoes.listadecompras.DAO.anotacoes.Tabela;

@Tabela(nome="itens", prefixo="itm")
public class Item {


    @Campo(nome = "id", Inteiro = true, set = "setId", get = "getId", Id=true)
    private int id;

    @Campo(nome = "fk_compra", Inteiro = true, set = "setFk_compra", get = "getFk_compra")
    private int fk_compra;

    @Campo(nome = "fk_produto", Inteiro = true, set = "setFk_produto", get = "getFk_produto")
    private int fk_produto;


    @Campo(nome = "quant", Inteiro = true, set = "setQuant", get = "getQuant")
    private int quant;

    @Campo(nome = "valor_unitario", set = "setValor_unitario", get = "getValor_unitario")
    private String valor_unitario;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getFk_compra() {
        return fk_compra;
    }

    public void setFk_compra(int fk_compra) {
        this.fk_compra = fk_compra;
    }

    public int getFk_produto() {
        return fk_produto;
    }

    public void setFk_produto(int fk_produto) {
        this.fk_produto = fk_produto;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(String valor_unitario) {
        this.valor_unitario = valor_unitario;
    }
}
