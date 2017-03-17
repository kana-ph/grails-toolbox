package ph.kana.grtb.controller;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import com.sun.xml.internal.ws.util.InjectionPlan.FieldInjectionPlan;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
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
import ph.kana.grtb.utils.IoUtils;

import java.io.*;
import java.util.Optional;
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
	@FXML private Accordion toolboxAccordion;
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
		initializeToolboxAccordion();
	}

	@FXML
	public void toggleToolbox() {
		boolean expanding = DEFAULT_CONSOLE_LEFT_ANCHOR == AnchorPane.getLeftAnchor(consoleAnchorPane);

		RotateTransition iconRotate = new RotateTransition(Duration.millis(400), collapseButtonIcon);
		iconRotate.setFromAngle(expanding? 0 : 180);
		iconRotate.setToAngle(expanding? 180: 0);

		Timer animationTimer = new Timer();
		animationTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				double increment = expanding? -1.0 : 1.0;
				double finalAnchor = expanding? EXPANDED_CONSOLE_LEFT_ANCHOR : DEFAULT_CONSOLE_LEFT_ANCHOR;

				double currentAnchor = AnchorPane.getLeftAnchor(consoleAnchorPane);
				if ((expanding && (currentAnchor > finalAnchor)) || (!expanding && currentAnchor < finalAnchor)) {
					AnchorPane.setLeftAnchor(consoleAnchorPane, currentAnchor + increment);
				} else {
					this.cancel();
				}
			}
		}, 0, 1);
		iconRotate.play();
	}

	@FXML
	public boolean openProject() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Grails Project Directory");
		directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		return Optional
			.ofNullable(directoryChooser.showDialog(window))
			.map(directory -> {
				grailsService.setProjectDirectory(directory);
				Platform.runLater(() ->
					window.setTitle(String.format("Grails Toolbox [%s]", directory.getAbsolutePath()))
				);
				return true;
			})
			.orElse(false);
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
		killProcessButton.setDisable(true);
		grailsService.endCurrentProcess();
		killProcessButton.setDisable(false);
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
			streamToTextArea(grailsProcess);
		}
	}

	private void initializeGrailsProject() {
		grailsService
			.getProjectDirectory()
			.map(File::getAbsolutePath)
			.map(path -> String.format("Grails Toolbox [%s]", path))
			.filter(title -> {
				Platform.runLater(() -> window.setTitle(title));
				return true;
			})
			.orElseGet(() -> {
				boolean projectOpened = openProject();
				if (!projectOpened) {
					exitApp();
				}
				return null;
			});
	}

	private void initializeRunAppTypeComboBox() {
		Platform.runLater(() -> {
			ObservableList<String> comboBoxItems = runAppTypeComboBox.getItems();
			comboBoxItems.addAll(RunAppType.descriptions());
			runAppTypeComboBox.setValue(RunAppType.SERVER.getDescription());
		});
	}

	private void initializeToolboxAccordion() {
		int lastOpenToolboxIndex = IoUtils.fetchOpenToolbox();
		TitledPane lastOpenToolbox = toolboxAccordion
			.getPanes()
			.get(lastOpenToolboxIndex);
		ObjectProperty<TitledPane> selectedPane = toolboxAccordion.expandedPaneProperty();
		selectedPane.setValue(lastOpenToolbox);
		selectedPane.addListener((observable, oldValue, newValue) -> saveSelectedPane());
	}

	private void openDialog(Pane dialogPane) {
		activeDialog = dialogPane;
		activeDialog.setVisible(true);
	}

	private void startActiveProcessBehavior(GrailsProcess grailsProcess) {
		streamToTextArea(grailsProcess);

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

	private void streamToTextArea(GrailsProcess grailsProcess) {
		Task bgTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				InputStream inputStream = grailsProcess.getInputStream();
				StringBuilder consoleContent = new StringBuilder();

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
					String line;
					while ((line = reader.readLine()) != null) {
						consoleContent
							.append(line)
							.append("\n");
						updateMessage(consoleContent.toString());
					}
				} catch (IOException e) {
					throw new GrailsProcessException("Error streaming grails process.", e);
				}
				return null;
			}
		};

		consoleTextArea
			.textProperty()
			.bind(bgTask.messageProperty());
		bgTask
			.messageProperty()
			.addListener((observable, oldValue, newValue) -> scrollConsoleToEnd());
		EventHandler<Event> endProcessEventHandler = event -> {
			grailsService.endCurrentProcess();
			startInactiveProcessBehavior();
			event.consume();
		};
		bgTask.setOnSucceeded(endProcessEventHandler);
		bgTask.setOnCancelled(endProcessEventHandler);
		bgTask.setOnFailed(endProcessEventHandler);

		startDaemonThread(bgTask, "process-streaming-bg-task");
	}

	private void scrollConsoleToEnd() {
		Optional
			.of(consoleTextArea.getSelectedText())
			.filter(String::isEmpty)
			.map(text -> Double.MAX_VALUE)
			.ifPresent(consoleTextArea::setScrollTop);
	}

	private void openLink(String url) {
		HostServicesDelegate hostServices = HostServicesFactory.getInstance(application);
		hostServices.showDocument(url);
	}
	
	private void exitApp() {
		Platform.exit();
		System.exit(0);
	}

	private void saveSelectedPane() {
		TitledPane selectedPane = toolboxAccordion.getExpandedPane();
		Task bgTask = new Task<Void>() {
			@Override
			public Void call() {
				sleep(10000);
				if (selectedPane == toolboxAccordion.getExpandedPane()) {
					int selectedIndex = toolboxAccordion
						.getPanes()
						.indexOf(selectedPane);
					IoUtils.saveOpenToolbox(selectedIndex);
				}
				return null;
			}
		};
		startDaemonThread(bgTask, "save-last-toolbox-bg-task");
	}

	private void startDaemonThread(Task<Void> task, String threadName) {
		Thread thread = new Thread(task, threadName);
		thread.setDaemon(true);
		thread.start();
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
}
