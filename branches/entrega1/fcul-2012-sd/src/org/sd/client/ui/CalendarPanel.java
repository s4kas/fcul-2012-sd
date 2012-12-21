package org.sd.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.sd.client.ClientController;

public class CalendarPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private GridBagConstraints c;
	private JPanel yearChoice, monthChoice, statusPanel;
	private JScrollPane recentEvents;
	private MonthPanel monthPanel;
	private int month, year, yearLowLimit, yearSupLimit;
	private Icon back, forward;
	private JLabel connectionLabel;
	private JTextArea recentEventsText;

	public MonthPanel getMonthPanel() {
		return monthPanel;
	}

	public CalendarPanel(int actualMonth, int actualYear, int yearLowLimit, 
			int yearSupLimit) {
		this.year = actualYear;
		this.month = actualMonth;
		this.yearLowLimit = yearLowLimit;
		this.yearSupLimit = yearSupLimit;
		this.forward = new ImageIcon(this.getClass().getResource( 
				"/toolbarButtonGraphics/navigation/Forward16.gif" ));
		this.back = new ImageIcon(this.getClass().getResource( 
				"/toolbarButtonGraphics/navigation/Back16.gif" ));
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridBagLayout());
	}

	public JLabel getConnectionLabel() {
		return connectionLabel;
	}
	
	public JTextArea getRecentEventsText() {
		return recentEventsText;
	}

	public CalendarPanel construct() {
		//constraints
		this.c = new GridBagConstraints();
		this.c.insets = new Insets(5,5,5,5);
		
		//year panel
		addChoiceYearPanel();
		
		//month panel
		addChoiceMonthPanel();
		
		//days Panel
		addMonthPanel();
		
		//status Panel
		addStatusPanel();
		
		//add events panel (right)
		addRecentEventsPanel();
		
		return this;
	}
	
	public void reconstruct() {
		this.removeAll();
		this.construct();
		this.revalidate();
		this.repaint();
		ClientController.updateStatus();
	}
	
	private void addMonthPanel() {
		this.monthPanel = new MonthPanel(year, month);
		c.gridx = 0;
		c.gridy = 2;
		add(monthPanel.construct(), c);
	}
	
	private void addChoiceYearPanel() {
		this.yearChoice = new JPanel();
		//regredir ano
		yearChoice.add(getBackButton(),c);
		//ano
		yearChoice.add(new JLabel(String.valueOf(year),JLabel.CENTER),c);
		//forward
		yearChoice.add(getForwardButton(),c);
		
		//add year panel to mainpanel
		c.gridx = 0;
		c.gridy = 0;
		add(yearChoice,c);
	}
	
	private void addChoiceMonthPanel() {
		this.monthChoice = new JPanel();
		//regredir mes
		monthChoice.add(getBackButton(),c);
		//mes
		monthChoice.add(new JLabel(UIConstants.MONTHS[month],JLabel.CENTER),c);
		//avancar mes
		monthChoice.add(getForwardButton(),c);
		
		//add month panel to main panel
		c.gridx = 0;
		c.gridy = 1;
		add(monthChoice,c);
	}
	
	private void addStatusPanel() {
		statusPanel = new JPanel(new GridBagLayout());
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;
		//Label top
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5,5,5,5);
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridy = 0;
		statusPanel.add(new JLabel("Estado Conexão"),gbc);
		statusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		connectionLabel = new JLabel();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 1;
		statusPanel.add(connectionLabel,gbc);
		
		statusPanel.setPreferredSize(new Dimension(250,80));
		add(statusPanel, c);
	}
	
	private void addRecentEventsPanel() {
		JPanel re = new JPanel(new GridBagLayout());
		c.gridx = 1;
		c.gridy = 2;
		//Label top
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		re.add(new JLabel("Actividades Recentes"),gbc);
		if (recentEventsText == null) {
			recentEventsText = new JTextArea("Client - Started");
			recentEventsText.setLineWrap(true);
			recentEventsText.setWrapStyleWord(true);
			recentEventsText.setEnabled(false);
		}
		gbc.gridy = 1;
		recentEvents = new JScrollPane(recentEventsText);
		recentEvents.setPreferredSize(new Dimension(225,225));
		re.add(recentEvents, gbc);
		re.setBorder(BorderFactory.createLineBorder(Color.black));
		re.setPreferredSize(new Dimension(250,250));
		add(re, c);
	}
	
	private JButton getForwardButton() {
		JButton forwardButton = new JButton(forward);
		forwardButton.setOpaque(false);
		forwardButton.setContentAreaFilled(false);
		forwardButton.addActionListener(this);
		return forwardButton;
	}
	
	private JButton getBackButton() {
		JButton backButton = new JButton(back);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.addActionListener(this);
		return backButton;
	}
	
	public void actionPerformed(ActionEvent ev) {
		JButton jb = (JButton) ev.getSource();
		
		//mudou de ano
		if (jb.getParent() == this.yearChoice) {
			if (jb.getIcon() == this.forward) { //frente
				this.year++;
			} else { //tras
				this.year--;
			}
		//mudou de mes
		} else if (jb.getParent() == this.monthChoice) {
			if (jb.getIcon() == this.forward) { //frente
				this.month++;
			} else { //tras
				this.month--;
			}
		}
		
		//ultrapassei ambos os limites (ano e mes)
		if (this.month == 12 && this.year == yearSupLimit) this.month = 11;
		if (this.month == -1 && this.year == yearLowLimit) this.month = 0;
		
		//ultrapassei os limites de meses
		if (this.month == 12) { this.month = 0; this.year++; }
		if (this.month == -1) { this.month = 11; this.year--; }
		
		//ultrapassei os limites de anos
		if (this.year < yearLowLimit) this.year = yearLowLimit; 
		if (this.year > yearSupLimit) this.year = yearSupLimit;
		
		reconstruct();
	}
}
