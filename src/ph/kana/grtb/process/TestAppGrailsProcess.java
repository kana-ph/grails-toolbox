package ph.kana.grtb.process;

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
		StringBuilder builder = new StringBuilder("test-app ");
		
		if (isIncludeUnitTest()) {
			builder.append("unit: ");
		}
		if (isIncludeIntegTest()) {
			builder.append("integration: ");
		}
		builder.append(getClassNamePattern());
		
		return builder.toString().split(" ");
	}
}
