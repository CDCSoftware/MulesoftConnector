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

package org.mule.modules.cdcsoftware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.Source;
import org.mule.api.annotations.licensing.RequiresEnterpriseLicense;
import org.mule.api.callback.SourceCallback;
import org.mule.modules.cdcsoftware.config.ConnectorConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RequiresEnterpriseLicense(allowEval = true)
@Connector(name = "cdc-software", friendlyName = "CDCSoftware",minMuleVersion="3.6.2")
public class CDCSoftwareConnector {

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
	

	private static final Log logger = LogFactory.getLog(CDCSoftwareConnector.class);
	
	private long getEventsIOErrors;

	@Config
	ConnectorConfig config;

	private String makeRequest(String event,DTKProperties properties) {

		String result;
		try {

			DTKEvent dtkEvent = new DTKEvent();
			dtkEvent.setEventName(event);
			dtkEvent.setProperties(properties);

			Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
			String body = gsonBuilder.toJson(dtkEvent);

			String postUrl = buildPostURL();

			String message = doHTTP("POST",postUrl, 25, body);
			if (message != null) {
				result = OK;
			} else {
				result = NO_RESPONSE;
			}
		} catch (Exception e) {
			String message = String.format(ERROR_ON_REQUEST, event, e);
			result = message;
			logger.error(message, e);
		}

		return result;

	}
    /**
     * releaseCall
     *
     * @param call the callId to answer
     * @param agentInstrument the extension used by agent  
     * @return OK if succesfull or error message
     */
	

	@Processor
	public String releaseCall(String call, String agentInstrument) {
			DTKProperties properties = new DTKProperties();
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
	public String answerCall(String call, String agentInstrument) {
		
			DTKProperties properties = new DTKProperties();
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
	public String makeCall(String agentId, String phoneNumber) {

		DTKProperties properties = new DTKProperties();
		properties.getProperties().put(DTK_PROPERTY_TEL_AGENT_ID, agentId);
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
		String getURL =buildGetURL(java.util.UUID.randomUUID().toString());

		do{
			   String message =null; 
				try{
					message = doHTTP("GET",getURL, 25,null);
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
			logger.error(e);
		}
		
	}
	
	private void setMuleProperties(Map<String, Object> properties,DTKEvent dtkEvent){
		if(dtkEvent.getProperties()!=null && dtkEvent.getProperties().getProperties().containsKey("InOut")){
			String inOut = dtkEvent.getProperties().getProperties().get("InOut").toString();
			if("Inbound".equals(inOut)){
				properties.put("phone", dtkEvent.getProperties().getProperties().get("From").toString());
			}else{
				properties.put("phone", dtkEvent.getProperties().getProperties().get("To").toString());
			}
		}
		
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

	private String doHTTP(String method,String urlString, int timeout,String body) throws IOException {

		String result = null;

		URL url = new URL(urlString);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setConnectTimeout(timeout * 1000);
		conn.setReadTimeout(timeout * 1000);

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setRequestProperty("Content-Type", TEXT_XML_UTF_8);
		conn.setRequestProperty("Accept-Encoding",GZIP_DEFLATE);

		conn.setRequestMethod(method);
		
		if("POST".equals(method)){
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", Integer.toString(body.getBytes(UTF_8).length));  			
			OutputStream post = conn.getOutputStream();
			post.write(body.getBytes("UTF-8"));
			post.flush();
			post.close();
		}else{
			conn.setDoOutput(false);
		}
		

		int responseCode = conn.getResponseCode();

		if (responseCode == 200) {

			String encoding = conn.getContentEncoding();

			InputStream resultingInputStream;

			if (encoding != null && encoding.equalsIgnoreCase(GZIP)) {

				resultingInputStream = new GZIPInputStream(conn.getInputStream());

			} else {
				resultingInputStream = conn.getInputStream();
			}

			result = getResponseAsString(resultingInputStream);

		}

		return result;
	}


	public static String getResponseAsString(InputStream is) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,UTF_8));
		String line = bufferedReader.readLine();
		while (line != null) {
			inputStringBuilder.append(line);
			line = bufferedReader.readLine();
		}
		return inputStringBuilder.toString();
	}

	public String buildPostURL() {
		// https://cloud.cdc-tek.com/api/message?destination=topic://LENOVO.output
		StringBuilder sb = new StringBuilder();
		sb.append(config.getDomain());
		sb.append("/api/message?destination=topic://");
		sb.append(config.getServerId());
		sb.append(".output");
		return sb.toString();
	}

	public String buildGetURL(String clientId) {
		// "https://cloud.cdc-tek.com/api/message?readTimeout=20000&destination=topic://LENOVO.5002.input&clientId=dtk.out_3bc17590-e6a3-466d-a19d-b70d39449c99"
		StringBuilder sb = new StringBuilder();
		sb.append(config.getDomain());
		sb.append("/api/message?destination=topic://");
		sb.append(config.getServerId());
		sb.append(".global.input");
		sb.append("&readTimeout=20000");
		sb.append("&clientId=mule_");
		sb.append(clientId);

		return sb.toString();
	}

}