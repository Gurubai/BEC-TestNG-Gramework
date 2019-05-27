package com.bec.api.automation.usecases.readinglevelhistorydata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

public class ClassReadingHitsoryValidationWithInvaidInputs extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(ClassReadingHitsoryValidationWithInvaidInputs.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateClassHistoryResponseWithoutstartdate() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/classReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("classId");

		String classReadingHistoryApiEndpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"classReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), classReadingHistoryApiEndpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("classReadingHistoryData") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {
			throw new RuntimeException("Failed with : " + responseBody.statusCode());
		}
	}

	@Test(priority = 2)
	private void validateClassHistoryResponseWithoutEndDate() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/classReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("startDate");

		String classreadingHistoryApiEndpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"classReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), classreadingHistoryApiEndpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("classReadingHistoryData") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {
			throw new RuntimeException("Failed with : " + responseBody.statusCode());
		}
	}

	@Test(priority = 3)
	private void validateClassHistoryResponseWithouteClassId() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();
		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/classReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("endDate");

		String classreadingHistoryApiEndpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"classReadingHistoryData");

		// Whenr(
		responseBody = postServiceResponse(requestpayloadobject.toString(), classreadingHistoryApiEndpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("classReadingHistoryData") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {
			throw new RuntimeException("Failed with : " + responseBody.statusCode());
		}
	}

	@Test(priority = 6)
	private void validateReadingHistoryApiResponsewithInvalidClassId() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/classReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.replace("classId", -6);

		String classreadingHistoryApiEndpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"classReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), classreadingHistoryApiEndpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Class Id does not exist :");
			assertTrue(responseBody.jsonPath().getString("classReadingHistoryData") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {

			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}
}
