package ph.kana.grtb.process;

public class ConsoleGrailsProcess extends RunAppGrailsProcess {

	@Override
	protected String[] getArgs() {
		String[] args = super.getArgs();
		args[args.length - 1] = "console";
		
		return args;
	}
	
}
