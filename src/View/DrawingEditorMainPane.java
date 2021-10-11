package View;

import javafx.scene.layout.BorderPane;

public class DrawingEditorMainPane extends BorderPane {
	
		//Variables
		private MenuPane menuPane;
		private DrawingPane drawingPane;
		
		public DrawingEditorMainPane()
		{
			
			//We create the drawingPane
			drawingPane = new DrawingPane();
			this.setCenter(drawingPane);
			
			//We create the menuPane
			menuPane = new MenuPane();
			this.setLeft(menuPane);
			
		}

		public MenuPane getMenuPane() {
			return menuPane;
		}

		public DrawingPane getDrawingPane() {
			return drawingPane;
		}
		
}
