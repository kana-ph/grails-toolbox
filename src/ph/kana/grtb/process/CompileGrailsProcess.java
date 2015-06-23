package ph.kana.grtb.process;

public class CompileGrailsProcess extends GrailsProcess {

	@Override
	protected String[] getArgs() {
		return new String[] { "compile" };
	}
}
