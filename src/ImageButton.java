
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ImageButton extends Button {
	
	
	public ImageButton() {
		setGraphic(new ImageView(new Image(getClass().getResourceAsStream(""))));
		
		setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//if(arg0.getButton().to)
			}

		});

	}

}
/*
 * ~5:20 arrive
 * 5:20-5:35 phone stuff
 * 5:35-6:00 arrive at atlantic
 * 
 * */
