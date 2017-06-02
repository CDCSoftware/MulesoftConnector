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