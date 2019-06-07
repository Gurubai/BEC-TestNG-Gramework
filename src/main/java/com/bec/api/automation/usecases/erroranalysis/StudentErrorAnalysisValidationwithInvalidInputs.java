package com.bec.api.automation.usecases.erroranalysis;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.response.Response;

public class StudentErrorAnalysisValidationwithInvalidInputs extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(StudentErrorAnalysisValidationwithInvalidInputs.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateStudentLvelerrorAnalysisResponseWithInvalidInput() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/erroranalysis/studenterroranalysisinputjson.json"));
		String inputparam = getRandomValueFromList(getAnyFilter());
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove(inputparam);

		String studentLeveleErrorAnalysisApiEndpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"studentLevelErrorAnalsysisData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentLeveleErrorAnalysisApiEndpoint,
				provideAuthenticatedHeaders());
		if (500 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			assertEquals(responseBody.jsonPath().getString("errorMessage"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("responseData") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);
			

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	private static List<String> getAnyFilter() {
		return Arrays.asList(new String[] { "endDate", "startDate", "studentId", "teacherId" });
	}

	public static String getRandomValueFromSet(Set<String> values) {
		return getRandomValueFromList(new ArrayList<String>(values));
	}

	public static String getRandomValueFromList(List<String> values) {
		Collections.shuffle(values);
		return values.get(0);
	}

}
