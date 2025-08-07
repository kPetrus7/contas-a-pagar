package view.Formats;

import model.Formats.FormatosDB;

public class Formatos {

	public static String padraMonetario (String entrada) {

		if (entrada.trim().isEmpty()) entrada = "0,00";
		
		String modfiedValor = entrada.replace(".", ",");
		int tamanho = modfiedValor.length();
		int casas_decimais =  tamanho - modfiedValor.indexOf(',') - 1;

		if (modfiedValor.indexOf(",") == -1) {
			modfiedValor = modfiedValor + ",00";
			tamanho = modfiedValor.length();
			casas_decimais =  tamanho - modfiedValor.indexOf(',') - 1;
		}

		if (casas_decimais == 0) {
			modfiedValor = modfiedValor + "00";
			tamanho = modfiedValor.length();
			casas_decimais =  tamanho - modfiedValor.indexOf(',') - 1;
		}

		if (casas_decimais == 1) {
			modfiedValor = modfiedValor + "0";
			tamanho = modfiedValor.length();
			casas_decimais =  tamanho - modfiedValor.indexOf(',') - 1;
		}
		
		if (tamanho > 6) {
			StringBuilder sb = new StringBuilder(modfiedValor);
			sb.insert(tamanho - 6, ".");
			modfiedValor = sb.toString();
		}
		
		FormatosDB.valorVerifier(modfiedValor);
		return modfiedValor;
	}

	public static String padraoData (String entrada) {

		if (entrada == null || entrada.trim().isEmpty()) return null;
		
		entrada = entrada.replace("/", "");
		if (entrada.length() < 8) return "";

		int dia = Integer.parseInt(entrada.substring(0, 2));
		int mes = Integer.parseInt(entrada.substring(2, 4));
		int ano = Integer.parseInt(entrada.substring(4, 8));

		String strAno = String.valueOf(ano);
		String strMes;
		String strDia;

		if (mes > 12) mes = 12;

		if (mes < 10) {
			strMes = "0" + String.valueOf(mes);
		} else {
			strMes = String.valueOf(mes);
		} 
		
		int[] meses_30 = {4, 6, 9, 11};
		int[] meses_31 = {1, 3, 5, 7, 8, 10 ,12};

		for (int nMes : meses_30) {
			if (mes == nMes && dia > 30) dia = 30;
		}

		for (int nMes : meses_31) {
			if (mes == nMes && dia > 31) dia = 31;
		}

		if (ano % 4 == 0) {
			if (mes == 2 && dia > 29) dia = 29;
		} else if (ano % 4 != 0) {
			if (mes == 2 && dia > 28) dia = 28;
		}

		if (dia < 10){
			strDia = "0" + String.valueOf(dia);
		} else {
			strDia = String.valueOf(dia);
		}

		return strDia + "/" + strMes + "/" + strAno;
	}
}
