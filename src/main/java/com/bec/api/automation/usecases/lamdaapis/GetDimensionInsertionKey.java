package com.bec.api.automation.usecases.lamdaapis;

import static org.testng.AssertJUnit.assertTrue;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.response.Response;

public class GetDimensionInsertionKey extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(GetDimensionInsertionKey.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void getDimensionInsertionKeysValidation() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getDimensionInsertionKey.json"));

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "getDimensionInsertionKeys");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), getDimensionInsertionKeysAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			assertTrue(!(responseBody.body().jsonPath().get("classKey").equals(null)));
			assertTrue(!(responseBody.body().jsonPath().get("studentKey").equals(null)));
			assertTrue(!(responseBody.body().jsonPath().get("schoolKey").equals(null)));
			assertTrue(!(responseBody.body().jsonPath().get("teacherKey").equals(null)));
			assertTrue(!(responseBody.body().jsonPath().get("districtKey").equals(null)));

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	@Test(priority = 2)
	private void getDimensionInsertionKeysValidationWithInvaidInput() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getDimensionInsertionKey.json"));
		String value = getRandomValueFromList(inputValues());
		requestpayloadobject.remove(value);

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "getDimensionInsertionKeys");

		responseBody = postServiceResponse(getDimensionInsertionKeysAPIendpoint, provideAuthenticatedHeaders());
		if (400 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}
	}

	private static List<String> inputValues() {
		return Arrays.asList(new String[] { "classId", "districtId", "schoolId", "studentId", "teacherId" });
	}

	public static String getRandomValueFromSet(Set<String> values) {
		return getRandomValueFromList(new ArrayList<String>(values));
	}

	public static String getRandomValueFromList(List<String> values) {
		Collections.shuffle(values);
		return values.get(0);
	}

}
