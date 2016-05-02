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

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		setUserAgentStylesheet(STYLESHEET_MODENA);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ph/kana/grtb/fxml/Toolbox.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 830, 750);

		ToolboxController toolboxController = loader.getController();
		toolboxController.setWindow(stage);

		stage.setTitle("Grails Toolbox");
		stage.setScene(scene);
		stage.show();
	}
}
