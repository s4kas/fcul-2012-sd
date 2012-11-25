package org.sd.client.ui;

import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientUI {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){  
	    	public void run(){  
	    		ClientUI.buildUI();
	    	}  
	    });  
	} 
	
	public static void buildUI() {
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getCrossPlatformLookAndFeelClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar c = Calendar.getInstance();
		int mes = c.get(Calendar.MONTH);
		int ano = c.get(Calendar.YEAR);
		
		JFrame f = new JFrame("Agenda");
		f.getContentPane().add(new CalendarPanel(mes,ano, 1970, 2050).construct());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
}
