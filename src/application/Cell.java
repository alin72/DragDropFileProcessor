package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Cell extends ListCell<String> {
	HBox hbox = new HBox();
	Label label = new Label();
	Pane pane = new Pane();
	ArrayList<Button> b = new ArrayList<>();
	Button button = new Button();
	String lastItem;

	public Cell() {
		super();
		hbox.getChildren().addAll(label, pane, button);
		HBox.setHgrow(pane, Priority.ALWAYS);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(lastItem + " : " + event);
			}
		});
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
}
