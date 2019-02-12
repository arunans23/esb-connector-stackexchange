/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.connector.integration.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.wso2.carbon.connector.integration.test.ConnectorTestUtil.TestType;
import static org.wso2.carbon.connector.integration.test.ConnectorTestUtil.HttpVerb;

/**
 * Sample integration test
 */
public class StackExchangeConnectorIntegrationTest extends ConnectorIntegrationTestBase {

    private static final Log LOG = LogFactory.getLog(StackExchangeConnectorIntegrationTest.class);

    private Map<String, String> eiRequestHeadersMap = new HashMap<String, String>();
    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {

        init(ConnectorTestUtil.getConnectorName());

        getApiConfigProperties();

        eiRequestHeadersMap.put("Accept-Charset", "UTF-8");
        eiRequestHeadersMap.put("Content-Type", "application/json");
    }

    /* ======================================= GetMe  ======================================= */

    @Test(groups = {"wso2.ei"})
    public void testGetMeWithMandatory() throws IOException, JSONException {
        RestResponse<JSONObject> r = sendJsonPostRestToEi("getMe", TestType.MANDATORY);
        Assert.assertEquals(r.getHttpStatusCode(), 200);
    }

    @Test(groups = {"wso2.ei"})
    public void testGetMeWithInvalid_BadRequest() throws IOException, JSONException {
        RestResponse<JSONObject> r = sendJsonPostRestToEi("getMe", TestType.INVALID, "bad_request");
        Assert.assertEquals(r.getHttpStatusCode(), 400);
    }

    /* ======================================= addQuestion  ======================================= */

    @Test(groups = {"wso2.ei"})
    public void testAddQuestionWithMandatory() throws IOException, JSONException {
        RestResponse<JSONObject> r = sendJsonPostRestToEi("addQuestion", TestType.MANDATORY);
        Assert.assertEquals(r.getHttpStatusCode(), 200);
    }

    @Test(groups = {"wso2.ei"})
    public void testAddQuestionWithInvalid_BadRequest() throws IOException, JSONException {
        RestResponse<JSONObject> r = sendJsonPostRestToEi("addQuestion", TestType.INVALID, "bad_request");
    }

    /* ======================================= Utils  ======================================= */

    private RestResponse<JSONObject> sendJsonPostRestToEi(String method, TestType type)
            throws IOException, JSONException {
        return sendJsonPostRestToEi(method, type, null);
    }

    private RestResponse<JSONObject> sendJsonPostRestToEi(String method, TestType type, String suffix)
            throws IOException, JSONException {
        eiRequestHeadersMap.put("Action", String.format("urn:%s", method));
        return sendJsonRestRequest(proxyUrl,
                HttpVerb.POST,
                eiRequestHeadersMap,
                ConnectorTestUtil.filename(method, type, suffix));
    }
}