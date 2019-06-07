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

public class GetDimAssismentTestKey extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(GetDimAssismentTestKey.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validategetDimensionAssismentTesrKeysApiResponse() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getdimassesmenttestkey.json"));

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getDimAssessmentTestKey");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), getDimensionInsertionKeysAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			assertTrue(!(responseBody.body().jsonPath().get("dimAssesmentKey").equals(null)));
			assertTrue(responseBody.body().jsonPath().get("message").equals("Successfully Updated/Inserted"));

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	@Test(priority = 2)
	private void validateGetDimensionInsertionKeysWithInvaidInput() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getdimassesmenttestkey.json"));

		requestpayloadobject.remove("assessmentTestId");

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getDimAssessmentTestKey");

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
