package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.VisibleAreaEvent;
import com.intellij.openapi.editor.event.VisibleAreaListener;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class BackgroundImageVisibleAreaListener
  implements VisibleAreaListener
{
  public void visibleAreaChanged(VisibleAreaEvent paramVisibleAreaEvent)
  {
    Editor localEditor = paramVisibleAreaEvent.getEditor();
    Rectangle localRectangle = localEditor.getContentComponent().getVisibleRect();
    Border localBorder = localEditor.getContentComponent().getBorder();
    localBorder.paintBorder(localEditor.getContentComponent(), localEditor.getContentComponent().getGraphics(), localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.BackgroundImageVisibleAreaListener
 * JD-Core Version:    0.6.0
 */