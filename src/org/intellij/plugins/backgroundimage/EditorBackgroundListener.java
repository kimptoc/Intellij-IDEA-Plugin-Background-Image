package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.ui.Messages;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.border.Border;

public class EditorBackgroundListener
  implements EditorFactoryListener
{
  private Border background;
  private String url;
  private boolean isLocalFile;

  public EditorBackgroundListener(String paramString, boolean paramBoolean)
  {
    this.url = paramString;
    this.isLocalFile = paramBoolean;
    try
    {
      if (this.isLocalFile)
        this.background = getFileBackgroundBorder();
      else
        this.background = new BackgroundBorder(ImageIO.read(new URL(this.url)));
    }
    catch (Exception localException)
    {
      Messages.showErrorDialog(localException.toString(), "Error loading background image");
    }
  }

  public void editorReleased(EditorFactoryEvent paramEditorFactoryEvent)
  {
    paramEditorFactoryEvent.getEditor().getContentComponent().setBorder(null);
  }

  public void editorCreated(EditorFactoryEvent paramEditorFactoryEvent)
  {
    Editor localEditor = paramEditorFactoryEvent.getEditor();
    try
    {
      localEditor.getContentComponent().setBorder(getBackground());
      JViewport localJViewport = (JViewport)localEditor.getContentComponent().getParent();
      localJViewport.setScrollMode(0);
    }
    catch (Exception localException)
    {
      Messages.showErrorDialog(localException.toString(), "Error setting background image.");
    }
  }

  private Border getBackground()
  {
    if (null == this.background)
      try
      {
        if (this.isLocalFile)
          this.background = getFileBackgroundBorder();
        else
          this.background = new BackgroundBorder(ImageIO.read(new URL(this.url)));
      }
      catch (Exception localException)
      {
        Messages.showErrorDialog(localException.toString(), "Error loading background image");
      }
    return this.background;
  }

    private BackgroundBorder getFileBackgroundBorder() throws IOException {
        File file = getFile();

        return new BackgroundBorder(ImageIO.read(file));
    }

    private File getFile() {
        File file = new File(url);
        if (!file.isFile())
        {
            // its a directory, so pick a random file...
            List allFiles = getAllFilesIncludingSubdirectories(file);
            file = (File) allFiles.get((int) (Math.random()*allFiles.size()));
    //        return new BackgroundBorder(ImageIO.read(selectedFile));
        }
        return file;
    }

    private List getAllFilesIncludingSubdirectories(File file) {
        List results = new ArrayList();
        results.addAll(getAllFilesInDirectory(file));

        File[] subdirs = file.listFiles(new FileFilter(){
                    public boolean accept(File pathname) {
                        if (pathname.isDirectory())
                        {
                            return true;
                        }
                        return false;
                    }
                });

        for (int i = 0; i < subdirs.length; i++) {
            File subdir = subdirs[i];
            results.addAll(getAllFilesIncludingSubdirectories(subdir));
        }

        return results;
    }

    private Collection getAllFilesInDirectory(File file) {
        File[] files = file.listFiles(new FileFilter(){
                    public boolean accept(File pathname) {
                        String name = pathname.getName();
                        if (name.toLowerCase().endsWith(".jpg")  ||
                            name.toLowerCase().endsWith(".jpeg") ||
                            name.toLowerCase().endsWith(".gif")  ||
                            name.toLowerCase().endsWith(".png") )
                        {
                            return true;
                        }
                        return false;
                    }
                });

        return Arrays.asList(files);
    }



}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.EditorBackgroundListener
 * JD-Core Version:    0.6.0
 */