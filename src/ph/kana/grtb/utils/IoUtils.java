package ph.kana.grtb.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
	public static void saveCurrentProject(File directory) throws IOException {
		if (directory != null) {
			CACHE.setProperty(KEY_PREVIOUS_PROJECT, directory.getAbsolutePath());
			saveCache();
		}
	}
	
	public static File fetchPreviousProject() throws IOException {
		String previousProjectLocation = CACHE.getProperty(KEY_PREVIOUS_PROJECT);
		if (null == previousProjectLocation || previousProjectLocation.isEmpty()) {
			return null;
		} else {
			return new File(previousProjectLocation);
		}
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
