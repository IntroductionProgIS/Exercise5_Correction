package Model;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawingModel_Implemented implements DrawingModel{

	//Parameters
	private String mode;
	private Color color;
	
	private ArrayList<Shape> shapesList = new ArrayList<Shape>();
	
	public DrawingModel_Implemented() {
	
		this.color = Color.WHITE;
		this.mode = Mode.MODE_SELECTORMOVE;
		
	}
	
	@Override
	public void setMode(String mode) {

		this.mode = mode;
		
	}

	@Override
	public void setColor(Color color) {

		this.color = color;
		
	}

	@Override
	public void addEllipse(double mousePositionX, double mousePositionY, double radiusX, double radiusY, Color color) {

		Ellipse ellipse = new Ellipse(mousePositionX, mousePositionY, radiusX, radiusY);
		ellipse.setFill(color);
		ellipse.setStroke(color);
		this.shapesList.add(ellipse);
		
	}

	@Override
	public void addRectangle(double mousePositionX, double mousePositionY, double height, double width, Color color) {

		Rectangle rectangle = new Rectangle(mousePositionX, mousePositionY, height, width);
		rectangle.setFill(color);
		rectangle.setStroke(color);
		this.shapesList.add(rectangle);
		
	}

	@Override
	public void addLine(double mousePositionX, double mousePositionY, double width, Color color) {
		
		Line line = new Line(mousePositionX, mousePositionY, width+mousePositionX, mousePositionY);
		line.setFill(color);
		line.setStroke(color);
		line.setStrokeWidth(3);
		this.shapesList.add(line);
		
	}
	
	@Override
	public void addShape(Shape shape) { 
		
		this.shapesList.add(shape);
	
	}
	
	@Override
	public void removeLastShape() {
		// TODO Auto-generated method stub
		if(shapesList.size()>0) {
			
			this.shapesList.remove(this.shapesList.size() - 1);
			
		}
		
	}

	@Override
	public void bringShapeToFront(Shape shape) {
		
		for(int index = 0 ; index < shapesList.size() ; index++) {
			
			if(shape == shapesList.get(index)) {
				
				shapesList.remove(index);
				shapesList.add(shape);
				
			}
			
		}
		
	}

	@Override
	public ArrayList<Shape> getShapesList() {
		// TODO Auto-generated method stub
		return shapesList;
	}
	
	@Override
	public String getMode() {
		// TODO Auto-generated method stub
		return this.mode;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}
	
}
