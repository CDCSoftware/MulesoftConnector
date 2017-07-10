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

package org.mule.modules.cdcsoftware.automation.runner;

import java.util.Date;

import org.junit.Test;
import org.mule.modules.cdcsoftware.CDCSoftwareConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class AbstractCDCSoftwareTest extends AbstractTestCase<CDCSoftwareConnector>{

	public static final String TEST_MESSAGE = "test message";
    public static final String USER_ID = "U03NE28RL";
    public static final String GROUP_ID = "G03R7DHNY";
    public static final String CHANNEL_ID = "C03NE28RY";
    public static final String CHANNEL_RENAMING ="C04KG7XAM";
    public static final String CHANNEL_NAME = "random";
    public static final String ESTEBANWASINGER = "estebanwasinger";
    public static final String DM_CHANNEL_ID = "D03NE28RN";

    public AbstractCDCSoftwareTest() {
        super(CDCSoftwareConnector.class);
    }

    protected String getDateString() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }
    @Test
    public void dummySonar(){
    	System.out.print("Make Sonar happy!");
    }
}
