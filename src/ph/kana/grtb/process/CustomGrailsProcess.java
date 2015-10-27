package ph.kana.grtb.process;

public class CustomGrailsProcess extends GrailsProcess {

	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	protected String[] getArgs() {
		return command.split(" ");
	}
	
}
