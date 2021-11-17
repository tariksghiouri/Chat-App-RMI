package client;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class loginController {
	public String smiya;
	@FXML
	private BorderPane root;
@FXML
private TextField nom;
@FXML
void login(ActionEvent event) throws IOException {
	
	try {
		
		Stage Stg = (Stage) root.getScene().getWindow();
		Stg.close();
        
        FXMLLoader loader =new FXMLLoader(getClass().getResource("Sample.fxml"));
        BorderPane root = loader.load();
         
        
        Main scene2Controller = loader.getController();
        
        scene2Controller.initialize(nom.getText());
       
                
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(nom.getText()+" chatt room");
       
        scene2Controller.getstage(stage);
        stage.show();
    } catch (Exception ex) {
        System.err.println(ex);
    }
	
	
	
	
	/*
	
	String name = nom.getText();				
	
		
	
		
 BorderPane pane=FXMLLoader.load(getClass().getResource("Sample.fxml"));
 Stage Stg = (Stage) root.getScene().getWindow();
Stg.setTitle(name);
Scene hii= root.getScene();
hii.setRoot(pane);

root.setPrefSize(800,600);*/
}
}