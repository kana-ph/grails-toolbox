package ph.kana.grtb.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.Timer;
import java.util.TimerTask;

public class ToolboxController {
	@FXML
	private Button collapseButton;
	@FXML
	private AnchorPane rootAnchorPane;
	@FXML
	private AnchorPane consoleAnchorPane;

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
}
