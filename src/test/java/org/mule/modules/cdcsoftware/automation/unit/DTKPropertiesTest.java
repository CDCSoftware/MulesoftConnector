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

import org.mule.modules.cdcsoftware.DTKProperties;



public class DTKPropertiesTest {


    
	public DTKPropertiesTest() {
		
	}
	
	
	@org.junit.Test
	public void verify() {
		DTKProperties prop = new DTKProperties();
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("event", "DTK_EVENT");
		prop.setProperties(properties);
		
		String event  = (String) prop.getProperties().get("event");
		
		assertEquals("DTK_EVENT", event);
		

	}

}