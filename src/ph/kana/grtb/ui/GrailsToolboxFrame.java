package ph.kana.grtb.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import ph.kana.grtb.process.CleanGrailsProcess;
import ph.kana.grtb.process.CompileGrailsProcess;
import ph.kana.grtb.process.ConsoleGrailsProcess;
import ph.kana.grtb.process.GrailsProcess;
import ph.kana.grtb.process.RunAppGrailsProcess;
import ph.kana.grtb.process.TestAppGrailsProcess;
import ph.kana.grtb.process.VersionGrailsProcess;
import ph.kana.grtb.utils.ComponentUtils;
import ph.kana.grtb.utils.IOUtils;

public class GrailsToolboxFrame extends javax.swing.JFrame {
	
	public GrailsToolboxFrame() {
		initComponents();
		ComponentUtils.implementAutoScroll(consoleTextArea);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolboxPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        runAppButton = new javax.swing.JButton();
        runAppEnviroComboBox = new javax.swing.JComboBox();
        useConsoleCheckbox = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        testAppButton = new javax.swing.JButton();
        testAppUnitCheckBox = new javax.swing.JCheckBox();
        testAppIntegCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        testAppPatternTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        compileButton = new javax.swing.JButton();
        cleanAllButton = new javax.swing.JButton();
        cleanButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        killProcessButton = new javax.swing.JButton();
        consoleScrollPane = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        progressBar = new javax.swing.JProgressBar();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        useStacktraceCheckbox = new javax.swing.JCheckBoxMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenu5 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Grails Toolbox");
        setMinimumSize(new java.awt.Dimension(830, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        toolboxPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        toolboxPanel.setMinimumSize(new java.awt.Dimension(200, 580));
        toolboxPanel.setPreferredSize(new java.awt.Dimension(200, 580));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Run App"));

        runAppButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ph/kana/grtb/ui/icon/Running Rabbit-32.png"))); // NOI18N
        runAppButton.setText("Run App");
        runAppButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        runAppButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        runAppButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runAppButtonActionPerformed(evt);
            }
        });

        runAppEnviroComboBox.setEditable(true);
        runAppEnviroComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "dev", "prod", "test" }));

        useConsoleCheckbox.setText("Use Console");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(runAppButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(runAppEnviroComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(useConsoleCheckbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(runAppButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useConsoleCheckbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(runAppEnviroComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Test App"));

        testAppButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ph/kana/grtb/ui/icon/Test Tube-32.png"))); // NOI18N
        testAppButton.setText("Test App");
        testAppButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        testAppButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        testAppButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testAppButtonActionPerformed(evt);
            }
        });

        testAppUnitCheckBox.setSelected(true);
        testAppUnitCheckBox.setText("Unit Test");

        testAppIntegCheckBox.setText("Integration Test");

        jLabel1.setText("Classnames Pattern");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton1.setText("x");
        jButton1.setToolTipText("");
        jButton1.setIconTextGap(0);
        jButton1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(testAppUnitCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(testAppIntegCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
            .addComponent(testAppButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(testAppPatternTextField)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(testAppButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testAppUnitCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testAppIntegCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(6, 6, 6)
                .addComponent(testAppPatternTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Compile / Clean"));
        jPanel5.setToolTipText("");

        compileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ph/kana/grtb/ui/icon/Robot-25.png"))); // NOI18N
        compileButton.setText("Compile");
        compileButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        compileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compileButtonActionPerformed(evt);
            }
        });

        cleanAllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ph/kana/grtb/ui/icon/Mushroom Cloud-25.png"))); // NOI18N
        cleanAllButton.setText("Clean All");
        cleanAllButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cleanAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanAllButtonActionPerformed(evt);
            }
        });

        cleanButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ph/kana/grtb/ui/icon/Broom-25.png"))); // NOI18N
        cleanButton.setText("Clean");
        cleanButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cleanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cleanButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cleanAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(compileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(compileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(cleanButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cleanAllButton))
        );

        killProcessButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ph/kana/grtb/ui/icon/Horror-25.png"))); // NOI18N
        killProcessButton.setText("Kill Process");
        killProcessButton.setEnabled(false);
        killProcessButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        killProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                killProcessButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toolboxPanelLayout = new javax.swing.GroupLayout(toolboxPanel);
        toolboxPanel.setLayout(toolboxPanelLayout);
        toolboxPanelLayout.setHorizontalGroup(
            toolboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolboxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(killProcessButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        toolboxPanelLayout.setVerticalGroup(
            toolboxPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolboxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(killProcessButton)
                .addContainerGap())
        );

        consoleTextArea.setEditable(false);
        consoleTextArea.setBackground(new java.awt.Color(0, 0, 0));
        consoleTextArea.setColumns(20);
        consoleTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        consoleTextArea.setForeground(new java.awt.Color(255, 225, 225));
        consoleTextArea.setRows(5);
        consoleScrollPane.setViewportView(consoleTextArea);

        progressBar.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        progressBar.setToolTipText("");
        progressBar.setFocusable(false);
        progressBar.setRequestFocusEnabled(false);
        progressBar.setString("");
        progressBar.setStringPainted(true);

        jMenu1.setMnemonic('F');
        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setMnemonic('O');
        jMenuItem1.setText("Open Project");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        menuBar.add(jMenu1);

        jMenu4.setMnemonic('C');
        jMenu4.setText("Console");
        jMenu4.setToolTipText("");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setMnemonic('S');
        jMenuItem2.setText("Save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setMnemonic('l');
        jMenuItem5.setText("Clear");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        menuBar.add(jMenu4);

        jMenu3.setMnemonic('G');
        jMenu3.setText("Grails");
        jMenu3.setToolTipText("");

        useStacktraceCheckbox.setText("Use '--stacktrace'");
        useStacktraceCheckbox.setToolTipText("");
        jMenu3.add(useStacktraceCheckbox);
        jMenu3.add(jSeparator3);

        jMenu5.setText("Generate");
        jMenu5.setEnabled(false);
        jMenu3.add(jMenu5);

        menuBar.add(jMenu3);

        jMenu2.setMnemonic('H');
        jMenu2.setText("Help");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem3.setMnemonic('A');
        jMenuItem3.setText("About");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(toolboxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(consoleScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(consoleScrollPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(toolboxPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
	
	private File grailsProjectDirectory;
	private GrailsProcess currentProcess;
	
	private void enableComponents(boolean enabled) {
		ComponentUtils.enableContainer(toolboxPanel, enabled);
		
		killProcessButton.setEnabled(!enabled);
		progressBar.setIndeterminate(!enabled);
	}
	
	private void executeGrailsProcess(GrailsProcess grailsProcess) throws InterruptedException, IOException {
		currentProcess = grailsProcess;
		currentProcess.execute();
		IOUtils.reflectStreamToTextArea(currentProcess.getInputStream(), consoleTextArea);
	}
	
	private void executeGrailsProcessAsBackground(final GrailsProcess grailsProcess) {
		grailsProcess.setGrailsProjectDirectrory(grailsProjectDirectory);
		grailsProcess.setStacktraceMode(useStacktraceCheckbox.isSelected());
		
		consoleTextArea.setText("");
		enableComponents(false);
		progressBar.setString(grailsProcess.getCommandString());
		
		SwingWorker worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				try {
					executeGrailsProcess(grailsProcess);
				} catch (IOException e) {
					handleException(e);
				}
				return null;
			}
		};
		worker.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
				if ("DONE".equals(propertyChangeEvent.getNewValue().toString())) {
					IOUtils.logExit(currentProcess);
					
					enableComponents(true);
					progressBar.setString("");
					currentProcess = null;
				}
			}
		});
		worker.execute();
	}
	
	private void initializeGrailsProjectDirectory() {
		try {
			File newDirectory = fetchGrailsDirectory();
			if (newDirectory != null) {
				grailsProjectDirectory = newDirectory;
				setTitle(String.format("Grails Toolbox: [%s]", grailsProjectDirectory.getAbsolutePath()));
				IOUtils.logProjectChange(newDirectory);
			} else {
				dispose();
			}
		} catch (IOException e) {
			handleException(e);
			dispose();
		}
	}
	private void setGrailsProjectDirectory() {
		try {
			File newDirectory = ComponentUtils.selectReadDirectoryViaFileChooser(this);
			if (newDirectory != null) {
				grailsProjectDirectory = newDirectory;
				IOUtils.saveCurrentProject(newDirectory);
				setTitle(String.format("Grails Toolbox: [%s]", grailsProjectDirectory.getAbsolutePath()));
				IOUtils.logProjectChange(newDirectory);
			}
		} catch (IOException e) {
			handleException(e);
		}
	}
	
	private File fetchGrailsDirectory() throws IOException {
		File directory = IOUtils.fetchPreviousProject();

		if (directory == null) {
			directory = ComponentUtils.selectReadDirectoryViaFileChooser(this);
			IOUtils.saveCurrentProject(directory);
		}
		return directory;
	}
	
	private boolean grailsIsInstalled() throws Exception {
		try {
			executeGrailsProcess(new VersionGrailsProcess());
			return true;
		} catch (IOException e) {
			handleException(e);
			return false;
		}
	}
	
	private void handleException(Exception exception) {
		exception.printStackTrace(System.err);
		ExceptionDialog.showDialog(this, exception);
		try {
			currentProcess.stop();
		} catch (IOException ex) { }
	}
	
    private void runAppButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runAppButtonActionPerformed
        String environment = runAppEnviroComboBox.getSelectedItem().toString();
		
		RunAppGrailsProcess runApp;
		if (useConsoleCheckbox.isSelected()) {
			runApp = new ConsoleGrailsProcess();
		} else {
			runApp = new RunAppGrailsProcess();
		}
		runApp.setEnvironment(environment);

		executeGrailsProcessAsBackground(runApp);
    }//GEN-LAST:event_runAppButtonActionPerformed

    private void testAppButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testAppButtonActionPerformed
        boolean withUnitTest = testAppUnitCheckBox.isSelected();
		boolean withIntegTest = testAppIntegCheckBox.isSelected();
		String classPattern = testAppPatternTextField.getText();
		
		TestAppGrailsProcess testApp = new TestAppGrailsProcess();
		testApp.setIncludeUnitTest(withUnitTest);
		testApp.setIncludeIntegTest(withIntegTest);
		testApp.setClassNamePattern(classPattern);
		
		executeGrailsProcessAsBackground(testApp);
    }//GEN-LAST:event_testAppButtonActionPerformed

    private void cleanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanButtonActionPerformed
        CleanGrailsProcess clean = new CleanGrailsProcess();
		clean.setCleanAllCommand(false);
		
		executeGrailsProcessAsBackground(clean);
    }//GEN-LAST:event_cleanButtonActionPerformed

    private void cleanAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanAllButtonActionPerformed
        CleanGrailsProcess cleanAll = new CleanGrailsProcess();
		cleanAll.setCleanAllCommand(true);
		
		executeGrailsProcessAsBackground(cleanAll);
    }//GEN-LAST:event_cleanAllButtonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
		try {
			if (grailsIsInstalled()) {
				initializeGrailsProjectDirectory();
			} else {
				JOptionPane.showMessageDialog(this, "FATAL: Grails is not installed!\nGrails Toolbox will close.", "Grails Toolbox Error", JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		} catch (Exception e) {
			handleException(e);
		}
    }//GEN-LAST:event_formWindowOpened

    private void killProcessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_killProcessButtonActionPerformed
		try {
			currentProcess.stop();
		} catch (IOException ex) {
			handleException(ex);
		}
    }//GEN-LAST:event_killProcessButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        setGrailsProjectDirectory();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
		File target = ComponentUtils.selectSaveTextFileViaFileChooser(this);
		if (target != null) {
			try {
				IOUtils.printToFile(consoleTextArea.getText(), target);
			} catch (IOException ex) {
				handleException(ex);
			}
		}
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void compileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compileButtonActionPerformed
        CompileGrailsProcess compile = new CompileGrailsProcess();
		compile.setGrailsProjectDirectrory(grailsProjectDirectory);
		
		executeGrailsProcessAsBackground(compile);
    }//GEN-LAST:event_compileButtonActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        AboutDialog aboutDialog = new AboutDialog(this, true);
		aboutDialog.setLocationRelativeTo(this);
		aboutDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        ComponentUtils.refreshComponents(this);
    }//GEN-LAST:event_formWindowActivated

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        consoleTextArea.setText(null);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        testAppPatternTextField.setText(null);
		testAppPatternTextField.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cleanAllButton;
    private javax.swing.JButton cleanButton;
    private javax.swing.JButton compileButton;
    private javax.swing.JScrollPane consoleScrollPane;
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JButton killProcessButton;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton runAppButton;
    private javax.swing.JComboBox runAppEnviroComboBox;
    private javax.swing.JButton testAppButton;
    private javax.swing.JCheckBox testAppIntegCheckBox;
    private javax.swing.JTextField testAppPatternTextField;
    private javax.swing.JCheckBox testAppUnitCheckBox;
    private javax.swing.JPanel toolboxPanel;
    private javax.swing.JCheckBox useConsoleCheckbox;
    private javax.swing.JCheckBoxMenuItem useStacktraceCheckbox;
    // End of variables declaration//GEN-END:variables
}
