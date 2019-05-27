package com.bec.api.automation.usecases.erroranalysis;

import static org.testng.Assert.assertEquals;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class StudentErrorAnalysisAverageCalculationValidation extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(StudentErrorAnalysisAverageCalculationValidation.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateStudentLvelerrorAnalysisAverageCalculation() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/erroranalysis/studenterroranalysisinputjson.json"));
		String seaApiEndpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"studentLevelErrorAnalsysisData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), seaApiEndpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			JsonPath jsonPath = new JsonPath(responseBody.asString());
			Object eaAverObject = jsonPath.getJsonObject("responseData.errorAnalysisAverage");
			if (!eaAverObject.toString().isEmpty()) {
				int substitutionAverage = jsonPath.get("responseData.errorAnalysisAverage.substitutionAverage");
				int insertionAverage = jsonPath.get("responseData.errorAnalysisAverage.insertionAverage");
				int ommissionAverage = jsonPath.get("responseData.errorAnalysisAverage.ommissionAverage");
				int toldAverage = jsonPath.get("responseData.errorAnalysisAverage.toldAverage");
				int repetationAverage = jsonPath.get("responseData.errorAnalysisAverage.repetationAverage");
				int selfCorrectionAverage = jsonPath.get("responseData.errorAnalysisAverage.selfCorrectionAverage");
				int meaningCuesAverageAverage = jsonPath.get("responseData.errorAnalysisAverage.meaningCuesAverage");

				int structuralCuesAverage = jsonPath.get("responseData.errorAnalysisAverage.structuralCuesAverage");

				int visualCuesAverage = jsonPath.get("responseData.errorAnalysisAverage.visualCuesAverage");

				int ommissionToldsAverage = jsonPath.get("responseData.errorAnalysisAverage.ommissionToldsAverage");

				int totalmsvValue = meaningCuesAverageAverage + structuralCuesAverage + visualCuesAverage
						+ ommissionToldsAverage;
				ArrayList<Integer> substitution = jsonPath.get("responseData.errorAnalysisData.substitution.value");
				ArrayList<Integer> repetation = jsonPath.get("responseData.errorAnalysisData.repetation.value");
				ArrayList<Integer> ommission = jsonPath.get("responseData.errorAnalysisData.omission.value");
				ArrayList<Integer> told = jsonPath.get("responseData.errorAnalysisData.told.value");
				ArrayList<Integer> insertion = jsonPath.get("responseData.errorAnalysisData.insertion.value");
				ArrayList<Integer> selfCorrection = jsonPath.get("responseData.errorAnalysisData.selfCorrection.value");
				ArrayList<Float> meaningCues = jsonPath.get("responseData.errorAnalysisData.meaningCues.value");
				ArrayList<Float> structuralCues = jsonPath.get("responseData.errorAnalysisData.structuralCues.value");
				ArrayList<Float> visualCues = jsonPath.get("responseData.errorAnalysisData.visualCues.value");
				ArrayList<Float> ommissionTolds = jsonPath.get("responseData.errorAnalysisData.omissionTolds.value");

				int rounded_substitution = (int) Math
						.round((double) sumOfErrors(substitution) / (double) substitution.size());
				assertEquals(substitutionAverage, rounded_substitution);
				int rounded_repetation = (int) Math
						.round((double) sumOfErrors(repetation) / (double) repetation.size());
				assertEquals(repetationAverage, rounded_repetation);
				int rounded_ommission = (int) Math.round((double) sumOfErrors(ommission) / (double) ommission.size());
				assertEquals(ommissionAverage, rounded_ommission);
				int rounded_told = (int) Math.round((double) sumOfErrors(told) / (double) told.size());
				assertEquals(toldAverage, rounded_told);
				int rounded_insertion = (int) Math.round((double) sumOfErrors(insertion) / (double) insertion.size());
				assertEquals(insertionAverage, rounded_insertion);
				int rounded_selfCorrection = (int) Math
						.round((double) sumOfErrors(selfCorrection) / (double) selfCorrection.size());
				assertEquals(selfCorrectionAverage, rounded_selfCorrection);
				int rounded_meaningCuesresultList = (int) Math
						.round((double) sumOfErrorsformsv(meaningCues) / (double) meaningCues.size());
				assertEquals(meaningCuesAverageAverage, rounded_meaningCuesresultList,
						"meaningCuesAverage Average value is not matching with calculated value ");
				int rounded_structuralCuesresultList = (int) Math
						.round((double) sumOfErrorsformsv(structuralCues) / (double) structuralCues.size());
				assertEquals(structuralCuesAverage, rounded_structuralCuesresultList,
						"structuralCuesAverage value is not matching with calculated values");
				int rounded_visualCuesresultList = (int) Math
						.round((double) sumOfErrorsformsv(visualCues) / (double) visualCues.size());
				assertEquals(visualCuesAverage, rounded_visualCuesresultList,
						"visualCuesAverage value is not matching with calculated total values");

				int rounded_ommissionToldsresultList = (int) Math
						.round((double) sumOfErrorsformsv(ommissionTolds) / (double) ommissionTolds.size());
				assertEquals(ommissionToldsAverage, rounded_ommissionToldsresultList,
						"ommissionToldsAverage value is not matching with calculated value");
				assertEquals(totalmsvValue, 100, "the total average value of msv are not matching or not equal to 100");

			} else {
				logger.error("There is no erroranlysisAverage data in the api response");
			}

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}

	public static float sumOfErrorsformsv(ArrayList<Float> values) {
		float sum = 0;
		for (int i = 0; i < values.size(); i++) {
			sum = sum + values.get(i);
		}
		return sum;
	}

	public static int sumOfErrors(ArrayList<Integer> values) {
		int sum = 0;
		for (int i = 0; i < values.size(); i++) {
			sum = sum + values.get(i);
		}
		return sum;
	}

}
