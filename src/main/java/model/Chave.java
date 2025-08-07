package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Chave {

	
	protected String entrada;

	public Chave(String entrada) {
		this.entrada = entrada;
	}

	public String getEntrada() {
		return entrada;
	}

	public String Encrypt() {

		String entrada = getEntrada();
			
		MessageDigest messageDigest = null;
		StringBuilder hexString = null;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		byte[] hash = messageDigest.digest(entrada.getBytes(StandardCharsets.UTF_8));
		
		hexString = new StringBuilder(2 * hash.length);
		
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('$');
			}
			hexString.append(hex);
		}
		return hexString.toString().toUpperCase();
	}
}
