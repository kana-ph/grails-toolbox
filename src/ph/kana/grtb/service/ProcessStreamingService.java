package ph.kana.grtb.service;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import jdk.internal.util.xml.impl.Input;
import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.GrailsProcess;
import ph.kana.grtb.utils.GrailsProcessHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProcessStreamingService {

	private final GrailsProcessHolder grailsProcessHolder = GrailsProcessHolder.getInstance();

	public void streamToTextArea(GrailsProcess grailsProcess, TextArea textArea) {
		streamToTextArea(grailsProcess, textArea, (process) -> {});
	}

	public void streamToTextArea(GrailsProcess grailsProcess, TextArea textArea, Consumer<GrailsProcess> cleanup) {
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
			grailsProcessHolder.endCurrentProcess();
			event.consume();
			cleanup.accept(grailsProcess);
		};
		bgTask.setOnSucceeded(endProcessEventHandler);
		bgTask.setOnCancelled(endProcessEventHandler);
		bgTask.setOnFailed(endProcessEventHandler);
	}
}
