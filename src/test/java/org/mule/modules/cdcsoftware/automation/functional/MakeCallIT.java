/*
 * About MuleSoft Certified Connectors
 * MuleSoft Certified Connectors are developed by MuleSoft’s partners and developer community. These connectors have been reviewed and certified by MuleSoft. To purchase the [Connector Name] Connector or to receive assistance or support for it, please contact [Partner Name] directly at [Contact Information]. MuleSoft disclaims any support obligation for MuleSoft Certified Connectors.
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

import static org.junit.Assert.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class MakeCallIT extends AbstractTestCase<CDCSoftwareConnector> {

	public MakeCallIT() {
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
	
	@Test
	public void verify() {
		
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
		
		java.lang.String expected = CDCSoftwareConnector.OK;
		java.lang.String agentId = "100";
		java.lang.String phoneNumber = "8005551234";
		assertEquals(getConnector().makeCall(agentId, phoneNumber), expected);
	}

}