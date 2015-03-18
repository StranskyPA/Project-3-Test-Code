package application;
	
import Networking.CustomServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private Controller controller;
	private Model model = new Model();
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("Gui.fxml"));
			VBox root = (VBox) loader.load();
			
			Scene scene = new Scene(root,640,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ChatApp");
			primaryStage.show();
			
			CustomServer server;
			server = new CustomServer(8888, this, model);
			server.start();
			
			controller = loader.getController();
			controller.setModel(model);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Application.launch(Main.class, new String[0]);
	}
}
