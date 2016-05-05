package ph.kana.grtb.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.GrailsProcess;
import ph.kana.grtb.service.GrailsService;
import ph.kana.grtb.type.RunAppType;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ToolboxController {

	static private final double DEFAULT_CONSOLE_LEFT_ANCHOR = 240.0;
	static private final double EXPANDED_CONSOLE_LEFT_ANCHOR = 2.0;

	private GrailsService grailsService = new GrailsService();

	private Stage window;

	@FXML private Button collapseButton;
	@FXML private ComboBox<String> runAppTypeComboBox;
	@FXML private ComboBox<String> runEnvironmentComboBox;
	@FXML private TextArea consoleTextArea;
	@FXML private TextField commandStringTextField;
	@FXML private ProgressBar processProgressBar;
	@FXML private AnchorPane rootAnchorPane;
	@FXML private AnchorPane consoleAnchorPane;
	@FXML private AnchorPane killAppPane;

	public void setWindow(Stage window) {
		this.window = window;
	}

	@FXML
	public void initialize() {
		checkGrailsInstallation();
		initializeGrailsProject();
		initializeRunAppTypeComboBox();
	}

	@FXML
	public void toggleToolbox() {
		boolean expanding = DEFAULT_CONSOLE_LEFT_ANCHOR == rootAnchorPane.getLeftAnchor(consoleAnchorPane);

		collapseButton.setText(expanding? "\u00bb" : "\u00ab");

		Timer animationTimer = new Timer();
		animationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				double increment = expanding? -1.0 : 1.0;
				double finalAnchor = expanding? EXPANDED_CONSOLE_LEFT_ANCHOR : DEFAULT_CONSOLE_LEFT_ANCHOR;

				double currentAnchor = rootAnchorPane.getLeftAnchor(consoleAnchorPane);
				if ((expanding && (currentAnchor > finalAnchor)) || (!expanding && currentAnchor < finalAnchor)) {
					rootAnchorPane.setLeftAnchor(consoleAnchorPane, currentAnchor + increment);
				} else {
					this.cancel();
				}
			}
		}, 0, 1);
	}

	@FXML
	public void openProject() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Grails Project Directory");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File directory = directoryChooser.showDialog(window);

		if (directory != null) {
			grailsService.setProjectDirectory(directory);
			Platform.runLater(() ->
				window.setTitle(String.format("Grails Toolbox [%s]", directory.getAbsolutePath()))
			);
		} else {
			Platform.exit();
		}
	}

	@FXML
	public void runAppButtonClick() {
		RunAppType type = RunAppType.findByDescription(runAppTypeComboBox.getValue());
		String environment = runEnvironmentComboBox.getValue();

		GrailsProcess grailsProcess = grailsService.runApp(type, environment);
		startActiveProcessBehavior(grailsProcess);
	}

	private void checkGrailsInstallation() {
		GrailsProcess grailsProcess = grailsService.checkInstallation();
		if (null == grailsProcess) {
			Platform.runLater(() -> {
				alertError("Grails is not installed!");
				Platform.exit();
			});
		} else {
			streamToTextArea(grailsProcess, consoleTextArea);
		}
	}

	private void initializeGrailsProject() {
		File projectDirectory = grailsService.getProjectDirectory();
		if (projectDirectory != null) {
			Platform.runLater(() ->
				window.setTitle(String.format("Grails Toolbox [%s]", projectDirectory.getAbsolutePath()))
			);
		} else {
			openProject();
		}
	}

	private void initializeRunAppTypeComboBox() {
		Platform.runLater(() -> {
			ObservableList<String> comboBoxItems = runAppTypeComboBox.getItems();
			comboBoxItems.addAll(RunAppType.descriptions());
			runAppTypeComboBox.setValue(RunAppType.SERVER.getDescription());
		});
	}

	private void startActiveProcessBehavior(GrailsProcess grailsProcess) {
		streamToTextArea(grailsProcess, consoleTextArea);

		commandStringTextField.setText(grailsProcess.getCommand().substring(7));
		processProgressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
		killAppPane.setVisible(true);
	}

	private void startInactiveProcessBehavior(GrailsProcess grailsProcess) {
		commandStringTextField.setText("");
		processProgressBar.setProgress(0.0);
		killAppPane.setVisible(false);
	}

	private void alertError(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
	}

	private void streamToTextArea(GrailsProcess grailsProcess, TextArea textArea) {
		Task bgTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				InputStream inputStream = grailsProcess.getInputStream();
				StringBuilder consoleContent = new StringBuilder();

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
					String line = reader.readLine();
					while (null != line) {
						consoleContent
							.append(line)
							.append("\n");
						updateMessage(consoleContent.toString());
						line = reader.readLine();
					}
				} catch (IOException e) {
					throw new GrailsProcessException("Error streaming grails process.", e);
				}
				return null;
			}
		};
		textArea.textProperty()
			.bind(bgTask.messageProperty());
		Thread thread = new Thread(bgTask, "process-streaming-bg-task");
		thread.setDaemon(true);
		thread.start();

		EventHandler<Event> endProcessEventHandler = event -> {
			grailsService.endCurrentProcess();
			startInactiveProcessBehavior(grailsProcess);
			event.consume();
		};
		bgTask.setOnSucceeded(endProcessEventHandler);
		bgTask.setOnCancelled(endProcessEventHandler);
		bgTask.setOnFailed(endProcessEventHandler);
	}
}
