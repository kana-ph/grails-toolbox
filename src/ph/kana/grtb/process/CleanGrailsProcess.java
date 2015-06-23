package ph.kana.grtb.process;

public class CleanGrailsProcess extends GrailsProcess {
	private boolean cleanAllCommand;

	public boolean isCleanAllCommand() {
		return cleanAllCommand;
	}

	public void setCleanAllCommand(boolean cleanAllCommand) {
		this.cleanAllCommand = cleanAllCommand;
	}

	@Override
	public String[] getArgs() {
		if (isCleanAllCommand()) {
			return new String[] {"clean-all"};
		} else {
			return new String[] {"clean"};
		}
	}
}
