
package org.mule.modules.cdcsoftware.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.modules.cdcsoftware.automation.functional.AnswerCallIT;
import org.mule.modules.cdcsoftware.automation.functional.CDCSoftwareConnectorIT;
import org.mule.modules.cdcsoftware.automation.functional.DTKEventIT;
import org.mule.modules.cdcsoftware.automation.functional.DTKPropertiesIT;
import org.mule.modules.cdcsoftware.automation.functional.MakeCallIT;
import org.mule.modules.cdcsoftware.automation.functional.ReleaseCallIT;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({

	 AnswerCallIT.class,
	 CDCSoftwareConnectorIT.class,
	 DTKEventIT.class,
	 DTKPropertiesIT.class,
	 MakeCallIT.class,
	 ReleaseCallIT.class })

public class FunctionalTestSuite {

	@BeforeClass
	public static void initialiseSuite() {
		System.setProperty("automation-credential.properties", "automation-credentials.properties");
		ConnectorTestContext.initialize(CDCSoftwareConnector.class);
	}

	@AfterClass
	public static void shutdownSuite() {
		ConnectorTestContext.shutDown();
	}

}
