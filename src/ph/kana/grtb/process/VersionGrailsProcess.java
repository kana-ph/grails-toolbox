package ph.kana.grtb.process;

@Deprecated
public class VersionGrailsProcess extends GrailsProcess {

	@Override
	protected String[] getArgs() {
		return new String[] { "--version" };
	}
	
}
