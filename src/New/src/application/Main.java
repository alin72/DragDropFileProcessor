package application;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Main extends Application {

	// Alice's work directory:
	// private final String
	private final String FILE_TYPE_LOCATION = "C://Users//916423//Documents//workspace//FileProcessProject//Types.txt";

	// Alice's destination:
	private final String FILE_ROOT_DESTINATION = "C://Users//Public//Music//Sample Music";

	// Alice's home directory:
	// "//Users//alin//Documents//workspace//CSE306//RyansFileProcessor//src//Types.txt";

	// Ryan's work directory:
	// C://Users//jhu//Desktop//branchy//deebee

	// Ryan's home directory:
	// C://Users//jhu//Desktop//branchy//src//Types.txt
	@FXML
	private BorderPane bp;

	@Override
	public void start(Stage stg) throws Exception {
		Parent root = FXMLLoader.load(ClassLoader.getSystemResource("application/GUILayout.fxml"));
		VBox rightButtons = new VBox();
		
		MainController c = new MainController();
		
	//	ArrayList<String> listOfTypes = readTypesFromFile();
	//	for (String type : listOfTypes) {
	//		rightButtons.getChildren().add(new Button(type));
	//	}
	//	bp.setRight(rightButtons);

		stg.setTitle("File Processor");
		Scene scene = new Scene(root);
		stg.setScene(scene);
		stg.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
