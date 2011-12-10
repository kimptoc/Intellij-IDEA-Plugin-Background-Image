package org.intellij.plugins.backgroundimage;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.util.DefaultJDOMExternalizer;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class BackgroundImageConfiguration
  implements ApplicationComponent, JDOMExternalizable
{
  public String filename = "";
  public boolean localFile;
  public boolean enabled;
  public double visibility = 0.2D;
  public boolean stretched;

  public String getComponentName()
  {
    return "BackgroundImageConfiguration";
  }

  public void initComponent()
  {
  }

  public void disposeComponent()
  {
  }

  public void writeExternal(Element paramElement)
    throws WriteExternalException
  {
    DefaultJDOMExternalizer.writeExternal(this, paramElement);
  }

  public void readExternal(Element paramElement)
    throws InvalidDataException
  {
    DefaultJDOMExternalizer.readExternal(this, paramElement);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof BackgroundImageConfiguration))
      return false;
    BackgroundImageConfiguration localBackgroundImageConfiguration = (BackgroundImageConfiguration)paramObject;
    if (!this.filename.equalsIgnoreCase(localBackgroundImageConfiguration.filename))
      return false;
    if (this.localFile != localBackgroundImageConfiguration.localFile)
      return false;
    if (this.enabled != localBackgroundImageConfiguration.enabled)
      return false;
    return (this.stretched == localBackgroundImageConfiguration.stretched) && (this.visibility == localBackgroundImageConfiguration.visibility);
  }

  public void setVisibility(double paramDouble)
  {
    if (paramDouble > 100.0D)
      paramDouble = 100.0D;
    this.visibility = paramDouble;
  }

  public double getVisibility()
  {
    return this.visibility;
  }

  public void setStretched(boolean paramBoolean)
  {
    this.stretched = paramBoolean;
  }

  public boolean isStretched()
  {
    return this.stretched;
  }
}

/* Location:           C:\Projects\1\
 * Qualified Name:     org.intellij.plugins.backgroundimage.BackgroundImageConfiguration
 * JD-Core Version:    0.6.0
 */