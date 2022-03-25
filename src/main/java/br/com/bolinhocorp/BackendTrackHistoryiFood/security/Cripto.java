package br.com.bolinhocorp.BackendTrackHistoryiFood.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Cripto {
	
	public static String encrypt(String senha) throws Exception{
		
		String nossaChaveStr = "t3st4nd0 c0m um4 ch4v3 a4l34t0r1";
		Key nossaChave = new SecretKeySpec(nossaChaveStr.getBytes(), "AES");
		
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, nossaChave);
		cipher.update(senha.getBytes());
		String senhaCripto = new String(cipher.doFinal(), "UTF-8");
		
		return senhaCripto;
		
	}

}
