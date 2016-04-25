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


	// Alice's work directory:
	// C://Users//916423//Documents//workspace//DragDropFileProcessor-master//src//Types.txt

	// Alice's home directory:
	// //Users//alin//Documents//workspace//CSE306//RyansFileProcessor//src//Types.txt
	
	// Ryan's work directory:
	//  

	//Ryan's home directory:
	//  
	
	private final String FILE_TYPE_LOCATION = "C://Users//916423//Documents//workspace//DragDropFileProcessor-master//src//Types.txt";
	private ArrayList<File> filesImported = new ArrayList<>();
	private ArrayList<VBox> items = new ArrayList<>();
	private VBox hb3 = new VBox();
	private ArrayList<String> types = new ArrayList<>();
	private ListView<VBox> lv = new ListView<>();
	
	

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
		VBox hb2 = new VBox(8);
		hb2.getChildren().addAll(askNum, numField);
		hb2.setStyle("-fx-border-color: black;");
		return hb2;
	}


	public void initP3() {
		
		ObservableList<VBox> ol = FXCollections.observableArrayList(items);
		lv.setItems(ol);
		hb3.getChildren().addAll(lv);
		hb3.setStyle("-fx-border-color: black;");
	}
	
	// creates the vbox to add as each item in the queue.
	//the vbox will contain the filename & buttons of types
	public void generateQueue(){
		VBox item;
		HBox buttons;
		for(File f:filesImported){
			 buttons = new HBox();
			item = new VBox();
			item.getChildren().add(new Label(f.getName()));
			
			for(int j = 0; j< types.size();j++){
				buttons.getChildren().add(new Button(types.get(j)));
			}
			item.getChildren().add(buttons);
			
			items.add(item);
		}

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			types = readFile();
			//after getting the array of types, show them on p3 as test
			BorderPane border = new BorderPane();
			HBox hb1 = initP1();
			VBox hb2 = initP2();
			generateQueue();
			initP3();
			
			border.setCenter(hb2);
			border.setLeft(hb1);
			border.setRight(hb3);

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
						for (File file : db.getFiles()) {
							generateQueue();
							filesImported.add(file);
							System.out.println(file.getName());
						}
					}
					event.setDropCompleted(success);
					initP3();
					event.consume();
				}
			});
			
			Scene scene = new Scene(border, 551, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
