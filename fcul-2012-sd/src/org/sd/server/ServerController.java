package org.sd.server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerController extends JFrame implements ActionListener {
	
	
	private static final long serialVersionUID = 1L;
	private JPanel 											controlPanel = new JPanel();
	private static JTextArea 								dialog = new JTextArea(5, 20);
	private JButton											startButton = new JButton ();
	private JButton											stopButton = new JButton ();
	private GroupLayout 									layout;
	private JScrollPane 									sp = new JScrollPane();
	private ServerFacade 									runningServerFacade;
	
	
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

    	startButton.setText("Iniciar");
    	startButton.addActionListener(this);
    	stopButton.setText("Parar");
		stopButton.addActionListener(this);
		sp.getViewport().add(dialog);

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
	    				)
	    		);
	    
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
				.addComponent(stopButton)
				.addComponent(startButton)
				.addComponent(sp)

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
		this.getContentPane().add(controlPanel,BorderLayout.CENTER);
	    this.pack();
 	}
	
	public void start() {
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
		JButton src = (JButton) e.getSource();
		if (src == this.stopButton) {
			this.stop();
		} else if (src == this.startButton) {
			this.start();
		}
	}
	
	
	
	/***************************************************************
	 * Entry Point!!
	 * @param args
	 */
	public static void main(String[] args) {
		ServerController s = new ServerController();
		s.start();
		s.setVisible(true);
		ServerController.addToInfoConsole("Inicializado");
	}
	
}
