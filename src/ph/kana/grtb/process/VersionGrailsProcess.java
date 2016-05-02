package ph.kana.grtb.process;

import java.io.File;

public class VersionGrailsProcess extends GrailsProcess {

	@Override
	protected String[] getArgs() {
		return new String[] { "--version" };
	}

	@Override
	public void setProjectDirectory(File file) { }
	
}
