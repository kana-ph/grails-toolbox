package ph.kana.grtb.service;

import ph.kana.grtb.process.VersionGrailsProcess;

public class GrailsService {

	public void checkInstallation() {
		new VersionGrailsProcess().execute();
	}
}
