package ph.kana.grtb.process;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class ConsoleGrailsProcess extends RunAppGrailsProcess {

	@Override
	protected List<String> getArgs() {
		List<String> args = super.getArgs();
		args.set(1, "console");
		
		return args;
	}
	
}
