package client;
	
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class Main extends Application implements ActionListener {
	
	@FXML
	public BorderPane rot;
	@FXML
	public BorderPane mesBorderPane;
	@FXML
	public Text messages;
	
	 @FXML
	public  AnchorPane  utils;
	 @FXML
	public ButtonBar  ah;
	 @FXML
	 public TextField message;
	 
	 
	public String[] liste;
	public static final long serialVersionUID = 1L;	
	public chatclient chatClient;
	public  VBox  box;
	public int c;
	public String name,m;
	
	
	@FXML
    void initialize(String text) throws RemoteException {
	
	//Stage Stg = (Stage) rot.getScene().getWindow();
	//name=Stg.getTitle();
	 name=text;
	 System.out.println(text);
    	
    	getConnected(name);
    	
    }
	 @FXML
	 void click(ActionEvent event) throws RemoteException {
			//messages.setText(messages.getText()+"hhhhh");
			m = message.getText();
			message.setText("");
			sendMessage(m);
			System.out.println("Sending message : " + m);
			    } 
	
	 
	 
	 public void getConnected(String userName) throws RemoteException{
			//remove whitespace and non word characters to avoid malformed url
			String cleanedUserName = userName.replaceAll("\\s+","_");
			cleanedUserName = userName.replaceAll("\\W+","_");
			try {		
				chatClient = new chatclient(this, cleanedUserName);
				chatClient.startClient();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	public void sendMessage(String chatMessage) throws RemoteException {
			chatClient.serverIF.updateChat(name, chatMessage);
		}
	 public  void setClientPanel(String[] currClients)  {  
		 box=new VBox();
		 box.setPrefWidth(195);
		this.c=0;
		 liste=currClients;
	        for(String s : currClients) {
	        	if(!name.contains(s)) {
	        	Button ba= new Button();
	        	ba.setPrefSize(195, 32);
	        	ba.setText(s);
	        	ba.setId(""+c);
	        	 ba.setOnAction (valeur -> {
	                
	                	//System.out.println("selected index :" +ba.getId());
	             		
	             		m = message.getText();
	             		message.setText("");
	             		try {
							sendPrivate(Integer.parseInt(ba.getId()));
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
	              });
	        	
	        	System.out.println(s+" hzhzh");
				box.getChildren().add(ba);
				
	        	
	        }this.c++;}
	        try {
	        	 utils.setPrefWidth(195);
	        	 utils.getChildren().add(box);  
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	        
	        //Create the list and put it in a scroll pane.
	       
	    }
	 
	 
	 private void sendPrivate(int privateList) throws RemoteException {
			String privateMessage = "[PM from " + name + "] :" + m + "\n";
			chatClient.serverIF.sendPM(privateList, privateMessage);
		}
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			

				
		
			
			
						 
		   
		   
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}catch(Exception e){
		}
		launch(args);
	}
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void getstage(Stage stage) {
		 stage.setOnCloseRequest( new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent arg0) {
					if(chatClient != null){
				    	try {
				        	sendMessage("baaabaay !!!!");
				        	chatClient.serverIF.leaveChat(name);
						} catch (RemoteException e) {
							e.printStackTrace();
						}		        	
			        }
			        System.exit(0);  
					
				}
			});
		
	}
	
}
