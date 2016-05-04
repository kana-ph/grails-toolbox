package ph.kana.grtb.process;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class VersionGrailsProcess extends GrailsProcess {

	@Override
	protected List<String> getArgs() {
		return Arrays.asList("--version");
	}

	@Override
	public void setProjectDirectory(File file) { }
	
}
