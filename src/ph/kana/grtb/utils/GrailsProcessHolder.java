package ph.kana.grtb.utils;

import ph.kana.grtb.exception.GrailsProcessException;
import ph.kana.grtb.process.GrailsProcess;

import java.io.InputStream;

public class GrailsProcessHolder {

	static private GrailsProcessHolder instance = null;

	static public GrailsProcessHolder getInstance() {
		if (null == instance) {
			instance = new GrailsProcessHolder();
		}
		return instance;
	}

	private GrailsProcessHolder() { }

	private GrailsProcess grailsProcess;

	public InputStream execute(GrailsProcess grailsProcess) {
		setGrailsProcess(grailsProcess);
		grailsProcess.execute();

		return grailsProcess.getInputStream();
	}

	private void setGrailsProcess(GrailsProcess grailsProcess) {
		if (null == this.grailsProcess || !this.grailsProcess.isAlive()) {
			this.grailsProcess = grailsProcess;
		} else {
			throw new GrailsProcessException(String.format("Grails command in progress: '%s'", grailsProcess.getCommand()));
		}
	}
}
