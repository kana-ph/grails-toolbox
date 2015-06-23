package ph.kana.grtb.utils;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

public final class ComponentUtils {

	private ComponentUtils() { }
	
	public static void enableContainer(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableContainer((Container)component, enable);
            }
        }
    }
	
	public static void implementAutoScroll(JTextArea textArea) {
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	public static void refreshComponents(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.revalidate();
			component.repaint();
            if (component instanceof Container) {
                refreshComponents((Container)component);
            }
        }
	}
	
	public static File selectSaveTextFileViaFileChooser(Component parent) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Where to Save Console Contents");
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		if (JFileChooser.APPROVE_OPTION == fileChooser.showSaveDialog(parent)) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}
	
	public static File selectReadDirectoryViaFileChooser(Component parent) {
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Grails Project Location");
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
		
		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(parent)) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}
}
