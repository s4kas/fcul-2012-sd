package org.sd.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerController extends JFrame implements ActionListener {
	
	
	private static final long serialVersionUID = 1L;
	private JPanel 											controlPanel = new JPanel();
	private static JTextArea 								dialog = new JTextArea(5, 20);
	private JButton											startButton = new JButton ();
	private JButton											stopButton = new JButton ();
	private GroupLayout 									layout;
	private JScrollPane 									sp = new JScrollPane();
	private ServerFacade 									runningServerFacade;
	private JRadioButton 									primary, secondary;
	private JTextField										secondaryAddress;
	private JLabel											secondaryAddressLabel;
	
	/***********************************************************************
	 * DEBUG WINDOW
	 * @param s
	 */
	public static void addToInfoConsole(String s){
		dialog.setText(dialog.getText()+"\n"+s);
		
	}
	
	/****************************************************************
	 * CONSTRUCTS SWING PANEL INTERFACE 
	 */
	private void controlPanelLayoutInit(){
		
    	this.setTitle("AGENDA SERVER");

		dialog.setLineWrap(true);
		dialog.setWrapStyleWord(true);
    	dialog.setEditable(false);

    	//simplebuttons
    	startButton.setText("Iniciar");
    	startButton.addActionListener(this);
    	stopButton.setText("Parar");
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		stopButton.repaint();
		sp.getViewport().add(dialog);
		
		//radiobuttons
		ButtonGroup rg = new ButtonGroup();
		primary = new JRadioButton("Primário");
		primary.addActionListener(this);
		rg.add(primary);
		primary.setSelected(true);
		
		secondary = new JRadioButton("Secundário");
		secondary.addActionListener(this);
		rg.add(secondary);
		
		//input for secondary server address
		secondaryAddressLabel = new JLabel("Endereço do primário:");
		secondaryAddress = new JTextField();
		secondaryAddress.setEnabled(false);

		//Definicao do layout das caixas
	    layout = new GroupLayout(controlPanel);
	    layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	    controlPanel.setLayout(layout);
	    
	    layout.setHorizontalGroup(
	    		layout.createSequentialGroup()
	    		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    				.addComponent(stopButton)
	    				.addComponent(startButton)
	    				.addComponent(sp)
	    				.addComponent(primary)
	    				.addComponent(secondary)
	    				.addComponent(secondaryAddressLabel)
	    				.addComponent(secondaryAddress)
	    				)
	    		);
	    
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
				.addComponent(stopButton)
				.addComponent(startButton)
				.addComponent(sp)
				.addComponent(primary)
	    		.addComponent(secondary)
	    		.addComponent(secondaryAddressLabel)
	    		.addComponent(secondaryAddress)
	    );
	}

	
	/********************************************************************
	 * INITIALIZE COMPONENTS AND UI
	 */
	public ServerController (){
		//Intitializinf
		addToInfoConsole("initializing components!");
		//server facade
		runningServerFacade = new ServerFacade();
		runningServerFacade.initialize(ServerConfigProxy.getConfig(true));
		controlPanelLayoutInit(); //Inicializa o JPanel
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(controlPanel,BorderLayout.CENTER);
	    this.pack();
 	}
	
	public void start() {
		if (this.primary.isSelected()) {
			runningServerFacade.setIsPrimaryServer();
		} else if (this.secondary.isSelected()) {
			runningServerFacade.addPrimaryServer(secondaryAddress.getText());
		}
		runningServerFacade.start();
		addToInfoConsole("ServerFacede Initialized!");
		this.stopButton.setEnabled(true);
		this.stopButton.repaint();
		this.startButton.setEnabled(false);
		this.startButton.repaint();
	}
	
	public void stop() {
		if (runningServerFacade != null) {
			runningServerFacade.terminate();
			addToInfoConsole("ServerFacede Terminated!");
		}
		this.stopButton.setEnabled(false);
		this.stopButton.repaint();
		this.startButton.setEnabled(true);
		this.startButton.repaint();
	}
	
	/**************************************************************+
	 * ACTION PERFORMED
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton src = (JButton) e.getSource();
			if (src == this.stopButton) {
				this.stop();
			} else if (src == this.startButton) {
				this.start();
			} 
		} else if (e.getSource() instanceof JRadioButton) {
			JRadioButton src = (JRadioButton) e.getSource();
			if (src == this.secondary) {
				secondaryAddress.setEnabled(true);
			} else if (src == this.primary) {
				secondaryAddress.setEnabled(false);
			}
		}
	}
	
	
	
	/***************************************************************
	 * Entry Point!!
	 * @param args
	 */
	public static void main(String[] args) {
		ServerController s = new ServerController();
		s.setVisible(true);
		ServerController.addToInfoConsole("Inicializado");
	}
	
}
