package org.intellij.plugins.backgroundimage.filechooser;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter
{
  public boolean accept(File paramFile)
  {
    if (paramFile.isDirectory())
      return true;
    String str = Utils.getExtension(paramFile);
    if (str != null)
      return (str.equals("tiff")) || (str.equals("tif")) || (str.equals("gif")) || (str.equals("jpeg")) || (str.equals("jpg")) || (str.equals("png"));
    return false;
  }

  public String getDescription()
  {
    return "Supported Images";
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.filechooser.ImageFilter
 * JD-Core Version:    0.6.0
 */