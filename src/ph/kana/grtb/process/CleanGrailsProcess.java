package ph.kana.grtb.process;

import java.util.Arrays;
import java.util.List;

public class CleanGrailsProcess extends GrailsProcess {

	private boolean cleanAll;

	@Override
	protected List<String> getArgs() {
		return Arrays.asList(cleanAll? "clean" : "clean-all");
	}

	public void setCleanAll(boolean cleanAll) {
		this.cleanAll = cleanAll;
	}
}
