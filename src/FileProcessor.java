import java.io.*;
import java.nio.file.*;
import java.util.*;

import javafx.application.Application;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FileProcessor extends Application {

	private final String FILE_TYPE_LOCATION = "//Users//alin//Documents//workspace//CSE306//RyansFileProcessor//src//Types.txt";
	private ArrayList<VBox> filesImported = new ArrayList<>();

	public ArrayList<String> readFile() throws IOException {
		ArrayList<String> typesList = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(FILE_TYPE_LOCATION));
		for (String line : lines) {
			typesList.add(line);
		}
		return typesList;
	}

	public HBox initP1() {
		Label dragDrop = new Label("Drag & Drop your \nfiles here");
		// HBOX IS HORIZONTAL BOX. VBOX IS VERTICAL BOX.
		HBox hb = new HBox(8);
		hb.getChildren().add(dragDrop);
		hb.setStyle("-fx-border-color: black;");
		return hb;
	}

	public VBox initP2() {
		Label askNum = new Label("Enter the file number: ");
		TextField numField = new TextField();
		numField.setMaxWidth(100);
		Button kk = new Button("ok");
		VBox hb2 = new VBox(8);
		hb2.getChildren().addAll(askNum, numField, kk);
		hb2.setStyle("-fx-border-color: black;");
		return hb2;
	}

	public VBox initP3() {

		 ListView<VBox> lv = new ListView<>();
		 ObservableList<VBox> items =
		 FXCollections.observableArrayList(filesImported);
		 lv.setItems(items);
		VBox hb3 = new VBox(8);
		hb3.getChildren().addAll(lv);
		hb3.setStyle("-fx-border-color: black;");
		return hb3;
	}

	public void showTypesOnP3(ArrayList<String> types) {
		ListView<HBox> ab = new ListView<>();
		for (int j = 0; j < 5; j++) {
			HBox top = new HBox();
			Label x = new Label("click one type: ");
			top.getChildren().addAll(x);
			Button a = new Button();
			for (int i = 0; i < types.size(); i++) {
				a = new Button(types.get(i));
				top.getChildren().add(a);
			}
			ab.setItems((ObservableList<HBox>) top);
		}

	}

	@Override
	public void start(Stage primaryStage) {

		ArrayList<String> types = null;

		try {
			types = readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BorderPane border = new BorderPane();
		HBox hb1 = initP1();
		VBox hb2 = initP2();
		VBox hb3 = initP3();

		// SETTING NODES TO BORDER LAYOUT
		border.setCenter(hb2);
		border.setLeft(hb1);
		border.setRight(hb3);

		Scene scene = new Scene(border, 551, 400);
		hb1.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});

		// Dropping over surface
		hb1.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					success = true;
					String filePath = null;
					for (File file : db.getFiles()) {
						filePath = file.getAbsolutePath();
						// DO STUFF WITH FILES DROPPED
						System.out.println(filePath);
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
