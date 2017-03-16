package ph.kana.grtb.type;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
		return Arrays
			.stream(values())
			.map(RunAppType::getDescription)
			.collect(Collectors.toList());
	}

	public static RunAppType findByDescription(String description) {
		return Arrays
			.stream(values())
			.filter(description::equals)
			.findFirst()
			.orElse(null);
	}

	public String getDescription() {
		return description;
	}

	public String getCommand() {
		return command;
	}
}
