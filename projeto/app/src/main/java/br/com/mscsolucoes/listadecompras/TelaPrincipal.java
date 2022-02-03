package br.com.mscsolucoes.listadecompras;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.mscsolucoes.listadecompras.DAO.DAO;
import br.com.mscsolucoes.listadecompras.beans.Compra;
import br.com.mscsolucoes.listadecompras.beans.Item;
import br.com.mscsolucoes.listadecompras.componentes.Tela;
import br.com.mscsolucoes.listadecompras.util.Calculo;
import br.com.mscsolucoes.listadecompras.util.Data;



public class TelaPrincipal extends Tela {



    private ListaAdapter lista_de_compras;

    private DAO<Compra> dao_compra;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.tl_principal);

        this.dao_compra= new DAO<Compra>(Compra.class, this);

        RecyclerView lista  = (RecyclerView) findViewById(R.id.lista_minhas_compras);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lista.setLayoutManager(layoutManager);

        this.lista_de_compras = new ListaAdapter(new ArrayList<>(0));
        lista.setAdapter(this.lista_de_compras);

        findViewById(R.id.bt_novo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(tela, TelaNovaCompra.class);
                i.putExtra("id_compra", 0);
                tela.startActivity(i);
            }
        });
    }



    public void onResume(){

        super.onResume();

        setDados();
    }




    private void mostraDados(){

        if(this.lista_de_compras.compras.size()>0) {

            findViewById(R.id.area_nada).setVisibility(View.GONE);
            findViewById(R.id.lista_minhas_compras).setVisibility(View.VISIBLE);
        }
        else{

            findViewById(R.id.area_nada).setVisibility(View.VISIBLE);
            findViewById(R.id.lista_minhas_compras).setVisibility(View.GONE);
        }
    }




    public void setDados() {

        this.lista_de_compras.atualizaLista(this.dao_compra.
                get(null, null, "cmp.data DESC, cmp.id DESC"));

        this.mostraDados();
    }




    public class Linha extends RecyclerView.ViewHolder{


        public TextView data;

        public TextView  valor;

        public View ref;


        public Linha(View itemView) {

            super(itemView);

            this.ref = itemView;

            this.data = (TextView) itemView.findViewById(R.id.data);

            this.valor = (TextView) itemView.findViewById(R.id.valor);
        }
    }





    public class ListaAdapter extends RecyclerView.Adapter<Linha> {



        private List<Compra> compras;




        public ListaAdapter(ArrayList compras) {

            this. compras = compras;
        }





        @Override
        public Linha onCreateViewHolder(ViewGroup parent, int viewType) {

            return new Linha(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_minhas_compras, parent, false));
        }




        @Override
        public void onBindViewHolder(final Linha item, int position) {

            final Compra compra = compras.get(position);

            item.data.setText(Data.converteEUAParaBR(compra.getData()));
            item.valor.setText("R$: "+Calculo.formataValor(compra.getValor_total()));


            item.ref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(tela, TelaNovaCompra.class);
                    i.putExtra("id_compra", compra.getId());
                    tela.startActivity(i);
                }
            });


            item.ref.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(tela);
                    alert.setMessage(R.string.dia_exclusao);
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            removeItem(compra);
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

            return compras != null ? compras.size() : 0;
        }



        public void atualizaLista(List<Compra> novas){

            if(novas!=null && novas.size()>0) {

                this.compras.clear();

                this.compras.addAll(novas);

                notifyDataSetChanged();
            }
        }




        public void removeItem(Compra compra){

            if(compra!=null) {

                int pos=  compras.indexOf(compra);

                if(pos>=0) {

                    this.compras.remove(compra);

                    notifyItemRemoved(pos);

                    new DAO<Item>(Item.class, tela).remove("fk_compra="+compra.getId());

                    dao_compra.remove(compra.getId());

                    mostraDados();
                }
            }
        }
    }



}
