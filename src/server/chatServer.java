package server;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;
import client.clientit;


public class chatServer extends UnicastRemoteObject implements serverif{
	private Vector<chatter> chatters;
	public static serverif hello;
	protected chatServer() throws RemoteException {
		super();
		chatters = new Vector<chatter>(30, 1);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		//launch(args);
            
			chatServer.startRMIRegistry();	
			String hostName = "localhost";
			String serviceName = "GroupChatService";
			
			
			
			try{
				hello = new chatServer();
				Naming.rebind("rmi://" + hostName + "/" + serviceName, hello);
				System.out.println(hello.toString());
				System.out.println("Group Chat RMI Server is running...");
			}
			catch(Exception e){
				System.out.println("Server had problems starting");
			}	
		}
	public static void startRMIRegistry() {
		try{
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("RMI Server ready");
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateChat(String name, String nextPost) throws RemoteException {
		String message =  name + " : " + nextPost + "\n";
		sendToAll(message);
	}
	
	

	public void sendToAll(String newMessage){	
		for(chatter c : chatters){
			try {
				c.getClient().messageFromServer(newMessage);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	@Override
	public void passIDentity(RemoteRef ref) throws RemoteException {	
		//System.out.println("\n" + ref.remoteToString() + "\n");
		try{
			System.out.println("----------------------------------------------\n"+ ref.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void registerListener(String[] details) throws RemoteException {	
		
		System.out.println(details[0] + " has joined the chat session");
		System.out.println(details[0] + "'s hostname : " + details[1]);
		System.out.println(details[0] + "'sRMI service : " + details[2]);
		registerChatter(details);
	}

	private void registerChatter(String[] details){		
		try{
			clientit nextClient = ( clientit )Naming.lookup("rmi://"+details[1] + "/" + details[2]);
			
			chatters.addElement(new chatter(details[0], nextClient));
			
			nextClient.messageFromServer(" Bienvenue  " + details[0] + " tu peut participer .\n");
			
			sendToAll("Alexa : " + details[0] + " est la.\n");
			
			updateUserList();		
		}
		catch(RemoteException | MalformedURLException | NotBoundException e){
			e.printStackTrace();
		}
	}
	private void updateUserList() {
		String[] currentUsers = getUserList();	
		for(chatter c : chatters){
			try {
				c.getClient().updateUserList(currentUsers);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	private String[] getUserList(){
		// generate an array of current users
		String[] allUsers = new String[chatters.size()];
		for(int i = 0; i< allUsers.length; i++){
			allUsers[i] = chatters.elementAt(i).getName();
		}
		return allUsers;
	}
	@Override
	public void leaveChat(String userName) throws RemoteException{
		
		for(chatter c : chatters){
			if(c.getName().equals(userName)){
				System.out.println("-------------------------------------------------\n"+ userName + " left the chat ");
				
				chatters.remove(c);
				break;
			}
		}		
		if(!chatters.isEmpty()){
			updateUserList();
		}			
	}

	@Override
	public void sendPM(int privateGroup, String privateMessage) throws RemoteException{
		chatter pc;
		System.out.println("\n" +privateMessage);
			pc= chatters.elementAt(privateGroup);
			pc.getClient().messageFromServer(privateMessage);
		
	}

}
