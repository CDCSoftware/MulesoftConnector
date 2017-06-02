
package org.mule.modules.cdcsoftware.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>CDCSoftwareConnectorProcessAdapter</code> is a wrapper around {@link CDCSoftwareConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-04-17T10:57:30-04:00", comments = "Build UNNAMED.2793.f49b6c7")
public class CDCSoftwareConnectorProcessAdapter
    extends CDCSoftwareConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<CDCSoftwareConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, CDCSoftwareConnectorCapabilitiesAdapter> getProcessTemplate() {
        final CDCSoftwareConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,CDCSoftwareConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, CDCSoftwareConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, CDCSoftwareConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
