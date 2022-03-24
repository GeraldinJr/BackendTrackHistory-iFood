package br.com.bolinhocorp.BackendTrackHistoryiFood.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class MethodsUtil {
	
	public static Integer getIdPessoa() {
		return Integer.valueOf((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

}
