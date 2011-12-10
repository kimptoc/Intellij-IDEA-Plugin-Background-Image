package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.Messages;
import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JViewport;
import javax.swing.border.Border;

public class BackgroundBorder
  implements Border
{
  public static Image image;
  private static final Insets insets = new Insets(0, 0, 0, 0);

  public BackgroundBorder(BufferedImage paramBufferedImage)
  {
    setImage(paramBufferedImage);
  }

  public BackgroundBorder(Image image)
  {
    setImage(image);
  }

  public void paintBorder(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Application localApplication = ApplicationManager.getApplication();
    BackgroundImageConfiguration localBackgroundImageConfiguration = (BackgroundImageConfiguration)localApplication.getComponent(BackgroundImageConfiguration.class);
    if (!localBackgroundImageConfiguration.enabled)
      return;
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    localGraphics2D.setComposite(AlphaComposite.getInstance(3, (float)localBackgroundImageConfiguration.getVisibility()));
    JViewport localJViewport = (JViewport)paramComponent.getParent();
    Dimension localDimension = localJViewport.getSize();
    paramInt1 = localJViewport.getViewRect().x;
    paramInt2 = localJViewport.getViewRect().y;
    localGraphics2D.setClip(paramInt1, paramInt2, (int)localDimension.getWidth(), (int)localDimension.getHeight());
    if (image != null)
      localGraphics2D.drawImage(image, paramInt1, paramInt2, localJViewport);
  }

  public Insets getBorderInsets(Component paramComponent)
  {
    return insets;
  }

  public boolean isBorderOpaque()
  {
    return true;
  }

  public static Image getImage()
  {
    return image;
  }

  public static void setImage()
  {
    Application localApplication = ApplicationManager.getApplication();
    BackgroundImageConfiguration localBackgroundImageConfiguration = (BackgroundImageConfiguration)localApplication.getComponent(BackgroundImageConfiguration.class);
    BufferedImage localBufferedImage = null;
    try
    {
      if (localBackgroundImageConfiguration.localFile)
        localBufferedImage = ImageIO.read(new File(localBackgroundImageConfiguration.filename));
      else
        localBufferedImage = ImageIO.read(new URL(localBackgroundImageConfiguration.filename));
    }
    catch (Exception localException)
    {
      Messages.showErrorDialog(localException.toString(), "Error loading background image");
    }
    setImage(localBufferedImage);
  }

  private static void setImage(Image paramImage)
  {
    Application localApplication = ApplicationManager.getApplication();
    BackgroundImageConfiguration localBackgroundImageConfiguration = (BackgroundImageConfiguration)localApplication.getComponent(BackgroundImageConfiguration.class);
    if (localBackgroundImageConfiguration.isStretched())
    {
      Dimension localDimension = Toolkit.getDefaultToolkit().getScreenSize();
      paramImage = paramImage.getScaledInstance((int)(0.9D * localDimension.getWidth()), (int)(0.9D * localDimension.getHeight()), 4);
    }
    image = paramImage;
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.BackgroundBorder
 * JD-Core Version:    0.6.0
 */