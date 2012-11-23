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
	
	
	
	/********************************************************************
	 * Construtor de ServerAgenda
	 */
	public ServerAgenda (){

		//Inicializa sub-sistemas
		//Inicializa agenda - carrega dados ficheiro
		//Inicializa controlo - carrega os dados de sistema.
		//Se inicializado.
		//Inicializa ServerFacede.
		//inicializa ServerDispatcher.
		//Inicializa Interface grafico.

		
//		pauseButton.setEnabled(false);
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
	    				.addComponent(sp)
	    				)
	    		);
	    
	    layout.setVerticalGroup(
	    		layout.createSequentialGroup()
				.addComponent(quitButton)
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

			
		
	}


	
}
