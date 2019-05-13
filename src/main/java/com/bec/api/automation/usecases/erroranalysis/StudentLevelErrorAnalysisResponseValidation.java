package com.bec.api.automation.usecases.erroranalysis;

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

import com.bec.api.automation.usecases.readinglevelprogress.ReadingLevelProgressInputDatatest;
import com.bec.api.automation.utils.RestAssuredUtil;
import com.google.gson.JsonObject;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class StudentLevelErrorAnalysisResponseValidation extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(ReadingLevelProgressInputDatatest.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateStudentLvelerrorAnalysisResponse() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/erroranalysis/studenterroranalysisinputjson.json"));

		String classlevelreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentLevelErrorAnalsysisData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), classlevelreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			Boolean flag = studentlevelerroranalysisairesponce(responseBody);
			assertTrue(flag);

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	/* method to validate content of the students values should not be null */
	public static boolean studentlevelerroranalysisairesponce(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());

		ArrayList<String> dateRangeReadingLevelAxis = jsonPath.get("responseData.dateRangeReadingLevelAxis");

 Object obje = jsonPath.getJsonObject("responseData.errorAnalysisDataResultDTO");
 

		if (!dateRangeReadingLevelAxis.isEmpty()) {
			ArrayList<String> assignmentDate = jsonPath.get("responseData.dateRangeReadingLevelAxis.assignmentDate");
			ArrayList<String> readingLevel = jsonPath.get("responseData.dateRangeReadingLevelAxis.readingLevel");
			if (assignmentDate.contains(null)) {
				results.add(false);
			}
			if (readingLevel.contains(null)) {
				results.add(false);
			}
			if (!obje.toString().isEmpty()) {
				ArrayList<String> substitution = jsonPath.get("responseData.errorAnalysisDataResultDTO.substitution");
				ArrayList<String> omission = jsonPath.get("responseData.errorAnalysisDataResultDTO.omission");
				ArrayList<String> insertion = jsonPath.get("responseData.errorAnalysisDataResultDTO.insertion");
				ArrayList<String> told = jsonPath.get("responseData.errorAnalysisDataResultDTO.told");
				ArrayList<String> repetation = jsonPath.get("responseData.errorAnalysisDataResultDTO.repetation");
				ArrayList<String> selfCorrection = jsonPath.get("responseData.errorAnalysisDataResultDTO.selfCorrection");
				ArrayList<String> meaningCues = jsonPath.get("responseData.errorAnalysisDataResultDTO.meaningCues");
				ArrayList<String> structuralCues = jsonPath.get("responseData.errorAnalysisDataResultDTO.structuralCues");
				ArrayList<String> visualCues = jsonPath.get("responseData.errorAnalysisDataResultDTO.visualCues");
				ArrayList<String> omissionTolds = jsonPath.get("responseData.errorAnalysisDataResultDTO.omissionTolds");

				if (!substitution.isEmpty()) {
					ArrayList<String> substitutionassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.substitution.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.substitution.value");
					if (substitutionassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for substitution");
					results.add(false);
				}
				if (!omission.isEmpty()) {
					ArrayList<String> omissionassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.omission.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.omission.value");
					if (omissionassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}
				if (!insertion.isEmpty()) {
					ArrayList<String> insertionassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.insertion.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.insertion.value");
					if (insertionassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}
				if (!told.isEmpty()) {
					ArrayList<String> toldassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.told.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.told.value");
					if (toldassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}
				if (!repetation.isEmpty()) {
					ArrayList<String> repetationassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.repetation.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.repetation.value");
					if (repetationassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}
				if (!selfCorrection.isEmpty()) {
					ArrayList<String> selfCorrectionassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.selfCorrection.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.selfCorrection.value");
					if (selfCorrectionassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}
				if (!meaningCues.isEmpty()) {
					ArrayList<String> meaningCuesassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.meaningCues.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.meaningCues.value");
					if (meaningCuesassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}
				if (!structuralCues.isEmpty()) {
					ArrayList<String> structuralCuesassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.structuralCues.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.structuralCues.value");
					if (structuralCuesassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}

				if (!visualCues.isEmpty()) {
					ArrayList<String> visualCuessassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.visualCues.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.visualCues.value");
					if (visualCuessassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}

				if (!omissionTolds.isEmpty()) {
					ArrayList<String> omissionToldsassignmentDate = jsonPath
							.get("responseData.errorAnalysisDataResultDTO.omissionTolds.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisDataResultDTO.omissionTolds.value");
					if (omissionToldsassignmentDate.contains(null)) {
						results.add(false);
					}
					if (value.contains(null)) {
						results.add(false);
					}
				} else {
					logger.error("There is no data for omission");
					results.add(false);
				}

			}

			else {
				logger.info("There are no student level error analysis data avaliable");
				return true;
			}
			return results.contains(false) ? false : true;
		}

		else {
			logger.info("There are no student level error analysis data avaliable");
			return true;
		}

	}
}
