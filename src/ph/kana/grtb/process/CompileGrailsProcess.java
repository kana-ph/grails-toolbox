package ph.kana.grtb.process;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class CompileGrailsProcess extends GrailsProcess {

	@Override
	protected List<String> getArgs() {
		return Arrays.asList("compile");
	}
}
