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