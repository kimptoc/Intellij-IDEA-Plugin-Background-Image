package org.intellij.plugins.backgroundimage.filechooser;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import javax.swing.ImageIcon;

public class Utils
{
  public static final String jpeg = "jpeg";
  public static final String jpg = "jpg";
  public static final String gif = "gif";
  public static final String tiff = "tiff";
  public static final String tif = "tif";
  public static final String png = "png";

  public static String getExtension(File paramFile)
  {
    String str1 = null;
    String str2 = paramFile.getName();
    int i = str2.lastIndexOf('.');
    if ((i > 0) && (i < str2.length() - 1))
      str1 = str2.substring(i + 1).toLowerCase();
    return str1;
  }

  protected static ImageIcon createImageIcon(String paramString)
  {
    URL localURL = Utils.class.getResource(paramString);
    if (localURL != null)
      return new ImageIcon(localURL);
    System.err.println("Couldn't find file: " + paramString);
    return null;
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.filechooser.Utils
 * JD-Core Version:    0.6.0
 */