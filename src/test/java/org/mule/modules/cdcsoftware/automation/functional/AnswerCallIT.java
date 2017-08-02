/*
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
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.junit.After;
import org.junit.Before;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;



public class AnswerCallIT extends AbstractTestCase<CDCSoftwareConnector> {


    
	public AnswerCallIT() {
		super(CDCSoftwareConnector.class);
	}
	
	private ClientAndServer mockServer;
	
	@Before
	public void startProxy() {
	    mockServer = startClientAndServer(1080);
	}
	
	@After
	public void stopProxy() {
	    mockServer.stop();
	}
	
	//@Test
	@org.junit.Test
	public void verify() throws Exception {
		
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
		
		

		java.lang.String call = "100";
		java.lang.String agentInstrument = "12345";
		assertEquals(getConnector().answerCall(call, agentInstrument), true);
		
	}

}