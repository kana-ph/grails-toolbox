package ph.kana.grtb;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ph.kana.grtb.controller.ToolboxController;

import java.io.IOException;

public class GrailsToolboxJavaFx extends Application {

	private static final double APP_WIDTH = 830.0;
	private static final double APP_HEIGHT = 750.0;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		setUserAgentStylesheet(STYLESHEET_MODENA);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/kana/grtb/fxml/Toolbox.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);

		ToolboxController toolboxController = loader.getController();
		toolboxController.setWindow(stage);
		toolboxController.setApplication(this);

		stage.setTitle("Grails Toolbox");
		stage.setMinWidth(APP_WIDTH);
		stage.setMinHeight(APP_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}
}
