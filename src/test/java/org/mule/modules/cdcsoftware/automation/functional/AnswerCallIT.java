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

import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.mvel2.ast.AssertNode;
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
		java.lang.String call = "100";
		java.lang.String agentInstrument = "12345";
		assertEquals(getConnector().answerCall(call, agentInstrument), expected);
		
	}

}