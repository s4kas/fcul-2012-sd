package org.sd.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

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

public class DayPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints c;
	private Calendar calendar;
	private JButton addButton, consultButton, eliminateButton,
		confirmAddEvent, back;
	ButtonGroup radioGroup;
	JTextField subject;
	JTextArea content;
	JComboBox startHours, startMinutes, endHours, endMinutes;
	
	public DayPanel(int day, int month, int year) {
		c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.calendar = Calendar.getInstance();
		this.calendar.set(Calendar.YEAR, year);
		this.calendar.set(Calendar.MONTH, month);
		this.calendar.set(Calendar.DAY_OF_MONTH, day);
		
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
		for (int i = 0; i < 1; i ++) {
			button = new JRadioButton("cenas jkasdjkhadshjdashjdsahjkdsahkjdsahkjdsahkjdashkdhkajshkjsadhkjdsakhjsdahkjdsakhjdsakhjakshdjkhajsdkhadskhjdsakjhkjsadkhjdsakhjdsakhj"+i);
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
		if (radioGroup.getSelection() == null) {
			JOptionPane.showMessageDialog(null,
				    "Deve seleccionar um evento.",
				    "Erro consultar evento",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		this.removeAll();
		
		//top panel
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridy = 0;
		add(new JLabel("Consultar Evento"),c);
		
		//middle panel
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 1;
		JPanel consultEventPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridy = gbc.gridx = 0;
		
		JLabel subjectLabel = new JLabel("Título: ");
		consultEventPanel.add(subjectLabel,gbc);
		JLabel subject = new JLabel("cenas maradas de titulo e tal");
		gbc.gridx = 1;
		consultEventPanel.add(subject,gbc);
		
		gbc.gridy = 1;
		gbc.gridx = 0;
		JLabel contentLabel = new JLabel("Evento: ");
		consultEventPanel.add(contentLabel,gbc);
		gbc.gridx = 1;
		JTextArea content = new JTextArea("cenas e tal e coisada marada ui uiui uiui" +
				"klskljdfskjlfdsjlkdfsjlkfdsjlkfdsljkfdsjlkdfsjlkjldfksjlkfdsjldsfjlkdsfjlkjlkdsflkdfs" +
				"jkldfsljkdsfjkldfsljkdsfljkdfsjkldfsljkdsfljkldjksfljkdsfljkdsfjlksdfjlkfsdjlkljkdsflksdf" +
				"jkldsfljkdsfjlkdfsljksdfljksdflkjdsfljkdslkjsdfljkdsjlkdsflksdfkljdsf" +
				"lkjdsfjkldsfljkdsfljksdfljkdsflkjdsflk");
		content.setPreferredSize(new Dimension(245,240));
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		content.setEditable(false);
		consultEventPanel.add(content,gbc);
		
		add(consultEventPanel,c);
		
		//actions
		c.anchor = GridBagConstraints.PAGE_END;
		c.gridy = 2;
		
		JPanel actionsPanel = new JPanel();
		back = new JButton("Voltar");
		back.addActionListener(this);
		actionsPanel.add(back);
		add(actionsPanel,c);
		
		this.revalidate();
		this.repaint();
		return this;
	}
	
	private void addEvent() {
		if (this.subject.getText().isEmpty() || this.content.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null,
				    "Deve preencher todos os campos.",
				    "Erro adicionar evento",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (this.subject.getText().length() > 25) {
			JOptionPane.showMessageDialog(null,
				    "O titulo deve ter um máximo de 25 caractéres.",
				    "Erro adicionar evento",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (this.content.getText().length() > 255) {
			JOptionPane.showMessageDialog(null,
				    "O conteúdo do evento deve ter um máximo de 255 caractéres.",
				    "Erro adicionar evento",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int startHour = Integer.parseInt(startHours.getSelectedItem().toString());
		int startMinute = Integer.parseInt(startMinutes.getSelectedItem().toString());
		int endHour = Integer.parseInt(endHours.getSelectedItem().toString());
		int endMinute = Integer.parseInt(endMinutes.getSelectedItem().toString());
		String title = subject.getText();
		String contentText = content.getText();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		boolean result = ClientController.addEvent(day, month, year, 
				startHour, startMinute, endHour, endMinute, title, contentText);
		
		if (result) {
			JOptionPane.showMessageDialog(null,
				    "Evento inserido com sucesso.",
				    "Sucesso adicionar evento",
				    JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
				    "Não foi possível inserir o evento.",
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
		}
	}
}
