package Controller;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface DrawingController {
	
	public void setButtonsActions();
	public void setMode(String mode);
	public void setColor(Color color);
	
	public void doOperation(String operation);
	public void newShape(String mode, double mousePositionX, double mousePostionY);
	public Shape shapeSelected(double mousePositionX, double mousePositionY);
	public void duplicateShape(Shape oldShape);
	public void bringShapeToFront(Shape shape);
	
	public String getMode();
	public ArrayList<Shape> getShapesList();
	public Shape getSelectedShape();
	
}
