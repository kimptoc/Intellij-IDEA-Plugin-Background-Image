package org.intellij.plugins.backgroundimage;


import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.*;

/**
 * <h3>BackgroundImagePlugin</h3>
 */
public class BackgroundImagePlugin implements ApplicationComponent, Configurable {

    private BackgroundImageConfiguration theConfiguration;
    private BackgroundImageConfigurationPanel userInterface;

    public BackgroundImagePlugin() {
        super();
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/nemo.jpg")));
    }

    public String getDisplayName() {
        return "Background Image";
    }

    public String getHelpTopic() {
        return "plugins.BackgroundImagePlugin";
    }

    public void disposeUIResources() {
        userInterface = null;
    }

    public Icon getIcon() {
        return icon;
    }

    public JComponent createComponent() {
        if (userInterface == null) {
            userInterface = new BackgroundImageConfigurationPanel();
            reset();
        }

        return userInterface;
    }

    public void initComponent() {
        Application application = ApplicationManager.getApplication();
        theConfiguration = (BackgroundImageConfiguration) application.getComponent(BackgroundImageConfiguration.class);

        if (theConfiguration.filename.equalsIgnoreCase("")) {
            theConfiguration.filename = "http://i.imdb.com/Photos/Ss/0266543/Nemo102.jpg";
            theConfiguration.localFile = false;
        }

        backgroundListener = new EditorBackgroundListener(theConfiguration.filename, theConfiguration.localFile);

        if (theConfiguration.enabled) {
            EditorFactory.getInstance().addEditorFactoryListener(backgroundListener);
        }
        //visibleAreaChangedListener = new BackgroundImageVisibleAreaListener();
        //EditorFactory.getInstance().getEventMulticaster().addVisibleAreaListener(visibleAreaChangedListener);

    }

    public boolean isModified() {
        boolean flag = false;
        if (userInterface != null && theConfiguration != null) {
            flag = !userInterface.getFilename().equals(theConfiguration.filename) ||
                    userInterface.isLocalFile() != theConfiguration.localFile ||
                    userInterface.isEnabled() != theConfiguration.enabled;
        }
        return flag;
    }

    public void apply() {
        if (userInterface != null && theConfiguration != null) {
            theConfiguration.filename = userInterface.getFilename();
            theConfiguration.localFile = userInterface.isLocalFile();
            theConfiguration.enabled = userInterface.isEnabled();
        }

        if (0 == Messages.showYesNoDialog(DIALOG_MSG, "Warning!", null)) {
            ApplicationManager.getApplication().exit();
        }
    }

    public void reset() {
        if (userInterface != null && theConfiguration != null) {
            userInterface.setFilename(theConfiguration.filename);
            userInterface.setLocalFile(theConfiguration.localFile);
            userInterface.setEnabled(theConfiguration.enabled);
        }
    }

    /**
     * This method is called on plugin disposal.
     */
    public void disposeComponent() {
        if (theConfiguration.enabled) {
            EditorFactory.getInstance().removeEditorFactoryListener(backgroundListener);
            //EditorFactory.getInstance().getEventMulticaster().removeVisibleAreaListener(visibleAreaChangedListener);
        }
    }

    public String getComponentName() {
        return "BackgroundImagePlugin";
    }

    private Icon icon;
    private EditorBackgroundListener backgroundListener;
    //private VisibleAreaListener visibleAreaChangedListener;

    private static String DIALOG_MSG =
            "IDEA must be restarted for changes to take" +
            "\neffect. Would you like to shutdown IDEA?\n";
}
