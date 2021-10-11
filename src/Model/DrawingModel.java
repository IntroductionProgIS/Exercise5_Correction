package Model;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface DrawingModel {

	public void setMode(String mode);
	public void setColor(Color color);
	
	public void addEllipse(double mousePositionX, double mousePostionY, double radiusX, double radiusY, Color color);
	public void addRectangle(double mousePositionX, double mousePostionY, double height, double width, Color color);
	public void addLine(double mousePositionX, double mousePostionY, double width, Color color);
	public void addShape(Shape shape);
	public void removeLastShape();
	public void bringShapeToFront(Shape shape);
	
	public ArrayList<Shape> getShapesList();
	public String getMode();
	public Color getColor();
	
}
