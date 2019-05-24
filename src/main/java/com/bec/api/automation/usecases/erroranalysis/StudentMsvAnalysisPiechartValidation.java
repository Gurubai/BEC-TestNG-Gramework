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

public class StudentMsvAnalysisPiechartValidation extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(StudentErrorAnalysisPieChartValidation.class);

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
			logger.info(responseBody.body().jsonPath().get("message"));
			JsonPath jsonPath = new JsonPath(responseBody.asString());
			Object obje = jsonPath.getJsonObject("responseData.msvAnalysisDonutData");
			if (!obje.toString().isEmpty()) {
				int meaningCues = jsonPath.get("responseData.msvAnalysisDonutData.meaningCues");
				int structuralCues = jsonPath.get("responseData.msvAnalysisDonutData.structuralCues");
				int visualCues = jsonPath.get("responseData.msvAnalysisDonutData.visualCues");
				int ommissionAndTolds = jsonPath.get("responseData.msvAnalysisDonutData.ommissionAndTolds");

				int eaTotalPercentage = meaningCues + structuralCues + visualCues + ommissionAndTolds;

				assertEquals(eaTotalPercentage, 100, "the total percent values is not equal to 100%");
				int meaningCuesAverage = jsonPath.get("responseData.errorAnalysisAverage.meaningCuesAverage");
				int structuralCuesAverage = jsonPath.get("responseData.errorAnalysisAverage.structuralCuesAverage");
				int visualCuesAverage = jsonPath.get("responseData.errorAnalysisAverage.visualCuesAverage");
				int ommissionToldsAverage = jsonPath.get("responseData.errorAnalysisAverage.ommissionToldsAverage");
				assertEquals(meaningCuesAverage, meaningCues,
						"The average meaningCues value and pie chart meaningCues percentage is not matching ");
				assertEquals(structuralCuesAverage, structuralCues,
						"The structuralCuesAverage value and pie chart structuralCues percentage is not matching ");
				assertEquals(ommissionToldsAverage, ommissionAndTolds,
						"The ommissionToldsAverage value and pie chart ommissionAndTolds percentage is not matching ");
				assertEquals(visualCuesAverage, visualCues,
						"The visualCuesAverage value and pie chart visualCues percentage is not matching ");

			} else {
				logger.error("msvAnalysisDonutData data is null");

			}
		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

}