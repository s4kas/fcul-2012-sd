/**
 * ATENÇÃO CONFIGURAÇÃO AINDA NAO ACABADA!!!
 * TODO Gerar a configuração do cliente apartir desta.
 * TODO gerar a configuração do  deployer apartir desta.
 */
package protocol;

import java.util.Calendar;
import java.io.Serializable;
import java.util.ArrayList;


/****************************************************************
 * @author tpires
 *	Estrutura de configuração do servidor 
 * Todas as configurações dos clientes e agentes originam desta. 
 */
public class ServerConfigData implements Serializable {

	public static final long serialVersionUID=84658245;
	public static final int PROCESS00= 0; //asps
	public static final int PROCESS01= 1; //coms
	public static final int PROCESS02= 2; //scripts
	public static final int PROCESS03= 3; //outros
	public static final int PROCESS04= 4;
	public static final int PROCESS05= 5;
	public static final int PROCESS06= 6;
	public static final int PROCESS07= 7;
	public static final int PROCESS08= 8;
	public static final int PROCESS09= 9;

	private int serverPortAddress=1500;
	private int agentPortAddress=1501;
	private long configVersion=0;
	private String CFG_cfg_file_dir ="cfgs/";
	private String CFG_pkg_dir ="pkgs/";
	private String CFG_report_file_dir ="reports/";
	private String CFG_working_dir ="temp/";
	private ArrayList <Networks> CFG_deploy_networks = new 	ArrayList <Networks> ();
	
	
	/*************************************************************
	 * INNER CLASSE que representa a configuração de cada pacote.
	 ************************************************************/
	private class Content implements Serializable	{
		
		private static final long serialVersionUID=345500998;
		
		private String CFG_Content_name="";
		private String CFG_Content_zipName="";
		private int CFG_Content_weight=0;
		private String CFG_Content_installDir="";
		private int CFG_Content_agent=0;
		private ArrayList<String> CFG_Content_agentsAddress =
											new ArrayList <String> ();

		/*******************************************************
		 * Constructor de objecto conteudo
		 * @param String name Nome do conteudo.
		 */
		private Content (String name)	{	CFG_Content_name= name;	}

		/**************************************************************
		 * Metodo que devolve o nome deste conteudo.
		 * @return Nome deste conteudo.
		 */
		private String getContent ()	{	return CFG_Content_name;	}		
		
		/*************************************************************************
		 * Metodo para alterar a precedencia deste conteudo, em relação aos outros 
		 * @param Int valor inteiro representando a sua precedencia (maior=>prioritario) 
		 */
		private void setContentWeight (int weight)	{CFG_Content_weight=weight;	}
		
		/*************************************************************************
		 * Metodo para a alteração do nome da pasta no ZIP.
		 * @param String name
		 */
		private void setContentZipName(String name)	{	CFG_Content_zipName=name; }

		/*******************************************************
		 * Metodo para alterar a directoria de instalaçao do pacote
		 * @param name
		 */
		private  void setContentInstallDir (String installDir)	{
			CFG_Content_installDir=installDir;
		}			
		
		/*******************************************************
		 * Metodo para alterar a directoria de instalaçao do pacote
		 * @param name
		 */
		private void setContentAgent (int agent)	{
			CFG_Content_agent = agent;	
		}			
		
		/*******************************************************
		 * Metodo para alterar o nome do pacote 
		 * @param name
		 */
		private void addNewAgentAddress ( String address){
			CFG_Content_agentsAddress.add(address);	
		}
	}

	/**********************************************************
	 * INNER CLASSE que representa a configuração das redes de deploy
	 *********************************************************/
	private class Networks implements Serializable{
		
		private static final long serialVersionUID=883455;
		private String CFG_network_name;
		private boolean CFG_config_valid=false;
		private ArrayList <Calendar> CFG_agenda = new ArrayList <Calendar> ();
		private ArrayList <Content> CFG_conteudos = new ArrayList <Content> ();
		
		
		/***********************************************************
		 * Construtor privado de networks
		 * @param name String nome da rede 
		 */
		private Networks (String netname){	CFG_network_name=netname; }

		/*************************************************************
		 * 
		 * @param valid
		 */
		private void setConfigValid (boolean valid)	{ CFG_config_valid=valid; }
		
		/*************************************************************
		 * 
		 * @return
		 */
		private boolean isConfigValid() { return CFG_config_valid;	}
			
		/***********************************************************************
		 * Metodo que adiciona um novo tipo de conteudo de pacote a este ambiente
		 * @param name Nome do conteudo a paremetrizar
		 */
		private void addNewContent (String name){
			CFG_conteudos.add(new Content (name));
		}
		
		/*********************************************************
		 * 
		 * @return
		 */
		private String [] getContentNames ()
		{
			String [] contents = new String [CFG_conteudos.size()];
			
			for (int index=0;index<CFG_conteudos.size();index++)
			{
				contents[index] = CFG_conteudos.get(index).getContent();
				//agendado[contador].set(Calendar.HOUR_OF_DAY,)=iter.next();
			}
			return contents;		
		}
		
		/*********************************************************
		 * 
		 * @return
		 */
		private String [] getContentAgentAddresses (int contentIndex)
		{
			String [] addr= new String [CFG_conteudos.get(contentIndex).CFG_Content_agentsAddress.size()];
			
			for (int index=0;index<addr.length;index++)
			{
				addr[index] = CFG_conteudos.get(contentIndex).CFG_Content_agentsAddress.get(index);
				//agendado[contador].set(Calendar.HOUR_OF_DAY,)=iter.next();
			}
			return addr;		
		}
		
		/*********************************************************
		 * 
		 * @return
		 */
		private String getContentZipFolder (int contentIndex)	{
			return CFG_conteudos.get(contentIndex).CFG_Content_zipName;
		}

		/*********************************************************
		 * 
		 * @return
		 */
		private String getContentInstallDir (int contentIndex)	{
			return CFG_conteudos.get(contentIndex).CFG_Content_installDir;
		}
		
		/*********************************************************
		 * 
		 * @return
		 */
		private int getContentPriorityValue (int contentIndex)	{
			return CFG_conteudos.get(contentIndex).CFG_Content_weight;
		}
		
		/*********************************************************
		 * 
		 * @return
		 */
		private String getContentPriority (int contentIndex)	{
			return ((Integer) (CFG_conteudos.get(contentIndex).CFG_Content_weight)).toString();
		}
		
		/*********************************************************
		 * 
		 * @return
		 */
		private String getContentAgentType (int contentIndex)	{
			String result="";
			 
			switch (CFG_conteudos.get(contentIndex).CFG_Content_agent)
			{
				case PROCESS00:result  = "PROCESS00";break;
				case PROCESS01:result  = "PROCESS01";break;
				case PROCESS02:result  = "PROCESS02";break;
				case PROCESS03:result  = "PROCESS03";break;
				default:result  = "UNDEFINED";break;
			}
			return result;
		}

	
		/*****************************************************************
		 * Metodo para definir o agendamento de actividades para esta network
		 * @param Calendar com as horas e minutos para o agendamento de actividade
		 */
		private void addNewAgenda (Calendar novoAgendamento)
		{
			boolean placeFound=false;
			
			//iterar por todo o array e colocar ordenadamente 
			//este valor.
			
			for (int index=0;index<CFG_agenda.size();index++)
			{
				//se for igual ignora
				if ((novoAgendamento.get(Calendar.HOUR_OF_DAY)*100)
						 +novoAgendamento.get(Calendar.MINUTE) == (CFG_agenda.get(index).get(
								 Calendar.HOUR_OF_DAY)*100)+
							CFG_agenda.get(index).get(Calendar.MINUTE)) 			
					{
						//ja existe
						placeFound=true;
						break;
					}

				//se for menor adiciona  
				if ((novoAgendamento.get(Calendar.HOUR_OF_DAY)*100)
					 +novoAgendamento.get(Calendar.MINUTE) < (CFG_agenda.get(index).get(
							 Calendar.HOUR_OF_DAY)*100)+
						CFG_agenda.get(index).get(Calendar.MINUTE)) 			
				{
					CFG_agenda.add(index,(Calendar) novoAgendamento.clone());
					placeFound=true;
					break;
				}
			}

			if (!placeFound)
				CFG_agenda.add ((Calendar) novoAgendamento.clone());
		}
	
		/**************************************************************
		 * Metodo para obter os agendamentos definidos de actividades para este ambiente
		 * @return Calendar retorna um objecto do tipo calendario com o agendamento
		 *  desta rede.
		 */
		private Calendar[] getCalendar ()	
		{	
			Calendar [] agendado = new Calendar[CFG_agenda.size()];
				
			for (int index=0;index<CFG_agenda.size();index++)
			{
				agendado[index] = (Calendar) CFG_agenda.get(index).clone();
				//agendado[contador].set(Calendar.HOUR_OF_DAY,)=iter.next();
			}
			return agendado;		
		}
		
		/**********************************************************
		 * 
		 */
		private void resetCalendar ()
		{
			CFG_agenda.clear();
		}
		
		/************************************************************
		 * Metodo que retorna o nome da rede alvo a instalar o pacote
		 * @return String com o nome da rede configurado.
		 */
		private String getNetworkName ()	{	return CFG_network_name;	}
	}

	/****************************************************************************
	 * Construtor do objecto Configuração Serializavel e escito em disco.
	 * ********************************************************************
	 ***************************************************************************/
	public ServerConfigData ()
	{
		Calendar temp = Calendar.getInstance();

		//Adiciona uma nova rede configuravel
		CFG_deploy_networks.add (new Networks("QREN PRODUÇÃO"));
		
		temp.set(Calendar.HOUR_OF_DAY,8);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(0).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,12);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(0).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,14);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(0).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,16);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(0).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,10);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(0).addNewAgenda(temp);
		
		CFG_deploy_networks.get(0).addNewContent ("Active Server Pages");
		CFG_deploy_networks.get(0).CFG_conteudos.get(0).setContentZipName("+ASP");
		CFG_deploy_networks.get(0).CFG_conteudos.get(0).setContentWeight(10);
		CFG_deploy_networks.get(0).CFG_conteudos.get(0).setContentInstallDir("asp/");
		CFG_deploy_networks.get(0).CFG_conteudos.get(0).setContentAgent(PROCESS00);
		CFG_deploy_networks.get(0).CFG_conteudos.get(0).addNewAgentAddress("10.10.103.13");
		CFG_deploy_networks.get(0).CFG_conteudos.get(0).addNewAgentAddress("10.10.103.14");
		CFG_deploy_networks.get(0).CFG_conteudos.get(0).addNewAgentAddress("10.10.103.15");
		
		CFG_deploy_networks.get(0).addNewContent ("Components");
		CFG_deploy_networks.get(0).CFG_conteudos.get(1).setContentZipName("+COM");
		CFG_deploy_networks.get(0).CFG_conteudos.get(1).setContentWeight(20);
		CFG_deploy_networks.get(0).CFG_conteudos.get(1).setContentInstallDir("coms/");
		CFG_deploy_networks.get(0).CFG_conteudos.get(1).setContentAgent(PROCESS01);
		CFG_deploy_networks.get(0).CFG_conteudos.get(1).addNewAgentAddress("10.10.103.13");
		CFG_deploy_networks.get(0).CFG_conteudos.get(1).addNewAgentAddress("10.10.103.14");
		CFG_deploy_networks.get(0).CFG_conteudos.get(1).addNewAgentAddress("10.10.103.15");
		
		CFG_deploy_networks.get(0).addNewContent ("SQL Scripts");
		CFG_deploy_networks.get(0).CFG_conteudos.get(2).setContentZipName("+SCRIPTS");
		CFG_deploy_networks.get(0).CFG_conteudos.get(2).setContentWeight(30);
		CFG_deploy_networks.get(0).CFG_conteudos.get(2).setContentInstallDir("");
		CFG_deploy_networks.get(0).CFG_conteudos.get(2).setContentAgent(PROCESS02);
		CFG_deploy_networks.get(0).CFG_conteudos.get(2).addNewAgentAddress("10.10.105.20");

		CFG_deploy_networks.get(0).addNewContent ("Offline Batch jobs");
		CFG_deploy_networks.get(0).CFG_conteudos.get(3).setContentZipName("+OUTROS");
		CFG_deploy_networks.get(0).CFG_conteudos.get(3).setContentWeight(5);
		CFG_deploy_networks.get(0).CFG_conteudos.get(3).setContentInstallDir("qren_uploads/");
		CFG_deploy_networks.get(0).CFG_conteudos.get(3).setContentAgent(PROCESS03);
		CFG_deploy_networks.get(0).CFG_conteudos.get(3).addNewAgentAddress("10.10.103.13");
		CFG_deploy_networks.get(0).CFG_conteudos.get(3).addNewAgentAddress("10.10.103.14");
		CFG_deploy_networks.get(0).CFG_conteudos.get(3).addNewAgentAddress("10.10.103.15");
		CFG_deploy_networks.get(0).setConfigValid(true);
		
		//Adiciona uma nova rede configuravel	
		CFG_deploy_networks.add (new Networks ("QREN ENSAIO"));
		temp.set(Calendar.HOUR_OF_DAY,8);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(1).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,10);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(1).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,12);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(1).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,14);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(1).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,16);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(1).addNewAgenda(temp);
		
		CFG_deploy_networks.get(1).addNewContent ("Active Server Pages");
		CFG_deploy_networks.get(1).CFG_conteudos.get(0).setContentZipName("+ASP");
		CFG_deploy_networks.get(1).CFG_conteudos.get(0).setContentWeight(10);
		CFG_deploy_networks.get(1).CFG_conteudos.get(0).setContentInstallDir("asp/");
		CFG_deploy_networks.get(1).CFG_conteudos.get(0).setContentAgent(PROCESS00);
		CFG_deploy_networks.get(1).CFG_conteudos.get(0).addNewAgentAddress("10.10.43.199");
		
		CFG_deploy_networks.get(1).addNewContent ("Components");
		CFG_deploy_networks.get(1).CFG_conteudos.get(1).setContentZipName("+COM");
		CFG_deploy_networks.get(1).CFG_conteudos.get(1).setContentWeight(20);
		CFG_deploy_networks.get(1).CFG_conteudos.get(1).setContentInstallDir("coms/");
		CFG_deploy_networks.get(1).CFG_conteudos.get(1).setContentAgent(PROCESS01);
		CFG_deploy_networks.get(1).CFG_conteudos.get(1).addNewAgentAddress("10.10.43.199");

		CFG_deploy_networks.get(1).addNewContent ("SQL Scripts");
		CFG_deploy_networks.get(1).CFG_conteudos.get(2).setContentZipName("+SCRIPTS");
		CFG_deploy_networks.get(1).CFG_conteudos.get(2).setContentWeight(30);
		CFG_deploy_networks.get(1).CFG_conteudos.get(2).setContentInstallDir("");
		CFG_deploy_networks.get(1).CFG_conteudos.get(2).setContentAgent(PROCESS02);
		CFG_deploy_networks.get(1).CFG_conteudos.get(2).addNewAgentAddress("10.10.45.30");

		CFG_deploy_networks.get(1).addNewContent ("Offline Batch jobs");
		CFG_deploy_networks.get(1).CFG_conteudos.get(3).setContentZipName("+OUTROS");
		CFG_deploy_networks.get(1).CFG_conteudos.get(3).setContentWeight(5);
		CFG_deploy_networks.get(1).CFG_conteudos.get(3).setContentInstallDir("qren_uploads/");
		CFG_deploy_networks.get(1).CFG_conteudos.get(3).setContentAgent(PROCESS03);
		CFG_deploy_networks.get(1).CFG_conteudos.get(3).addNewAgentAddress("10.10.43.199");

		CFG_deploy_networks.get(1).setConfigValid(true);
		
		CFG_deploy_networks.add (new Networks ("QCA PRODUÇÃO"));
		temp.set(Calendar.HOUR_OF_DAY,8);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(2).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,10);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(2).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,12);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(2).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,14);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(2).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,16);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(2).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,11);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(2).addNewAgenda(temp);
		
		CFG_deploy_networks.get(2).addNewContent ("Active Server Pages");
		CFG_deploy_networks.get(2).CFG_conteudos.get(0).setContentZipName("+ASP");
		CFG_deploy_networks.get(2).CFG_conteudos.get(0).setContentWeight(10);
		CFG_deploy_networks.get(2).CFG_conteudos.get(0).setContentInstallDir("asp/");
		CFG_deploy_networks.get(2).CFG_conteudos.get(0).setContentAgent(PROCESS00);
		CFG_deploy_networks.get(2).CFG_conteudos.get(0).addNewAgentAddress("10.10.103.11");
		CFG_deploy_networks.get(2).CFG_conteudos.get(0).addNewAgentAddress("10.10.103.12");

		CFG_deploy_networks.get(2).addNewContent ("Components");
		CFG_deploy_networks.get(2).CFG_conteudos.get(1).setContentZipName("+COM");
		CFG_deploy_networks.get(2).CFG_conteudos.get(1).setContentWeight(20);
		CFG_deploy_networks.get(2).CFG_conteudos.get(1).setContentInstallDir("coms/");
		CFG_deploy_networks.get(2).CFG_conteudos.get(1).setContentAgent(PROCESS01);
		CFG_deploy_networks.get(2).CFG_conteudos.get(1).addNewAgentAddress("10.10.103.11");
		CFG_deploy_networks.get(2).CFG_conteudos.get(1).addNewAgentAddress("10.10.103.12");
		
		CFG_deploy_networks.get(2).addNewContent ("SQL Scripts");
		CFG_deploy_networks.get(2).CFG_conteudos.get(2).setContentZipName("+SCRIPTS");
		CFG_deploy_networks.get(2).CFG_conteudos.get(2).setContentWeight(30);
		CFG_deploy_networks.get(2).CFG_conteudos.get(2).setContentInstallDir("");
		CFG_deploy_networks.get(2).CFG_conteudos.get(2).setContentAgent(PROCESS02);
		CFG_deploy_networks.get(2).CFG_conteudos.get(2).addNewAgentAddress("10.10.5.23");

		CFG_deploy_networks.get(2).addNewContent ("Offline Batch jobs");
		CFG_deploy_networks.get(2).CFG_conteudos.get(3).setContentZipName("+OUTROS");
		CFG_deploy_networks.get(2).CFG_conteudos.get(3).setContentWeight(5);
		CFG_deploy_networks.get(2).CFG_conteudos.get(3).setContentInstallDir("qren_uploads/");
		CFG_deploy_networks.get(2).CFG_conteudos.get(3).setContentAgent(PROCESS03);
		CFG_deploy_networks.get(2).CFG_conteudos.get(3).addNewAgentAddress("10.10.103.11");
		CFG_deploy_networks.get(2).CFG_conteudos.get(3).addNewAgentAddress("10.10.103.12");
		CFG_deploy_networks.get(2).setConfigValid(true);
		
		CFG_deploy_networks.add ( new Networks ("QCA ENSAIO"));
		temp.set(Calendar.HOUR_OF_DAY,8);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(3).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,10);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(3).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,12);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(3).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,14);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(3).addNewAgenda(temp);
		temp.set(Calendar.HOUR_OF_DAY,16);temp.set(Calendar.MINUTE,00);
		CFG_deploy_networks.get(3).addNewAgenda(temp);
		
		CFG_deploy_networks.get(3).addNewContent ("Active Server Pages");
		CFG_deploy_networks.get(3).CFG_conteudos.get(0).setContentZipName("+ASP");
		CFG_deploy_networks.get(3).CFG_conteudos.get(0).setContentWeight(10);
		CFG_deploy_networks.get(3).CFG_conteudos.get(0).setContentInstallDir("asp/");
		CFG_deploy_networks.get(3).CFG_conteudos.get(0).setContentAgent(PROCESS00);
		CFG_deploy_networks.get(3).CFG_conteudos.get(0).addNewAgentAddress("10.10.43.198");
		
		CFG_deploy_networks.get(3).addNewContent ("Components");
		CFG_deploy_networks.get(3).CFG_conteudos.get(1).setContentZipName("+COM");
		CFG_deploy_networks.get(3).CFG_conteudos.get(1).setContentWeight(20);
		CFG_deploy_networks.get(3).CFG_conteudos.get(1).setContentInstallDir("coms/");
		CFG_deploy_networks.get(3).CFG_conteudos.get(1).setContentAgent(PROCESS01);
		CFG_deploy_networks.get(3).CFG_conteudos.get(1).addNewAgentAddress("10.10.43.198");

		CFG_deploy_networks.get(3).addNewContent ("SQL Scripts");
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentZipName("+SCRIPTS");
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentWeight(30);
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentInstallDir("");
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentAgent(PROCESS02);
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).addNewAgentAddress("10.10.45.20");

		CFG_deploy_networks.get(3).addNewContent ("Offline Batch jobs");
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentZipName("+OUTROS");
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentWeight(5);
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentInstallDir("qren_uploads/");
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).setContentAgent(PROCESS03);
		CFG_deploy_networks.get(3).CFG_conteudos.get(2).addNewAgentAddress("10.10.43.198");
		CFG_deploy_networks.get(3).setConfigValid(true);
	}
	/**********************************************************************
	 * Metodo que retorna o directorio aonde se encontram os ficheiros de configuração
	 * @return String com o caminho relativo
	 */
	public String getConfigFilesDir ()	{		return CFG_cfg_file_dir;	}
	
	/**********************************************************************
	 * Metodo que retorna o directorio aonde se encontram os ficheiros Pacote
	 * @return String com o caminho relativo
	 */
	public String getConfigPkgDir()	{		return CFG_pkg_dir;	}
	
	/**********************************************************************
	 * Metodo que retorna o directorio aonde se encontram os ficheiros relatorios
	 * @return String com o caminho relativo
	 */
	public String getConfigReportDir()	{		return CFG_report_file_dir;	}
	
	/**********************************************************************
	 * Metodo que retorna o numero de redes configuradas
	 * @return int com o numero de redes configuradas.
	 */
	public int getNumberOfNetworksConfigured ()	{		return CFG_deploy_networks.size();	}
	
	/**********************************************************************
	 * Metodo que retorna o directorio de trabalho interno.
	 * @return String com o caminho relativo
	 */
	public String getConfigWorkingDir()	{		return CFG_working_dir;	}
	
	/******************************************************************
	 * Metodo que retorna a versão de configuração do servidor
	 * @return long valor da vversão de configuração do servidor.
	 */
	public long getConfigVersion ()	{		return configVersion;	}
	
	/*****************************************************************
	 * Metodo que incrementa o valor de versao de configuraçao uma unidade.
	 */
	public void updateConfigVersion ()	{		configVersion++;	}
	
	
	/***********************************************************************
	 * Metodo que retorna a porta de rede configurada para este servidor
	 * @return int com o numero de porta de rede.
	 */
	public int getServerPortAddress ()	{ return this.serverPortAddress;	}

	/***********************************************************************
	 * Metodo que retorna a porta de rede configurada para os agentes
	 * @return int com o numero de porta de rede.
	 */
	public int getAgentPortAddress ()	{ return this.agentPortAddress;	}

	
	
	/***********************************************************************
	 * Metodo que retorna o nome da rede consuante o networkid
	 * @param networkid long numero de serie que identifica a rede alvo configurada
	 * @return String nome da rede configurada.
	 */
	public String getDeployNetworkName (int networkid)	{
		return CFG_deploy_networks.get(networkid).getNetworkName();
	}
	
	/***************************************************************
	 * Metodo que devolve o agendamento configurado para esta rede de deploy
	 * @param networkid int que identifica e referencia a rede das configurações 
	 * @return Calendar[] Agendamento para esta rede.
	 */
	public Calendar [] getDeployNetworkCalendar (int networkid)	{
		return CFG_deploy_networks.get(networkid).getCalendar();
	}
	
	/***************************************************************
	 * Metodo que devolve o agendamento configurado para esta rede de deploy
	 * @param networkid int que identifica e referencia a rede das configurações 
	 * @return Calendar[] Agendamento para esta rede.
	 */
	public void setDeployNetworkCalendar (int networkid, Calendar temp)	{
		CFG_deploy_networks.get(networkid).addNewAgenda(temp);
	}
	
	/***************************************************************
	 * Metodo que devolve o agendamento configurado para esta rede de deploy
	 * @param networkid int que identifica e referencia a rede das configurações 
	 * @return Calendar[] Agendamento para esta rede.
	 */
	public void resetDeployNetworkCalendar (int networkid)	{
		CFG_deploy_networks.get(networkid).resetCalendar();
	}

	/*******************************************************************
	 * Metodo que devolve um array de strings com os nomes dos conteudos definidos
	 * @param networkid Id da rede configurada (ambiente)
	 * @return String [] com os nomes dos conteudos configurados.
	 */
	public String [] getDeployNetworkContentNames (int networkid)	{
		return CFG_deploy_networks.get(networkid).getContentNames();
	}

	/*******************************************************************
	 * Metodo que devolve um array de strings com os nomes dos conteudos definidos
	 * @param networkid Id da rede configurada (ambiente)
	 * @param networkid Id do Conteudo da rede configurada (ambiente)
	 * @return String [] com os nomes dos conteudos configurados.
	 */
	public String [] getDeployNetworkContentAgentAddresses (int networkid, int content)	{
		return CFG_deploy_networks.get(networkid).getContentAgentAddresses(content);
	}
	
	/*******************************************************************
	 * Metodo que devolve um array de strings com os nomes dos conteudos definidos
	 * @param networkid Id da rede configurada (ambiente)
	 * @param networkid Id do Conteudo da rede configurada (ambiente)
	 * @return String com o nome da pasta ZIP configuradadA
	 *  
	 */
	public String getDeployNetworkContentZipFolder (int networkid, int content)	{
		return CFG_deploy_networks.get(networkid).getContentZipFolder (content);
	}

	/*******************************************************************
	 * Metodo que devolve um array de strings com os nomes dos conteudos definidos
	 * @param networkid Id da rede configurada (ambiente)
	 * @param networkid Id do Conteudo da rede configurada (ambiente)
	 * @return String com o nome da pasta ZIP configuradadA
	 */
	public String getDeployNetworkContentPriority (int networkid, int content)	{
		return CFG_deploy_networks.get(networkid).getContentPriority (content);
	}
	
	/*******************************************************************
	 * Metodo que devolve um array de strings com os nomes dos conteudos definidos
	 * @param networkid Id da rede configurada (ambiente)
	 * @param networkid Id do Conteudo da rede configurada (ambiente)
	 * @return String com o nome da pasta ZIP configuradadA
	 */
	public int getDeployNetworkContentPriorityValue (int networkid, int content)	{
		return CFG_deploy_networks.get(networkid).getContentPriorityValue(content);
	}

	
	/*******************************************************************
	 * Metodo que devolve um array de strings com os nomes dos conteudos definidos
	 * @param networkid Id da rede configurada (ambiente)
	 * @param networkid Id do Conteudo da rede configurada (ambiente)
	 * @return String com o nome da pasta ZIP configuradadA
	 *  
	 */
	public String getDeployNetworkContentInstallDir (int networkid, int content)	{
		return CFG_deploy_networks.get(networkid).getContentInstallDir (content);
	}

	/*******************************************************************
	 * Metodo que devolve um array de strings com os nomes dos conteudos definidos
	 * @param networkid Id da rede configurada (ambiente)
	 * @param networkid Id do Conteudo da rede configurada (ambiente)
	 * @return String com o nome da pasta ZIP configuradadA
	 *  
	 */
	public String getDeployNetworkContentAgentType (int networkid, int content)	{
		return CFG_deploy_networks.get(networkid).getContentAgentType (content);
	}
	
	/************************************************************************
	 * 
	 * @param networkid
	 * @return
	 */
	public boolean isDeployNetworkConfigValid (int networkid)	{
		return CFG_deploy_networks.get(networkid).isConfigValid();
	}
	
	
		
	
	
	
}
