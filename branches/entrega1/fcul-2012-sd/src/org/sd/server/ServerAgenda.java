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
	
	private ServerFacade 									sf;
	private DispatcherEngine								de;
	private Agenda 											ag;
	private ServerConfig									sc;
	
	public static void addToInfoConsole(String s){
		dialog.setText(dialog.getText()+"\n"+s);
		
	}
	
	
	/********************************************************************
	 * Construtor de ServerAgenda
	 */
	public ServerAgenda (int sPort){

		//Inicializa sub-sistemas
		//Inicializa agenda - carrega dados ficheiro
		ag = new Agenda();
		//Inicializa controlo - carrega os dados de sistema.
		sc = new ServerConfig();
		//Inicializa ServerFacede.
		sf = new ServerFacade();
		//inicializa ServerDispatcher.
		de = new DispatcherEngine();
		//Inicializa Interface grafico.
		//Se inicializado.
		
		
		pauseButton.setEnabled(false);
	//	pauseButton.repaint();
		
		controlPanelLayoutInit(); //Inicializa o JPanel
		
		this.getContentPane().add(controlPanel,BorderLayout.CENTER);
	    this.pack();
 
	}
	
	
	/****************************************************************
	 * Constroi o interface swing para operar o mundo.
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
	
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
	}
	
	/**
	 * PONTO DE ENTRADA DO PROGRAMA.
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
