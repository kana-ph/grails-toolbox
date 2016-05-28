package ph.kana.grtb.type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RunAppType {
	SERVER("as Server", "run-app"),
	CONSOLE("as Grails Console", "console"),
	WAR("as WAR Deployment", "run-war");

	private String description;
	private String command;

	RunAppType(String description, String command) {
		this.description = description;
		this.command = command;
	}

	public static List<String> descriptions() {
		List<RunAppType> instances = Arrays.asList(values());
		return instances.stream()
			.map(RunAppType::getDescription)
			.collect(Collectors.toList());
	}

	public static RunAppType findByDescription(String description) {
		List<RunAppType> instances = Arrays.asList(values());
		List<RunAppType> findResults = instances.stream()
			.filter((type) -> type.description.equals(description))
			.collect(Collectors.toList());
		return findResults.isEmpty()? null : findResults.get(0);
	}

	public String getDescription() {
		return description;
	}

	public String getCommand() {
		return command;
	}
}
