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

public class GetDimreadingLevelKey extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(GetDimreadingLevelKey.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validategetDimReadingLevelKeyrKeysApiResponse() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getdimreadinglevelkey.json"));

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getDimReadingLevelKey");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), getDimensionInsertionKeysAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			assertTrue(!(responseBody.body().jsonPath().get("dimReadingLevelKey").equals(null)));
			assertTrue(responseBody.body().jsonPath().get("message").equals("Successfully Updated/Inserted"));

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	@Test(priority = 2)
	private void validateGetDimReadingLevelKeyWithInvaidInput() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/lamdaapis/getdimreadinglevelkey.json"));
		String inputparam = getRandomValueFromList(inputValues());
		requestpayloadobject.remove(inputparam);

		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getDimReadingLevelKey");

		responseBody = postServiceResponse(getDimensionInsertionKeysAPIendpoint, provideAuthenticatedHeaders());
		if (400 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			assertTrue(responseBody.body().jsonPath().get("dimReadingLevelKey")==null);

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}
	}

	private static List<String> inputValues() {
		return Arrays.asList(new String[] { "gradeLevel", "leterLevel", "numberLevel" });
	}

	public static String getRandomValueFromSet(Set<String> values) {
		return getRandomValueFromList(new ArrayList<String>(values));
	}

	public static String getRandomValueFromList(List<String> values) {
		Collections.shuffle(values);
		return values.get(0);
	}

}
