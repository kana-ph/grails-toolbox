package ph.kana.grtb.process;

public class VersionGrailsProcess extends GrailsProcess {

	@Override
	protected String[] getArgs() {
		return new String[] { "--version" };
	}
	
}
