
package org.mule.modules.cdcsoftware.generated.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/cdc-software</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-04-17T10:57:30-04:00", comments = "Build UNNAMED.2793.f49b6c7")
public class CdcSoftwareNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(CdcSoftwareNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [cdc-software] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [cdc-software] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new CDCSoftwareConnectorConnectorConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("release-call", new ReleaseCallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("release-call", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("answer-call", new AnswerCallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("answer-call", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("make-call", new MakeCallDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("make-call", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-events", new GetEventsDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-events", "@Source", ex);
        }
    }

}
