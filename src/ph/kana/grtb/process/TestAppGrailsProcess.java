package ph.kana.grtb.process;

import java.util.ArrayList;
import java.util.List;

public class TestAppGrailsProcess extends GrailsProcess {
	private boolean flagUnit;
	private boolean flagIntegration;
	private String classNamePattern;

	public void setFlagUnit(boolean flagUnit) {
		this.flagUnit = flagUnit;
	}

	public void setFlagIntegration(boolean flagIntegration) {
		this.flagIntegration = flagIntegration;
	}

	public void setClassNamePattern(String classNamePattern) {
		this.classNamePattern = classNamePattern;
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();
		
		args.add("test-app");
		if (flagUnit) {
			args.add("-unit");
		}
		if (flagIntegration) {
			args.add("-integration");
		}
		args.add(classNamePattern);
		
		return args;
	}
}
