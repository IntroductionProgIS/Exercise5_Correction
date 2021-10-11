package Application;

import Controller.DrawingController_Implemented;
import Model.DrawingModel;
import Model.DrawingModel_Implemented;
import View.DrawingEditorMainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	private static DrawingEditorMainPane drawingEditorMainPane;
	private static DrawingModel drawingModel;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		drawingModel = new DrawingModel_Implemented();
		drawingEditorMainPane = new DrawingEditorMainPane();
		new DrawingController_Implemented(drawingModel,drawingEditorMainPane);
        primaryStage.setTitle("Drawing editor");
        primaryStage.setScene(new Scene(drawingEditorMainPane, 800,500));
        primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		
        launch(args);
        
    }
	
	

}
