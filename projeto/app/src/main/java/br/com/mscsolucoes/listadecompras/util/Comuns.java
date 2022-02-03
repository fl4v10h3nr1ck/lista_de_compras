package br.com.mscsolucoes.listadecompras.util;


import android.content.Context;

import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.util.DisplayMetrics;
import android.widget.Toast;


import br.com.mscsolucoes.listadecompras.componentes.Tela;




/**
 * Created by fl4v10 on 03/10/2016.
 */

public class Comuns {


    public static SQLiteDatabase conexao;

    public static int tela_largura;
    public static int tela_altura;

    public static Tela telaAtual;












/*
    public static void setNotificacao(){

        if(telaAtual!=null){

            int cont = new DAO<Notificacao>(Notificacao.class, telaAtual).getCont(null, "ntf.status IS NULL or ntf.status<=0", null);

            telaAtual.setNotificacoes(cont);
        }
    }
*/






   /********* diversos *******/


    public static void setDimensoes(Tela tela) {

        DisplayMetrics dm = new DisplayMetrics();
        tela.getWindowManager().getDefaultDisplay().getMetrics(dm);

        Comuns.tela_largura = dm.widthPixels;
        Comuns.tela_altura = dm.heightPixels;
    }




    public static void mensagem(Context contexto, String msg){

    if(msg==null || msg.length()==0 || contexto==null)
        return;

        Toast.makeText(contexto, msg, Toast.LENGTH_LONG).show();
    }





    public static int convertDpToPixel(Context contexto, float dp){
        Resources resources =contexto.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }




    public static int convertPixelsToDp(Context contexto, float px){
        Resources resources = contexto.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) dp;
    }



    public static String removeUltimoSeparador(String valor, String separador) {

        if(separador ==null || separador.length()==0)
            return valor;

        if (valor.length() > 0) {

           if(valor.endsWith(separador)) {

               int posicao = valor.length() - separador.length();

               if(posicao==0)
                   return "";

               return valor.substring(0, posicao);
           }

           return valor;
        }

        return "";
    }




    public static boolean temValor(String valor) {

        return valor!=null && valor.length()>0;
    }




    public static String addPaddingAEsquerda(String valor, int num, String add){


        for(int i  = valor.length(); i < num; i++)
            valor = add+valor;

        return valor;
    }





    public static String addPaddingADireita(String valor, int num, String add){


        for(int i  = valor.length(); i < num; i++)
            valor += add;

        return valor;
    }





}
