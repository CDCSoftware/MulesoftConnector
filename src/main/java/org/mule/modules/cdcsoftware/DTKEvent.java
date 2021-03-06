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
