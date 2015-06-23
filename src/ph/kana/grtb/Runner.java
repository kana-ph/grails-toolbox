package ph.kana.grtb;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import ph.kana.grtb.ui.GrailsToolboxFrame;

public class Runner {

	public static void main(String[] args) {
		setSystemLookAndFeel();
		
		showInCenter(new GrailsToolboxFrame());
	}
	
	private static void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace(System.err);
		}
	}
	
	private static void showInCenter(final JFrame frame) {
		frame.setLocationRelativeTo(null);
		EventQueue.invokeLater( new Runnable() {
			@Override
			public void run(){
				frame.setVisible(true);
			}
		});
	}
}
