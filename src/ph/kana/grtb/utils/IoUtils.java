package ph.kana.grtb.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JTextArea;
import ph.kana.grtb.process.GrailsProcess;

public class IoUtils {
	
	private IoUtils() { }
	
	public static final File CACHE = new File(".grtb-cache");
	
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
			try (PrintWriter writer = new PrintWriter(CACHE)) {
				writer.println(directory.getAbsolutePath());
				writer.flush();
			}
		}
	}
	
	public static File fetchPreviousProject() throws IOException {
		if (CACHE.exists()) {
			Scanner scanner = new Scanner(CACHE);
			
			if (scanner.hasNextLine()) {
				File previousProject = new File(scanner.nextLine());

				if (previousProject.exists() && previousProject.isDirectory()) {
					return previousProject;
				}
			}
		}
		return null;
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
	
	private static void printCommandLog(char icon, String message, String command) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(String.format("[GrailsToolbox](%s) %s %c %s", dateFormat.format(new Date()), message, icon, command));
	}
}
