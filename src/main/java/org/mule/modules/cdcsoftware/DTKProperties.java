package org.mule.modules.cdcsoftware;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;



public class DTKProperties {

	@SerializedName("Properties")
	private Map<String,Object> properties = new HashMap<String,Object>();

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}
