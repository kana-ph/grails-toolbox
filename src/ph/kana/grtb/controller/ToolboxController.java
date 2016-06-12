package ph.kana.grtb.controller;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.GrailsProcess;
import ph.kana.grtb.service.GrailsService;
import ph.kana.grtb.type.RunAppType;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class ToolboxController {

	private static final double DEFAULT_CONSOLE_LEFT_ANCHOR = 240.0;
	private static final double EXPANDED_CONSOLE_LEFT_ANCHOR = 2.0;

	private final GrailsService grailsService = new GrailsService();

	private Pane activeDialog;

	private Stage window;
	private Application application;

	@FXML private ImageView collapseButtonIcon;
	@FXML private Button killProcessButton;
	@FXML private CheckBox includeUnitTestCheckbox;
	@FXML private CheckBox includeIntegrationTestCheckbox;
	@FXML private CheckBox cleanAllCheckbox;
	@FXML private CheckMenuItem flagStacktraceCheckbox;
	@FXML private CheckMenuItem flagVerboseCheckbox;
	@FXML private ComboBox<String> runAppTypeComboBox;
	@FXML private ComboBox<String> runEnvironmentComboBox;
	@FXML private TextArea consoleTextArea;
	@FXML private TextField commandStringTextField;
	@FXML private TextField customCommandTextField;
	@FXML private TextField testClassPatternTextField;
	@FXML private ProgressBar processProgressBar;
	@FXML private AnchorPane rootAnchorPane;
	@FXML private AnchorPane consoleAnchorPane;
	@FXML private TilePane killAppPane;
	@FXML private TilePane customCommandPane;
	@FXML private TilePane aboutPane;

	public void setWindow(Stage window) {
		this.window = window;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@FXML
	public void initialize() {
		initializeGrailsProject();
		checkGrailsInstallation();
		initializeRunAppTypeComboBox();
	}

	@FXML
	public void toggleToolbox() {
		boolean expanding = DEFAULT_CONSOLE_LEFT_ANCHOR == rootAnchorPane.getLeftAnchor(consoleAnchorPane);

		RotateTransition iconRotate = new RotateTransition(Duration.millis(400), collapseButtonIcon);
		iconRotate.setFromAngle(expanding? 0 : 180);
		iconRotate.setToAngle(expanding? 180: 0);

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
		iconRotate.play();
	}

	@FXML
	public boolean openProject() {
		boolean fileOpened = false;

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Grails Project Directory");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File directory = directoryChooser.showDialog(window);

		if (directory != null) {
			grailsService.setProjectDirectory(directory);
			Platform.runLater(() ->
				window.setTitle(String.format("Grails Toolbox [%s]", directory.getAbsolutePath()))
			);
			fileOpened = true;
		}
		return fileOpened;
	}

	@FXML
	public void runAppButtonClick() {
		RunAppType type = RunAppType.findByDescription(runAppTypeComboBox.getValue());
		String environment = runEnvironmentComboBox.getValue();

		GrailsProcess grailsProcess = grailsService.runApp(type, environment);
		startActiveProcessBehavior(grailsProcess);
	}

	@FXML
	public void killProcessButtonClick() {
		killProcessButton.disableProperty().setValue(true);
		grailsService.endCurrentProcess();
		killProcessButton.disableProperty().setValue(false);
	}

	@FXML
	public void compileButtonClick() {
		GrailsProcess grailsProcess = grailsService.compile();
		startActiveProcessBehavior(grailsProcess);
	}

	@FXML
	public void cleanButtonClick() {
		boolean cleanAll = cleanAllCheckbox.isSelected();
		GrailsProcess grailsProcess = grailsService.clean(cleanAll);
		startActiveProcessBehavior(grailsProcess);
	}

	@FXML
	public void testAppButtonClick() {
		boolean includeUnitTest = includeUnitTestCheckbox.isSelected();
		boolean includeIntegrationTest = includeIntegrationTestCheckbox.isSelected();
		String classPattern = testClassPatternTextField.getText();

		GrailsProcess grailsProcess = grailsService.testApp(includeUnitTest, includeIntegrationTest, classPattern);
		startActiveProcessBehavior(grailsProcess);
	}

	@FXML
	public void testClassPatternKeyPress(KeyEvent keyEvent) {
		if (KeyCode.ENTER == keyEvent.getCode()) {
			testAppButtonClick();
		}
	}

	@FXML
	public void toggleStacktrace() {
		grailsService.setStacktraceFlag(flagStacktraceCheckbox.isSelected());
	}

	@FXML
	public void toggleVerbose() {
		grailsService.setVerboseFlag(flagVerboseCheckbox.isSelected());
	}

	@FXML
	public void closeDialogClick() {
		activeDialog.setVisible(false);
		activeDialog = null;
	}

	@FXML
	public void customCommandMenuItemClick() {
		openDialog(customCommandPane);
		customCommandTextField.requestFocus();
	}

	@FXML
	public void executeButtonClick() {
		String command = customCommandTextField.getText();
		GrailsProcess grailsProcess = grailsService.executeCustom(command);
		closeDialogClick();

		startActiveProcessBehavior(grailsProcess);
	}

	@FXML
	public void customCommandTextFieldKeyPress(KeyEvent keyEvent) {
		if (KeyCode.ENTER == keyEvent.getCode()) {
			executeButtonClick();
		}
	}

	@FXML
	public void aboutMenuItemClick() {
		openDialog(aboutPane);
	}

	@FXML
	public void githubLinkClicked() {
		openLink("https://github.com/kana0011/grails-toolbox");
	}

	@FXML
	public void facebookLinkClicked() {
		openLink("https://www.facebook.com/kana0011");
	}

	@FXML
	public void twitterLinkClicked() {
		openLink("https://twitter.com/_kana0011");
	}

	@FXML
	public void icons8LinkClicked() {
		openLink("https://icons8.com/");
	}

	private void checkGrailsInstallation() {
		GrailsProcess grailsProcess = grailsService.checkInstallation();
		if (null == grailsProcess) {
			Platform.runLater(() -> {
				alertError("Grails is not installed!");
				exitApp();
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
			boolean projectOpened = openProject();
			if (!projectOpened) {
				exitApp();
			}
		}
	}

	private void initializeRunAppTypeComboBox() {
		Platform.runLater(() -> {
			ObservableList<String> comboBoxItems = runAppTypeComboBox.getItems();
			comboBoxItems.addAll(RunAppType.descriptions());
			runAppTypeComboBox.setValue(RunAppType.SERVER.getDescription());
		});
	}

	private void openDialog(Pane dialogPane) {
		activeDialog = dialogPane;
		activeDialog.setVisible(true);
	}

	private void startActiveProcessBehavior(GrailsProcess grailsProcess) {
		streamToTextArea(grailsProcess, consoleTextArea);

		commandStringTextField.setText(grailsProcess.getCommand());
		processProgressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
		killAppPane.setVisible(true);
	}

	private void startInactiveProcessBehavior() {
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
			startInactiveProcessBehavior();
			event.consume();
		};
		bgTask.setOnSucceeded(endProcessEventHandler);
		bgTask.setOnCancelled(endProcessEventHandler);
		bgTask.setOnFailed(endProcessEventHandler);
	}

	private void openLink(String url) {
		HostServicesDelegate hostServices = HostServicesFactory.getInstance(application);
		hostServices.showDocument(url);
	}
	
	private void exitApp() {
		Platform.exit();
		System.exit(0);
	}
}
