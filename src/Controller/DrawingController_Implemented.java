package Controller;

import java.util.ArrayList;

import EventHandlers.ClickEventHandler;
import EventHandlers.ColorEventHandler;
import EventHandlers.DragEventHandler;
import EventHandlers.ModeEventHandler;
import EventHandlers.OperationEventHandler;

import Model.DrawingModel;
import Model.Mode;
import Model.Operation;

import View.DrawingEditorMainPane;
import View.DrawingPane;
import View.MenuPane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawingController_Implemented implements DrawingController{
	
	//Variables
	private DrawingModel drawingModel;
	private DrawingEditorMainPane drawingEditorMainPane;
	
	private EventHandler<MouseEvent> clickEventHandler;
	private EventHandler<MouseEvent> dragEventHandler;
	
	private int indexSelectedShape;
	
	public DrawingController_Implemented(DrawingModel drawingModel, DrawingEditorMainPane drawingEditorMainPane) {
		
		this.drawingEditorMainPane = drawingEditorMainPane;
		this.drawingModel = drawingModel;
		
		this.setButtonsActions();
		
		clickEventHandler = new ClickEventHandler(this);
		dragEventHandler = new DragEventHandler(this);
		
		//We change the active event handlers
		drawingEditorMainPane.getDrawingPane().setOnMousePressed(clickEventHandler);
    	drawingEditorMainPane.getDrawingPane().setOnMouseDragged(dragEventHandler);
	}

	@Override
	public void setButtonsActions() {
		
		MenuPane menuPane = drawingEditorMainPane.getMenuPane();
				
		//We associate the selectOrMoveRadioButton to its action
		menuPane.getSelectOrMoveRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_SELECTORMOVE));

		//We associate the ellipseRadioButton to its action
		menuPane.getEllipseRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_ELLIPSE));
		
		//We associate the ellipseRadioButton to its action
		menuPane.getRectangleRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_RECTANGLE));
				
		//We associate the ellipseRadioButton to its action
		menuPane.getLineRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_LINE));
		
		//We associate the colorPicker to its action
		menuPane.getColorPicker().setOnAction(new ColorEventHandler(this));
		
		//We associate the colorPicker to its action
		menuPane.getDeleteButton().setOnAction(new OperationEventHandler(this,Operation.OPERATION_DELETE));
		
		//We associate the colorPicker to its action
		menuPane.getCloneButton().setOnAction(new OperationEventHandler(this,Operation.OPERATION_CLONE));
		
	}
	
	@Override
	public void setMode(String mode) {
		
		drawingModel.setMode(mode);
		
		if(mode.equals(Mode.MODE_SELECTORMOVE)) {
			
			//We change accessible buttons
			drawingEditorMainPane.getMenuPane().getDeleteButton().setDisable(false);
			drawingEditorMainPane.getMenuPane().getCloneButton().setDisable(false);
			
		}
		else {
			
			//We change accessible buttons
			drawingEditorMainPane.getMenuPane().getDeleteButton().setDisable(true);
			drawingEditorMainPane.getMenuPane().getCloneButton().setDisable(true);
	        
		}
		
	}
	
	@Override
	public void setColor(Color color) {
		
		drawingModel.setColor(color);
		
	}
	
	@Override
	public void doOperation(String operation) {
		
		DrawingPane drawingPane = drawingEditorMainPane.getDrawingPane();
		
		if(operation.equals(Operation.OPERATION_DELETE)) {
			
			if(indexSelectedShape >= 0 && indexSelectedShape < drawingModel.getShapesList().size()) {
				
				drawingModel.getShapesList().remove(indexSelectedShape);
				drawingPane.drawShapes(drawingModel.getShapesList());
				indexSelectedShape = -1;
				
			}
			
		}
		else if(operation.equals(Operation.OPERATION_CLONE)) {
			
			if(indexSelectedShape >= 0 && indexSelectedShape < drawingModel.getShapesList().size()) {
				
				Shape oldShape = drawingModel.getShapesList().get(indexSelectedShape);
				
				duplicateShape(oldShape);
				
				indexSelectedShape = drawingModel.getShapesList().size() - 1;
				drawingEditorMainPane.getDrawingPane().drawShapes(drawingModel.getShapesList());
				
			}
			
		}
	}
	
	@Override
	public void newShape(String mode, double mousePositionX, double mousePostionY) {
		
		DrawingPane drawingPane = drawingEditorMainPane.getDrawingPane();
		
		switch(mode) {
		
		case Mode.MODE_ELLIPSE:
			drawingModel.addEllipse(mousePositionX, mousePostionY, 1, 1, drawingModel.getColor());
			break;
			
		case Mode.MODE_RECTANGLE:
			drawingModel.addRectangle(mousePositionX, mousePostionY, 1, 1, drawingModel.getColor());
			break;
			
		case Mode.MODE_LINE:
			drawingModel.addLine(mousePositionX, mousePostionY, 1, drawingModel.getColor());
			break;
			
		default:
			break;
		
		}
		
		drawingPane.drawShapes(drawingModel.getShapesList());
		
	}
	
	@Override
	public Shape shapeSelected(double mousePositionX, double mousePositionY) {
		
		ArrayList<Shape> shapesList = drawingModel.getShapesList();
		Shape shape;
		indexSelectedShape = -1;
		
		//We test all the shapes to see if one is located at the mouse position
		for(int index = shapesList.size() - 1 ; index >= 0 ; index--) {
			
			shape = shapesList.get(index);
			
			//If this shape is an Ellipse
			if (shape instanceof Ellipse) {
				
				//We cast the shape 
				Ellipse ellipse = (Ellipse) shape;
				
				//We calculate the click position taking the Ellipse as a reference
				double tmpX = mousePositionX - ellipse.getCenterX();
                double tmpY = mousePositionY - ellipse.getCenterY();
                tmpX /= ellipse.getRadiusX();
                tmpY /= ellipse.getRadiusY();
                double r = tmpX * tmpX + tmpY * tmpY;
                
                //If the click is located on the shape
                if (r < 1.0) 
                {
                	//We set the Ellipse as selected
                	this.bringShapeToFront(shape);
                    indexSelectedShape = drawingModel.getShapesList().size() - 1;
                    
                    //We stop looking for the selected shape since we found it
                    break;
                }
				
			}
			//If this shape is a Rectangle
			else if (shape instanceof Rectangle) {
				
				Rectangle rectangle = (Rectangle) shape;
				
				//We test if the click was performed on the retangle
				if (mousePositionX >= rectangle.getX() + rectangle.getTranslateX() 
						&& mousePositionX <= rectangle.getX() + rectangle.getTranslateX() + rectangle.getWidth() 
						&& mousePositionY >= rectangle.getY() + rectangle.getTranslateY() 
						&& mousePositionY <= rectangle.getY() + rectangle.getTranslateY() + rectangle.getHeight()) {
                    
					//We set the Rectangle as selected
					this.bringShapeToFront(shape);
                    indexSelectedShape = drawingModel.getShapesList().size() - 1;
                    
                    //We stop looking for the selected shape since we found it
					break;
					
                }
				
			}	
			//If this shape is a Line
			else if (shape instanceof Line) {
				
				Line line = (Line) shape;
				
				double yError = 0;
				
				//We calculate the equation of the Line
				double slope = 1;
				
				if(line.getStartX() != line.getEndX()){
					
					slope = (line.getStartY() - line.getEndY()) / (line.getStartX() - line.getEndX());
				}
				
				double yIntercept = line.getStartY() - (line.getStartX() * slope); 
				double theoricalYValue = slope * mousePositionX + yIntercept;
				
				yError = line.getStrokeWidth() * Math.abs(slope);
				
				boolean lineCondition = mousePositionY > theoricalYValue - yError - line.getStrokeWidth()
							&& mousePositionY < theoricalYValue + yError + line.getStrokeWidth();
				
				//We calculate the extremities of the line
				boolean extremitiesCondition;
				
				extremitiesCondition = mousePositionX <= Math.max(line.getStartX(), line.getEndX()) 
							&& mousePositionX + line.getTranslateX() >= Math.min(line.getStartX(), line.getEndX())
							&& mousePositionY >= Math.min(line.getStartY(), line.getEndY())
							&& mousePositionY <= Math.max(line.getStartY(), line.getEndY());
				
				//We test if the click was performed on the Line
				if (lineCondition && extremitiesCondition) {

					//We set the Line as selected
					this.bringShapeToFront(shape);
                    indexSelectedShape = drawingModel.getShapesList().size() - 1;
                    
                    //We stop looking for the selected shape since we found it
					break;
					
                }
				
			}
			
		}
		
		//If a shape has been selected
		if(indexSelectedShape >= 0) {
			
			return drawingModel.getShapesList().get(indexSelectedShape);
			
		}
		
		return null;
	}
	
	@Override
	public void duplicateShape(Shape oldShape) {
		
		//If the shape that we want to duplicate is an Ellipse
		if (oldShape instanceof Ellipse) {
				
			Ellipse oldEllipse = (Ellipse) oldShape;
			
			//We create a new Ellipse that copy the old one
			Ellipse newEllipse = new Ellipse(oldEllipse.getCenterX() + 10, oldEllipse.getCenterY() + 10, oldEllipse.getRadiusX(), oldEllipse.getRadiusY());
			newEllipse.setFill(oldEllipse.getFill());
			newEllipse.setTranslateX(0);
			newEllipse.setTranslateY(0);
			
			//We add this Ellipse to our list of shapes
			drawingModel.addShape(newEllipse);
			
		}
		else if (oldShape instanceof Rectangle) {
				
			Rectangle oldRectangle = (Rectangle) oldShape;
			
			//We create a new Rectangle that copy the old one
			Rectangle newRectangle = new Rectangle(oldRectangle.getX() + 10, oldRectangle.getY() + 10, oldRectangle.getWidth(), oldRectangle.getHeight());
			newRectangle.setFill(oldRectangle.getFill());
			newRectangle.setTranslateX(0);
			newRectangle.setTranslateY(0);
			
			//We add this Rectangle to our list of shapes
			drawingModel.addShape(newRectangle);
		
		}
		else if (oldShape instanceof Line) {
				
			Line oldLine = (Line) oldShape;
			
			//We create a new Line that copy the old one
			Line newLine = new Line(oldLine.getStartX() + 10, oldLine.getStartY() + 10, oldLine.getEndX() + 10, oldLine.getEndY() + 10);
			newLine.setStroke(oldLine.getStroke());
			newLine.setStrokeWidth(oldLine.getStrokeWidth());
			newLine.setTranslateX(0);
			newLine.setTranslateY(0);
			
			//We add this Line to our list of shapes
			drawingModel.addShape(newLine);
			
		}
				
	}
	
	@Override
	public void bringShapeToFront(Shape shape) {
		
		drawingModel.bringShapeToFront(shape);
		drawingEditorMainPane.getDrawingPane().drawShapes(drawingModel.getShapesList());
		
	}

	@Override
	public String getMode() {

		return drawingModel.getMode();	
		
	}

	@Override
	public ArrayList<Shape> getShapesList() {

		return this.drawingModel.getShapesList();
		
	}

	@Override
	public Shape getSelectedShape() {
		
		if(indexSelectedShape >= 0) {
			
			return drawingModel.getShapesList().get(indexSelectedShape);
			
		}
		
		return null;
		
	}    

}

/*
package Controller;

import java.util.ArrayList;

import EventHandlers.ClickEventHandler;
import EventHandlers.ColorEventHandler;
import EventHandlers.DragEventHandler;
import EventHandlers.ModeEventHandler;
import EventHandlers.OperationEventHandler;

import Model.DrawingModel;
import Model.Mode;
import Model.Operation;

import View.DrawingEditorMainPane;
import View.DrawingPane;
import View.MenuPane;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawingController_Implemented implements DrawingController{
	
	//Variables
	private DrawingModel drawingModel;
	private DrawingEditorMainPane drawingEditorMainPane;
	
	private EventHandler<MouseEvent> clickEventHandler;
	private EventHandler<MouseEvent> dragEventHandler;
	
	private int indexSelectedShape;
	
	public DrawingController_Implemented(DrawingModel drawingModel, DrawingEditorMainPane drawingEditorMainPane) {
		
		this.drawingEditorMainPane = drawingEditorMainPane;
		this.drawingModel = drawingModel;
		
		this.setButtonsActions();
		
		clickEventHandler = new ClickEventHandler(this);
		dragEventHandler = new DragEventHandler(this);
		
		//We change the active event handlers
		drawingEditorMainPane.getDrawingPane().setOnMousePressed(clickEventHandler);
    	drawingEditorMainPane.getDrawingPane().setOnMouseDragged(dragEventHandler);
	}

	@Override
	public void setButtonsActions() {
		
		MenuPane menuPane = drawingEditorMainPane.getMenuPane();
				
		//We associate the selectOrMoveRadioButton to its action
		menuPane.getSelectOrMoveRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_SELECTORMOVE));

		//We associate the ellipseRadioButton to its action
		menuPane.getEllipseRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_ELLIPSE));
		
		//We associate the ellipseRadioButton to its action
		menuPane.getRectangleRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_RECTANGLE));
				
		//We associate the ellipseRadioButton to its action
		menuPane.getLineRadioButton().setOnAction(new ModeEventHandler(this,Mode.MODE_LINE));
		
		//We associate the colorPicker to its action
		menuPane.getColorPicker().setOnAction(new ColorEventHandler(this));
		
		//We associate the colorPicker to its action
		menuPane.getDeleteButton().setOnAction(new OperationEventHandler(this,Operation.OPERATION_DELETE));
		
		//We associate the colorPicker to its action
		menuPane.getCloneButton().setOnAction(new OperationEventHandler(this,Operation.OPERATION_CLONE));
		
	}
	
	@Override
	public void setMode(String mode) {
		
		drawingModel.setMode(mode);
		
		if(mode.equals(Mode.MODE_SELECTORMOVE)) {
			
			//We change accessible buttons
			drawingEditorMainPane.getMenuPane().getDeleteButton().setDisable(false);
			drawingEditorMainPane.getMenuPane().getCloneButton().setDisable(false);
			
		}
		else {
			
			//We change accessible buttons
			drawingEditorMainPane.getMenuPane().getDeleteButton().setDisable(true);
			drawingEditorMainPane.getMenuPane().getCloneButton().setDisable(true);
	        
		}
		
	}
	
	@Override
	public void setColor(Color color) {
		
		drawingModel.setColor(color);
		
	}
	
	@Override
	public void doOperation(String operation) {
		
		DrawingPane drawingPane = drawingEditorMainPane.getDrawingPane();
		
		if(operation.equals(Operation.OPERATION_DELETE)) {
			
			if(indexSelectedShape >= 0 && indexSelectedShape < drawingModel.getShapesList().size()) {
				
				drawingModel.getShapesList().remove(indexSelectedShape);
				drawingPane.drawShapes(drawingModel.getShapesList());
				indexSelectedShape = -1;
				
			}
			
		}
		else if(operation.equals(Operation.OPERATION_CLONE)) {
			
			if(indexSelectedShape >= 0 && indexSelectedShape < drawingModel.getShapesList().size()) {
				
				Shape oldShape = drawingModel.getShapesList().get(indexSelectedShape);
				
				duplicateShape(oldShape);
				
				indexSelectedShape = drawingModel.getShapesList().size() - 1;
				drawingEditorMainPane.getDrawingPane().drawShapes(drawingModel.getShapesList());
				
			}
			
		}
	}
	
	@Override
	public void newShape(String mode, double mousePositionX, double mousePostionY) {
		
		DrawingPane drawingPane = drawingEditorMainPane.getDrawingPane();
		
		switch(mode) {
		
		case Mode.MODE_ELLIPSE:
			drawingModel.addEllipse(mousePositionX, mousePostionY, 1, 1, drawingModel.getColor());
			break;
			
		case Mode.MODE_RECTANGLE:
			drawingModel.addRectangle(mousePositionX, mousePostionY, 1, 1, drawingModel.getColor());
			break;
			
		case Mode.MODE_LINE:
			drawingModel.addLine(mousePositionX, mousePostionY, 1, drawingModel.getColor());
			break;
			
		default:
			break;
		
		}
		
		drawingPane.drawShapes(drawingModel.getShapesList());
		
	}
	
	@Override
	public Shape shapeSelected(double mousePositionX, double mousePositionY) {
		
		ArrayList<Shape> shapesList = drawingModel.getShapesList();
		Shape shape;
		indexSelectedShape = -1;
		
		//We test all the shapes to see if one is located at the mouse position
		for(int index = shapesList.size() - 1 ; index >= 0 ; index--) {
			
			shape = shapesList.get(index);
			
			//If this shape is an Ellipse
			if (shape instanceof Ellipse) {
				
				//We cast the shape 
				Ellipse ellipse = (Ellipse) shape;
				
				//We calculate the click position taking the Ellipse as a reference
				double tmpX = mousePositionX - ellipse.getCenterX();
                double tmpY = mousePositionY - ellipse.getCenterY();
                tmpX /= ellipse.getRadiusX();
                tmpY /= ellipse.getRadiusY();
                double r = tmpX * tmpX + tmpY * tmpY;
                
                //If the click is located on the shape
                if (r < 1.0) 
                {
                	//We set the Ellipse as selected
                	this.bringShapeToFront(shape);
                    indexSelectedShape = drawingModel.getShapesList().size() - 1;
                    
                    //We stop looking for the selected shape since we found it
                    break;
                }
				
			}
			//If this shape is a Rectangle
			else if (shape instanceof Rectangle) {
				
				Rectangle rectangle = (Rectangle) shape;
				
				//We test if the click was performed on the retangle
				if (mousePositionX >= rectangle.getX() + rectangle.getTranslateX() 
						&& mousePositionX <= rectangle.getX() + rectangle.getTranslateX() + rectangle.getWidth() 
						&& mousePositionY >= rectangle.getY() + rectangle.getTranslateY() 
						&& mousePositionY <= rectangle.getY() + rectangle.getTranslateY() + rectangle.getHeight()) {
                    
					//We set the Rectangle as selected
					this.bringShapeToFront(shape);
                    indexSelectedShape = drawingModel.getShapesList().size() - 1;
                    
                    //We stop looking for the selected shape since we found it
					break;
					
                }
				
			}	
			//If this shape is a Line
			else if (shape instanceof Line) {
				
				Line line = (Line) shape;
				
				double slope = 1;
				boolean lineCondition;
				boolean extremitiesCondition;
				
				//We calculate the equation of the Line
				if(line.getStartX() != line.getEndX()){
					
					slope = (line.getStartY() - line.getEndY()) / (line.getStartX() - line.getEndX());
					
					double yIntercept = line.getStartY() - (line.getStartX() * slope); 
					
					double theoricalYValue = slope * mousePositionX + yIntercept;
					
					lineCondition = mousePositionY > theoricalYValue - line.getStrokeWidth() 
								&& mousePositionY < theoricalYValue + line.getStrokeWidth();
					
					//We calculate the extremities of the line
					if(line.getStartX() > line.getEndX()) {
						
						extremitiesCondition = mousePositionX < line.getStartX() && mousePositionX > line.getEndX();
						
					}
					else {
						
						extremitiesCondition = mousePositionX > line.getStartX() && mousePositionX < line.getEndX();
						
					}
				}
				else {
					
					lineCondition = mousePositionX <= Math.max(line.getStartX(),line.getEndX()) + line.getStrokeWidth() && mousePositionX >= Math.min(line.getStartX(),line.getEndX()) - line.getStrokeWidth();
					extremitiesCondition = mousePositionY <= Math.max(line.getStartY(),line.getEndY()) && mousePositionY >= Math.min(line.getStartY(),line.getEndY());
					
				}
				
				
				
				//We test if the click was performed on the Line
				if (lineCondition && extremitiesCondition) {

					//We set the Line as selected
					this.bringShapeToFront(shape);
                    indexSelectedShape = drawingModel.getShapesList().size() - 1;
                    
                    //We stop looking for the selected shape since we found it
					break;
					
                }
				
			}
			
		}
		
		//If a shape has been selected
		if(indexSelectedShape >= 0) {
			
			return drawingModel.getShapesList().get(indexSelectedShape);
			
		}
		
		return null;
	}
	
	@Override
	public void duplicateShape(Shape oldShape) {
		
		//If the shape that we want to duplicate is an Ellipse
		if (oldShape instanceof Ellipse) {
				
			Ellipse oldEllipse = (Ellipse) oldShape;
			
			//We create a new Ellipse that copy the old one
			Ellipse newEllipse = new Ellipse(oldEllipse.getCenterX() + 10, oldEllipse.getCenterY() + 10, oldEllipse.getRadiusX(), oldEllipse.getRadiusY());
			newEllipse.setFill(oldEllipse.getFill());
			newEllipse.setTranslateX(0);
			newEllipse.setTranslateY(0);
			
			//We add this Ellipse to our list of shapes
			drawingModel.addShape(newEllipse);
			
		}
		else if (oldShape instanceof Rectangle) {
				
			Rectangle oldRectangle = (Rectangle) oldShape;
			
			//We create a new Rectangle that copy the old one
			Rectangle newRectangle = new Rectangle(oldRectangle.getX() + 10, oldRectangle.getY() + 10, oldRectangle.getWidth(), oldRectangle.getHeight());
			newRectangle.setFill(oldRectangle.getFill());
			newRectangle.setTranslateX(0);
			newRectangle.setTranslateY(0);
			
			//We add this Rectangle to our list of shapes
			drawingModel.addShape(newRectangle);
		
		}
		else if (oldShape instanceof Line) {
				
			Line oldLine = (Line) oldShape;
			
			//We create a new Line that copy the old one
			Line newLine = new Line(oldLine.getStartX() + 10, oldLine.getStartY() + 10, oldLine.getEndX() + 10, oldLine.getEndY() + 10);
			newLine.setStroke(oldLine.getStroke());
			newLine.setStrokeWidth(oldLine.getStrokeWidth());
			newLine.setTranslateX(0);
			newLine.setTranslateY(0);
			
			//We add this Line to our list of shapes
			drawingModel.addShape(newLine);
			
		}
				
	}
	
	@Override
	public void bringShapeToFront(Shape shape) {
		
		drawingModel.bringShapeToFront(shape);
		drawingEditorMainPane.getDrawingPane().drawShapes(drawingModel.getShapesList());
		
	}

	@Override
	public String getMode() {

		return drawingModel.getMode();	
		
	}

	@Override
	public ArrayList<Shape> getShapesList() {

		return this.drawingModel.getShapesList();
		
	}

	@Override
	public Shape getSelectedShape() {
		
		if(indexSelectedShape >= 0) {
			
			return drawingModel.getShapesList().get(indexSelectedShape);
			
		}
		
		return null;
		
	}    

}

*/
