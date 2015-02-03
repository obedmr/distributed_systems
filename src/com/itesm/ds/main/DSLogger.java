package com.itesm.ds.main;

import java.util.logging.Logger;

public class DSLogger {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void severe(String msg){
		LOGGER.severe(msg);
	}
	
	public static void warning(String msg){
		LOGGER.warning(msg);
	}
	
	public static void info(String msg){
		LOGGER.info(msg);
	}
	
	public static void finest(String msg){
		LOGGER.finest(msg);
	}
}
