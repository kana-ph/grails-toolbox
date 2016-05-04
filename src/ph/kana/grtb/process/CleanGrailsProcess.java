package ph.kana.grtb.process;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class CleanGrailsProcess extends GrailsProcess {
	private boolean cleanAllCommand;

	public boolean isCleanAllCommand() {
		return cleanAllCommand;
	}

	public void setCleanAllCommand(boolean cleanAllCommand) {
		this.cleanAllCommand = cleanAllCommand;
	}

	@Override
	public List<String> getArgs() {
		if (isCleanAllCommand()) {
			return Arrays.asList("clean-all");
		} else {
			return Arrays.asList("clean");
		}
	}
}
