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
package muleproject1;

import java.util.Map;
import java.util.HashMap;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

public class CRMSimulator extends AbstractMessageTransformer{
    /**
     * @param args
     */
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

    	String userPhoneNumber =message.getProperty("UserPhoneNumber",PropertyScope.INBOUND).toString();
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	switch (userPhoneNumber) {
			case "3055551234":
				map.put("name", "joe");
				map.put("email", "joe@mycompany.com");
				break;
				
			case "3059725556":
				map.put("name", "Manuel de Anzizu");
				map.put("email", "mdeanzizu@cdcsoftware.com");
				break;
				
	
			default:
				map.put("name", "notfound");
				map.put("email", "notfound");
				
				break;
		}
    	
    	message.addProperties(map, PropertyScope.OUTBOUND);
        return message;
    }
}