/**
 * Copyright (C) 2017 CDC Software LLC
 *
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
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;



public class CDCSoftwareConnectorIT extends AbstractCDCSoftwareTest {
	
    private static final String RETRIEVE_EVENTS_SOURCE = "getEvents";

	private ClientAndServer mockServer;
	
    
    @Before
    public void setUp() throws Throwable{
    	mockServer = startClientAndServer(1080);
        Object[] signature = {null, true};
    	new MockServerClient("127.0.0.1", 1080)
        .when(
                request()
                        .withMethod("POST")
                        .withPath("/api/message")
        )
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody("Message sent")
        );
        
        getDispatcher().initializeSource(RETRIEVE_EVENTS_SOURCE, signature);
    }

    @Test
    public void testSource() throws InterruptedException {
    	
    	String message = "{\"m_eventName\":\""+CDCSoftwareConnector.DTK_MULE_SHUTDOWN_EVENT+"\",\"m_properties\":{\"Properties\":{\"callId\":\"100\"}}}";
    	new MockServerClient("127.0.0.1", 1080)
        .when(
                request()
                        .withMethod("GET")
                        .withPath("/api/message")
        )
        .respond(
                response()
                        .withStatusCode(200)
                        .withBody(message)
        );
		    	
    	   	
        List<Object> result = getDispatcher().getSourceMessages(RETRIEVE_EVENTS_SOURCE);
        String event = (String) result.get(0);
        assertEquals(message, event);
    }

    @After
    public void tearDown() throws Throwable{
    	mockServer.stop();
        getDispatcher().shutDownSource(RETRIEVE_EVENTS_SOURCE);
    }	
	
	
}