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

import org.sd.common.IConfig;
import org.sd.data.Agenda;
import org.sd.server.dispatcher.Dispatchable;
import org.sd.server.dispatcher.DispatcherProcess;
import org.sd.server.dispatcher.ServerDispatcher;

public class ServerAgenda extends JFrame implements ActionListener{
	
	
	private static final long serialVersionUID = 1L;
	private JPanel 											controlPanel = new JPanel();
	private static JTextArea 								dialog = new JTextArea(5, 20);
	private JButton											nextButton = new JButton ();
	private JButton											pauseButton = new JButton ();
	private JButton											continueButton = new JButton ();
	private JButton											quitButton = new JButton ();
	private GroupLayout 									layout;
	private JScrollPane 									sp = new JScrollPane();
	
	private ServerFacade 									runningServerFacade;
	private ServerDispatcher								runningDispatcher;
	private Agenda 											runningAgenda;
	private ServerConfig									runningServerConfig;
	
	
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
	
    	dialog.setText("teste!");
    	dialog.setEditable(false);

    	pauseButton.setText("Rejeita");
    	pauseButton.addActionListener(this);
    	continueButton.setText("Aceita");
    	continueButton.addActionListener(this);
    	quitButton.setText("Sair");
		quitButton.addActionListener(this);
		sp.getViewport().add(dialog);

		//Definicao do layout das caixas
	    layout = new GroupLayout(controlPanel);
	    layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	    controlPanel.setLayout(layout);
	    
	    layout.setHorizontalGroup(
	    		layout.createSequentialGroup()
	    		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    				.addComponent(quitButton)
	    				.addComponent(pauseButton)
	    				.addComponent(sp)
	    				)
	    		);
	    
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
				.addComponent(quitButton)
				.addComponent(pauseButton)
				.addComponent(sp)

	    );
	}

	
	/********************************************************************
	 * INITIALIZE COMPONENTS AND UI
	 */
	public ServerAgenda (int sPort){

		//Intitializinf
		addToInfoConsole("initializing components!");
		//Inicializa agenda - carrega dados ficheiro
		addToInfoConsole("Initializing Agenda!");
		runningAgenda = new Agenda();
		addToInfoConsole("Agenda Initialized!");
		//Inicializa controlo - carrega os dados de sistema.
		addToInfoConsole("Initializing ServerConfig!");
		runningServerConfig = new ServerConfig();
		runningServerConfig.loadConfig();
		addToInfoConsole("ServerConfig Initialized!");
		//Inicializa ServerFacede.

		addToInfoConsole("Initializing DispatcherEngine!");
		runningDispatcher = new ServerDispatcher(runningAgenda);
		addToInfoConsole("DispatcherEngine Initialized!");
		addToInfoConsole("Released to network!");

		runningServerFacade = new ServerFacade();
		runningServerFacade.initialize(runningServerConfig,runningDispatcher);
		addToInfoConsole("ServerFacede Initialized!");
		//inicializa ServerDispatcher.
		
		pauseButton.setEnabled(false);
		controlPanelLayoutInit(); //Inicializa o JPanel
		this.getContentPane().add(controlPanel,BorderLayout.CENTER);
	    this.pack();
 	}
	
	
	
	/**************************************************************+
	 * ACTION PERFORMED
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
	}
	
	
	
	/***************************************************************
	 * Entry Point!!
	 * @param args
	 */
	public static void main(String[] args) {
		
		int serverPort = 0;
		
		if (args.length>0){
			try {
				System.out.println("debug"+args[0]);
				serverPort= Integer.parseInt(args[1]);	
			} catch (NumberFormatException n){
				//not a number
			}
		}
		ServerAgenda s = new ServerAgenda(serverPort);
		s.setVisible(true);
		ServerAgenda.addToInfoConsole("Forçada a porta: "+serverPort );
		ServerAgenda.addToInfoConsole("Inicializado");
	}
	
}
