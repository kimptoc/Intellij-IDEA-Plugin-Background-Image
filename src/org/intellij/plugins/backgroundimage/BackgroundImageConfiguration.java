package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.components.ApplicationComponent;
import org.jdom.Element;

/**
 * BackgroundImageConfiguration
 */
public class BackgroundImageConfiguration implements ApplicationComponent, JDOMExternalizable {

    public String getComponentName() {
        return "BackgroundImageConfiguration";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public void writeExternal(Element element) throws WriteExternalException {
        DefaultJDOMExternalizer.writeExternal(this, element);
    }

    public void readExternal(Element element) throws InvalidDataException {
        DefaultJDOMExternalizer.readExternal(this, element);
    }

    public boolean equals(final Object bj) {
        if (this == bj) {
            return true;
        }
        if (!(bj instanceof BackgroundImageConfiguration)) {
            return false;
        }

        final BackgroundImageConfiguration backgroundImageConfiguration = (BackgroundImageConfiguration) bj;

        if (!filename.equalsIgnoreCase(backgroundImageConfiguration.filename)) {
            return false;
        }

        if (localFile != backgroundImageConfiguration.localFile) {
            return false;
        }

        if (enabled != backgroundImageConfiguration.enabled) {
            return false;
        }

        return true;
    }

    public String filename = "";
    public boolean localFile;
    public boolean enabled;
}
