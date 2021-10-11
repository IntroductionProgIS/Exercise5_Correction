package EventHandlers;

import java.util.ArrayList;

import Controller.DrawingController;

import Model.Mode;

import View.DrawingPane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DragEventHandler implements EventHandler<MouseEvent>{

	//Variables
	private DrawingController drawingController;
	
	public DragEventHandler(DrawingController drawingController) {
		
		this.drawingController = drawingController;
		
	}
	
	@Override
	public void handle(MouseEvent event) {
		
		if(event.getSource() instanceof DrawingPane) {
			
			String mode = drawingController.getMode();
			switch (mode) {
			
			case Mode.MODE_SELECTORMOVE:
				Shape shape = drawingController.getSelectedShape();
				
				if(shape != null) {
					
					moveShape(shape, event);
					
				}
				break;
				
			case Mode.MODE_ELLIPSE:	
			case Mode.MODE_RECTANGLE:
			case Mode.MODE_LINE:
				drawShape(event);
				break;
				
			default:
				break;
				
			}
			
		}
		
	}

	public void moveShape(Shape shape, MouseEvent event) {
		
		if(shape instanceof Ellipse) {
					
			Ellipse ellipse = (Ellipse) shape;
					
			ellipse.setCenterX(ellipse.getTranslateX() + event.getX());
			ellipse.setCenterY(ellipse.getTranslateY() + event.getY());
					
		}
		else if(shape instanceof Rectangle) {
					
			Rectangle rectangle = (Rectangle) shape;
				
			rectangle.setX(rectangle.getTranslateX() + event.getX() - rectangle.getWidth()/2);
			rectangle.setY(rectangle.getTranslateY() + event.getY() - rectangle.getHeight()/2);
				
			rectangle.setTranslateX(0);
			rectangle.setTranslateY(0);
		}
		else if(shape instanceof Line) {
					
			Line line = (Line) shape;
					
			double startX = line.getStartX();
			double endX = line.getEndX();
			double startY = line.getStartY();
			double endY = line.getEndY();
			line.setStartX(event.getX() + (startX - endX)/2);
			line.setStartY(event.getY() + (startY - endY)/2);
			line.setEndX(event.getX() - (startX - endX)/2);
			line.setEndY(event.getY() - (startY - endY)/2);
			
		}
				
	}	
	
	public void drawShape(MouseEvent event) {
		
		ArrayList<Shape> shapes = drawingController.getShapesList();
		Shape lastCreatedShape = shapes.get(shapes.size() - 1);
		
		if(lastCreatedShape instanceof Ellipse) {
			
			Ellipse ellipse = (Ellipse) lastCreatedShape;
			
			ellipse.setRadiusX(Math.abs(ellipse.getCenterX()-event.getX()));
			ellipse.setRadiusY(Math.abs(ellipse.getCenterY()-event.getY()));
			
		}
		
		else if(lastCreatedShape instanceof Rectangle) {
			
			Rectangle rectangle = (Rectangle) lastCreatedShape;
			
			if(rectangle.getY() - event.getY() <= 0) {
				
				rectangle.setHeight(Math.abs(rectangle.getY() - event.getY()));
				
			}
			else {
				
				rectangle.setHeight(Math.abs(rectangle.getY() - event.getY()));
				rectangle.setTranslateY(- rectangle.getY() + event.getY());
				
			}
			
			if(rectangle.getX() - event.getX() <= 0) {
				
				rectangle.setWidth(Math.abs(rectangle.getX() - event.getX()));
				
			}
			else {
				
				rectangle.setWidth(Math.abs(rectangle.getX() - event.getX()));
				rectangle.setTranslateX(- rectangle.getX() + event.getX());
				
			}
			
		}
		
		else if(lastCreatedShape instanceof Line) {
	
			Line line = (Line) lastCreatedShape;
			
			line.setEndX(event.getX());
			line.setEndY(event.getY());
			
		}
	
	}

}