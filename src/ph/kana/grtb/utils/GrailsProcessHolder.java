package ph.kana.grtb.utils;

import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.GrailsProcess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GrailsProcessHolder {

	private static GrailsProcessHolder instance = null;
	public static GrailsProcessHolder getInstance() {
		if (null == instance) {
			instance = new GrailsProcessHolder();
		}
		return instance;
	}

	private GrailsProcessHolder() { }

	private File projectDirectory;
	private GrailsProcess grailsProcess;

	private boolean stacktraceFlag;
	private boolean verboseFlag;

	public InputStream execute(GrailsProcess grailsProcess) {
		setGrailsProcess(grailsProcess);
		grailsProcess.setProjectDirectory(projectDirectory);
		grailsProcess.execute();

		return grailsProcess.getInputStream();
	}

	public void endCurrentProcess() {
		grailsProcess.stop();
	}

	public void setProjectDirectory(File projectDirectory) {
		try {
			this.projectDirectory = projectDirectory;
			IoUtils.saveCurrentProject(projectDirectory);
		} catch (IOException e) { }
	}

	public File getProjectDirectory() {
		if (projectDirectory == null) {
			try {
				projectDirectory = IoUtils.fetchPreviousProject();
			} catch (IOException e) {
				return null;
			}
		}
		return projectDirectory;
	}

	public void setStacktraceFlag(boolean flag) {
		stacktraceFlag = flag;
	}

	public void setVerboseFlag(boolean flag) {
		verboseFlag = flag;
	}

	private void setGrailsProcess(GrailsProcess grailsProcess) {
		if (null == this.grailsProcess || !this.grailsProcess.isAlive()) {
			addFlags(grailsProcess);
			this.grailsProcess = grailsProcess;
		} else {
			throw new GrailsProcessException(String.format("Grails command in progress: '%s'", grailsProcess.getCommand()));
		}
	}

	private void addFlags(GrailsProcess grailsProcess) {
		grailsProcess.setStacktraceMode(stacktraceFlag);
		grailsProcess.setVerboseMode(verboseFlag);
	}
}
