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
package org.mule.modules.cdcsoftware.automation.unit;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.mule.modules.cdcsoftware.DTKEvent;
import org.mule.modules.cdcsoftware.DTKProperties;

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