package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.editor.event.VisibleAreaListener;
import com.intellij.openapi.editor.event.VisibleAreaEvent;
import com.intellij.openapi.editor.Editor;

import javax.swing.border.Border;
import java.awt.*;

/**
 * BackgroundImageVisibleAreaListener
 */
public class BackgroundImageVisibleAreaListener implements VisibleAreaListener {
    public void visibleAreaChanged(VisibleAreaEvent event) {
        Rectangle rect = event.getNewRectangle();
        Editor editor = event.getEditor();
        Border border = editor.getContentComponent().getBorder();
        border.paintBorder(editor.getContentComponent(),
                editor.getContentComponent().getGraphics(),
                rect.x,
                rect.y,
                rect.width,
                rect.height);
    }

}
