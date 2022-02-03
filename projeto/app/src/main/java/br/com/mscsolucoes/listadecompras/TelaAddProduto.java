package br.com.mscsolucoes.listadecompras;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.mscsolucoes.listadecompras.DAO.DAO;
import br.com.mscsolucoes.listadecompras.beans.Produto;
import br.com.mscsolucoes.listadecompras.componentes.Tela;
import br.com.mscsolucoes.listadecompras.util.Comuns;


public class TelaAddProduto extends Tela {

    private EditText nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.tl_add_produto);

        this.nome = findViewById(R.id.nome);


        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nome.getText().toString().length()==0){

                    Comuns.mensagem(tela, "Informe o nome do produto.");
                    return;
                }

                Produto novo = new Produto();

                novo.setNome(nome.getText().toString());
                novo.setId(new DAO<Produto>(Produto.class, tela).novo(novo));

                if( novo.getId()<=0){

                    Comuns.mensagem(tela, "Não foi possível adicionar o produto.");
                    return;
                }

                setResult(RESULT_OK);
                tela.finish();
            }
        });
    }



}
