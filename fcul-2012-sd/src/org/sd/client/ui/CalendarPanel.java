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

public class CalendarPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private GridBagConstraints c;
	private JPanel yearChoice, monthChoice;
	private JScrollPane recentEvents;
	private MonthPanel monthPanel;
	private int month, year, yearLowLimit, yearSupLimit;
	private Icon back, forward;

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
	
	public CalendarPanel construct() {
		//constraints
		this.c = new GridBagConstraints();
		this.c.insets = new Insets(5,5,5,5);
		
		//year panel
		addChoiceYearPanel();
		
		//month panel
		addChoiceMonthPanel();
		
		//days Panel
		this.monthPanel = new MonthPanel(year, month);
		c.gridy = 2;
		add(monthPanel.construct(), c);
		
		//add events panel (right)
		addRecentEventsPanel();
		
		return this;
	}
	
	private void reconstruct() {
		this.removeAll();
		this.construct();
		this.revalidate();
		this.repaint();
	}
	
	private void addChoiceYearPanel() {
		this.yearChoice = new JPanel();
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		//regredir ano
		yearChoice.add(getBackButton(),c);
		//ano
		yearChoice.add(new JLabel(String.valueOf(year),JLabel.CENTER),c);
		//forward
		yearChoice.add(getForwardButton(),c);
		
		//add year panel to mainpanel
		add(yearChoice,c);
	}
	
	private void addChoiceMonthPanel() {
		this.monthChoice = new JPanel();
		c.gridy = 1;
		//regredir mes
		monthChoice.add(getBackButton(),c);
		//mes
		monthChoice.add(new JLabel(UIConstants.MONTHS[month],JLabel.CENTER),c);
		//avancar mes
		monthChoice.add(getForwardButton(),c);
		
		//add month panel to main panel
		add(monthChoice,c);
	}
	
	private void addRecentEventsPanel() {
		JPanel re = new JPanel();
		c.gridy = 0;
		c.gridheight = 3;
		//Label top
		re.add(new JLabel("Actividades Recentes"));
		re.setBorder(BorderFactory.createLineBorder(Color.black));
		re.setPreferredSize(new Dimension(250,350));
		recentEvents = new JScrollPane(re);
		add(recentEvents, c);
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
