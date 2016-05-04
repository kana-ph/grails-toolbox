package ph.kana.grtb.process;

import ph.kana.grtb.type.RunAppType;

import java.util.ArrayList;
import java.util.List;

public class RunAppGrailsProcess extends GrailsProcess {
	private RunAppType type;
	private String environment;

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public void setType(RunAppType type) {
		this.type = type;
	}
	
	@Override
	protected List<String> getArgs() {
		List<String > args = new ArrayList<>();
		args.add(buildEnvironmentString());
		args.add(type.getCommand());

		return args;
	}

	private String buildEnvironmentString() {
		if (null == environment) {
			return "";
		}
		switch (environment) {
			case "":
			case "dev":
				return "";
			case "prod":
			case "test":
				return environment;
			default:
				return String.format("-Dgrails.env=%s", environment);
		}
	}
}
