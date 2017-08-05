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