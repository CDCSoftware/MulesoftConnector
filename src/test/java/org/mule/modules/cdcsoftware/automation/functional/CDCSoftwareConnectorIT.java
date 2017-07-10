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
import org.mule.modules.cdcsoftware.automation.runner.AbstractCDCSoftwareTest;


public class CDCSoftwareConnectorIT extends AbstractCDCSoftwareTest {
	
    private static final String RETRIEVE_EVENTS_SOURCE = "getEvents";

	private ClientAndServer mockServer;
	
    
    @Before
    public void setUp() throws Throwable{
    	mockServer = startClientAndServer(1080);
        Object[] signature = {null, true};
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
        int x =5;
        if(x==5){
        	
        }
        String event = (String) result.get(0);
        
        if(x==5){
        	
        }
        
        assertEquals(message, event);
    }

    @After
    public void tearDown() throws Throwable{
    	mockServer.stop();
        getDispatcher().shutDownSource(RETRIEVE_EVENTS_SOURCE);
    }	
	
	
//  @org.junit.Test
//  public void testSomeMethod() {
//	  
//	  CDCSoftwareConnector conn = new CDCSoftwareConnector();
//	  ConnectorConfig cfg = new ConnectorConfig();
//	  cfg.setDomain("http://127.0.0.1:1080");
//	  conn.setConfig(cfg);
//	  String url = conn.buildGetURL("test");
//	  assertEquals(url,  "http://127.0.0.1:1080/api/message?destination=topic://null.null.input&readTimeout=20000&clientId=mule_test");
//	  
//	  ConnectorConfig cfg2 =conn.getConfig();
//	  assertEquals(cfg, cfg2);
//	  
//  }
}