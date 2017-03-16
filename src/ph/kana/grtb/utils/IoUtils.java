package ph.kana.grtb.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

public class IoUtils {
	
	private IoUtils() { }
	
	private static final File CACHE_FILE = new File(System.getProperty("user.home") + "/.grtb-cache");
	private static final Properties CACHE = new Properties();
	static {
		loadCache();
	}
	
	private static final String KEY_PREVIOUS_PROJECT = "previous_project";
	
	public static void printToFile(String content, File file) throws IOException {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			bufferedWriter.write(content);
		}
	}
	
	public static void saveCurrentProject(Optional<File> directory) throws IOException {
		directory
			.map(File::getAbsolutePath)
			.ifPresent(path -> {
				CACHE.setProperty(KEY_PREVIOUS_PROJECT, path);
				saveCache();
			});
	}
	
	public static Optional<File> fetchPreviousProject() throws IOException {
		return Optional
			.ofNullable(CACHE.getProperty(KEY_PREVIOUS_PROJECT))
			.filter(property -> !property.isEmpty())
			.map(File::new);
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
}
