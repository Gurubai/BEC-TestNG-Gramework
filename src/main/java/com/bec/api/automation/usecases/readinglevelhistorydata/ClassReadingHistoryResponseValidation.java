package com.bec.api.automation.usecases.readinglevelhistorydata;

import static org.testng.AssertJUnit.assertTrue;

import java.io.FileReader;
import java.util.ArrayList;
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

public class ClassReadingHistoryResponseValidation extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(ClassReadingHistoryResponseValidation.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateClassReadingHistoryApiResponse() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/classReadingHistoryDataInput.json"));

		String classlevelreadinghistoryAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"classReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), classlevelreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			boolean flag = validateClasscontentInApiResponse(responseBody);
			assertTrue(flag);
		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	/* method to validate content of the students values should not be null */
	public static boolean validateClasscontentInApiResponse(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> classHistorycontent = jsonPath.get("classReadingHistoryDataDTOS.content");
		ArrayList<String> studentid = jsonPath.get("classReadingHistoryDataDTOS.content.studentId");
		ArrayList<String> letterlevel = jsonPath.get("classReadingHistoryDataDTOS.content.letterLevel");
		ArrayList<String> lastpassage = jsonPath.get("classReadingHistoryDataDTOS.content.lastPassage");
		ArrayList<String> category = jsonPath.get("classReadingHistoryDataDTOS.content.category");
		ArrayList<String> accuracy = jsonPath.get("classReadingHistoryDataDTOS.content.accuracy");
		ArrayList<String> proficiency = jsonPath.get("classReadingHistoryDataDTOS.content.proficiency");
		ArrayList<String> fluency = jsonPath.get("classReadingHistoryDataDTOS.content.fluency");
		ArrayList<String> assignmentDate = jsonPath.get("classReadingHistoryDataDTOS.content.assignmentDate");

		if (!classHistorycontent.isEmpty()) {
			if (studentid.contains(null)) {
				results.add(false);
			}
			if (letterlevel.contains(null)) {
				results.add(false);
			}
			if (lastpassage.contains(null)) {
				results.add(false);
			}
			if (category.contains(null)) {
				results.add(false);
			}
			if (accuracy.contains(null)) {
				results.add(false);
			}
			if (proficiency.contains(null)) {
				results.add(false);
			}
			if (fluency.contains(null)) {
				results.add(false);
			}
			if (assignmentDate.contains(null)) {
				results.add(false);
			}
			return results.contains(false) ? false : true;
		}

		else {
			logger.info("There are no student history data avaliable");
			return true;
		}

	}

}
