package View;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MenuPane extends VBox{

	//Variables
	
	//Modes' radio buttons
	RadioButton selectOrMoveRadioButton;
	RadioButton ellipseRadioButton;
	RadioButton rectangleRadioButton;
	RadioButton lineRadioButton;
	
	//Color picker
	ColorPicker colorPicker;
	
	//Operations' buttons
	Button deleteButton;
	Button cloneButton;
	
	public MenuPane(){
		
		this.setMaxWidth(200);
		this.setPrefWidth(200);
		
		//We create a separator between the menus
		Separator separator = new Separator();
		separator.setValignment(VPos.CENTER);
		this.getChildren().add(separator);
		        
		//We create the modes' radio buttons
		ToggleGroup modeRadioButtonsGroup = new ToggleGroup();
			
			//Select or Move
			selectOrMoveRadioButton = new RadioButton("Select or Move");
			selectOrMoveRadioButton.setStyle("-fx-padding: 5 10 8 10;");
			selectOrMoveRadioButton.setToggleGroup(modeRadioButtonsGroup);
			selectOrMoveRadioButton.setSelected(true);
						
			//Ellipse
			ellipseRadioButton = new RadioButton("Ellipse");
			ellipseRadioButton.setStyle("-fx-padding: 5 10 8 10;");
			ellipseRadioButton.setToggleGroup(modeRadioButtonsGroup);
	
			//Rectangle
			rectangleRadioButton = new RadioButton("Rectangle");
			rectangleRadioButton.setStyle("-fx-padding: 5 10 8 10;");
			rectangleRadioButton.setToggleGroup(modeRadioButtonsGroup);
				
			//Line
			lineRadioButton = new RadioButton("Line");
			lineRadioButton.setStyle("-fx-padding: 5 10 8 10;");
			lineRadioButton.setToggleGroup(modeRadioButtonsGroup);
				
		//We add the mode's radio buttons to the new menuPane
		this.getChildren().addAll(selectOrMoveRadioButton, ellipseRadioButton, rectangleRadioButton, lineRadioButton);
			
		//We create a separator between the menus
		separator = new Separator();
		separator.setValignment(VPos.CENTER);
		this.getChildren().add(separator);
				
		//We create the color picker
		colorPicker = new ColorPicker();
		colorPicker.setStyle("-fx-padding: 5 10 8 10;");
		VBox.setMargin(colorPicker,new Insets(10, 10, 10, 10));
					
		//We add the color picker to the new menuPane
		this.getChildren().add(colorPicker);
		
		//We create a separator between the menus
		separator = new Separator();
		separator.setValignment(VPos.CENTER);
		this.getChildren().add(separator);
			
		//We create the operations' buttons
			
			//Delete 
			deleteButton = new Button("Delete");
			deleteButton.setStyle("-fx-padding: 5 8 5 8;");
			VBox.setMargin(deleteButton,new Insets(10, 10, 10, 10));
			deleteButton.setWrapText(false);
			deleteButton.setMaxSize(200, 20);
						
			//Clone
			cloneButton = new Button("Clone");
			cloneButton.setStyle("-fx-padding: 5 8 5 8;");
			VBox.setMargin(cloneButton,new Insets(10, 10, 10, 10));
			cloneButton.setWrapText(false);
			cloneButton.setMaxSize(200, 20);
						
		//We add the operations' buttons to the new menuPane
		this.getChildren().addAll(deleteButton, cloneButton);
		
		//We create a separator between the menus
		separator = new Separator();
		separator.setValignment(VPos.CENTER);
		this.getChildren().add(separator);
		
		

	}

	public RadioButton getSelectOrMoveRadioButton() {
		return selectOrMoveRadioButton;
	}

	public RadioButton getEllipseRadioButton() {
		return ellipseRadioButton;
	}

	public RadioButton getRectangleRadioButton() {
		return rectangleRadioButton;
	}

	public RadioButton getLineRadioButton() {
		return lineRadioButton;
	}

	public ColorPicker getColorPicker() {
		return colorPicker;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public Button getCloneButton() {
		return cloneButton;
	}
	
}
