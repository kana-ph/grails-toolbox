package ph.kana.grtb.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JTextArea;
import ph.kana.grtb.process.GrailsProcess;

public class IoUtils {
	
	private IoUtils() { }
	
	private static final File CACHE_FILE = new File(".grtb-cache");
	private static final Properties CACHE = new Properties();
	static {
		loadCache();
	}
	
	private static final String PREVIOUS_PROJECT = "previous_project";
	
	public static void printToFile(String content, File file) throws IOException {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			bufferedWriter.write(content);
		}
	}
	
	public static void reflectStreamToTextArea(InputStream inputStream, JTextArea textArea) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = reader.readLine();
			while (line != null) {
				textArea.append(line);
				textArea.append("\n");
				line = reader.readLine();
			}
		}
	}
	
	public static void saveCurrentProject(File directory) throws IOException {
		if (directory != null) {
			CACHE.setProperty(PREVIOUS_PROJECT, directory.getAbsolutePath());
			saveCache();
		}
	}
	
	public static File fetchPreviousProject() throws IOException {
		String previousProjectLocation = CACHE.getProperty(PREVIOUS_PROJECT);
		if (null == previousProjectLocation || previousProjectLocation.isEmpty()) {
			return null;
		} else {
			return new File(previousProjectLocation);
		}
	}
	
	public static void logRunning(GrailsProcess process) {
		printCommandLog('\u2192', "Process Running", process.getCommandString());
	}
	
	public static void logKill(GrailsProcess process) {
		printCommandLog('\u219b', "Killing Process", process.getCommandString());
	}
	
	public static void logExit(GrailsProcess process) {
		printCommandLog('\u21e5', "Process Exited!", process.getCommandString());
	}
	
	public static void logProjectChange(File project) {
		printCommandLog('>', "Grails project path set!", project.getAbsolutePath());
	}
	
	private static void loadCache() {
		try {
			if (CACHE_FILE.exists()) {
				InputStream cacheFileInputStream = new FileInputStream(CACHE_FILE);
				CACHE.load(cacheFileInputStream);
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	private static void saveCache() {
		try {
			OutputStream cacheFileOutputStream = new FileOutputStream(CACHE_FILE);
			
			String cacheHeader = "Cache for grails-toolbox.\nCan be deleted.";
			CACHE.store(cacheFileOutputStream, cacheHeader);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	private static void printCommandLog(char icon, String message, String command) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MMMdd HH:mm:ss");
		System.out.println(String.format("[GrTb](%s) %s %c %s", dateFormat.format(new Date()), message, icon, command));
	}
}
