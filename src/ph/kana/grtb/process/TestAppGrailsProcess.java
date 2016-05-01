package ph.kana.grtb.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Deprecated
public class TestAppGrailsProcess extends GrailsProcess {
	private boolean includeUnitTest;
	private boolean includeIntegTest;
	private String classNamePattern;

	public boolean isIncludeUnitTest() {
		return includeUnitTest;
	}

	public void setIncludeUnitTest(boolean includeUnitTest) {
		this.includeUnitTest = includeUnitTest;
	}

	public boolean isIncludeIntegTest() {
		return includeIntegTest;
	}

	public void setIncludeIntegTest(boolean includeIntegTest) {
		this.includeIntegTest = includeIntegTest;
	}

	public String getClassNamePattern() {
		return classNamePattern;
	}

	public void setClassNamePattern(String classNamePattern) {
		this.classNamePattern = classNamePattern;
	}

	@Override
	public String[] getArgs() {
		List<String> args = new ArrayList<>();
		
		args.add("test-app");
		if (isIncludeUnitTest()) {
			args.add("-unit");
		}
		if (isIncludeIntegTest()) {
			args.add("-integration");
		}
		List<String> classPatterns = Arrays.asList(getClassNamePattern());
		args.addAll(classPatterns);
		
		return args.toArray(new String[args.size()]);
	}
}
