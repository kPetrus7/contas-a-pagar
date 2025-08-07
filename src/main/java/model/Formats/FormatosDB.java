package model.Formats;

import javax.swing.JOptionPane;

public class FormatosDB {

	public static double valorForDB(String entrada) {

		String valorStr = entrada.replace(".", "").replace(",", ".");
		return Double.parseDouble(valorStr);
	}

	public static String dateForDB(String entrada) {

		if (entrada == null || entrada.trim().isEmpty())
			return "";

		entrada = entrada.replace("/", "");
		if (entrada.length() < 8)
			return "";

		int dia = Integer.parseInt(entrada.substring(0, 2));
		int mes = Integer.parseInt(entrada.substring(2, 4));
		int ano = Integer.parseInt(entrada.substring(4, 8));

		String strAno = String.valueOf(ano);
		String strMes;
		String strDia;

		if (mes > 12)
			mes = 12;

		if (mes < 10) {
			strMes = "0" + String.valueOf(mes);
		} else {
			strMes = String.valueOf(mes);
		}

		int[] meses_30 = { 4, 6, 9, 11 };
		int[] meses_31 = { 1, 3, 5, 7, 8, 10, 12 };

		for (int nMes : meses_30) {
			if (mes == nMes && dia > 30)
				dia = 30;
		}

		for (int nMes : meses_31) {
			if (mes == nMes && dia > 31)
				dia = 31;
		}

		if (ano % 4 == 0) {
			if (mes == 2 && dia > 29) dia = 29;
		} else if (ano % 4 !=0) { 
			if (mes == 2 && dia > 28) dia = 28;
		}

		if (dia < 10) {
			strDia = "0" + String.valueOf(dia);
		} else {
			strDia = String.valueOf(dia);
		}

		return strDia + strMes + strAno;
	}

	public static boolean valorVerifier(String text) {

		if (text == null || text.trim().isEmpty())
			return false;
		text = text.replace(".", "");
		text = text.replace(",", ".");

		double valor = Double.parseDouble(text);

		if (valor >= 1_000_000.00) {
			JOptionPane.showMessageDialog(null, "Valor máximo suportado R$ 999.999,99", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return false;
		} else
			return true;
	}

}
