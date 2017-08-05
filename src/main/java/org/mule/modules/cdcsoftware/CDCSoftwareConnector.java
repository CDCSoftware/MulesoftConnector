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

package org.mule.modules.cdcsoftware;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.licensing.RequiresEnterpriseLicense;
import org.mule.api.callback.SourceCallback;
import org.mule.modules.cdcsoftware.config.ConnectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RequiresEnterpriseLicense(allowEval = true)

@Connector(name = "cdc-software", friendlyName = "CDCSoftware",minMuleVersion="3.6")
public class CDCSoftwareConnector {

	public static final int  POOLING_TIMEOUT_MILLISECONDS = 20000;
	public static final int  CONNECTIVITY_TIMEOUT_MILLISECONDS = 1000;
	public static final String GZIP = "gzip";
	public static final String OK = "OK";
	public static final String NO_RESPONSE = "No response";
	public static final String ERROR_ON_REQUEST = "Error on %s exception %s";
	public static final String UTF_8 = "UTF-8";	
	public static final String TEXT_XML_UTF_8 ="text/xml;charset=UTF-8";
	public static final String GZIP_DEFLATE ="gzip,deflate";
	public static final String DTK_SERVICE_TELEPHONY_CALL_MAKE = "DTK_SERVICE_TELEPHONY_CALL_MAKE";
	public static final String DTK_SERVICE_TELEPHONY_ANSWER_CALL ="DTK_SERVICE_TELEPHONY_ANSWER_CALL";
	public static final String DTK_SERVICE_TELEPHONY_HOLD_CALL = "DTK_SERVICE_TELEPHONY_HOLD_CALL";
	public static final String DTK_SERVICE_TELEPHONY_CLEAR_CONNECTION = "DTK_SERVICE_TELEPHONY_CLEAR_CONNECTION";
	
	public static final String DTK_PROPERTY_CALL = "Call";
	public static final String DTK_PROPERTY_AGENT_INSTRUMENT = "AgentInstrument";
	
	public static final String DTK_PROPERTY_TEL_AGENT_ID = "TelAgentId";
	public static final String DTK_PROPERTY_PHONE_NUMBER= "phoneNumber";
	public static final String DTK_PROPERTY_C2C_PHONE_NUMBER = "Click2CallPhoneNumberRequested";
	public static final String DTK_MULE_SHUTDOWN_EVENT = "MULE_SHUTDOWN_EVENT";	
	
	private static final  Logger logger = LoggerFactory.getLogger(CDCSoftwareConnector.class);
	
	private long getEventsIOErrors;

	@Config
	ConnectorConfig config;

	private boolean makeRequest(String event,DTKProperties properties) throws Exception {

		boolean result;

			String body = createJSONDTKEvent(event,properties);

			String postUrl = HTTPHelper.buildPostURL(config.getDomain(),config.getServerId(),config.connectionId());

			String message = HTTPHelper.doHTTP("POST",postUrl, 5, body);
			if (message != null) {
				result = true;
			} else {
				throw new Exception( "HTTP Request for POSt operation to URL:"+postUrl+" and body="+body+" returned: "+NO_RESPONSE);
			}

		return result;

	}
	
	public static String createJSONDTKEvent(String event,DTKProperties properties){

		DTKEvent dtkEvent = new DTKEvent();
		dtkEvent.setEventName(event);
		dtkEvent.setProperties(properties);

		Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		return gsonBuilder.toJson(dtkEvent);
	}
	
    /**
     * releaseCall
     *
     * @param call the callId to answer
     * @param agentInstrument the extension used by agent  
     * @return OK if succesfull or error message
     */
	

	@Processor
	public boolean releaseCall(String call, String agentInstrument) throws Exception{
			DTKProperties properties = new DTKProperties();
			
			if(call==null){
				throw new Exception("DTK_PROPERTY_CALL is required parameter.");
			}
			
			
			properties.getProperties().put(DTK_PROPERTY_CALL, call);
			properties.getProperties().put(DTK_PROPERTY_AGENT_INSTRUMENT, agentInstrument);


		return makeRequest(DTK_SERVICE_TELEPHONY_CLEAR_CONNECTION, properties);
	}


    /**
     * answerCall
     *
     * @param call the callId to answer
     * @param agentInstrument the extension used by agent  
     * @return OK if succesfull or error message
     */
	
	@Processor
	public boolean answerCall(String call, String agentInstrument) throws Exception {
		
			DTKProperties properties = new DTKProperties();
			if(call==null){
				throw new Exception("DTK_PROPERTY_CALL is required parameter.");
			}
			properties.getProperties().put(DTK_PROPERTY_CALL, call);
			properties.getProperties().put(DTK_PROPERTY_AGENT_INSTRUMENT, agentInstrument);
 
			return makeRequest(DTK_SERVICE_TELEPHONY_ANSWER_CALL, properties);
	}
	
		
    /**
     * makeCall
     *
     * @param agentId the agent Id placing the call
     * @param phoneNumber the phone number to dial
     * @return OK if succesfull or error message
     */
	
	@Processor
	public boolean makeCall(String agentId, String phoneNumber) throws Exception{

		DTKProperties properties = new DTKProperties();
		if(agentId==null){
			throw new Exception("DTK_PROPERTY_TEL_AGENT_ID is required parameter.");
		}
		properties.getProperties().put(DTK_PROPERTY_TEL_AGENT_ID, agentId);
		
		if(phoneNumber==null){
			throw new Exception("DTK_PROPERTY_PHONE_NUMBER is required parameter.");
		}
		
		properties.getProperties().put(DTK_PROPERTY_PHONE_NUMBER, phoneNumber);
		properties.getProperties().put(DTK_PROPERTY_C2C_PHONE_NUMBER, phoneNumber);
		
		return makeRequest(DTK_SERVICE_TELEPHONY_CALL_MAKE, properties);


	}
	

	/**
	 * getEvents
	 *
	 * @param callback
	 *            The sourcecallback used to dispatch message to the flow
	 * @throws Exception
	 *             error produced while processing the payload
	 */
	@Source
	public void getEvents(SourceCallback callback) throws Exception {

		this.getEventsIOErrors =0;
		boolean run = true;
		Gson gson= new GsonBuilder().create();
		String getURL =HTTPHelper.buildGetURL(config.getDomain(),config.getServerId(),config.connectionId(),POOLING_TIMEOUT_MILLISECONDS);

		do{
			   String message =null; 
				try{
					message = HTTPHelper.doHTTP("GET",getURL, 25,null);
					this.getEventsIOErrors=0;
					if (message != null) {
						DTKEvent dtkEvent = gson.fromJson(message, DTKEvent.class);
		
						Map<String, Object> properties = new HashMap<String, Object>();
						
						properties.put("event", dtkEvent.getEventName());
						
						if(DTK_MULE_SHUTDOWN_EVENT.equals(dtkEvent.getEventName())){
							run =false;
						}
						
						setMuleProperties(properties,dtkEvent);
		
						callback.process(message, properties);
		
					}
				}catch(Exception e){
					logger.error("Error processing message: "+message,e);
					if(e instanceof IOException){
						this.getEventsIOErrors++;
						if(this.getEventsIOErrors<=5){
							logger.warn("throttlePolling for 1 second retry count:"+getEventsIOErrors);
							throttlePolling(1);
						}else{
							logger.warn("throttlePolling for 30 second retry count:"+getEventsIOErrors);							
							throttlePolling(30);
						}
					}
				}
		}while (run); 

	}
	
	private void throttlePolling(int seconds){
		try{
			Thread.sleep( (seconds * 1000));
			
		}catch(Exception e){
			logger.error("Error trying to throttlePolling",e);
		}
		
	}
	
	private void setMuleProperties(Map<String, Object> properties,DTKEvent dtkEvent){
		
		for (String propertyName : dtkEvent.getProperties().getProperties().keySet()) {
			Object propertyValue = null;
			try{
				propertyValue = dtkEvent.getProperties().getProperties().get(propertyName);
				if(propertyValue!=null){
					properties.put(propertyName,propertyValue);
				}
			}catch(Exception e){
				logger.error("Error processing property name: "+propertyName+" property value:"+propertyValue,e);
			}

		}
		
	}

	public ConnectorConfig getConfig() {
		return config;
	}

	public void setConfig(ConnectorConfig config) {
		this.config = config;
	}



}