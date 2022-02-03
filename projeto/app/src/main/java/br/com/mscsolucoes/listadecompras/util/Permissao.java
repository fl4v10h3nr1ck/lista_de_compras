package br.com.mscsolucoes.listadecompras.util;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

import br.com.mscsolucoes.listadecompras.componentes.Tela;


/**
 * Created by fl4v10 on 06/10/2016.
 */



public class Permissao{


    public static final int PERMISSAO_PARA_ARQUIVO= 17;
    public static final int PERMISSAO_PARA_CAMERA= 27;
    public static final int PERMISSAO_PARA_LOCALIZACAO= 57;

    private Tela tela;

    public int ult_permissao_solicitada;






    public Permissao(Tela tela){

        this.ult_permissao_solicitada = 0;

        this.tela = tela;
    }





    public boolean temPermissao(int tipo_permissao){

        boolean permissao = true;

        if (Build.VERSION.SDK_INT >= 23) {

            if(tipo_permissao == PERMISSAO_PARA_ARQUIVO) {

                if (tela.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE")
                        != PackageManager.PERMISSION_GRANTED)
                    permissao = false;
            }

            if(tipo_permissao == PERMISSAO_PARA_CAMERA) {

                if (tela.checkSelfPermission("android.permission.CAMERA")
                        != PackageManager.PERMISSION_GRANTED)
                    permissao = false;
            }

            if(tipo_permissao == PERMISSAO_PARA_LOCALIZACAO) {

                if (tela.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION")
                        != PackageManager.PERMISSION_GRANTED)
                    permissao = false;
            }

        }

        return permissao;
    }






    public void solicitaPermissao(int tipo_permissao){

        this.ult_permissao_solicitada = 0;

        if (Build.VERSION.SDK_INT >= 23) {

            String[] permissoes = null;

            if(tipo_permissao == PERMISSAO_PARA_ARQUIVO) {
                permissoes = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                this.ult_permissao_solicitada = PERMISSAO_PARA_ARQUIVO;
            }
            else if(tipo_permissao == PERMISSAO_PARA_CAMERA) {
                permissoes = new String[]{Manifest.permission.CAMERA};
                this.ult_permissao_solicitada = PERMISSAO_PARA_CAMERA;
            }
            else if(tipo_permissao == PERMISSAO_PARA_LOCALIZACAO) {
                permissoes = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                this.ult_permissao_solicitada = PERMISSAO_PARA_LOCALIZACAO;
            }


            if(permissoes!=null && permissoes.length>0)
                tela.requestPermissions(permissoes, this.ult_permissao_solicitada);
        }
    }




}