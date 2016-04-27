import java.io.*;
import java.nio.file.*;
import java.util.*;

import javafx.application.Application;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FileProcessor extends Application {
	// Alice's work directory:
	// C://Users//916423//Documents//workspace//DragDropFileProcessor-master//src//Types.txt

	// Alice's home directory:
	// //Users//alin//Documents//workspace//CSE306//RyansFileProcessor//src//Types.txt

	// Ryan's work directory:
	//

	// Ryan's home directory:
	//

	// TODO: ADD A BOTTOM BAR THAT CAN EDIT TYPE AND CLEAR ALL DATA

	// the parent directory of where the files are supposed to go
	private final String FILE_ROOT_DESTINATION = "C://Users//916423//Downloads//";
	// location of Types.txt
	private final String FILE_TYPE_LOCATION = "C://Users//916423//Documents//workspace//DragDropFileProcessor-master//src//Types.txt";
	// list of files drag & dropped in
	private ArrayList<File> filesImported = new ArrayList<>();
	// list of items (file name & buttons) in the queue for part 3
	private ArrayList<VBox> items = new ArrayList<>();
	// section to put part 3 components
	private VBox hb3 = new VBox();
	// list of types read from Types.txt
	private ArrayList<String> types = new ArrayList<>();
	// the queue list in part 3
	private ListView<VBox> lv = new ListView<>();
	// list of the new/generated filenames - used to check for duplicate names
	private HashMap<String, Integer> fileNames = new HashMap<>();

	// reads the list of Types from Types.txt
	public ArrayList<String> readTypesFromFile() throws IOException {
		ArrayList<String> typesList = new ArrayList<>();
		List<String> lines = Files.readAllLines(Paths.get(FILE_TYPE_LOCATION));
		for (String line : lines) {
			typesList.add(line);
		}
		return typesList;
	}

	// sets up panel 1 (drag & drop)
	public HBox initP1() {
		Label dragDrop = new Label("Drag & Drop your \nfiles here");
		// HBOX IS HORIZONTAL BOX. VBOX IS VERTICAL BOX.
		HBox hb = new HBox(8);
		hb.getChildren().add(dragDrop);
		hb.setStyle("-fx-border-color: black;");
		return hb;
	}

	TextField numField = new TextField();

	// sets up panel 2 (file number input)
	public VBox initP2() {
		Label askNum = new Label("Enter the file number: ");
		// Label askNum = new Label("Enter the file number: ");

		numField.setMaxWidth(100);
		VBox hb2 = new VBox(8);
		hb2.getChildren().addAll(askNum, numField);
		hb2.setStyle("-fx-border-color: black;");
		return hb2;
	}

	// sets up panel 3 (setting file-to-type queue)
	public void initP3() {
		generateQueue();
		// lv = new ListView<>();
		// lv.
		ObservableList<VBox> ol = FXCollections.observableArrayList(items);
		lv.setItems(ol);

		hb3.getChildren().clear();
		hb3.getChildren().add(lv);
		hb3.setStyle("-fx-border-color: black;");

		border.setRight(null);
		border.setRight(hb3);
	}

	// creates the vbox to add as each item in the queue.
	// the vbox will contain the filename & buttons of types
	public void generateQueue() {
		items = new ArrayList<>();
		VBox item;
		HBox buttons;
		Button btn;
		for (File f : filesImported) {
			buttons = new HBox();
			item = new VBox();
			item.getChildren().add(new Label(f.getName()));

			for (int j = 0; j < types.size(); j++) {
				btn = new Button(types.get(j));
				btn.setId(f.getName());
				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						buttonOnClick((Button) event.getSource());
					}
				});
				buttons.getChildren().add(btn);
			}
			item.getChildren().add(buttons);

			items.add(item);
		}
	}

	/*
	 * WORK IN PROGRESS Deals with buttons being clicked on in part 3
	 * 
	 * It will:
	 * 
	 * 0. pull the file number from part 2
	 * 
	 * 1. take generate the new file name
	 * 
	 * 2. add the new name in the hash to check for duplicates
	 * 
	 * 2.5 if duplicate, add a _2 or w/e value the hash has
	 * 
	 * 3. rename & move the file
	 * 
	 * 4. remove from list & update the listview
	 */
	public void buttonOnClick(Button b) {
		String num = numField.getText(), labelName = b.getId(),
				origFullPath = filesImported.get(findIndexOfFile(labelName)).getAbsolutePath();
		System.out.println(origFullPath);

		if (num.equals("") || num.matches("[0-9]+")) {// TODO: or is not a number
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Invalid Input");
			a.setContentText("Input for file number is invalid");
			a.showAndWait();
			System.out.println("Please enter file number");
		} else {
			String fileName = num + "_" + b.getText() + ((origFullPath.indexOf(".") >= 0)
					? origFullPath.substring(origFullPath.indexOf("."), origFullPath.length()) : "");
			// TO
			// FILENAME
			if (fileNames.containsKey(fileName)) {
				int x = Integer.valueOf(fileNames.get(fileName));
				fileNames.put(fileName, new Integer(x + 1));
				fileName += "_" + (x + 1);
			} else {
				fileNames.put(fileName, new Integer(1));
			}
			System.out.println(fileName);
			// TODO: rename & move file
			renameAndMoveFile(labelName, fileName, Integer.valueOf(num));
			// update listview
			removeFile(labelName);
			initP3();
		}
	}

	public void renameAndMoveFile(String name, String newName, int fileNum) {
		int index;

		if ((index = findIndexOfFile(name)) > -1) {
			File target = new File(FILE_ROOT_DESTINATION + "//" + fileNum + "//" + newName);
			File f = filesImported.get(index);
			Path src = Paths.get(f.getAbsolutePath()), //
					dest = Paths.get(target.getAbsolutePath());
			if (!target.exists()) {
				// Files.createDirectory(target);
				// target.mkdir();
				target.getParentFile().mkdirs();
			}
			System.out.println(dest);
			try {
				Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public int findIndexOfFile(String name) {
		for (int i = 0; i < filesImported.size(); i++) {
			if (filesImported.get(i).getName().equals(name)) {
				// filesImported.remove(i);
				return i;
			}
		}
		return -1;
	}

	// removes the file from the queue after the button is clicked
	public void removeFile(String name) {
		int index;
		if ((index = findIndexOfFile(name)) > -1)
			filesImported.remove(index);
	}

	BorderPane border = new BorderPane();
	Scene scene;

	// the "main method" of this project
	@Override
	public void start(Stage primaryStage) {
		// will try to read the files from Types.txt
		try {
			types = readTypesFromFile();
			// set up the GUI window
			border = new BorderPane();
			HBox hb1 = initP1();
			VBox hb2 = initP2();
			// after getting the list of types, show them on p3 as test
			initP3();

			// handler to notice the files being dragged in panel
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

			// handler to deal with files dropped
			hb1.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					boolean success = false;
					if (db.hasFiles()) {
						success = true;
						filesImported = new ArrayList<>();
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
					initP3();
					event.consume();
				}
			});

			// setting the panels in the border-layout object
			border.setCenter(hb2);
			border.setLeft(hb1);
			border.setRight(hb3);

			Scene scene = new Scene(border, 551, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("File Processor");
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
