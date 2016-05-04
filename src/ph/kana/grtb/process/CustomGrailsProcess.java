package ph.kana.grtb.process;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class CustomGrailsProcess extends GrailsProcess {

	private String customCommand;

	public String getCustomCommand() {
		return customCommand;
	}

	public void setCustomCommand(String command) {
		customCommand = command;
	}
	
	@Override
	protected List<String> getArgs() {
		return Arrays.asList(customCommand);
	}
	
}
