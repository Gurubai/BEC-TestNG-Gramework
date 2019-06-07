package com.bec.api.automation.usecases.lamdaapis;

import static org.testng.AssertJUnit.assertTrue;

import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.response.Response;

public class GetDimTestInstanceKey extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(GetDimTestInstanceKey.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validategetDimTestInstanceKeyApiResponse() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getdimtestinstancekey.json"));

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getDimTestInstanceKey");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), getDimensionInsertionKeysAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			assertTrue(!(responseBody.body().jsonPath().get("dimTestInstanceKey").equals(null)));
			assertTrue(responseBody.body().jsonPath().get("message").equals("Successfully Updated/Inserted"));

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	@Test(priority = 2)
	private void validategetDimTestInstanceKeyWithInvaidInput() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getdimtestinstancekey.json"));

		requestpayloadobject.remove("testInstanceId");

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getDimTestInstanceKey");

		responseBody = postServiceResponse(getDimensionInsertionKeysAPIendpoint, provideAuthenticatedHeaders());
		if (400 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}
	}

}
