package application;

import java.io.File;
import java.io.IOException;

import packet.Packetizer;

import com.sun.javafx.scene.control.skin.CustomColorDialog;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
	@FXML
	private Button send;
	@FXML
	private Button attach;
	@FXML
	private Button receive;
	@FXML
	private ListView<Text> chatArea;
	@FXML
	private VBox applicationBounds;
	@FXML
	private MenuItem closeChat;
	@FXML
	private MenuItem newChat;
	@FXML
	private MenuItem preferences;
	@FXML
	private MenuItem quitApp;
	@FXML
	private MenuItem inviteChat;
	@FXML
	private MenuItem saveChat;
	@FXML
	private TextField messageText;
	@FXML
	private ScrollPane scroll;
	
	private Model model;
	private Packetizer packetizer = new Packetizer(1024);
	
	private Color userColor = Color.BLUE;
	private Color ReceiveColor = Color.BLACK;
	
	@FXML
	private void initialize(){
		chatArea.setItems(model.getObservable());
		Text welcome = new Text();
		welcome.setText("Welcome to ChatApp!\r\n");
		model.initializeChat(welcome);
	}
	
	public void setModel(Model model){
		this.model = model;
	}
	
	@FXML
	public void colorPicker(){
		CustomColorDialog dialog = new CustomColorDialog(applicationBounds.getScene().getWindow());
		dialog.show();
		System.out.println("DONE");
	}
	
	public void receiveMessage(File f){
		Text text = new Text();
		String msg = f.toString();
		text.setText(msg);
		model.addMessage(text);
	}
	
	@FXML
	public void inviteChat(){
		final Stage newStage = new Stage();
		VBox comp = new VBox();
		final TextField host = new TextField();
		Label hostLabel = new Label("Host: ");
		final TextField port = new TextField();
		Label portLabel = new Label("Port: ");
		comp.getChildren().addAll(hostLabel, host, portLabel, port);
		host.setOnAction(new EventHandler<ActionEvent>() {
		   public void handle(ActionEvent event) {
		        sendInfo(host, port);
		        newStage.close();
		    }     
		});  
		port.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
		    	sendInfo(host, port);
		    	newStage.close();
		    }     
		}); 

		Scene stageScene = new Scene(comp, 300, 150);
		newStage.setTitle("Invite to Chat");
		newStage.setScene(stageScene);
		newStage.show();
	}
	
	private void sendInfo(TextField host, TextField port){
		System.out.println(host.getText());
		System.out.println(port.getText());
		model.setHost(host.getText());
		model.setPort(port.getText());
	}
	
	@FXML
	public void testReceiveMessage(){
		Text text = new Text();
		text.setText("This is a test, I am sending you a message ,This is a test, I am sending you a message, This is a test, I am sending you a message");
		model.receiveMessage(text);
		messageText.clear();
		chatArea.scrollTo(model.getObservable().size());
		Rectangle rec = new Rectangle(300,100);
		rec.setFill(Color.CYAN);
		model.getObservable().get(model.chatIndex).setStroke(ReceiveColor);
	}
	
	@FXML
	public void attachFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File attachFile = fileChooser.showOpenDialog(applicationBounds.getScene().getWindow());
		if (attachFile != null){
			//messageText.setText(attachFile.toString());
			packetizer.packetize(attachFile);
		}
	}
	
	@FXML
	public void sendMessage(){
		Text text = new Text();
		text.setText(messageText.getText());
		model.addMessage(text);
		messageText.clear();
		chatArea.scrollTo(model.getObservable().size());
		model.getObservable().get(model.chatIndex).setStroke(userColor);
		model.send(text.toString());
	}
	
	@FXML
	public void closeWindow(){
		Stage stage = (Stage) applicationBounds.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void endApplication(){
		Platform.exit();
	}
	
	@FXML
	public void saveChat() throws IOException{
		System.out.println(("user.dir") + "\\ChatLogs");
		final FileChooser fileChooser = new FileChooser();
	    File listFileDirectory = new File(System.getProperty("user.dir"), "ChatLogs");
	    listFileDirectory.mkdirs();
	    fileChooser.setInitialDirectory(
	            new File(System.getProperty("user.dir") + "\\ChatLogs"));
	    listFileDirectory = fileChooser.showSaveDialog(applicationBounds.getScene().getWindow());
	    if (listFileDirectory != null){
	    	model.saveChat(listFileDirectory);
	    }
	}
	
	@FXML
	public void newMenu() throws IOException {
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Gui.fxml"));
		root = loader.load();
	    Stage stage = new Stage();
        stage.setTitle("ChatApp");
        stage.setScene(new Scene(root, 640, 500));
        stage.show();
	}

}
