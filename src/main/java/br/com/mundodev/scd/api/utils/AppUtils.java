package br.com.mundodev.scd.api.utils;

import java.util.Random;
import java.util.regex.Pattern;

public class AppUtils {

	public static String getRandomNumberString() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public static Boolean isValidEmail(final String email) {
		
		if (email == null) {
			return Boolean.FALSE;
		}
		
		final var regex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		final var pattern = Pattern.compile(regex);
		
		return pattern.matcher(email).matches();
	}
	
	public static Boolean isCelular(final String celular) {
		
		if (celular == null) {
			return Boolean.FALSE;
		}
		
		final var regex = "^[0-9]{8,11}$";

		final var pattern = Pattern.compile(regex);
		
		return pattern.matcher(celular).matches();
	}

}
