/**
 * 
 */
package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.util.Calendar;
import javax.swing.JSeparator;

import controloservidor.NetworkServer;


/**
 * @author tpires
 *
 */
public class ServerMenu extends JFrame implements ActionListener {

	/**
	 * Classe para a criacao de um novo pacote de dados. 
	 * Menu de introdução de valores, e respectiva criação do XML.
	 * Upload de ficheiro anexo.
	 * @author tpires
	 */
		static final long serialVersionUID=0; //unused, just to clear warning.
		//Defenição de icons
		ImageIcon serverStartIcon = new ImageIcon ("Icons/network-32x32.png");
		ImageIcon quitIcon = new ImageIcon ("Icons/exit-32x32.png");
		ImageIcon jobsIcon = new ImageIcon ("Icons/Column-Chart-32x32.png");
		ImageIcon fseIcon = new ImageIcon ("Icons/fse1.gif");
		ImageIcon taskStartIcon = new ImageIcon ("Icons/exec-32x32.png");
		ImageIcon serverStopIcon = new ImageIcon ("Icons/Hatchet-32x32.png");
		ImageIcon taskStopIcon = new ImageIcon ("Icons/drive-3-br-external-usb-32x32.png");
		//Definição de butões
		JButton bServerStart = new JButton (" Act.Ligações      ",serverStartIcon);
		JButton bServerStop=   new JButton (" Desact.ligações",serverStopIcon);
		JButton bTaskStart=    new JButton (" Act.Tarefas       ",taskStartIcon);
		JButton jobPackages= new JButton   (" Tarefas               ",jobsIcon);
		JButton bTaskStop=     new JButton (" Desact.Tarefas   ",taskStopIcon);
		JButton menuQuit =     new JButton (" Terminar          ",quitIcon);
		
		//Definição de textareas
		JTextField data = new JTextField (16);
		JTextArea  dataStatusConns = new JTextArea(30,30);
		JScrollPane dsc = new JScrollPane(dataStatusConns);
		JTextArea  dataStatusTasks = new JTextArea(30,30);
		JScrollPane dst = new JScrollPane(dataStatusTasks);
		//Definição de Labels
		JLabel lblFse	= new JLabel(fseIcon);
		JLabel lblTitle  = new JLabel ("Controlo do Servidor");
		JLabel lblStatusConns = new JLabel("Act. Clientes:");
		JLabel lblStatusTasks = new JLabel("Act. Operadores:");
		
		//Adição de paineis
		JPanel cmdLayout = new JPanel();
		JPanel dataLayout = new JPanel();
		JPanel topLayout =new JPanel();
		JPanel dataPanel1 =new JPanel();
		JPanel dataGroup2 =new JPanel();

		JFrame parentFrame = null;
		boolean parentFrameDisable =false;
		NetworkServer servidor;
		TaskEngine tarefas;
		
	/**
	 *Constructor do menu servidor.
	 * 
	 */
	public ServerMenu ()
	{
		super ("Actividade do Servidor");
		initServerMenu();
	}
	
	/**
	 * Constructor do menu servidor
	 * @param parentFrame Objecto frame pai, doende origina este frame.
	 * @param disableParent True o frame pai fica desabilitado durante a existencia deste. 
	 *
	 */
	public ServerMenu (JFrame parentFrame, boolean disableParent )
	{
		super ("Actividade do Servidor");
		this.parentFrameDisable = disableParent;
		this.parentFrame = parentFrame;
		
		if (disableParent){
			parentFrame.setEnabled(false);
			parentFrame.repaint();
		}

		initServerMenu();
	}
	
	/******************************************************************
	 * Iniciação do menu de servidor.
	 */
	private void initServerMenu()
	{
		this.setResizable(true);
		this.setSize(920,540);
		this.setLayout(new BorderLayout());
		this.setLocation(50,50);
		//this.jobPackages.setEnabled(false);
		
		bServerStart.addActionListener(this);
		bServerStop.addActionListener(this);
		bTaskStart.addActionListener(this);
		jobPackages.addActionListener (this);
		bTaskStop.addActionListener(this);
		menuQuit.addActionListener(this);

		dataStatusConns.setLineWrap(true);
		dataStatusConns.setWrapStyleWord(true);
		dataStatusTasks.setLineWrap(true);
		dataStatusTasks.setWrapStyleWord(true);
		
		dsc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		dst.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JSeparator sep1 = new JSeparator();
		JSeparator sep2 = new JSeparator();
		
		cmdLayout.setLayout(new GridLayout(11,1,1,1));		
		cmdLayout.add(bServerStart);
		cmdLayout.add(bServerStop);
		cmdLayout.add(sep1);
		cmdLayout.add(bTaskStart);
		cmdLayout.add(bTaskStop);
		cmdLayout.add(sep2);
		cmdLayout.add(jobPackages);
		cmdLayout.add(menuQuit);
		cmdLayout.add(lblFse);
		bServerStop.setEnabled(false);
		bTaskStop.setEnabled(false);
		
		topLayout.setLayout(new FlowLayout (FlowLayout.CENTER,1,15));
		topLayout.add(lblTitle);
		
		GroupLayout grpLayout1 = new GroupLayout (dataPanel1);
		dataPanel1.setLayout(grpLayout1);
		
		grpLayout1.setAutoCreateGaps(true);
		grpLayout1.setAutoCreateContainerGaps(true);
		// LAYOUT horizontal do Grupo1
		grpLayout1.setHorizontalGroup(
				grpLayout1.createSequentialGroup()	
				
				.addGroup(
					 grpLayout1.createParallelGroup(GroupLayout.Alignment.CENTER)
					 .addComponent(lblStatusConns)
					 .addComponent(dsc)
				)	
				.addGroup(
						 grpLayout1.createParallelGroup(GroupLayout.Alignment.CENTER)
						 .addComponent(lblStatusTasks)
						 .addComponent(dst)
				)
		);
		// LAYOUT Vertical do Grupo1
		grpLayout1.setVerticalGroup(
				grpLayout1.createSequentialGroup()
				.addGroup(
						grpLayout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lblStatusConns)
						.addComponent(lblStatusTasks)
				)
				.addGroup(
						grpLayout1.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dsc)
						.addComponent(dst)
				)
		);
		
		dataLayout.add(dataPanel1);

		this.add(topLayout,BorderLayout.NORTH);
		this.add(cmdLayout,BorderLayout.WEST);
		this.add(dataLayout,BorderLayout.CENTER);

		//criação de estrutura de dados.
		tarefas = new TaskEngine (this,false);
		this.setVisible(true);
	}

	/*************************************************************
	 * Metodo para a actualização da caixa de actividade Conexões
	 * @param dados String de dados a escrever na caixa.
	 */	
	public void updateStatusConns (String dados)
	{
		this.dataStatusConns.setText(this.dataStatusConns.getText()+
					Calendar.getInstance().get(Calendar.MONTH)+"-"+
					Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+" "+
					Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+
					Calendar.getInstance().get(Calendar.MINUTE)+":"+
					Calendar.getInstance().get(Calendar.SECOND)+"-> "+dados+"\n");
		this.repaint();
	}
	
	/**************************************************************
	 * Metodo para a actualização da caixa de actividade Tarefas
	 * @param dados String de dados a escrever na caixa.
	 */
	public void updateStatusTasks (String dados)
	{
		this.dataStatusTasks.setText(this.dataStatusTasks.getText()+
				Calendar.getInstance().get(Calendar.MONTH)+"-"+
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+" "+
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+
				Calendar.getInstance().get(Calendar.MINUTE)+":"+
				Calendar.getInstance().get(Calendar.SECOND)+"-> "+dados+"\n");
		this.repaint();
	}
	
	/***************************************************************
	 * Handler de eventos do menu
	 */
	public void  actionPerformed (ActionEvent event)
	{
		Object eventSource = event.getSource();
		
		if (eventSource==bServerStart) 
		{
			//Inicia o servidor e recepcao de ligacoes
			//Objecto servidor é o handler de rede.
			servidor = new NetworkServer (this,false);
			bServerStart.setEnabled(false);
			bServerStop.setEnabled(true);
		}
		if (eventSource==bServerStop) //termina o servidor graciosamente
		{
			servidor.disconnect();
			bServerStop.setEnabled(false);
			bServerStart.setEnabled(true);
		}
	
		if (eventSource==jobPackages)
		{
			if (tarefas!=null)	
			{
				if (tarefas.hasData())	
					{ new TaskMenu(this,false,tarefas);	}
			} else {
				JOptionPane.showMessageDialog(this,"Não foram encontrados pacotes agendados");
			}
		}
		
		if (eventSource==bTaskStart)
		{
			//Inicia o Processo de agendamento e execução de tarefas.
				tarefas.initActivityOperator();
				bTaskStart.setEnabled(false);
				jobPackages.setEnabled(true);
				bTaskStop.setEnabled(true);
				this.repaint();
		}
		if (eventSource==bTaskStop)
		{
				//termina o processo de agendamento e execução de tarefas.
				tarefas.endActivityOperator();
				bTaskStart.setEnabled(true);
				bTaskStop.setEnabled(false);
				this.repaint();
		}
		if (eventSource==menuQuit)
		{
			if (bServerStop.isEnabled())
			{
				int response = JOptionPane.showConfirmDialog(this, 
					 	"Confirma terminar?\n O servidor "+
					 		"deixará de \n receber ligações.","Fechar a janela",
					 			JOptionPane.OK_OPTION,
					 				JOptionPane.CANCEL_OPTION);
				if (response==JOptionPane.OK_OPTION)
				{
					if (parentFrameDisable)
					{
						parentFrame.setEnabled(true);
						parentFrame.repaint();
					}
					servidor.disconnect();
					this.dispose();	
				}
			} else {
			
					if (parentFrameDisable)
					{
						parentFrame.setEnabled(true);
						parentFrame.repaint();
					}
					this.dispose();	
				}
			}
		}
}