package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.options.Configurable;
import java.awt.Toolkit;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSlider;

public class BackgroundImagePlugin
  implements ApplicationComponent, Configurable
{
  private BackgroundImageConfiguration theConfiguration;
  private BackgroundImageConfigurationPanel userInterface;
  private Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/nemo.jpg")));
  private EditorBackgroundListener backgroundListener;

  public String getDisplayName()
  {
    return "Background Image";
  }

  public String getHelpTopic()
  {
    return "plugins.BackgroundImagePlugin";
  }

  public void disposeUIResources()
  {
    this.userInterface = null;
  }

  public Icon getIcon()
  {
    return this.icon;
  }

  public JComponent createComponent()
  {
    if (this.userInterface == null)
    {
      this.userInterface = new BackgroundImageConfigurationPanel();
      reset();
    }
    return this.userInterface;
  }

  public void initComponent()
  {
    Application localApplication = ApplicationManager.getApplication();
    this.theConfiguration = ((BackgroundImageConfiguration)localApplication.getComponent(BackgroundImageConfiguration.class));
    if (this.theConfiguration.filename.equalsIgnoreCase(""))
    {
      this.theConfiguration.filename = "http://i.imdb.com/Photos/Ss/0266543/Nemo102.jpg";
      this.theConfiguration.localFile = false;
    }
    this.backgroundListener = new EditorBackgroundListener(this.theConfiguration.filename, this.theConfiguration.localFile);
    EditorFactory.getInstance().addEditorFactoryListener(this.backgroundListener);
  }

  public boolean isModified()
  {
    int i = 0;
    if ((this.userInterface != null) && (this.theConfiguration != null))
      i = (!this.userInterface.getFilename().equals(this.theConfiguration.filename)) ||
              (this.userInterface.isLocalFile() != this.theConfiguration.localFile) ||
              (this.userInterface.getVisibility() != this.theConfiguration.getVisibility()) ||
              (this.userInterface.isStretched() != this.theConfiguration.isStretched()) ||
              (this.userInterface.isEnabled() != this.theConfiguration.enabled) ? 1 : 0;
    return i == 1;
  }

  public void apply()
  {
    if ((this.userInterface != null) && (this.theConfiguration != null))
    {
      this.theConfiguration.filename = this.userInterface.getFilename();
      this.theConfiguration.localFile = this.userInterface.isLocalFile();
      this.theConfiguration.enabled = this.userInterface.isEnabled();
      this.theConfiguration.setVisibility(this.userInterface.getVisibility());
    }
    if (this.userInterface.getVisibility() != this.theConfiguration.getVisibility())
    {
      this.theConfiguration.setVisibility(this.userInterface.getVisibility());
      BackgroundBorder.setImage();
    }
    if (this.userInterface.getFilename().equals(this.theConfiguration.filename))
    {
      this.theConfiguration.filename = this.userInterface.getFilename();
      BackgroundBorder.setImage();
    }
    if (this.userInterface.isStretched() != this.theConfiguration.isStretched())
    {
      this.theConfiguration.setStretched(this.userInterface.isStretched());
      BackgroundBorder.setImage();
    }
  }

  public void reset()
  {
    if ((this.userInterface != null) && (this.theConfiguration != null))
    {
      this.userInterface.setFilename(this.theConfiguration.filename);
      this.userInterface.setStretched(this.theConfiguration.isStretched());
      this.userInterface.setLocalFile(this.theConfiguration.localFile);
      this.userInterface.setEnabled(this.theConfiguration.enabled);
      this.userInterface.getVisSlider().setValue((int)(this.theConfiguration.getVisibility() * 100.0D));
    }
  }

  public void disposeComponent()
  {
    if (this.theConfiguration.enabled)
      EditorFactory.getInstance().removeEditorFactoryListener(this.backgroundListener);
  }

  public String getComponentName()
  {
    return "BackgroundImagePlugin";
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.BackgroundImagePlugin
 * JD-Core Version:    0.6.0
 */