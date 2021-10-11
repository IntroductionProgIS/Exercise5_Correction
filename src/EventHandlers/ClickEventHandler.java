package EventHandlers;

import Controller.DrawingController;

import Model.Mode;

import View.DrawingPane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ClickEventHandler implements EventHandler<MouseEvent>{

	//Variables
	private DrawingController drawingController;
	
	public ClickEventHandler(DrawingController drawingController){
	
		this.drawingController = drawingController;
		
	}
	
	@Override
	public void handle(MouseEvent event) {
		
		if(event.getSource() instanceof DrawingPane) {

			double mousePositionX = event.getX();
			double mousePositionY = event.getY();
			
			String mode = drawingController.getMode();
			switch (mode) {
			
			case Mode.MODE_SELECTORMOVE:
				drawingController.shapeSelected(mousePositionX, mousePositionY);
				break;
				
			case Mode.MODE_ELLIPSE:	
			case Mode.MODE_RECTANGLE:
			case Mode.MODE_LINE:
				drawingController.newShape(mode,mousePositionX,mousePositionY);
				break;
				
			default:
				break;
				
			}
			
		}
		
	}

}
