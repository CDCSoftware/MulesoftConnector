/*
 * About MuleSoft Certified Connectors
 * MuleSoft Certified Connectors are developed by MuleSoft’s partners and developer community. These connectors have been reviewed and certified by MuleSoft. To purchase the [Connector Name] Connector or to receive assistance or support for it, please contact [Partner Name] directly at [Contact Information]. MuleSoft disclaims any support obligation for MuleSoft Certified Connectors.
 * By installing this connector, you consent to MuleSoft sharing your contact information with the developer of this connector so that you can receive more information about it directly from the developer.
 *
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

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

    /**
     * domain URL  (Ex: https://myserver)
     */
    @Configurable
    @Placement(group = "Server settings", tab = "Server")
    private String domain;

    /**
     * serverId (Ex: prod, test)
     */
    @Configurable 
    @Placement(group = "Server settings", tab = "Server")
    private String serverId;


    /**
     * user 
     */
    @Configurable 
    @Placement(group = "Security settings", tab = "Security")
    private String user;

    /**
     * password
     */
    @Configurable 
    @Placement(group = "Security settings", tab = "Security")
    @Password
    private String password;

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


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    

}