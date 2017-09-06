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

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

public class EmailGenerator extends AbstractMessageTransformer{
    /**
     * @param args
     */
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

    	String userPhoneNumber =message.getProperty("UserPhoneNumber",PropertyScope.INBOUND).toString();
    	
    	String callId =message.getProperty("CallId",PropertyScope.INBOUND).toString();
    	
    	String name =message.getProperty("name",PropertyScope.OUTBOUND).toString();
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("Dear: ");
    	sb.append(name);
    	sb.append("\r\n");
    	sb.append("We would like to thank you for your call we received from ");
    	sb.append(userPhoneNumber);
    	sb.append("\r\n");
    	sb.append("Our reference number is Call Id: ");
    	sb.append(callId);
    	
    	
        return sb.toString();
    }
}