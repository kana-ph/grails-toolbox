package ph.kana.grtb.service;

import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.*;
import ph.kana.grtb.type.RunAppType;
import ph.kana.grtb.utils.GrailsProcessHolder;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

public class GrailsService {

	private final GrailsProcessHolder grailsProcessHolder = GrailsProcessHolder.getInstance();

	public GrailsProcess checkInstallation() {
		GrailsProcess grailsProcess = new GenericGrailsProcess("--version");

		return execute(grailsProcess)
			.map(p -> grailsProcess)
			.orElse(null);
	}

	public GrailsProcess compile() {
		return runSimpleCommands("compile");
	}

	public GrailsProcess clean(boolean cleanAll) {
		CleanGrailsProcess cleanGrailsProcess = new CleanGrailsProcess();
		cleanGrailsProcess.setCleanAll(cleanAll);
		execute(cleanGrailsProcess);

		return cleanGrailsProcess;
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

	public Optional<File> getProjectDirectory() {
		return Optional
			.ofNullable(grailsProcessHolder.getProjectDirectory());
	}

	public void setStacktraceFlag(boolean flag) {
		grailsProcessHolder.setStacktraceFlag(flag);
	}

	public void setVerboseFlag(boolean flag) {
		grailsProcessHolder.setVerboseFlag(flag);
	}

	public GrailsProcess executeCustom(String command) {
		return runSimpleCommands(command);
	}

	private Optional<InputStream> execute(GrailsProcess process) {
		try {
			return Optional
				.of(grailsProcessHolder.execute(process));
		} catch (GrailsProcessException e) {
			return Optional.empty();
		}
	}

	private GrailsProcess runSimpleCommands(String command) {
		GrailsProcess grailsProcess = new GenericGrailsProcess(command);
		execute(grailsProcess);
		return grailsProcess;
	}
}
