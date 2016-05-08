package ph.kana.grtb.service;

import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.*;
import ph.kana.grtb.type.RunAppType;
import ph.kana.grtb.utils.GrailsProcessHolder;

import java.io.File;
import java.io.InputStream;

public class GrailsService {

	private final GrailsProcessHolder grailsProcessHolder = GrailsProcessHolder.getInstance();

	public GrailsProcess checkInstallation() {
		GrailsProcess grailsProcess = new GenericGrailsProcess("--version");
		InputStream inputStream = execute(grailsProcess);
		return inputStream == null? null : grailsProcess;
	}

	public GrailsProcess compile() {
		return runSimpleCommands("compile");
	}

	public GrailsProcess clean() {
		return runSimpleCommands("clean");
	}

	public GrailsProcess runApp(RunAppType type, String environment) {
		RunAppGrailsProcess runAppProcess = new RunAppGrailsProcess();
		runAppProcess.setType(type);
		runAppProcess.setEnvironment(environment);
		execute(runAppProcess);

		return runAppProcess;
	}

	public GrailsProcess testApp(boolean unit, boolean integration, String classPattern) {
		TestAppGrailsProcess testAppProcess = new TestAppGrailsProcess();
		testAppProcess.setFlagUnit(unit);
		testAppProcess.setFlagIntegration(integration);
		testAppProcess.setClassNamePattern(classPattern);
		execute(testAppProcess);

		return testAppProcess;
	}

	public void endCurrentProcess() {
		grailsProcessHolder.endCurrentProcess();
	}

	public void setProjectDirectory(File projectDirectory) {
		grailsProcessHolder.setProjectDirectory(projectDirectory);
	}

	public File getProjectDirectory() {
		return grailsProcessHolder.getProjectDirectory();
	}

	public void setStacktraceFlag(boolean flag) {
		grailsProcessHolder.setStacktraceFlag(flag);
	}

	public void setVerboseFlag(boolean flag) {
		grailsProcessHolder.setVerboseFlag(flag);
	}

	private InputStream execute(GrailsProcess process) {
		try {
			InputStream inputStream = grailsProcessHolder.execute(process);
			return inputStream;
		} catch (GrailsProcessException e) {
			return null;
		}
	}

	private GrailsProcess runSimpleCommands(String command) {
		GrailsProcess grailsProcess = new GenericGrailsProcess(command);
		execute(grailsProcess);
		return grailsProcess;
	}
}
