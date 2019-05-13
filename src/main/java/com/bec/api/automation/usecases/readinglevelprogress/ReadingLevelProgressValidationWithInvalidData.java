package com.bec.api.automation.usecases.readinglevelprogress;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.response.Response;

public class ReadingLevelProgressValidationWithInvalidData extends RestAssuredUtil {

	static Response responseBody = null;

	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private static void validateReadingLevelProgressAPiwithoutFilters() throws Throwable {

		JSONParser inputJsonparser = new JSONParser();

		JSONObject inputPayloadrequestObject = (JSONObject) inputJsonparser.parse(
				new FileReader("src/main/resources/payloads/readinglevelprogress/ReadingLevelProgressInput.json"));
		String value = getRandomValueFromList(getanyFilter());
		JSONObject filters = (JSONObject) inputPayloadrequestObject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove(value);

		String readingLevelProgressApiendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "readingLevelProgressData");
		responseBody = postServiceResponse(inputPayloadrequestObject.toString(), readingLevelProgressApiendpoint,
				provideAuthenticatedHeaders());

		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("responseData") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {
			throw new RuntimeException("Failed with : " + responseBody.statusCode());
		}
	}

	private static List<String> getanyFilter() {
		return Arrays.asList(
				new String[] { "assesmentId", "endDate", "startDate", "studentGrade", "studentId", "teacherId" });
	}

	public static String getRandomValueFromSet(Set<String> values) {
		return getRandomValueFromList(new ArrayList<String>(values));
	}

	public static String getRandomValueFromList(List<String> values) {
		Collections.shuffle(values);
		return values.get(0);
	}

}
