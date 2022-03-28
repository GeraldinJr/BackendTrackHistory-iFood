package br.com.bolinhocorp.BackendTrackHistoryiFood.util;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodsUtil {
	
	public static Integer getIdPessoa() {
		return Integer.valueOf((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	public static boolean emailIsValid(String email) {
		boolean emailValido = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				emailValido = true;
			}
		}
		return emailValido;
	}

}
