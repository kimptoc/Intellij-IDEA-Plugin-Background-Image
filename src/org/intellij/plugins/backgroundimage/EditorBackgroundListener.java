package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;

import javax.swing.border.Border;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.FileFilter;
import java.util.*;
import java.awt.Toolkit;

/**
 * EditorBackgroundListener - listens for new editors opening and adds a background image
 */
public class EditorBackgroundListener implements EditorFactoryListener {

    public EditorBackgroundListener(String s, boolean b) {
        this.url = s;
        this.isLocalFile = b;

        try {
            if (isLocalFile) {
                background = getFileBackgroundBorder();
            } else {
                background = new BackgroundBorder(ImageIO.read(new URL(url)));
            }
        } catch (Exception e) {
            Messages.showErrorDialog(e.toString(), "Error loading background image");
        }
    }

    public void editorReleased(EditorFactoryEvent event) {
        event.getEditor().getContentComponent().setBorder(null);
    }

    public void editorCreated(EditorFactoryEvent event) {

        Editor editor = event.getEditor();

        try {
            editor.getContentComponent().setBorder(getBackground());
        } catch (Exception e) {
            Messages.showErrorDialog(e.toString(), "Error setting background image.");
        }
    }

    private Border getBackground() {
        if (null == background) {
            try {
                if (isLocalFile) {
                    background = getFileBackgroundBorder();
                } else {
                    background = new BackgroundBorder(ImageIO.read(new URL(url)));
                }
            } catch (Exception e) {
                Messages.showErrorDialog(e.toString(), "Error loading background image");
            }
        }
        return background;
    }

    private BackgroundBorder getFileBackgroundBorder() throws IOException {
        File file = new File(url);
        if (file.isFile())
        {
            return new BackgroundBorder(ImageIO.read(file));
        }

        // its a directory, so pick a random file...
        List allFiles = getAllFilesIncludingSubdirectories(file);
        File selectedFile = (File) allFiles.get((int) (Math.random()*allFiles.size()));
//        return new BackgroundBorder(ImageIO.read(selectedFile));
        return new BackgroundBorder(Toolkit.getDefaultToolkit().getImage(selectedFile.getAbsolutePath()));
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

    private Border background;
    private String url;
    private boolean isLocalFile;
    /*private Border background = null;
    private String url = "http://i.imdb.com/Photos/Ss/0266543/Nemo102.jpg";
    private boolean isLocalFile = true;*/
}
