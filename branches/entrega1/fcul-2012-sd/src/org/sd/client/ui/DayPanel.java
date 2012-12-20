package org.sd.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sd.client.ClientController;
import org.sd.data.Evento;

public class DayPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints c;
	private JButton addButton, consultButton, eliminateButton,
		confirmAddEvent, back, modify;
	ButtonGroup radioGroup;
	List<JRadioButton> radios = new ArrayList<JRadioButton>();
	JTextField subject;
	JTextArea content;
	JComboBox startHours, startMinutes, endHours, endMinutes;
	JLabel startHoursLabel, startMinutesLabel, endHoursLabel, endMinutesLabel;
	List<Evento> eventos;
	int day, month, year;
	
	public DayPanel(int day, int month, int year) {
		c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.day = day;
		this.month = month;
		this.year = year;
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(400,400));
	}
	
	public DayPanel constructListEvents() {
		this.removeAll();
		
		addEventsPanel();
		addActionsPanel();
		
		this.revalidate();
		this.repaint();
		return this;
	}
	
	private void addEventsPanel() {
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridy = 0;
		add(new JLabel("Eventos"),c);
		eventos = ClientController.getEventsForDayMonthYear(day, month, year);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		JPanel eventsPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridy = 0;
		
		JRadioButton button;
		radioGroup = new ButtonGroup();
		for (int i = 0; i < eventos.size(); i ++) {
			String line = eventos.get(i).getStartsEndsHourMinute() 
					+ " - " + eventos.get(i).getDescript();
			button = new JRadioButton(line);
			radios.add(button);
			radioGroup.add(button);
			eventsPanel.add(button,gbc);
			gbc.gridy++;
		}
		
		JScrollPane scrollPane = new JScrollPane(eventsPanel);
		scrollPane.setPreferredSize(new Dimension(360,300));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(scrollPane,c);
	}
	
	private void addActionsPanel() {
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridy = 2;
		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setPreferredSize(new Dimension(380,40));
		
		addButton = new JButton("Adicionar");
		addButton.addActionListener(this);
		actionsPanel.add(addButton);
		
		consultButton = new JButton("Consultar");
		consultButton.addActionListener(this);
		actionsPanel.add(consultButton);
		
		eliminateButton = new JButton("Remover");
		eliminateButton.addActionListener(this);
		actionsPanel.add(eliminateButton);
		
		add(actionsPanel,c);
	}
	
	private DayPanel constructAddEvent() {
		this.removeAll();
		
		//top panel
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridy = 0;
		add(new JLabel("Adicionar Evento"),c);
		
		//middle panel
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		
		JPanel addEventPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1.0;
		
		JLabel startLabel = new JLabel("Inicio",JLabel.CENTER);
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		addEventPanel.add(startLabel,gbc);
		
		JLabel endLabel = new JLabel("Fim",JLabel.CENTER);
		gbc.gridy = 0;
		gbc.gridx = 2;
		gbc.gridwidth = 2;
		addEventPanel.add(endLabel,gbc);
		
		startHours = new JComboBox(UIConstants.HOURS);
		startHours.setSelectedIndex(0);
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		addEventPanel.add(startHours,gbc);
		
		startMinutes = new JComboBox(UIConstants.MINUTES);
		startMinutes.setSelectedIndex(0);
		gbc.gridx = 2;
		addEventPanel.add(startMinutes,gbc);
		
		endHours = new JComboBox(UIConstants.HOURS);
		endHours.setSelectedIndex(0);
		gbc.gridy = 1;
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		addEventPanel.add(endHours,gbc);
		
		endMinutes = new JComboBox(UIConstants.MINUTES);
		endMinutes.setSelectedIndex(0);
		gbc.gridx = 4;
		addEventPanel.add(endMinutes,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		JLabel subjectLabel = new JLabel("Título: ");
		addEventPanel.add(subjectLabel,gbc);
		subject = new JTextField(22);
		gbc.gridx = 1;
		gbc.gridwidth = 4;
		addEventPanel.add(subject,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		JLabel contentLabel = new JLabel("Evento: ");
		addEventPanel.add(contentLabel,gbc);
		
		gbc.gridx = 1;
		gbc.gridwidth = 4;
		content = new JTextArea();
		content.setPreferredSize(new Dimension(245,250));
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		addEventPanel.add(content,gbc);
		
		add(addEventPanel,c);
		
		//actions
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridy = 4;
		
		JPanel actionsPanel = new JPanel();
		confirmAddEvent = new JButton("Adicionar Evento");
		confirmAddEvent.addActionListener(this);
		actionsPanel.add(confirmAddEvent);
		
		back = new JButton("Voltar");
		back.addActionListener(this);
		actionsPanel.add(back);
		add(actionsPanel,c);
		
		this.revalidate();
		this.repaint();
		return this;
	}
	
	private DayPanel constructConsultEvent() {
		int index = -1;
		for (int i = 0; i < radios.size(); i ++) {
			if (radios.get(i).isSelected()) {
				index = i;
				break;
			}
		}
		
		if (index == -1) {
			JOptionPane.showMessageDialog(null,
				    "Deve seleccionar um evento.",
				    "Erro consultar evento",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		int startHour = eventos.get(index).getStartCalendar().get(Calendar.HOUR);
		int startMinute = eventos.get(index).getStartCalendar().get(Calendar.MINUTE);
		int endHour = eventos.get(index).getEndCalendar().get(Calendar.HOUR);
		int endMinute = eventos.get(index).getEndCalendar().get(Calendar.MINUTE);
		String[] temp = eventos.get(index).getDescript().split("-");
		String title = temp[0];
		String contentText = temp[1];
		
		this.removeAll();
		
		//middle panel
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
				
		JPanel consultEventPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = gbc.weighty = 1.0;
				
		JLabel startLabel = new JLabel("Inicio",JLabel.CENTER);
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		consultEventPanel.add(startLabel,gbc);
				
		JLabel endLabel = new JLabel("Fim",JLabel.CENTER);
		gbc.gridy = 0;
		gbc.gridx = 2;
		gbc.gridwidth = 2;
		consultEventPanel.add(endLabel,gbc);
				
		startHoursLabel = new JLabel(String.valueOf(startHour));
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.gridwidth = 1;
		consultEventPanel.add(startHoursLabel,gbc);
				
		startMinutesLabel = new JLabel(String.valueOf(startMinute));
		gbc.gridx = 2;
		consultEventPanel.add(startMinutesLabel,gbc);
				
		endHoursLabel = new JLabel(String.valueOf(endHour));
		gbc.gridy = 1;
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		consultEventPanel.add(endHoursLabel,gbc);
				
		endMinutesLabel = new JLabel(String.valueOf(endMinute));
		gbc.gridx = 4;
		consultEventPanel.add(endMinutesLabel,gbc);
				
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		JLabel subjectLabel = new JLabel("Título: ");
		consultEventPanel.add(subjectLabel,gbc);
		subject = new JTextField(22);
		subject.setText(title);
		gbc.gridx = 1;
		gbc.gridwidth = 4;
		consultEventPanel.add(subject,gbc);
				
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		JLabel contentLabel = new JLabel("Evento: ");
		consultEventPanel.add(contentLabel,gbc);
				
		gbc.gridx = 1;
		gbc.gridwidth = 4;
		content = new JTextArea();
		content.setText(contentText);
		content.setPreferredSize(new Dimension(245,250));
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		consultEventPanel.add(content,gbc);
		
		add(consultEventPanel,c);
		
		//actions
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridy = 2;
		
		JPanel actionsPanel = new JPanel();
		modify = new JButton("Modificar");
		modify.addActionListener(this);
		actionsPanel.add(modify);
		back = new JButton("Voltar");
		back.addActionListener(this);
		actionsPanel.add(back);
		add(actionsPanel,c);
		
		this.revalidate();
		this.repaint();
		return this;
	}
	
	private void addEvent() {
		if (!validateFields()) {
			return;
		}
		
		int startHour = Integer.parseInt(startHours.getSelectedItem().toString());
		int startMinute = Integer.parseInt(startMinutes.getSelectedItem().toString());
		int endHour = Integer.parseInt(endHours.getSelectedItem().toString());
		int endMinute = Integer.parseInt(endMinutes.getSelectedItem().toString());
		String title = subject.getText();
		String contentText = content.getText();
		
		boolean result = ClientController.addEvent(day, month, year, 
				startHour, startMinute, endHour, endMinute, title, contentText);
		
		if (result) {
			JOptionPane.showMessageDialog(null,
				    "Evento enviado com sucesso.",
				    "Sucesso adicionar evento",
				    JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
				    "Não foi possível enviar o evento.",
				    "Erro adicionar evento",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void removeEvent() {
		if (radioGroup.getSelection() == null) {
			JOptionPane.showMessageDialog(null,
				    "Deve seleccionar um evento.",
				    "Erro remoção evento",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Object[] options = {"Sim", "Não"};
		int result = JOptionPane.showOptionDialog(null, "" +
				"Confirma a remoção do evento?","Confirmar remoção",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		
		System.out.println(result);
		//0 = sim
		//1 = nao
	}
	
	private void modifyEvent() {
		if (!validateFields()) {
			return;
		}
		
		int startHour = Integer.parseInt(startHoursLabel.getText());
		int startMinute = Integer.parseInt(startMinutesLabel.getText());
		int endHour = Integer.parseInt(endHoursLabel.getText());
		int endMinute = Integer.parseInt(endMinutesLabel.getText());
		String title = subject.getText();
		String contentText = content.getText();
		
		boolean result = ClientController.modifyEvent(day, month, year, 
				startHour, startMinute, endHour, endMinute, title, contentText);
		
		if (result) {
			JOptionPane.showMessageDialog(null,
				    "Alteração de evento enviada com sucesso.",
				    "Sucesso modificar evento",
				    JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
				    "Não foi possível enviar a alteração de evento.",
				    "Erro modificar evento",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean validateFields() {
		if (this.subject.getText().isEmpty() || this.content.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,
				    "Deve preencher todos os campos.",
				    "Erro adicionar evento",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (this.subject.getText().length() > 25) {
			JOptionPane.showMessageDialog(null,
				    "O titulo deve ter um máximo de 25 caractéres.",
				    "Erro adicionar evento",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (this.content.getText().length() > 255) {
			JOptionPane.showMessageDialog(null,
				    "O conteúdo do evento deve ter um máximo de 255 caractéres.",
				    "Erro adicionar evento",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	public void actionPerformed(ActionEvent ev) {
		JButton button = (JButton) ev.getSource();
		if (button == this.addButton) {
			constructAddEvent();
		} else if (button == this.consultButton) {
			constructConsultEvent();
		} else if (button == this.eliminateButton) {
			removeEvent();
		} else if (button == this.back) {
			constructListEvents();
		} else if (button == this.confirmAddEvent) {
			addEvent();
		} else if (button == this.modify) {
			modifyEvent();
		}
	}
}
