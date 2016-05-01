package ph.kana.grtb.process;

@Deprecated
public class CustomGrailsProcess extends GrailsProcess {

	private String customCommand;

	public String getCustomCommand() {
		return customCommand;
	}

	public void setCustomCommand(String command) {
		customCommand = command;
	}
	
	@Override
	protected String[] getArgs() {
		return customCommand.split(" ");
	}
	
}
