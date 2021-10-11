package EventHandlers;

import Controller.DrawingController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ModeEventHandler implements EventHandler<ActionEvent>{

	//Variables
	private DrawingController drawingController;
	private String mode;
	
	public ModeEventHandler(DrawingController drawingController, String mode) {
		
		this.drawingController = drawingController;
		this.mode = mode;
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		drawingController.setMode(mode);
		
	}	

}
