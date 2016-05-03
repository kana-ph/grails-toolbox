package ph.kana.grtb.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RunAppType {
	SERVER("as Server"),
	CONSOLE("as Grails Console"),
	WAR("as WAR Deployment");

	String description;
	RunAppType(String description) {
		this.description = description;
	}

	static public List<String> descriptions() {
		List<RunAppType> instances = Arrays.asList(values());
		return instances.stream()
			.map(RunAppType::getDescription)
			.collect(Collectors.toList());
	}

	public String getDescription() {
		return description;
	}
}
