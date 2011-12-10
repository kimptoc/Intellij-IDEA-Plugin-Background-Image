package org.intellij.plugins.backgroundimage.filechooser;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class ImagePreview extends JComponent
  implements PropertyChangeListener
{
  ImageIcon thumbnail = null;
  File file = null;

  public ImagePreview(JFileChooser paramJFileChooser)
  {
    setPreferredSize(new Dimension(100, 50));
    paramJFileChooser.addPropertyChangeListener(this);
  }

  public void loadImage()
  {
    if (this.file == null)
    {
      this.thumbnail = null;
      return;
    }
    ImageIcon localImageIcon = new ImageIcon(this.file.getPath());
    if (localImageIcon != null)
      if (localImageIcon.getIconWidth() > 90)
        this.thumbnail = new ImageIcon(localImageIcon.getImage().getScaledInstance(90, -1, 1));
      else
        this.thumbnail = localImageIcon;
  }

  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
  {
    int i = 0;
    String str = paramPropertyChangeEvent.getPropertyName();
    if ("directoryChanged".equals(str))
    {
      this.file = null;
      i = 1;
    }
    else if ("SelectedFileChangedProperty".equals(str))
    {
      this.file = ((File)paramPropertyChangeEvent.getNewValue());
      i = 1;
    }
    if (i != 0)
    {
      this.thumbnail = null;
      if (isShowing())
      {
        loadImage();
        repaint();
      }
    }
  }

  protected void paintComponent(Graphics paramGraphics)
  {
    if (this.thumbnail == null)
      loadImage();
    if (this.thumbnail != null)
    {
      int i = getWidth() / 2 - this.thumbnail.getIconWidth() / 2;
      int j = getHeight() / 2 - this.thumbnail.getIconHeight() / 2;
      if (j < 0)
        j = 0;
      if (i < 5)
        i = 5;
      this.thumbnail.paintIcon(this, paramGraphics, i, j);
    }
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.filechooser.ImagePreview
 * JD-Core Version:    0.6.0
 */