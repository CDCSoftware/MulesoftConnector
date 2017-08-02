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

package org.mule.modules.cdcsoftware.automation.functional;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class CDCSoftwareTestCases extends
		AbstractTestCase<CDCSoftwareConnector> {

	public CDCSoftwareTestCases() {
		super(CDCSoftwareConnector.class);
	}

	@Before
	public void setup() {
		// TODO
	}

	@After
	public void tearDown() {
		// TODO
	}

	@Test
	public void verify() {
		java.lang.String expected = null;
		java.lang.String friend = null;
		//assertEquals(getConnector().greet(friend), expected);
	}

}