package EventHandlers;

import Controller.DrawingController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;

public class ColorEventHandler implements EventHandler<ActionEvent>{

	//Variables
	private DrawingController drawingController;
	
	public ColorEventHandler(DrawingController drawingController) {
		
		this.drawingController = drawingController;
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		if(event.getSource() instanceof ColorPicker) {
			
			drawingController.setColor(((ColorPicker) event.getSource()).getValue());
			
		}		
		
	}	

}
