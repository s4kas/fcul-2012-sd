package org.sd.client.ui;

import java.awt.Frame;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientUI {
	
	private static Frame frame;
	
	public static Frame getFrame() {
		return frame;
	}

	public static void buildUI() {
		startUI();
		constructCalendarFrame();
	}
	
	private static void startUI() {
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
	}
	
	private static void constructCalendarFrame() {
		Calendar c = Calendar.getInstance();
		int mes = c.get(Calendar.MONTH);
		int ano = c.get(Calendar.YEAR);
		
		frame = new JFrame("Agenda");
		((JFrame) frame).getContentPane().add(new CalendarPanel(mes,ano, 1970, 2050).construct());
		frame.pack();
		frame.setLocationRelativeTo(null);
		((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
