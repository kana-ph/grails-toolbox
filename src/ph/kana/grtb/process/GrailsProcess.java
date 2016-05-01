package ph.kana.grtb.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.utils.Logger;

public abstract class GrailsProcess {

	private Process process;
	private File projectDirectory;
	private Logger logger = Logger.getLogger();

	private boolean stacktraceMode;
	private boolean verboseMode;

	protected abstract String[] getArgs();

	public final String getCommand() {
		return new StringBuilder("grails ")
			.append(String.join(" ", getArgs()))
			.append(stacktraceMode? " --stacktrace" : "")
			.append(verboseMode? " --verbose" : "")
			.toString();
	}

	public final void execute() throws GrailsProcessException {
		String command = getCommand();
		logger.info("Running command '%s'", command);
		try {
			process = Runtime.getRuntime().exec(command, null, getProjectDirectory());
		} catch (IOException e) {
			throw new GrailsProcessException("Exception on starting process", e);
		}
	}

	public void stop() {
		logger.info("Killing command '%s'", getCommand());
		process.destroy();
	}

	public File getProjectDirectory() {
		return projectDirectory;
	}

	public void setProjectDirectory(File projectDirectory) {
		if (projectDirectory.isDirectory()) {
			this.projectDirectory = projectDirectory;
		} else {
			throw new IllegalArgumentException("Not a directory: " + projectDirectory.getAbsolutePath());
		}
	}

	public void setVerboseMode(boolean verboseMode) {
		this.verboseMode = verboseMode;
	}

	public void setStacktraceMode(boolean useStackTraceMode) {
		this.stacktraceMode = useStackTraceMode;
	}

	public InputStream getInputStream() {
		return process.getInputStream();
	}
}
