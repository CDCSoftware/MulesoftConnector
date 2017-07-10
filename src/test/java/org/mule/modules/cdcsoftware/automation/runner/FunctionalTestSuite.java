/*
package org.mule.modules.cdcsoftware.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.modules.cdcsoftware.automation.functional.AnswerCallTest;
import org.mule.modules.cdcsoftware.automation.functional.MakeCallTest;
import org.mule.modules.cdcsoftware.automation.functional.ReleaseCallTest;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({

	 AnswerCallTest.class,MakeCallTest.class,ReleaseCallTest.class })

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
*/