package ph.kana.grtb.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.utils.Logger;

public abstract class GrailsProcess {

	private Process process;
	private File projectDirectory;
	private Logger logger = Logger.getLogger();

	private boolean stacktraceMode;
	private boolean verboseMode;

	protected abstract List<String> getArgs();

	public final String getCommand() {
		return new StringBuilder()
			.append(String.join(" ", getArgs()).trim())
			.append(stacktraceMode? " --stacktrace" : "")
			.append(verboseMode? " --verbose" : "")
			.toString();
	}

	public final void execute() throws GrailsProcessException {
		String command = getCommand();
		logger.info("Running command '%s'", command);
		try {
			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(String.format("grails %s", command), null, projectDirectory);
		} catch (IOException e) {
			throw new GrailsProcessException("Exception on starting process", e);
		}
	}

	public void stop() {
		logger.info("Killing command '%s'", getCommand());
		process.destroy();
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

	public boolean isAlive() { return process.isAlive(); }

	@Override
	public String toString() {
		return getCommand();
	}
}
