/*
 * About MuleSoft Certified Connectors
 * MuleSoft Certified Connectors are developed by MuleSoft�s partners and developer community. These connectors have been reviewed and certified by MuleSoft. To purchase the [Connector Name] Connector or to receive assistance or support for it, please contact [Partner Name] directly at [Contact Information]. MuleSoft disclaims any support obligation for MuleSoft Certified Connectors.
 * By installing this connector, you consent to MuleSoft sharing your contact information with the developer of this connector so that you can receive more information about it directly from the developer.
 *
 * Copyright (C) 2017 CDC Software LLC
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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



public class DTKEventIT {


    
	public DTKEventIT() {
		
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