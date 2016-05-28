package ph.kana.grtb.utils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {

	private static final String HEADER = "GrTb";
	private static final String DATE_FORMAT = "yyyy-MMMdd HH:mm:ss";

	private static Logger STDOUT_LOGGER = null;

	public static Logger getLogger() {
		if (null == STDOUT_LOGGER) {
			STDOUT_LOGGER = new Logger(System.out);
		}
		return STDOUT_LOGGER;
	}

	private PrintWriter writer;

	private Logger(PrintStream printStream) {
		writer = new PrintWriter(printStream);
	}

	public void info(String message, Object... args) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

		String date = format.format(new Date());
		String formattedMessage = String.format(message, args);

		writer.println(String.format("[%s](%s) %s", HEADER, date, formattedMessage));
	}
}
