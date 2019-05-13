package com.bec.api.automation.usecases.readinglevelprogress;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import junit.framework.Assert;

/**
 * This class verifies the validation of input payload request data with API
 * response data (External and internal filters values are verified here
 * 
 * @author Gurubai Patil
 *
 */
public class ReadingLevelProgressInputDatatest extends RestAssuredUtil {

	private static Log logger = LogFactory.getLog(ReadingLevelProgressInputDatatest.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateReadingLevelProgressInputDatawithResponse() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(
				new FileReader("src/main/resources/payloads/readinglevelprogress/ReadingLevelProgressInput.json"));

		String readingLevelProgressDataAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "readingLevelProgressData");

		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), readingLevelProgressDataAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info("Success" + responseBody.asString());

			Boolean inpudata = validateinputdatainAPI(responseBody);
			Assert.assertTrue(inpudata);
			logger.info("Validation done of filters data againest APi response data" + inpudata);

		} else {
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}

	}

	/*
	 * Method to validate the filters data in the APi response is matching with
	 * request data
	 */
	public static boolean validateinputdatainAPI(Response responseBody) throws Throwable {
		JSONObject requestpayloadobject = (JSONObject) parser.parse(
				new FileReader("src/main/resources/payloads/readinglevelprogress/ReadingLevelProgressInput.json"));

		JsonPath apiResponseJsonPath = new JsonPath(responseBody.asString());
		JsonPath requestpayloadJsonPath = new JsonPath(requestpayloadobject.toString());
		List<Boolean> results = new ArrayList<Boolean>();

		HashMap<String, Object> actualexternalfilters = requestpayloadJsonPath.get("filters.externalFilter");
		HashMap<String, Object> expectedexternalfilters = apiResponseJsonPath.get("responseData.externalFilter");
		if (!actualexternalfilters.equals(expectedexternalfilters)) {
			results.add(false);
		}
		HashMap<String, Object> actualinternalfilters = requestpayloadJsonPath.get("filters.internalFilter");
		HashMap<String, Object> expectedinternalfilters = apiResponseJsonPath.get("responseData.internalFilter");
		if (!actualinternalfilters.equals(expectedinternalfilters)) {
			results.add(false);
		}
		return results.contains(false) ? false : true;
	}

}
