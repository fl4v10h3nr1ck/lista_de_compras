package br.com.mscsolucoes.listadecompras.componentes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import br.com.mscsolucoes.listadecompras.R;



/**
 * Created by fl4v10 on 02/12/2016.
 */

public abstract class Tela<T> extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{


    private volatile boolean bloqueado;

    private ProgressDialog load;

    public Tela tela;



    public Tela(){

        this.tela = this;

        this.desbloqueia();
    }






    public final void iniciaCarregamento(boolean mostrarProgresso) {


        if(!mostrarProgresso)
            carregando();
        else {

            load  = new ProgressDialog(this, R.style.dialogo_progresso);
            load.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            load.setMessage("Aguarde...");
            load.setCancelable(false);
            load.setCanceledOnTouchOutside(false);

            bloqueia();

            carregando();
        }
    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }




    public void carregando(){}






    public final void bloqueia(){

        if(load!=null)
            this.load.show();

        this.bloqueado = true;
    }





    public final void desbloqueia(){

        if(load!=null)
            this.load.dismiss();

        this.bloqueado = false;
    }





    public final boolean estaBloqueado(){

    return    this.bloqueado;
    }






    public void seOcorrerUmErro(){

        this.desbloqueia();
    }






    public void setTelaDeErro(){

        this.desbloqueia();

        setContentView(R.layout.lay_erro);

        Button bt_voltar = (Button) findViewById(R.id.bt_voltar_tela_erro);

        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acaoBotaoVoltarDeTelaDeErro();
            }
        });
    }






    public void acaoBotaoVoltarDeTelaDeErro(){

        finish();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        retornoDePermissao(requestCode);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }





    public void retornoDePermissao(int permissao_pedida){}




}
