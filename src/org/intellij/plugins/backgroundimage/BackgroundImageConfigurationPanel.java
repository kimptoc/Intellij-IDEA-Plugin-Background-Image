package org.intellij.plugins.backgroundimage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import org.intellij.plugins.backgroundimage.filechooser.ImageFilter;
import org.intellij.plugins.backgroundimage.filechooser.ImagePreview;

public class BackgroundImageConfigurationPanel extends JComponent
{
  private JTextField fileSystemFilename;
  private ButtonGroup cbxgrpFileType;
  private JRadioButton cbxFileSystem;
  private JRadioButton cbxUrl;
  private JCheckBox cbxEnabled;
  private JButton btnProject;
  private JLabel imageLabel;
  private JSlider visSlider;
  private JCheckBox stretch;
  private static String FILE_SYSTEM_LABEL = "FileSystem";
  private static String URL_LABEL = "URL";
  private JFileChooser fileChooser;

  public BackgroundImageConfigurationPanel()
  {
    setLayout(new GridBagLayout());
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    Insets localInsets = new Insets(5, 5, 5, 0);
    localGridBagConstraints.insets = localInsets;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.fill = 2;
    this.cbxEnabled = new JCheckBox("Enabled", false);
    add(this.cbxEnabled, localGridBagConstraints);
    this.cbxEnabled.addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent paramItemEvent)
      {
        BackgroundImageConfigurationPanel.this.setEnabled(1 == paramItemEvent.getStateChange());
      }
    });
    localGridBagConstraints.gridx += 1;
    add(getStretchCheckBox(), localGridBagConstraints);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy += 1;
    this.imageLabel = new JLabel("Image: ");
    add(this.imageLabel, localGridBagConstraints);
    localGridBagConstraints.gridx += 1;
    localGridBagConstraints.gridwidth = 5;
    this.fileSystemFilename = new JTextField();
    Dimension localDimension1 = new Dimension(200, this.fileSystemFilename.getPreferredSize().height);
    this.fileSystemFilename.setPreferredSize(localDimension1);
    add(this.fileSystemFilename, localGridBagConstraints);
    localGridBagConstraints.insets = localInsets;
    localGridBagConstraints.gridx += 5;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.insets = new Insets(0, 0, 0, 0);
    this.btnProject = new JButton("...");
    Dimension localDimension2 = new Dimension(this.fileSystemFilename.getPreferredSize().height, this.fileSystemFilename.getPreferredSize().height);
    this.btnProject.setPreferredSize(localDimension2);
    this.btnProject.setMinimumSize(localDimension2);
    this.btnProject.setMaximumSize(localDimension2);
    add(this.btnProject, localGridBagConstraints);
    localGridBagConstraints.insets = localInsets;
    this.btnProject.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramActionEvent)
      {
        BackgroundImageConfigurationPanel.this.selectImage();
      }
    });
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy += 1;
    this.cbxgrpFileType = new ButtonGroup();
    this.cbxFileSystem = new JRadioButton(FILE_SYSTEM_LABEL, false);
    this.cbxUrl = new JRadioButton(URL_LABEL, true);
    this.cbxgrpFileType.add(this.cbxFileSystem);
    this.cbxgrpFileType.add(this.cbxUrl);
    add(this.cbxFileSystem, localGridBagConstraints);
    localGridBagConstraints.gridx += 1;
    add(this.cbxUrl, localGridBagConstraints);
    JSlider localJSlider = getVisSlider();
    localGridBagConstraints.gridy += 1;
    localGridBagConstraints.gridx = 0;
    add(new JLabel("Visibility"), localGridBagConstraints);
    localGridBagConstraints.gridx += 1;
    localGridBagConstraints.gridwidth = 9;
    localGridBagConstraints.weightx = 0.9D;
    localGridBagConstraints.weighty = 1.0D;
    add(localJSlider, localGridBagConstraints);
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.gridx = 10;
    localGridBagConstraints.gridy += 1;
    localGridBagConstraints.fill = 1;
    add(new JPanel(), localGridBagConstraints);
  }

  private JCheckBox getStretchCheckBox()
  {
    if (this.stretch == null)
      this.stretch = new JCheckBox("Stretch Image");
    return this.stretch;
  }

  public JSlider getVisSlider()
  {
    if (this.visSlider == null)
    {
      this.visSlider = new JSlider(0, 0, 100, 0);
      this.visSlider.setPaintLabels(true);
      this.visSlider.setPaintTicks(true);
      this.visSlider.setPaintTrack(true);
      this.visSlider.setMajorTickSpacing(20);
      this.visSlider.setMinorTickSpacing(5);
    }
    return this.visSlider;
  }

  public String getFilename()
  {
    return this.fileSystemFilename.getText();
  }

  public void setFilename(String paramString)
  {
    this.fileSystemFilename.setText(paramString);
  }

  public boolean isLocalFile()
  {
    return this.cbxFileSystem.isSelected();
  }

  public void setLocalFile(boolean paramBoolean)
  {
    if (paramBoolean)
      this.cbxFileSystem.setSelected(true);
    else
      this.cbxUrl.setSelected(true);
  }

  public boolean isEnabled()
  {
    return this.cbxEnabled.getModel().isSelected();
  }

  public void setEnabled(boolean paramBoolean)
  {
    this.cbxEnabled.getModel().setSelected(paramBoolean);
    this.fileSystemFilename.setEnabled(paramBoolean);
    this.cbxFileSystem.setEnabled(paramBoolean);
    this.cbxUrl.setEnabled(paramBoolean);
    this.btnProject.setEnabled(paramBoolean);
    this.imageLabel.setEnabled(paramBoolean);
    getVisSlider().setEnabled(paramBoolean);
    getStretchCheckBox().setEnabled(paramBoolean);
  }

  private void selectImage()
  {
    JFileChooser localJFileChooser = getFileChooser();
    int i = localJFileChooser.showDialog(this, "Choose Background");
    if (i == 0)
    {
      File localFile = localJFileChooser.getSelectedFile();
      this.fileSystemFilename.setText(localFile.getPath());
    }
    localJFileChooser.setSelectedFile(null);
  }

  private JFileChooser getFileChooser()
  {
    if (this.fileChooser == null)
    {
      this.fileChooser = new JFileChooser();
      this.fileChooser.addChoosableFileFilter(new ImageFilter());
      this.fileChooser.setAcceptAllFileFilterUsed(false);
      this.fileChooser.setAccessory(new ImagePreview(this.fileChooser));
    }
    return this.fileChooser;
  }

  public double getVisibility()
  {
    return getVisSlider().getValue() / 100.0D;
  }

  public void setStretched(boolean paramBoolean)
  {
    getStretchCheckBox().getModel().setSelected(paramBoolean);
  }

  public boolean isStretched()
  {
    return getStretchCheckBox().isSelected();
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.BackgroundImageConfigurationPanel
 * JD-Core Version:    0.6.0
 */