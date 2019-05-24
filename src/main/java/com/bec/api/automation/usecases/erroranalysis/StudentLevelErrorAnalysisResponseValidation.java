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
import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class StudentLevelErrorAnalysisResponseValidation extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(StudentLevelErrorAnalysisResponseValidation.class);

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
			Boolean flag = studentlevelerroranalysisapiresponce(responseBody);
			assertTrue(flag);

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	/* method to validate content of the students values should not be null */
	public static boolean studentlevelerroranalysisapiresponce(Response responseBody) {
		List<Boolean> results = new ArrayList<Boolean>();
		JsonPath jsonPath = new JsonPath(responseBody.asString());

		ArrayList<String> dateRangeReadingLevelAxis = jsonPath.get("responseData.dateRangeReadingLevelAxis");

		Object obje = jsonPath.getJsonObject("responseData.errorAnalysisData");

		if (!dateRangeReadingLevelAxis.isEmpty()) {
			ArrayList<String> assignmentDate = jsonPath.get("responseData.dateRangeReadingLevelAxis.assignmentDate");
			ArrayList<String> readingLevel = jsonPath.get("responseData.dateRangeReadingLevelAxis.readingLevel");
			ArrayList<String> proficiency = jsonPath.get("responseData.dateRangeReadingLevelAxis.proficiency");
			ArrayList<String> lastPassage = jsonPath.get("responseData.dateRangeReadingLevelAxis.lastPassage");
			if (assignmentDate.contains(null)) {
				results.add(false);
			}
			if (readingLevel.contains(null)) {
				results.add(false);

			}
			if (proficiency.contains(null)) {
				results.add(false);
			}
			if (lastPassage.contains(null)) {
				results.add(false);
			}
			if (!obje.toString().isEmpty()) {
				ArrayList<String> substitution = jsonPath.get("responseData.errorAnalysisData.substitution");
				ArrayList<String> omission = jsonPath.get("responseData.errorAnalysisData.omission");
				ArrayList<String> insertion = jsonPath.get("responseData.errorAnalysisData.insertion");
				ArrayList<String> told = jsonPath.get("responseData.errorAnalysisData.told");
				ArrayList<String> repetation = jsonPath.get("responseData.errorAnalysisData.repetation");
				ArrayList<String> selfCorrection = jsonPath.get("responseData.errorAnalysisData.selfCorrection");
				ArrayList<String> meaningCues = jsonPath.get("responseData.errorAnalysisData.meaningCues");
				ArrayList<String> structuralCues = jsonPath.get("responseData.errorAnalysisData.structuralCues");
				ArrayList<String> visualCues = jsonPath.get("responseData.errorAnalysisData.visualCues");
				ArrayList<String> omissionTolds = jsonPath.get("responseData.errorAnalysisData.omissionTolds");

				if (!substitution.isEmpty()) {
					ArrayList<String> substitutionassignmentDate = jsonPath
							.get("responseData.errorAnalysisData.substitution.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.substitution.value");
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
							.get("responseData.errorAnalysisData.omission.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.omission.value");
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
							.get("responseData.errorAnalysisData.insertion.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.insertion.value");
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
							.get("responseData.errorAnalysisData.told.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.told.value");
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
							.get("responseData.errorAnalysisData.repetation.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.repetation.value");
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
							.get("responseData.errorAnalysisData.selfCorrection.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.selfCorrection.value");
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
							.get("responseData.errorAnalysisData.meaningCues.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.meaningCues.value");
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
							.get("responseData.errorAnalysisData.structuralCues.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.structuralCues.value");
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
							.get("responseData.errorAnalysisData.visualCues.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.visualCues.value");
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
							.get("responseData.errorAnalysisData.omissionTolds.assignmentDate");
					ArrayList<String> value = jsonPath.get("responseData.errorAnalysisData.omissionTolds.value");
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
