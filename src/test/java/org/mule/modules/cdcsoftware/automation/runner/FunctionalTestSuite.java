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
