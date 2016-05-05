package ph.kana.grtb.service;

import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.GrailsProcess;
import ph.kana.grtb.process.RunAppGrailsProcess;
import ph.kana.grtb.process.VersionGrailsProcess;
import ph.kana.grtb.type.RunAppType;
import ph.kana.grtb.utils.GrailsProcessHolder;

import java.io.File;
import java.io.InputStream;

public class GrailsService {

	private final GrailsProcessHolder grailsProcessHolder = GrailsProcessHolder.getInstance();

	public GrailsProcess checkInstallation() {
		GrailsProcess grailsProcess = new VersionGrailsProcess();
		InputStream inputStream = execute(grailsProcess);
		return inputStream == null? null : grailsProcess;
	}

	public GrailsProcess runApp(RunAppType type, String environment) {
		RunAppGrailsProcess runAppProcess = new RunAppGrailsProcess();
		runAppProcess.setType(type);
		runAppProcess.setEnvironment(environment);
		execute(runAppProcess);

		return runAppProcess;
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

	private InputStream execute(GrailsProcess process) {
		try {
			InputStream inputStream = grailsProcessHolder.execute(process);
			return inputStream;
		} catch (GrailsProcessException e) {
			return null;
		}
	}
}
