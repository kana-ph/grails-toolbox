package ph.kana.grtb.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import ph.kana.grtb.utils.IOUtils;

public abstract class GrailsProcess {
	
	private Process osProcess;
	private File grailsProjectDirectrory;
	private boolean stacktraceMode;
	
	protected abstract String[] getArgs();
	
	public final String getCommandString() {
		StringBuilder commandString = new StringBuilder("grails");
		for (String arg : this.getArgs()) {
			commandString.append(" ").append(arg);
		}
		
		if (stacktraceMode) {
			commandString.append(" --stacktrace");
		}
		return commandString.toString();
	}
	
	public final void execute() throws IOException, InterruptedException {
		String command = getCommandString();
		IOUtils.logRunning(this);
		osProcess = Runtime.getRuntime().exec(command, null, getGrailsProjectDirectrory());
	}
	
	public void stop() throws IOException {
		IOUtils.logKill(this);
		osProcess.destroy();
	}

	public File getGrailsProjectDirectrory() {
		return grailsProjectDirectrory;
	}

	public void setGrailsProjectDirectrory(File grailsProjectDirectrory) {
		if (grailsProjectDirectrory.isDirectory()) {
			this.grailsProjectDirectrory = grailsProjectDirectrory;
		} else {
			throw new IllegalArgumentException("Not a directory: " + grailsProjectDirectrory.getAbsolutePath());
		}
	}
	
	public boolean getStacktraceMode() {
		return stacktraceMode;
	}
	
	public void setStacktraceMode(boolean useStackTraceMode) {
		this.stacktraceMode = useStackTraceMode;
	}
	
	public InputStream getInputStream() {
		return osProcess.getInputStream();
	}
}
