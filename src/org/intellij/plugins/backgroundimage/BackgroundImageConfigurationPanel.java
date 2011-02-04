package org.intellij.plugins.backgroundimage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.text.MessageFormat;

/**
 * BackgroundImageConfigurationPanel
 */
public class BackgroundImageConfigurationPanel extends JComponent {

    public BackgroundImageConfigurationPanel() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        Insets defaultInsets = new Insets(5, 5, 5, 0);
        gb.insets = defaultInsets;
        gb.weightx = 0.0D;
        gb.weighty = 0.0D;
        gb.gridx = 0;
        gb.gridy = 0;
        gb.fill = 2;

        cbxEnabled = new Checkbox("Enabled", false);
        this.add(cbxEnabled, gb);
        cbxEnabled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                setEnabled(ItemEvent.SELECTED == event.getStateChange());
            }
        });

        gb.gridx = 0;
        gb.gridy++;
        imageLabel = new JLabel("Image: ");
        this.add(imageLabel, gb);

        gb.gridx++;
        gb.gridwidth = 5;
        fileSystemFilename = new JTextField();
        Dimension fieldPreferredSize = new Dimension(200, fileSystemFilename.getPreferredSize().height);
        fileSystemFilename.setPreferredSize(fieldPreferredSize);
        this.add(fileSystemFilename, gb);

        gb.insets = defaultInsets;
        gb.gridx += 5;
        gb.gridwidth = 1;
        gb.insets = new Insets(0, 0, 0, 0);
        btnProject = new JButton("...");
        Dimension d = new Dimension(fileSystemFilename.getPreferredSize().height, fileSystemFilename.getPreferredSize().height);
        btnProject.setPreferredSize(d);
        btnProject.setMinimumSize(d);
        btnProject.setMaximumSize(d);
        this.add(btnProject, gb);

        gb.insets = defaultInsets;
        btnProject.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                selectImage();
            }

        });

        gb.gridx = 1;
        gb.gridy++;
        cbxgrpFileType = new CheckboxGroup();
        cbxFileSystem = new Checkbox(FILE_SYSTEM_LABEL, false, cbxgrpFileType);
        cbxUrl = new Checkbox(URL_LABEL, true, cbxgrpFileType);
        this.add(cbxFileSystem, gb);
        gb.gridx++;
        this.add(cbxUrl, gb);

        gb.gridx = 10;
        gb.gridy++;
        gb.weightx = 1.0D;
        gb.weighty = 1.0D;
        gb.fill = 1;
        this.add(new JPanel(), gb);

    }

    public String getFilename() {
        return fileSystemFilename.getText();
    }

    public void setFilename(String s) {
        fileSystemFilename.setText(s);
    }

    public boolean isLocalFile() {
        return FILE_SYSTEM_LABEL.equalsIgnoreCase(cbxgrpFileType.getSelectedCheckbox().getLabel());
    }

    public void setLocalFile(boolean b){
        if (b) {
            cbxgrpFileType.setSelectedCheckbox(cbxFileSystem);
        } else {
            cbxgrpFileType.setSelectedCheckbox(cbxUrl);
        }
    }

    public boolean isEnabled() {
        return cbxEnabled.getState();
    }

    public void setEnabled(boolean b) {
        cbxEnabled.setState(b);
        fileSystemFilename.setEnabled(b);
        cbxFileSystem.setEnabled(b);
        cbxUrl.setEnabled(b);
        btnProject.setEnabled(b);
        imageLabel.setEnabled(b);
    }

    private void selectImage() {
        JFileChooser fileChooser = null;
        if (fileSystemFilename.getText() == null || fileSystemFilename.getText().equals("")) {
            fileChooser = new JFileChooser();
        } else {
            fileChooser = new JFileChooser(fileSystemFilename.getText());
        }

        fileChooser.setDialogTitle("Select Image:");
        File fileToOpen = null;
        do {
            int res = fileChooser.showOpenDialog(this);
            if (res != 0) {
                return;
            }
            fileToOpen = fileChooser.getSelectedFile();
            if (!fileToOpen.exists()) {
                JOptionPane.showMessageDialog(this, MessageFormat.format("File {0} does not exist.", new Object[]{
                    fileToOpen.toString()
                }), "File Error", 0);
            } else {
                fileSystemFilename.setText(fileToOpen.getPath());
                return;
            }
        } while (true);
    }

    private JTextField fileSystemFilename;
    private CheckboxGroup cbxgrpFileType;
    private Checkbox cbxFileSystem;
    private Checkbox cbxUrl;
    private Checkbox cbxEnabled;
    private JButton btnProject;
    private JLabel imageLabel;

    private static String FILE_SYSTEM_LABEL = "FileSystem";
    private static String URL_LABEL = "URL";
}
