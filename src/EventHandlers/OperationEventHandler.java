package EventHandlers;

import Controller.DrawingController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OperationEventHandler implements EventHandler<ActionEvent>{

	//Variables
	private DrawingController drawingController;
	private String operation;
	
	public OperationEventHandler(DrawingController drawingController, String operation) {
		
		this.drawingController = drawingController;
		this.operation = operation;
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		drawingController.doOperation(operation);
		
	}	

}