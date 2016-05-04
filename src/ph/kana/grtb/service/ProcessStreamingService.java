package ph.kana.grtb.service;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
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
		textArea.setText("");

		Task bgTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				streamBufferedToTextArea(grailsProcess.getInputStream(), textArea);
				return null;
			}
		};
		Thread thread = new Thread(bgTask);
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

	private void streamBufferedToTextArea(InputStream inputStream, TextArea textArea) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = reader.readLine();
			while (null != line) {
				final String readLine = line;
				Platform.runLater(() -> {
					textArea.appendText(readLine);
					textArea.appendText("\n");
				});
				line = reader.readLine();
			}
		} catch (IOException e) {
			throw new GrailsProcessException("Error streaming grails process.", e);
		}
	}
}
