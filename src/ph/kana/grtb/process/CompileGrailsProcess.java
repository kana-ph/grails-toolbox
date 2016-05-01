package ph.kana.grtb.process;

@Deprecated
public class CompileGrailsProcess extends GrailsProcess {

	@Override
	protected String[] getArgs() {
		return new String[] { "compile" };
	}
}
