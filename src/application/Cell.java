package application;

import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;

public class Cell extends ListCell<String> {
	private HBox hbox = new HBox(), listOfButtons = new HBox();
	private Label label;
	private Pane pane = new Pane();
	private ArrayList<Button> b = new ArrayList<>();
	private Button button;
	private String lastItem;

	public Cell(String fileName, ArrayList<String> types) {
		super();
		label = new Label(fileName);

		for (int i = 0; i < types.size(); i++) {
			button = new Button();

			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println(lastItem + " : " + event);
				}
			});

			b.add(button);
		}
		hbox.getChildren().addAll(label, pane, button);
		HBox.setHgrow(pane, Priority.ALWAYS);

	}

	public Label getLabel() {
		return label;
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		setText(null); // No text in label of super class
		if (empty) {
			lastItem = null;
			setGraphic(null);
		} else {
			lastItem = item;
			label.setText(item != null ? item : "<null>");
			setGraphic(hbox);
		}
	}

	public static Callback<Cell, Observable[]> extractor() {
		return (Cell p) -> new Observable[] { (Observable) p.getLabel() };
	}
}
