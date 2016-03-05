package ph.kana.grtb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GrailsToolboxJavaFx extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		setUserAgentStylesheet(STYLESHEET_MODENA);

		Parent root = FXMLLoader.load(getClass().getResource("/ph/kana/grtb/fxml/Toolbox.fxml"));
		Scene scene = new Scene(root, 830, 750);

		stage.setTitle("Grails Toolbox");
		stage.setScene(scene);
		stage.show();
	}
}
