package main;


import java.awt.EventQueue;
import java.util.logging.Logger;

import view.Dashboard;


public class Main {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void main(String args[]){
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				LOGGER.info("Starting Dashboard");
				Dashboard window = new Dashboard();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	}

}
