package com.bec.api.automation.usecases.readinglevelhistorydata;

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

import junit.framework.Assert;

public class StudentReadingHistoryResponseValidation extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(StudentReadingHistoryResponseValidation.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateStudentHistoryResponseValidation() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info("Success" + responseBody.asString());

			Boolean inpudata = validatestudentcontenetinApiResponse(responseBody);
			Assert.assertTrue(inpudata);
			logger.info("Validation done of filters data againest APi response data" + inpudata);

		} else {
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}

	}

	/* method to validate content of the students values should not be null */
	public static boolean validatestudentcontenetinApiResponse(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> classHistorycontent = jsonPath.get("studentReadingHistoryData");

		if (!classHistorycontent.isEmpty()) {
			ArrayList<String> studentid = jsonPath.get("studentReadingHistoryData.studentId");
			ArrayList<String> letterlevel = jsonPath.get("studentReadingHistoryData.letterLevel");
			ArrayList<String> lastpassage = jsonPath.get("studentReadingHistoryData.lastPassage");
			ArrayList<String> category = jsonPath.get("studentReadingHistoryData.category");
			ArrayList<String> accuracy = jsonPath.get("studentReadingHistoryData.accuracy");
			ArrayList<String> proficiency = jsonPath.get("studentReadingHistoryData.proficiency");
			ArrayList<String> fluency = jsonPath.get("studentReadingHistoryData.fluency");
			ArrayList<String> assignmentDate = jsonPath.get("studentReadingHistoryData.assignmentDate");
			String startLevel = jsonPath.get("studentReadingLevel.startingLevel");
			String targetLevel = jsonPath.get("studentReadingLevel.readingTarget");

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
			if (startLevel.equals(null)) {
				results.add(false);
			}

			if (targetLevel.equals(null)) {
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
