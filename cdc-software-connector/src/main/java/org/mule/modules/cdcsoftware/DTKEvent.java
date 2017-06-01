package org.mule.modules.cdcsoftware;

import com.google.gson.annotations.SerializedName;

public class DTKEvent {
	@SerializedName("m_eventName")
	private String eventName;
	@SerializedName("m_properties")
	private DTKProperties properties;
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public DTKProperties getProperties() {
		return properties;
	}
	public void setProperties(DTKProperties properties) {
		this.properties = properties;
	}
}
