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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FileProcessor extends Application {
	// Alice's work directory:
	// C://Users//916423//Documents//workspace//DragDropFileProcessor-master//src//Types.txt

	// Alice's home directory:
	// //Users//alin//Documents//workspace//CSE306//RyansFileProcessor//src//Types.txt

	// Ryan's work directory:
	// C://Users//jhu//Desktop//branchy//

	// Ryan's home directory:
	// C://Users//jhu//Desktop//branchy//Types.txt

	// TODO: ADD A BOTTOM BAR THAT CAN EDIT TYPE AND CLEAR ALL DATA

	// the parent directory of where the files are supposed to go
	private final String FILE_ROOT_DESTINATION = "C://Users//jhu//Desktop//branchy";
	// location of Types.txt
	private final String FILE_TYPE_LOCATION = "C://Users//jhu//Desktop//branchy//src//Types.txt";
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

	Label dragDrop = new Label("Drag & Drop your \nfiles anywhere in \nthis window");

	// sets up panel 1 (drag & drop)
	public void initP1() {
		hb1.getChildren().clear();
		dragDrop.setPrefHeight(100);
		generateFileQueue();
		ObservableList<VBox> ol = FXCollections.observableArrayList(items);
		lv.setItems(ol);
		hb1.getChildren().addAll(dragDrop, lv);
		hb1.setStyle("-fx-border-color: black;");

		hb1.setPrefWidth(130);
		border.setLeft(hb1);
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
	public void initP3(final Stage primaryStage) {
		generateButtonList(primaryStage);

		// hb3.getChildren().addAll(initP3AddOn(primaryStage));
		hb3.setStyle("-fx-border-color: black;");
		// hb3.setPrefWidth(100);
		border.setRight(hb3);

	}

	// sets up the clear and edit button for p3
	public HBox initP3AddOn(final Stage primaryStage) {
		HBox clearEdit = new HBox();
		Button clear = new Button("Clear All");
		Button edit = new Button("Edit Types");
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-border-color: black;");
		clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				numField.setText("");
				filesImported = new ArrayList<>();
				items = new ArrayList<>();
				initP1();
			}
		});
		///////////
		edit.setOnAction(new EventHandler<ActionEvent>() {

			public HBox generateCell(String name) {
				HBox result = new HBox();
				Button del, rename;

				del = new Button();
				rename = new Button();
				del.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {

					}
				});
				rename.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {

					}
				});

				result.getChildren().addAll(del, rename);
				return result;

			}

			public HBox generateBottomButtons() {
				HBox result = new HBox();
				Button save = new Button();
				Button cancel = new Button();

				return result;
			}

			@Override
			public void handle(ActionEvent arg0) {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(primaryStage);
				// TODO: ADD THE ADD TYPE POPUP
				ArrayList<HBox> items = new ArrayList<>();
				for (int i = 0; i < types.size(); i++) {
					items.add(generateCell(types.get(i)));
				}
				ListView<HBox> lv = new ListView<>();
				ObservableList<HBox> ol = FXCollections.observableArrayList(items);
				lv.setItems(ol);

			}

		});
		///////////
		clearEdit.getChildren().addAll(clear, edit);
		return clearEdit;
	}

	// creates the list of buttons to click to set the type of file on panel 3
	public void generateButtonList(final Stage ps) {
		VBox item = new VBox();
		Button btn;
		for (String t : types) {
			btn = new Button(t);
			btn.setPrefWidth(150);
			btn.setPrefHeight(60);
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					buttonOnClick((Button) event.getSource(), ps);
				}
			});
			item.getChildren().add(btn);
		}
		hb3.getChildren().add(item);
	}

	// creates the queue for the files dropped in the window
	public void generateFileQueue() {
		// items = new ArrayList<>();
		VBox item;
		Label fileName;
		for (File f : filesImported) {
			item = new VBox();
			fileName = new Label(f.getName());
			fileName.setId(f.getAbsolutePath());
			item.getChildren().add(fileName);
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
	public void buttonOnClick(Button b, Stage ps) {
		if(filesImported.size()<1){
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("There are no files to process");
		}
		String num = numField.getText(), 
				labelName = filesImported.get(0).getAbsolutePath();
		//origFullPath = filesImported.get(findIndexOfFile(labelName)).getAbsolutePath();
		//System.out.println(origFullPath);
		//int n = Integer.valueOf(num);
		if (num.equals("")) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Invalid Input");
			a.setContentText("Input for file number is invalid");
			a.showAndWait();
			System.out.println("Please enter file number");
		} else {
			String fileName = num + "_" + b.getText();
			System.out.println(fileName);
			
			// TO
			// FILENAME
			if (fileNames.containsKey(fileName)) {
				int x = Integer.valueOf(fileNames.get(fileName));
				fileNames.put(fileName, new Integer(x + 1));
				fileName += "_" + (x + 1);
			} else {
				fileNames.put(fileName, new Integer(1));
			}
			fileName+= ((labelName.indexOf(".") >= 0)
					? labelName.substring(labelName.indexOf("."), labelName.length()) : "");
			System.out.println(fileName);
			// TODO: rename & move file
			renameAndMoveFile(labelName, fileName, Integer.valueOf(num));
			// update listview
			removeFile(labelName);
			items = new ArrayList<>();
			initP1();
		}
	}

	public void renameAndMoveFile(String name, String newName, int fileNum) {
		int index;

	//	if ((index = findIndexOfFile(name)) > -1) {
			File target = new File(FILE_ROOT_DESTINATION + "//" + fileNum + "//" + newName);
		//	File f = filesImported.get(index);
			Path src = Paths.get(name), //
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

		//}
	}

	public int findIndexOfFile(String name) {
		for (int i = 0; i < filesImported.size(); i++) {
			if (filesImported.get(i).getAbsolutePath().equals(name)) {
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

	private BorderPane border = new BorderPane();
	private Scene scene;
	private VBox hb1 = new VBox();

	// the "main method" of this project
	@Override
	public void start(final Stage primaryStage) {
		// will try to read the files from Types.txt
		try {
			types = readTypesFromFile();
			// set up the GUI window
			border = new BorderPane();
			initP1();
			VBox hb2 = initP2();
			// after getting the list of types, show them on p3 as test
			initP3(primaryStage);

			// handler to notice the files being dragged in panel
			border.setOnDragOver(new EventHandler<DragEvent>() {
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
			border.setOnDragDropped(new EventHandler<DragEvent>() {
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
					initP1();
				}
			});

			// setting the panels in the border-layout object
			// border.setLeft(hb1);
			border.setCenter(hb2);
			// border.setRight(hb3);
			border.setTop(initP3AddOn(primaryStage));

			Scene scene = new Scene(border, 501, 430);
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
