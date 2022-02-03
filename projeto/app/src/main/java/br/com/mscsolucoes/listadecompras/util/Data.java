package br.com.mscsolucoes.listadecompras.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class Data {

	
	
	
	public static java.sql.Date getSqlData(java.util.Date data){
		
	return data == null? null: new java.sql.Date(data.getTime());	
	}
	
	
		
	
	
	public static String getDataAtual(String delimitador){
		
	return new SimpleDateFormat("dd"+delimitador+"MM"+delimitador+"yyyy").format(new Date());
	}




	public static String getDataAtualEUA(String delimitador){

		return new SimpleDateFormat("yyyy"+delimitador+"MM"+delimitador+"dd").format(new Date());
	}


	
	
	
	
	public static String converteDataParaString(Date aux){
		
	return aux== null?"":new SimpleDateFormat("dd/MM/yyyy").format(aux);
	}
	
	
	
	
	

	public static String getAnoAtual(){
		
	return new SimpleDateFormat("yyyy").format(new Date());
	}






	public static String getMesAtual(){

		return new SimpleDateFormat("MM").format(new Date());
	}





	public static Date converteStringParaData(String aux){
	
	if(aux==null)
	return null;
		
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
	try {return format.parse(aux);} 
	catch (ParseException e) {return null;}
	}
	
	
	
	
	
	
	
	public static int comparaDatas(Date d_inicial, Date d_final){
	
	if(d_inicial ==null || d_final==null)	
	return -2;	
	
	if(d_inicial.before(d_final))
	return -1;
	
	if(d_inicial.after(d_final))
	return 1;
	
	return 0;
	}
	
	

	
	

	public static int comparaDatas(String d_inicial, String d_final){
	
	return Data.comparaDatas(Data.converteStringParaData(d_inicial), Data.converteStringParaData(d_final));
	}



	public static int getHoraAtual(){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
		return calendar.get(Calendar.HOUR_OF_DAY);
	}




	public static int getMinAtual(){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
		return calendar.get(Calendar.MINUTE);
	}



	public static int getSegAtual(){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
		return calendar.get(Calendar.SECOND);
	}





	public static String converteEUAParaBR(String aux){

		if(aux==null || aux.length()!=10)
			return "";

        if(aux.charAt(2)=='/' && aux.charAt(5)=='/')
            return aux;


		return aux.substring(8)+"/"+aux.substring(5, 7)+"/"+aux.substring(0, 4);
	}




	public static String converteBRParaEUA(String aux){

		if(aux==null || aux.length()!=10)
			return "";

		if(aux.charAt(4)=='-' && aux.charAt(7)=='-')
			return aux;

		return aux.substring(6)+"-"+aux.substring(3, 5)+"-"+aux.substring(0, 2);
	}




    public static boolean validaData (String data){

        if(data == null || data.length()!=10)
            return false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try { dateFormat.parse(data); }
        catch(ParseException e) {return false;}

        return true;
    }

	
	
}
