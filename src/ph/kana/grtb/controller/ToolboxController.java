package ph.kana.grtb.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ph.kana.grtb.service.GrailsService;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class ToolboxController {

	GrailsService grailsService = new GrailsService();

	@FXML
	private Button collapseButton;
	@FXML
	private TextArea consoleTextArea;
	@FXML
	private AnchorPane rootAnchorPane;
	@FXML
	private AnchorPane consoleAnchorPane;

	@FXML
	public void initialize() {
		InputStream inputStream = grailsService.checkInstallation();
		if (null == inputStream) {
			// TODO say something nice.
		} else {
			streamToTextArea(inputStream, consoleTextArea);
		}
	}

	@FXML
	public void toggleToolbox() {
		double DEFAULT_CONSOLE_LEFT_ANCHOR = 240.0;
		boolean expanding = DEFAULT_CONSOLE_LEFT_ANCHOR == rootAnchorPane.getLeftAnchor(consoleAnchorPane);

		collapseButton.setText(expanding? "\u00bb" : "\u00ab");

		Timer animationTimer = new Timer();
		animationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				double increment = expanding? -1.0 : 1.0;
				double finalAnchor = expanding? 2.0 : DEFAULT_CONSOLE_LEFT_ANCHOR;

				double currentAnchor = rootAnchorPane.getLeftAnchor(consoleAnchorPane);
				if ((expanding && (currentAnchor > finalAnchor)) || (!expanding && currentAnchor < finalAnchor)) {
					rootAnchorPane.setLeftAnchor(consoleAnchorPane, currentAnchor + increment);
				} else {
					this.cancel();
				}
			}
		}, 0, 1);
	}

	//TODO move this
	private void streamToTextArea(InputStream inputStream, TextArea textArea) {
		textArea.setText("");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = reader.readLine();
			while (null != line) {
				textArea.appendText(line);
				textArea.appendText("\n");
				line = reader.readLine();
			}
		} catch (IOException e) { }
	}
}
