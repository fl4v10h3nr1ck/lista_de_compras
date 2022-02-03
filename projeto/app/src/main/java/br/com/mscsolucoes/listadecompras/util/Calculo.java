package br.com.mscsolucoes.listadecompras.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Pattern;


public abstract class Calculo {

	
	
	
	
	
	public static String getNumero(String valor){
		
	if(valor == null || valor.length() == 0 || valor.replaceAll("\\D", "").compareTo("")==0)
	return "0";

	if(!valor.contains(",") && !valor.contains("."))
	return valor.replaceAll("\\D", "");
	
	
	boolean negativo = valor.charAt(0)=='-';

	
	String partes[] = null; 
	
	if(valor.contains(","))
	partes = valor.split(",");
	else{
	partes = valor.split("\\.");
	}
	
	String aux  = partes[0].replaceAll("\\D", "");

	for(int i = 1 ; i < partes.length-1; i++)
	aux += partes[i].replaceAll("\\D", "");
	
	return (negativo?"-":"")+aux+(partes.length>1?"."+partes[partes.length-1].replaceAll("\\D", ""):"");
	}
	
	
	
	
	
	
	public static BigDecimal converteStringParaBigDecimal(String valor ){
				
	return new 	BigDecimal(Calculo.getNumero(valor));	
	}
	


	
	
	
	public static boolean stringZero( String valor){
		
	if(valor == null || valor.length() == 0)
	return true;
		
	
		for( int i = 0; i < valor.length(); i++){
		
		if( valor.charAt( i) != '0' && 
				valor.charAt( i) != '.' && 
					valor.charAt( i) != ',' && 
						valor.charAt( i) != '-')
		return false;	
		}
	

	return true;
	}
	

	

		
	public static Double stringParaDouble( String valor ){ 
			
	if(valor == null || valor.length() == 0)
	return 0.0;
				
	return Double.parseDouble(Calculo.getNumero(valor));
	}
	
	
	
	
		
	
	public static  boolean stringENumeroNatural(String valor){
	
	if(valor==null || valor.length()==0)	
	return false;
		
	String aux = valor.replaceAll("\\D", "");	
		
	return aux!=null && aux.length()==valor.length();
	}
	
	
	

	
	
	public static  boolean stringENumero(String aux){
	
	if(aux == null || aux.length() == 0)
	return false;
	
		for(int i=0; i < aux.length(); i++){
		
		if(aux.charAt(i)!='0'&&
				aux.charAt(i)!='1'&&
					aux.charAt(i)!='2'&&
						aux.charAt(i)!='3'&&
							aux.charAt(i)!='4'&&
								aux.charAt(i)!='5'&&
									aux.charAt(i)!='6'&&
										aux.charAt(i)!='7'&&
											aux.charAt(i)!='8'&&
												aux.charAt(i)!='9' &&
													aux.charAt(i)!='.' &&
														aux.charAt(i)!=',' &&
															aux.charAt(i)!='-')	
		return false;
		}
	
	return true;
	}
	
	
	

	
	
	
	public static String soma( String valor1, String valor2 ){ 

	return String.valueOf( converteStringParaBigDecimal(valor1).add( converteStringParaBigDecimal(valor2)));	
	}


	
	
	

	public static String subtrai( String valor1, String valor2 ){ 

	return String.valueOf( converteStringParaBigDecimal(valor1).subtract( converteStringParaBigDecimal(valor2)));
	}
		
		
	
	

	
	public static String multiplica( String valor1, String valor2 ){ 
		
	return String.valueOf( converteStringParaBigDecimal(valor1).multiply( converteStringParaBigDecimal(valor2)));
	}
		
		

	
	

	public static String dividi( String valor1, String valor2 ){ 
		
	
	return Calculo.dividi(valor1, valor2, 2);
	}
		
	
	
	
	

	public static String dividi( String valor1, String valor2, int precisao){ 
		
	if(precisao<2)
	precisao = 2;
	
	if(Calculo.stringZero(valor2))
	return "0.00";
		
	return String.valueOf( converteStringParaBigDecimal(valor1).divide( converteStringParaBigDecimal(valor2), precisao, RoundingMode.UP));
	}
		
	
	
	
	
		
	public static String formataValor( String valor){
		
	
	return Calculo.formataValor(valor, 2);
	}

		
	
	
		
	
	public static String formataValor( String valor, int precisao){
		
	if(valor == null || valor.length() == 0)
	return "0,00";
	
	if(precisao<2)
	precisao = 2;
	
	valor  = Calculo.getNumero(valor);
		
	DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##", new DecimalFormatSymbols (new Locale ("pt", "BR")));  
	df.setMinimumFractionDigits(precisao);   
					
	df.setParseBigDecimal (true);  

	return df.format(Double.parseDouble(valor));
	}

		
	
	
	
	
	public static String calcPorcentagem( String valor, String porcento){
				
			
	return	Calculo.calcPorcentagem(valor, porcento, 2);
	}
		
		
		
		
	
	
	public static String calcPorcentagem( String valor, String porcento, int precisao){
		
	if( stringZero(porcento) || stringZero(valor))	
	return  "0.00";
					
	return	dividi( multiplica( valor, porcento), "100", precisao);
	}
		
		
	
	
	
	

	public static String getPorcentagemCorrespondente( String valor_total, String valor){
				
	if( stringZero(valor_total) || stringZero(valor))	
	return  "0.00";
							
	return	dividi( multiplica( valor, "100"), valor_total, 2);
	}
		
	
	
	

	
	public static boolean compara(String valor1, String valor2){

	return Calculo.stringParaDouble(valor1).compareTo(Calculo.stringParaDouble(valor2))==0;
	}
	
	
	
	


	public static String media( String valor1, String valor2){
				
							
	return	media( valor1, valor2, 2);
	}
		
	
	


	public static String media( String valor1, String valor2, int precisao){
		
		
	return	dividi( soma(valor1, valor2), "2", precisao);
	}





	public static String getNatural( String valor){

		if(Comuns.temValor(valor)){

			String partes[] = valor.split(Pattern.quote(","));

			if(partes.length>1)
				return partes[0];

			return valor;
		}

		return "0";
	}





}
