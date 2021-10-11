package View;

import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DrawingPane extends AnchorPane{
	
	//Variables
	public ArrayList<Ellipse> ellipsesList = new ArrayList<Ellipse>();
	public ArrayList<Rectangle> rectanglesList = new ArrayList<Rectangle>();
	public ArrayList<Line> linesList = new ArrayList<Line>();
	
	public DrawingPane() {
		
		this.setMinWidth(200);
		this.setPrefWidth(1000);
		this.setStyle("-fx-background-color: white");
		this.setBorder(new Border(new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.SOLID,new CornerRadii(0),BorderStroke.MEDIUM)));

		final Rectangle outputClip = new Rectangle();
		outputClip.setArcWidth(0);
	    outputClip.setArcHeight(0);
	    this.setClip(outputClip);

	    this.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
	        outputClip.setWidth(newValue.getWidth());
	        outputClip.setHeight(newValue.getHeight());
	    });     
	    
	}
	
	public void addShapes(ArrayList<Shape> shapesList){
	
		Shape shape; 
		
		for(int index = 0 ; index < shapesList.size() ; index++) {
			
			shape = shapesList.get(index);
			this.getChildren().add(shape);
			
		}
        
	}
	
	public void drawShapes(ArrayList<Shape> shapesList) {
		
		clear();
		addShapes(shapesList);
		
	}
	
	public void clear() {
		
		this.getChildren().clear();
		
	}

}
