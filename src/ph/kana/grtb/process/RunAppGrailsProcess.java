package ph.kana.grtb.process;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class RunAppGrailsProcess extends GrailsProcess {
	private String environment;

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public String getEnvironment() {
		return environment;
	}
	
	@Override
	protected String[] getArgs() {
		if ("dev".equals(environment)) {
			return new String[] {"run-app"};
		} else {
			List<String> args = new ArrayList();
			
			if ("prod".equals(environment) || "test".equals(environment)) {
				args.add(environment);
			} else {
				args.add(String.format("-Dgrails.env=%s", environment));
			}
			
			args.add("run-app");
			return args.toArray(new String[args.size()]);
		}
	}
}
