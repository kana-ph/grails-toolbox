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

	private final GrailsProcessHolder processHolder = GrailsProcessHolder.getInstance();

	public InputStream checkInstallation() {
		return execute(new VersionGrailsProcess());
	}

	public InputStream runApp(RunAppType type, String environment) {
		RunAppGrailsProcess runAppProcess = new RunAppGrailsProcess();
		runAppProcess.setType(type);
		runAppProcess.setEnvironment(environment);
		return execute(runAppProcess);
	}

	public void setProjectDirectory(File projectDirectory) {
		processHolder.setProjectDirectory(projectDirectory);
	}

	public File getProjectDirectory() {
		return processHolder.getProjectDirectory();
	}

	private InputStream execute(GrailsProcess process) {
		try {
			InputStream inputStream = processHolder.execute(process);
			return inputStream;
		} catch (GrailsProcessException e) {
			return null;
		}
	}
}
