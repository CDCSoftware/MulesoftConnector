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

public class MakeCallTest extends AbstractTestCase<CDCSoftwareConnector> {

	public MakeCallTest() {
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