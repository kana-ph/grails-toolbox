package ph.kana.grtb.ui;

import java.awt.Frame;
import ph.kana.grtb.utils.ComponentUtils;

public class CustomGrailsCommandDialog extends javax.swing.JDialog {

	private CustomGrailsCommandDialog(Frame parent) {
		super(parent, true);
		initComponents();
		
		setModal(true);
		setLocationRelativeTo(parent);
		
		setupOkAndCancelButtonsForDialog();
	}
	
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        customCommandTextField = new javax.swing.JTextField();
        executeButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Grails Custom Command");
        setAlwaysOnTop(true);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jLabel2.setText("grails");

        executeButton.setText("Execute");
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customCommandTextField)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(executeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addGap(135, 135, 135))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(customCommandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(executeButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	public static String fetchCommand(Frame parent) {
		CustomGrailsCommandDialog dialog = new CustomGrailsCommandDialog(parent);
		dialog.setVisible(true);
		
		return dialog.getCommand();
	}
	
	private String getCommand() {
		return command;
	}
	
	private void setupOkAndCancelButtonsForDialog() {
		ComponentUtils.setupOkAndCancelButtonsForDialog(this, executeButton, cancelButton);
	}
    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        String inputCommand = customCommandTextField.getText();
		if (!inputCommand.isEmpty()) {
			command = inputCommand;
		}
		dispose();
    }//GEN-LAST:event_executeButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        command = null;
		dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed
	
	private String command;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField customCommandTextField;
    private javax.swing.JButton executeButton;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
