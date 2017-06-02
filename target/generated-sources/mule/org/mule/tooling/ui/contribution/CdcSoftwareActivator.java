
package org.mule.tooling.ui.contribution;

import javax.annotation.Generated;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-04-17T10:57:30-04:00", comments = "Build UNNAMED.2793.f49b6c7")
public class CdcSoftwareActivator
    extends AbstractUIPlugin
{

    public final static String PLUGIN_ID = "org.mule.tooling.ui.contribution.cdc-software";
    private static org.mule.tooling.ui.contribution.CdcSoftwareActivator plugin;

    public void start(BundleContext context)
        throws Exception
    {
        super.start(context);
        plugin = this;
    }

    public void stop(BundleContext context)
        throws Exception
    {
        plugin = null;
        super.stop(context);
    }

    public static org.mule.tooling.ui.contribution.CdcSoftwareActivator getDefault() {
        return plugin;
    }

}
