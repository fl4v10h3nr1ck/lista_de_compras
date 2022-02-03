package br.com.mscsolucoes.listadecompras;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.mscsolucoes.listadecompras.DAO.DAO;
import br.com.mscsolucoes.listadecompras.beans.Compra;
import br.com.mscsolucoes.listadecompras.beans.Item;
import br.com.mscsolucoes.listadecompras.beans.Produto;
import br.com.mscsolucoes.listadecompras.componentes.ComboBox;
import br.com.mscsolucoes.listadecompras.componentes.MoneyTextWatcher;
import br.com.mscsolucoes.listadecompras.componentes.Tela;
import br.com.mscsolucoes.listadecompras.util.Calculo;
import br.com.mscsolucoes.listadecompras.util.Comuns;
import br.com.mscsolucoes.listadecompras.util.Data;


public class TelaNovaCompra extends Tela {


    private ListaAdapter lista_de_itens;

    private Compra compra;

    private List<Produto> produtos;

    private ComboBox produto;

    private EditText quantidade;

    private EditText valor_unitario;

    private DAO<Compra> dao_compra;

    private DAO<Item> dao_item;

    private String valor_total;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.tl_nova_compra);

        int id_compra = getIntent().getIntExtra("id_compra", 0);

        this.dao_compra  = new DAO<Compra>(Compra.class, this);

        this.dao_item  = new DAO<Item>(Item.class, this);

        this.compra = this.dao_compra.get(id_compra);

        if(this.compra==null)
            this.compra = new Compra();

        RecyclerView lista  = (RecyclerView) findViewById(R.id.lista_de_itens);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutManager);

        this.lista_de_itens = new ListaAdapter(new ArrayList<>(0));
        lista.setAdapter(this.lista_de_itens);

        this.produto = (ComboBox) findViewById(R.id.produto);
        this.produto.prepara(tela);
        this.produto.setDados(getProdutos());

        this.quantidade = findViewById(R.id.quantidade);
        this.quantidade.setText("1");

        this.valor_unitario = findViewById(R.id.valor_unitario);

        this.valor_unitario.addTextChangedListener(new MoneyTextWatcher(this.valor_unitario, new Locale("pt", "BR"), 5));


        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIdProdutoSelecionado()<=0){

                    Comuns.mensagem(tela, "Selecione um produto.");
                    return;
                }

                if(quantidade.getText().toString().length()==0 ||
                        !Calculo.stringENumeroNatural(quantidade.getText().toString()) ||
                            Integer.parseInt(quantidade.getText().toString())<=0){

                    Comuns.mensagem(tela, "Quantidade inválida.");
                    return;
                }

                if(valor_unitario.getText().toString().length()==0 || Calculo.stringZero(valor_unitario.getText().toString())){

                    Comuns.mensagem(tela, "Valor unitário inválido.");
                    return;
                }

                novoItem();
            }
        });

        findViewById(R.id.bt_add_produto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(tela, TelaAddProduto.class);
                tela.startActivityForResult(intent, 7);
            }
        });


        setDados();
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 7){

            this.produto.setDados(getProdutos());
        }
    }





    private String[] getProdutos(){

        this.produtos = new DAO<Produto>(Produto.class, this).get(
                    null,
                    null,
                    "pdt.nome ASC");


        String[] aux = new String[this.produtos.size()+1];

        aux[0] = " ... ";

        if(this.produtos.size()>0){

            for(int i = 0; i< this.produtos.size(); i++)
                aux[i + 1] = this.produtos.get(i).getNome();

        }

        return aux;
    }




    private int getIdProdutoSelecionado(){

        if(produto.getSelectedItemPosition()<=0)
            return 0;

        return this.produtos.get(produto.getSelectedItemPosition()-1).getId();
    }





    public void setDados() {

        this.lista_de_itens.atualizaLista(this.dao_item.
                get(null, "itm.fk_compra="+this.compra.getId(), "itm.id DESC"));

        this.mostraDados();

        this.setValorTotal();

        this.atualizaValorTotal();
    }




    private void mostraDados(){

        if(this.lista_de_itens.itens.size()>0) {

            findViewById(R.id.area_nada).setVisibility(View.GONE);
            findViewById(R.id.lista_de_itens).setVisibility(View.VISIBLE);
        }
        else{

            findViewById(R.id.area_nada).setVisibility(View.VISIBLE);
            findViewById(R.id.lista_de_itens).setVisibility(View.GONE);
        }
    }





    public void novoItem(){

        if( this.compra.getId()<=0){

            this.compra.setData(Data.getDataAtualEUA("-"));

            this.compra.setId(this.dao_compra.novo(this.compra));

            if( this.compra.getId()<=0){

                Comuns.mensagem(this, "Não foi possível criar uma nova compra.");
                return;
            }
        }

        Item novo = new Item();

        novo.setFk_compra(this.compra.getId());
        novo.setFk_produto(this.getIdProdutoSelecionado());
        novo.setQuant(Integer.parseInt(this.quantidade.getText().toString()));
        novo.setValor_unitario(this.valor_unitario.getText().toString());

        novo.setId(this.dao_item.novo(novo));

        if( novo.getId()<=0){

            Comuns.mensagem(this, "Não foi possível adicionar item.");
            return;
        }

        lista_de_itens.atualizaItem(novo);

        this.quantidade.setText("1");

        this.valor_unitario.setText("");

        this.produto.setSelection(0);



        setValorTotal();

        this.compra.setValor_total(this.valor_total);

        this.dao_compra.altera(this.compra);

        this.atualizaValorTotal();

        this.mostraDados();
    }




    private void setValorTotal(){

        this.valor_total = "0.00";

        for(Item item: this.lista_de_itens.itens)
            this.valor_total = Calculo.soma(this.valor_total, Calculo.multiplica(""+item.getQuant(), item.getValor_unitario()));

    }




    private void atualizaValorTotal() {

        ((TextView)findViewById(R.id.valor_total)).setText("R$: "+Calculo.formataValor(this.valor_total));
    }





    public class Linha extends RecyclerView.ViewHolder{


        public TextView produto;

        public TextView  quantidade;

        public TextView  valor_unitario;

        public TextView  valor_total;

        public View ref;


        public Linha(View itemView) {

            super(itemView);

            this.ref = itemView;

            this.produto = (TextView) itemView.findViewById(R.id.produto);

            this.quantidade = (TextView) itemView.findViewById(R.id.quantidade);

            this.valor_unitario = (TextView) itemView.findViewById(R.id.valor_unitario);

            this.valor_total = (TextView) itemView.findViewById(R.id.valor_total);
        }
    }





    public class ListaAdapter extends RecyclerView.Adapter<Linha> {



        private List<Item> itens;




        public ListaAdapter(ArrayList itens) {

            this.itens = itens;
        }





        @Override
        public Linha onCreateViewHolder(ViewGroup parent, int viewType) {

            return new Linha(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_de_itens, parent, false));
        }




        @Override
        public void onBindViewHolder(final Linha item, int position) {

            final Item item_lista = itens.get(position);

            Produto produto = new DAO<Produto>(Produto.class, tela).get(item_lista.getFk_produto());

            item.produto.setText(produto!=null?produto.getNome():"<<Não encontrado>>");

            item.quantidade.setText(""+item_lista.getQuant());

            item.valor_unitario.setText(Calculo.formataValor(Calculo.formataValor(item_lista.getValor_unitario())));

            item.valor_total.setText(Calculo.formataValor(Calculo.multiplica(item_lista.getValor_unitario(), ""+item_lista.getQuant())));


            item.ref.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(tela);
                    alert.setMessage(R.string.dia_exclusao);
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            removeItem(item_lista);
                            dialog.dismiss();
                        }
                    });

                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();

                    return true;
                }
            });
        }




        @Override
        public int getItemCount() {

            return itens != null ? itens.size() : 0;
        }



        public void atualizaLista(List<Item> novos){

            if(novos!=null && novos.size()>0) {

                this.itens.clear();

                this.itens.addAll(novos);

                notifyDataSetChanged();
            }
        }




        public void atualizaItem(Item novo){

            if(novo!=null) {

                this.itens.add(novo);

                this.notifyItemInserted(this.itens.size());
            }
        }




        public void removeItem(Item item){

            if(item!=null) {

                int pos=  itens.indexOf(item);

                if(pos>=0) {

                    this.itens.remove(item);

                    notifyItemRemoved(pos);

                    dao_item.remove(item.getId());

                    setValorTotal();

                    compra.setValor_total(valor_total);

                    dao_compra.altera(compra);

                    atualizaValorTotal();

                    mostraDados();
                }
            }
        }

    }


}
