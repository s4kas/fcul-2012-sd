package org.sd.client.ui;

import java.awt.Color;
import java.awt.Frame;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientUI {
	
	private static Frame frame;
	private static CalendarPanel calendarPanel;
	private static final int YEAR_SUP_LIMIT = 2020;
	private static final int YEAR_LOW_LIMIT = 2010;

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
	
	public static void updateStatus(boolean connected, boolean reconnecting) {
		JLabel connectionLabel = calendarPanel.getConnectionLabel();
		JButton connectionButton = calendarPanel.getConnectionButton();
		if (connected) {
			connectionLabel.setForeground(Color.GREEN);
			connectionLabel.setText("Conectado");
			connectionButton.setText("Desligar");
		} else {
			connectionLabel.setForeground(Color.RED);
			connectionLabel.setText("Desconectado");
			connectionButton.setText("Ligar");
		}
		
		if (reconnecting) {
			connectionButton.setEnabled(false);
		} else {
			connectionButton.setEnabled(true);
		}
	}
	
	public static void updateRecentEvents(String message) {
		JTextArea textArea = calendarPanel.getRecentEventsText();
		textArea.setText(textArea.getText() + "\n" + message);
		textArea.validate();
	}
	
	private static void constructCalendarFrame() {
		Calendar c = Calendar.getInstance();
		int mes = c.get(Calendar.MONTH);
		int ano = c.get(Calendar.YEAR);
		
		frame = new JFrame("Agenda");
		calendarPanel = new CalendarPanel(mes,ano, YEAR_LOW_LIMIT, YEAR_SUP_LIMIT).construct();
		
		((JFrame) frame).getContentPane().add(calendarPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		((JFrame) frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
