package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {

	@FXML
	private VBox leftPane, rightPane, centerPane;
	@FXML
	private Label fileNo;
	@FXML
	private Button clearButton;

	private ArrayList<String> listOfTypes = null;
	private ArrayList<File> filesImported = new ArrayList<>();

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

	/**
	 * First thing that runs
	 */
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		generateButtons();
	}

	private ArrayList<String> readTypesFromFile() {
		ArrayList<String> typesList = new ArrayList<>();
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(FILE_TYPE_LOCATION));
			for (String line : lines) {
				typesList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return typesList;
	}


	private void generateButtons() {
		listOfTypes = readTypesFromFile();
		Button x;
		for (String type : listOfTypes) {
			x = new Button(type);
			x.setOnAction(this::handleButtonAction);
			rightPane.getChildren().add(x);
		}
	}

	/**
	 * Action Handler for buttons.
	 * 
	 * It checks if the inputs are correct (files are drag/dropped, file number
	 * is entered)
	 * 
	 * If so, it moves the file to the determined destination.
	 * 
	 * @param event
	 */
	private void handleButtonAction(ActionEvent event) {
		if (fileNo != null) {
			
		}
	}
	
	@FXML
	private void fileDragDropHandler(DragEvent event) {
		System.out.println("Something dropped");
		Dragboard db = event.getDragboard();
		boolean success = false;
		
		if (db.hasFiles()) {
			success = true;
			//filesImported = new ArrayList<>();
			for (File file : db.getFiles()) {
				if (filesImported.indexOf(file) < 0) {
					filesImported.add(file);
					System.out.println(file.getName());
				} else {
					System.out.println("You already dropped the file: " + file.getName());
				}
			}
		}
		event.setDropCompleted(success);
	}

	@FXML
	private void DragDetectedHandler(DragEvent event) {
		Dragboard db = event.getDragboard();
		if (db.hasFiles()) {
			event.acceptTransferModes(TransferMode.COPY);
		} else {
			event.consume();
		}
	}
}
