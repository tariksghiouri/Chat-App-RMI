package client;



import javafx.event.ActionEvent;
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

public class SampleController {
	@FXML
	private Text messages;
	@FXML
	private Button ahmed;
	 @FXML
		private VBox  utils;
	 @FXML
		private ButtonBar  ah;
	
	public SampleController() {
		
	}
	 @FXML
    void warak(ActionEvent event) {
messages.setText(messages.getText()+"hhhhh");

Button ba= new Button();
utils.getChildren().add(ba);
    }
    @FXML
    void brk(ActionEvent event) {
    	
    }
    

}
