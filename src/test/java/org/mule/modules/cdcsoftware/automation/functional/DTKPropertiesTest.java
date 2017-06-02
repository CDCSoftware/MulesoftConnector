package org.mule.modules.cdcsoftware.automation.functional;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.HashMap;
import java.util.Map;

import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.modules.cdcsoftware.DTKProperties;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;



public class DTKPropertiesTest {


    
	public DTKPropertiesTest() {
		
	}
	
	
	@org.junit.Test
	public void verify() {
		DTKProperties prop = new DTKProperties();
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("event", "DTK_EVENT");
		prop.setProperties(properties);
		
		String event  = (String) prop.getProperties().get("event");
		
		assertEquals("DTK_EVENT", event);
		

	}

}