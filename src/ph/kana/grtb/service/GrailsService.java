package ph.kana.grtb.service;

import ph.kana.grtb.process.VersionGrailsProcess;
import ph.kana.grtb.utils.GrailsProcessHolder;

import java.io.InputStream;

public class GrailsService {

	private final GrailsProcessHolder processHolder = GrailsProcessHolder.getInstance();

	public InputStream checkInstallation() {
		InputStream inputStream = processHolder.execute(new VersionGrailsProcess());
		return inputStream;
	}
}
