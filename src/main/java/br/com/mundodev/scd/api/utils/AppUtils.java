package br.com.mundodev.scd.api.utils;

import java.util.Random;

public class AppUtils {
	
	public static String getRandomNumberString() {	    
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);	    
	    return String.format("%06d", number);
	}

}
