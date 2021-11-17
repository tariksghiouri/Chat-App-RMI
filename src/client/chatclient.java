package client;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import server.serverif;
public class chatclient  extends UnicastRemoteObject implements clientit {
	private static final long serialVersionUID =  7468891722773409712L;
	Main chatGUI;
	private String hostName = "localhost";
	private final String serviceName = "GroupChatService";
	private String clientServiceName;
	private String name;
	protected serverif serverIF;
	protected boolean connectionProblem = false;
	protected chatclient(Main aChatGUI, String userName) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.chatGUI = aChatGUI;
		this.name = userName;
		this.clientServiceName = "ClientListenService_" + userName;
	}

	/**
	 * 
	 */
	
	public void startClient() throws RemoteException {		
		String[] details = {name, hostName, clientServiceName};	

		try {
			Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
			serverIF = ( serverif )Naming.lookup("rmi://" + hostName + "/" + serviceName);	
		} 
		catch (ConnectException  e) {
			System.out.println("errooooooooorr server");
			
			connectionProblem = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			connectionProblem = true;
			me.printStackTrace();
		}
		if(!connectionProblem){
			registerWithServer(details);////////////////////////////////////////////////////////////////
		}	
		System.out.println("Client Listen RMI Server is running...\n");
	}
	@Override
	public void messageFromServer(String message) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println( message );
		//messaage sound 
		String musicFile = "src/start.wav";
		Media mApplause = new Media( new File(musicFile).toURI().toString());
		MediaPlayer start= new MediaPlayer(mApplause);
		start.play();
		//end message sound
		chatGUI.messages.setText( message+"\n"+chatGUI.messages.getText() );
		//make the gui display the last appended text, ie scroll to bottom
		//chatGUI.messages.setCaretPosition(chatGUI.messages.getText().length());
	}

	@Override
	public void updateUserList(String[] currentUsers) throws RemoteException {
		
		//
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				chatGUI.utils.getChildren().clear();
            	chatGUI.setClientPanel(currentUsers);
			}
			   
			});
		            	
		            	
		
		//chatGUI.clientPanel.repaint();
		//chatGUI.clientPanel.revalidate();
		
	}
	public void registerWithServer(String[] details) {		
		try{
			serverIF.passIDentity(this.ref);
			System.out.println(this.ref.toString()+"   haiahiahia");//now redundant ??
			serverIF.registerListener(details);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		
	}
	


