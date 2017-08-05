/*
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

package org.mule.modules.cdcsoftware.config;

import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.modules.cdcsoftware.DTKProperties;
import org.mule.modules.cdcsoftware.HTTPHelper;

@ConnectionManagement(friendlyName = "Connection Management", configElementName = "config")
public class ConnectorConfig {
	
	/* Connection Management */
	private boolean connected = false;
	private String clientId = null;
	

	/**
	 * domain URL (Ex: https://myserver)
	 */
	@Configurable

	private String domain;

	/**
	 * serverId (Ex: prod, test)
	 */
	@Configurable

	private String serverId;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}


	@Connect
	@TestConnectivity
	public void connect(@ConnectionKey String username, @Password String password) throws ConnectionException {

		try {

			if (connected == false) {
				clientId = java.util.UUID.randomUUID().toString();
			}

			String postURL = HTTPHelper.buildPostURL(this.domain, this.serverId, clientId);

			String body = CDCSoftwareConnector.createJSONDTKEvent("MULESOFT_TEST_CONNECTIVITY", new DTKProperties());
			String message = HTTPHelper.doHTTP("POST", postURL, 5, body);
			if (message == null) {
				throw new Exception("Could not POST test message");
			} else {
				connected = true;
			}

		} catch (Exception e) {
			throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, e.getMessage(), e.getMessage(), e);
		}
	}

	@Disconnect
	public void disconnect() {
		connected = false;
	}

	@ValidateConnection
	public boolean isConnected() {
		return connected;
	}

	@ConnectionIdentifier
	public String connectionId() {
		return clientId;
	}
}