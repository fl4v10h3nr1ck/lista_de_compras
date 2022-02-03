package br.com.mscsolucoes.listadecompras.componentes;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.mscsolucoes.listadecompras.R;


/**
 * Created by fl4v10 on 07/07/2017.
 */

public class ComboBox extends AppCompatSpinner{


    private int cor_texto;

    private Context contexto;

    private ProcessadorDeEventosDeCombo processadorDeEventosDeCombo;




    public ComboBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ComboBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ComboBox(Context context) {
        super(context);
    }




    public void prepara(Context contexto) {

        prepara(contexto, null);
    }








    public void prepara(Context context, ProcessadorDeEventosDeCombo param){

        prepara(context, param, R.color.fonte_preta);
    }





    public void prepara(Context context, ProcessadorDeEventosDeCombo param, int cor_do_texto){

        this.contexto = context;

        this.cor_texto = cor_do_texto;

        this.processadorDeEventosDeCombo = param;

        this.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getChildAt(0) != null)
                    ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(contexto, cor_texto));

                if(processadorDeEventosDeCombo !=null)
                    processadorDeEventosDeCombo.aoMudarAEscolha();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }







    public void setDados(String[] valores){

        ArrayAdapterPerson adapter =  new ArrayAdapterPerson(contexto,android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //adapter_categoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.setAdapter(adapter);
    }






    private class ArrayAdapterPerson extends ArrayAdapter<String> {



        public ArrayAdapterPerson(Context context, int id, String[] valores){
            super(context, id, valores);
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);

                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setPadding(40, 45, 25, 45);

            return view;
        }


    }





    public class ProcessadorDeEventosDeCombo {



        public void aoMudarAEscolha(){}


    }
}
