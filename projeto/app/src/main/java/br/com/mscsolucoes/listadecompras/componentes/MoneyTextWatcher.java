package br.com.mscsolucoes.listadecompras.componentes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.mscsolucoes.listadecompras.util.Calculo;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by fl4v10 on 04/09/2017.
 */

public class MoneyTextWatcher implements TextWatcher {

    private final WeakReference<EditText> editTextWeakReference;
    private final Locale locale;

    private int comprimento;

    public MoneyTextWatcher(EditText editText, Locale locale, int comprimento) {
        this.editTextWeakReference = new WeakReference<EditText>(editText);
        this.locale = locale != null ? locale : Locale.getDefault();

        this.comprimento = comprimento;
    }

    public MoneyTextWatcher(EditText editText) {
        this.editTextWeakReference = new WeakReference<EditText>(editText);
        this.locale = Locale.getDefault();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {


        EditText editText = editTextWeakReference.get();
        if (editText == null) return;
        editText.removeTextChangedListener(this);
/*
        BigDecimal parsed = parseToBigDecimal(editable.toString(), locale);

        //parseToBigDecimal(editable.toString(), locale)
        String formatted = NumberFormat.getInstance(locale).format(parsed);
        //NumberFormat.getCurrencyInstance(locale).format(formatted);
*/

        String value = editable.toString().replaceAll("[^0123456789]", "");

        if(value.length()>comprimento)
            value = value.substring(0, comprimento);

        if(value.length()==0)
            value = "0";

        else if(value.length()==1)
            value = "0.0"+value;

        else if(value.length()==2)
            value = "0."+value;

        else
            value = value.substring(0, value.length()-2)+"."+value.substring(value.length()-2, value.length());


        String formatted = Calculo.formataValor(value);

        editText.setText(formatted);

        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }








    private BigDecimal parseToBigDecimal(String value, Locale locale) {

        if(value==null)
            value = "0";

        value = value.replaceAll("[^0123456789]", "");

        if(value.length()==0)
            value = "0";

        else if(value.length()==1)
            value = "0.0"+value;

        else if(value.length()==2)
            value = "0."+value;

        else
            value = value.substring(0, value.length()-2)+"."+value.substring(value.length()-2, value.length());


        //String replaceable = String.format("[%s.,\\s]", "");

        //String cleanString = value.replaceAll(replaceable, "");

        //return new BigDecimal(value).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);

        return new BigDecimal(value);
    }
}