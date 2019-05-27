package com.bec.api.automation.usecases.erroranalysis;

import static org.testng.Assert.assertEquals;

import java.io.FileReader;
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

public class StudentErrorAnalysisPieChartValidation extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(StudentErrorAnalysisPieChartValidation.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateStudentLvelerrorAnalysisPieChart() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser
				.parse(new FileReader("src/main/resources/payloads/erroranalysis/studenterroranalysisinputjson.json"));

		String studentLevelErrorAnalysisApiEndpoint = RestAssuredUtil
				.generateApiEndPoint("DevRPLServiceApiUrl", "studentLevelErrorAnalsysisData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentLevelErrorAnalysisApiEndpoint,
				provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());
			logger.info(responseBody.body().jsonPath().get("message"));
			JsonPath jsonPath = new JsonPath(responseBody.asString());
			Object obje = jsonPath.getJsonObject("responseData.errorAnalysisDonutData");
			if (!obje.toString().isEmpty()) {
				int substitution = jsonPath.get("responseData.errorAnalysisDonutData.substitution");
				int ommission = jsonPath.get("responseData.errorAnalysisDonutData.ommission");
				int insertion = jsonPath.get("responseData.errorAnalysisDonutData.insertion");
				int told = jsonPath.get("responseData.errorAnalysisDonutData.told");
				int repetation = jsonPath.get("responseData.errorAnalysisDonutData.repetation");
				int selfCorrection = jsonPath.get("responseData.errorAnalysisDonutData.selfCorrection");
				int eaTotalPercentage = substitution + ommission + insertion + told + repetation + selfCorrection;
				assertEquals(eaTotalPercentage, 100, "the total percent values is not equal to 100%");
				int substitutionAverage = jsonPath.get("responseData.errorAnalysisAverage.substitutionAverage");
				int insertionAverage = jsonPath.get("responseData.errorAnalysisAverage.insertionAverage");
				int ommissionAverage = jsonPath.get("responseData.errorAnalysisAverage.ommissionAverage");
				int toldAverage = jsonPath.get("responseData.errorAnalysisAverage.toldAverage");
				int repetationAverage = jsonPath.get("responseData.errorAnalysisAverage.repetationAverage");
				int selfCorrectionAverage = jsonPath.get("responseData.errorAnalysisAverage.selfCorrectionAverage");
				int totalAverage = substitutionAverage + insertionAverage + ommissionAverage + toldAverage
						+ repetationAverage + selfCorrectionAverage;
				double substitutionpercentage = ((double) substitutionAverage / (double) totalAverage) * 100;
				int rounded_substitutionpercentage = (int) Math.round(substitutionpercentage);
				assertEquals(rounded_substitutionpercentage, substitution,
						"substitutionpercentage in pie chart is not matchinbg with Average value");
				double ommissionpercentage = ((double) ommissionAverage / (double) totalAverage) * 100;
				int rounded_ommissionpercentage = (int) Math.round(ommissionpercentage);
				assertEquals(rounded_ommissionpercentage, ommission,
						"ommissionpercentage in pie chart is not matchinbg with Average value");
				double insertionpercentage = ((double) insertionAverage / (double) totalAverage) * 100;
				int rounded_insertionpercentage = (int) Math.round(insertionpercentage);
				assertEquals(rounded_insertionpercentage, insertion,
						" insertionpercentage in pie chart is not matchinbg with Average value");
				double toldpercentage = ((double) toldAverage / (double) totalAverage) * 100;
				int rounded_toldpercentage = (int) Math.round(toldpercentage);
				assertEquals(rounded_toldpercentage, told,
						"toldpercentage in pie chart is not matching with average value");
				double repetationpercentage = ((double) repetationAverage / (double) totalAverage) * 100;
				int rounded_repetationpercentage = (int) Math.round(repetationpercentage);
				assertEquals(rounded_repetationpercentage, repetation,
						"repetationpercentage value is not matching with average value");
				double selfCorrectionpercentage = ((double) selfCorrectionAverage / (double) totalAverage) * 100;
				int rounded_selfCorrectionpercentage = (int) Math.round(selfCorrectionpercentage);
				assertEquals(rounded_selfCorrectionpercentage, selfCorrection,
						"selfcorrectionpercentage in pie chart is not matching with average value");
			} else {
				logger.error("errorAnalysisDonutData data is null");

			}
		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}
}
