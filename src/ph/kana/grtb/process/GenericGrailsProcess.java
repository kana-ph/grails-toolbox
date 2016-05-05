package ph.kana.grtb.process;

import java.util.Arrays;
import java.util.List;

public class GenericGrailsProcess extends GrailsProcess {

	private String customCommand;

	public GenericGrailsProcess(String command) {
		customCommand = command;
	}
	
	@Override
	protected List<String> getArgs() {
		return Arrays.asList(customCommand);
	}
	
}
