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
import org.mule.modules.cdcsoftware.DTKEvent;
import org.mule.modules.cdcsoftware.DTKProperties;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class DTKEventTest {


    
	public DTKEventTest() {
		
	}
	
	
	@org.junit.Test
	public void verify() {
		
		DTKProperties prop = new DTKProperties();
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("callId", "100");
		prop.setProperties(properties);
		
		
		DTKEvent dtkEvent = new DTKEvent();
		dtkEvent.setEventName("DTK_EVENT");
		dtkEvent.setProperties(prop);

		Gson gsonBuilder = new GsonBuilder().create();
		String body = gsonBuilder.toJson(dtkEvent);
		assertEquals("{\"m_eventName\":\"DTK_EVENT\",\"m_properties\":{\"Properties\":{\"callId\":\"100\"}}}", body);
		

	}

}