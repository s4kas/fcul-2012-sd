package org.sd.client.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.sd.client.ClientController;

public class MonthPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private GridBagConstraints c;
	private Border border;
	private Calendar calendar;
	private DayFrame dayFrame;
	
	public MonthPanel(int year, int month) {
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5,5,5,5);
		border = LineBorder.createGrayLineBorder();
		this.calendar = Calendar.getInstance();
		this.calendar.set(Calendar.YEAR, year);
		this.calendar.set(Calendar.MONTH, month);
		this.calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridBagLayout());
	}	
	
	public MonthPanel construct() {
		//dias header
		c.gridy = 0;
		c.gridx = 0;
		for (int i = 0; i < UIConstants.DAYS_OF_WEEK.length; i ++) {
			c.gridx ++;
			JLabel label = new JLabel(UIConstants.DAYS_OF_WEEK[i], JLabel.CENTER);
			label.setBorder(border);
			add(label,c);
		}
				
		//dias
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.gridx = dayOfWeek;
		c.gridy++;
		for (int i = 0; i < daysOfMonth; i ++) {
			if ((dayOfWeek % 7) == 0) {
				c.gridy ++;
				c.gridx = 0;
			}
					
			c.gridx ++;
			JButton button = new JButton(String.valueOf(i+1));
					
			//teste
			if(ClientController.eventsForDayMonthYear(i, calendar.get(Calendar.MONTH), 
					calendar.get(Calendar.YEAR))) {
				button.setForeground(Color.BLACK);
			} else {
				button.setForeground(Color.GRAY);
			}
			
			button.addActionListener(this);
			add(button,c);
			dayOfWeek++;
		}
		
		return this;
	}

	public void actionPerformed(ActionEvent event) {
		final JButton button = (JButton) event.getSource();
		int day = Integer.parseInt(button.getText());
		
		startDayFrame(getDayPanelTitle(button.getText()), day);
	}
	
	private void startDayFrame(String title, int day) {
		if (dayFrame != null) {
			dayFrame.shutdown();
		}
		
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		dayFrame = new DayFrame(title);
		dayFrame.getContentPane().add(new DayPanel(day,month,year).constructListEvents());
		dayFrame.pack();
		dayFrame.setLocationRelativeTo(null);
		dayFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		dayFrame.setVisible(true);
	}
	
	private String getDayPanelTitle(String day) {
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		return UIConstants.DAYS_OF_WEEK[dayOfWeek-1] + ", "
				+ day + " de " + UIConstants.MONTHS[month] + " de " + year;
	}
}
