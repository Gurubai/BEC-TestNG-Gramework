package com.bec.api.automation.usecases.readinglevelprogress;

import static org.testng.Assert.assertTrue;
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

/**
 * This is happy scenario to cover end to end validation of Data for api
 * response
 * 
 * @author Gurubai Patil
 *
 */

@Test
public class RedingLevelProgressResponseTestData extends RestAssuredUtil {

	private static Log logger = LogFactory.getLog(RedingLevelProgressResponseTestData.class);

	static Response responseBody = null;

	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private static void validateReadingLevelProgressApiResponseData() throws Throwable {

		JSONParser inputJsonparser = new JSONParser();

		JSONObject inputPayloadrequestObject = (JSONObject) inputJsonparser.parse(
				new FileReader("src/main/resources/payloads/readinglevelprogress/ReadingLevelProgressInput.json"));

		String readingLevelProgressApiendpoint = RestAssuredUtil
				.generateApiEndPoint("DevRPLServiceApiUrl", "readingLevelProgressData");
		responseBody = postServiceResponse(inputPayloadrequestObject.toString(), readingLevelProgressApiendpoint,
				provideAuthenticatedHeaders());

		if (200 == responseBody.statusCode()) {
			logger.info("Success" + responseBody.asString());
			Boolean readinglevelaxisvalidation = validatereadinglevelaxisvalues(responseBody);
			assertTrue(readinglevelaxisvalidation, "readinglevelaxisvalidation have null values");

			logger.info("Validated readinglevelaxis" + readinglevelaxisvalidation);
			Boolean fluencyvalue = validateFluencyaxis(responseBody);
			assertTrue(fluencyvalue, "fluencyvalue have null values");
			logger.info("Validated fluencyvalues" + fluencyvalue);
			Boolean accuracyvalue = validateaccuracyaxis(responseBody);
			assertTrue(accuracyvalue, "accuracyvalue have null values");
			logger.info("Validated accuracyvalues" + accuracyvalue);
			Boolean daterangeaxisvalue = validatedateRangeaxis(responseBody);
			assertTrue(daterangeaxisvalue, "daterangeaxisvalue have null values");
			logger.info("Validated daterangeaxis" + daterangeaxisvalue);
			Boolean assignmentdata = validateassignmentdata(responseBody);
			assertTrue(assignmentdata, "assignmentdata have null values");
			logger.info("Validated assignmentdata" + assignmentdata);
			Boolean fluencyData = validateFluencydata(responseBody);
			logger.info("Validated fluencydata" + fluencyData);
			assertTrue(fluencyData, "fluencyData have null values");
			Boolean accuracyData = validateAccuracydata(responseBody);
			assertTrue(accuracyData, "accuracyData have null values");
			logger.info("Validated fluencydata" + accuracyData);

		} else {
			logger.error("Failed to load reading level progress api response=" + responseBody.asString());
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	/* method to validate reading level axis values should not be null */
	public static boolean validatereadinglevelaxisvalues(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> readinglevelaxisdata = jsonPath.get("responseData.readingLevelAxis");
		ArrayList<String> LevelNames = jsonPath.get("responseData.readingLevelAxis.levelName");
		ArrayList<String> levelbgcolors = jsonPath.get("responseData.readingLevelAxis.levelBgColor");
		ArrayList<String> levelaxiscolors = jsonPath.get("responseData.readingLevelAxis.levelAxisColor");
		ArrayList<String> levelHoverColor = jsonPath.get("responseData.readingLevelAxis.levelHoverColor");

		if (!readinglevelaxisdata.isEmpty()) {
			if (LevelNames.contains(null)) {
				results.add(false);
			}

			if (levelbgcolors.contains(null)) {
				results.add(false);
			}

			if (levelaxiscolors.contains(null)) {
				results.add(false);
			}
			if (levelHoverColor.contains(null)) {
				results.add(false);
			}

			return results.contains(false) ? false : true;
		}

		else {
			logger.info("There are no reading level axis data");
			return true;
		}

	}

	/* method to validate Fluency values should not be null */
	public static boolean validateFluencyaxis(Response responseBody) {
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> fluencyaxis = jsonPath.get("responseData.fluencyAxis");
		if (!fluencyaxis.isEmpty()) {
			ArrayList<String> fluencyvalues = jsonPath.get("responseData.fluencyAxis.value");
			if (fluencyvalues.contains(null)) {
				return false;
			}
		} else {
			logger.info("There are no fluency level axis data");
		}
		return true;
	}

	/* method to validate accuracy values should not be null */
	public static boolean validateaccuracyaxis(Response responseBody) {
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> accuracyaxisvalues = jsonPath.get("responseData.accuracyAxis.value");
		if (accuracyaxisvalues.contains(null)) {
			return false;
		}
		return true;
	}

	/* method to validate date range axis values should not be null */
	public static boolean validatedateRangeaxis(Response responseBody) {
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> dateRangeaxis = jsonPath.get("responseData.dateRangeAxis");
		if (!dateRangeaxis.isEmpty()) {
			ArrayList<String> dateRangeaxisvalues = jsonPath.get("responseData.dateRangeAxis.assignmentDate");
			if (dateRangeaxisvalues.contains(null)) {
				return false;
			}
		} else {
			logger.info("There are no date range axis data");
			return true;
		}
		return true;

	}

	/*
	 * method to validate assignmnetsData if there are assignments, assignment
	 * values should not be null
	 */
	public static boolean validateassignmentdata(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> assignments = jsonPath.get("responseData.assignmentDataList");
		assignments.remove(0);
		assignments.remove(assignments.size() - 1);
		if (!assignments.isEmpty()) {
			ArrayList<String> assignmentdate = jsonPath.get("responseData.assignmentDataList.assignmentDate");
			ArrayList<String> readinglevel = jsonPath.get("responseData.assignmentDataList.numberLevel");
			ArrayList<String> fluency = jsonPath.get("responseData.assignmentDataList.fluency");
			ArrayList<String> accuracy = jsonPath.get("responseData.assignmentDataList.accuracy");
			ArrayList<String> proficiency = jsonPath.get("responseData.assignmentDataList.proficiency");
			ArrayList<String> assignmentvalue = jsonPath.get("responseData.assignmentDataList.letterLevel");
			ArrayList<String> bubblecolor = jsonPath.get("responseData.assignmentDataList.bubbleColour");
			ArrayList<String> startlevel = jsonPath.get("responseData.assignmentDataList.startLevel");
			ArrayList<String> endlevel = jsonPath.get("responseData.assignmentDataList.targetLevel");

			if (assignmentdate.contains(null)) {
				results.add(false);
			}
			if (readinglevel.contains(null)) {
				results.add(false);
			}
			if (fluency.contains(null)) {
				results.add(false);
			}
			if (accuracy.contains(null)) {
				results.add(false);
			}
			if (proficiency.contains(null)) {
				results.add(false);
			}
			if (bubblecolor.contains(null)) {
				results.add(false);
			}
			if (startlevel.contains(null)) {
				results.add(false);
			}
			if (endlevel.contains(null)) {
				results.add(false);
			}
			if (assignmentvalue.contains(null)) {
				results.add(false);
			}
			return results.contains(false) ? false : true;

		} else {

			logger.info("There are no assignmentss" + assignments.toString());
		}
		return false;
	}

	/*
	 * method to validate fluency data if there is fluency graph present, values
	 * should not be null
	 */
	public static boolean validateFluencydata(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> fluencydata = jsonPath.get("responseData.fluencyDataList.");
		if (!fluencydata.isEmpty()) {
			ArrayList<String> date = jsonPath.get("responseData.fluencyDataList.assignmentDate");
			ArrayList<String> readinglevel = jsonPath.get("responseData.fluencyDataList.letterLevel");
			ArrayList<String> fluency = jsonPath.get("responseData.fluencyDataList.fluency");
			ArrayList<String> color = jsonPath.get("responseData.fluencyDataList.colour");
			ArrayList<String> startlevel = jsonPath.get("responseData.fluencyDataList.startLevel");
			ArrayList<String> endlevel = jsonPath.get("responseData.fluencyDataList.targetLevel");

			if (date.contains(null)) {
				results.add(false);
			}
			if (readinglevel.contains(null)) {
				results.add(false);
			}
			if (fluency.contains(null)) {
				results.add(false);
			}
			if (color.contains(null)) {
				results.add(false);
			}
			if (startlevel.contains(null)) {
				results.add(false);
			}
			if (endlevel.contains(null)) {
				results.add(false);
			}
			return results.contains(false) ? false : true;
		}

		else {
			logger.info("There are no fluency data present" + fluencydata.toString());
		}
		return false;
	}

	/*
	 * method to validate accuracy data if there is accuracy graph present, values
	 * should not be null
	 */
	public static boolean validateAccuracydata(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());
		ArrayList<String> accuracydata = jsonPath.get("responseData.accuracyDataList.");
		if (!accuracydata.isEmpty()) {
			ArrayList<String> date = jsonPath.get("responseData.accuracyDataList.assignmentDate");
			ArrayList<String> readinglevel = jsonPath.get("responseData.accuracyDataList.letterLevel");
			ArrayList<String> accuracy = jsonPath.get("responseData.accuracyDataList.accuracy");
			ArrayList<String> color = jsonPath.get("responseData.accuracyDataList.colour");
			ArrayList<String> startlevel = jsonPath.get("responseData.accuracyDataList.startLevel");
			ArrayList<String> endlevel = jsonPath.get("responseData.accuracyDataList.targetLevel");

			if (date.contains(null)) {
				results.add(false);
			}
			if (readinglevel.contains(null)) {
				results.add(false);
			}
			if (accuracy.contains(null)) {
				results.add(false);
			}
			if (color.contains(null)) {
				results.add(false);
			}
			if (startlevel.contains(null)) {
				results.add(false);
			}
			if (endlevel.contains(null)) {
				results.add(false);
			}
			return results.contains(false) ? false : true;
		}

		else {
			logger.info("There is no accuracy data prsent" + accuracydata.toString());
		}
		return false;
	}
}
