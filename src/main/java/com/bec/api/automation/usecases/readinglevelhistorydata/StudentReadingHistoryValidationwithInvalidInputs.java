package com.bec.api.automation.usecases.readinglevelhistorydata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.response.Response;

public class StudentReadingHistoryValidationwithInvalidInputs extends RestAssuredUtil {

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validatestudentreadinghistoryapiresponsewithoutstartdate() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("startDate");

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("studentReadingHistoryDataDTO") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {
			throw new RuntimeException("Failed with : " + responseBody.statusCode());
		}
	}

	@Test(priority = 2)
	private void getstudentreadinghistoryapiresponsewithoutEndDate() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("endDate");

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("studentReadingHistoryDataDTO") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {

			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}

	@Test(priority = 3)
	private void getstudentreadinghistoryapiresponsewithoutstudentGrade() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("studentGrade");

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("studentReadingHistoryDataDTO") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {

			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}

	@Test(priority = 4)
	private void getstudentreadinghistoryapiresponsewithoutstudentId() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("studentId");

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("studentReadingHistoryDataDTO") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {

			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}

	@Test(priority = 5)
	private void getstudentreadinghistoryapiresponsewithoutteacherId() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.remove("teacherId");

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Mandatory fields should not be null or empty");
			assertTrue(responseBody.jsonPath().getString("studentReadingHistoryDataDTO") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {

			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}

	@Test(priority = 6)
	private void studentreadinghistoryapiresponsewithinvalidTeacherId() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.replace("teacherId", -6);

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Teacher Id does not exist :");
			assertTrue(responseBody.jsonPath().getString("studentReadingHistoryDataDTO") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {

			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}

	@Test(priority = 6)
	private void studentreadinghistoryapiresponsewithinvalidStudentId() throws Throwable {

		JSONParser jsinInputparser = new JSONParser();

		JSONObject requestpayloadobject = (JSONObject) jsinInputparser.parse(new FileReader(
				"src/main/resources/payloads/readinglevelhistorydata/studentReadingHistoryDataInput.json"));
		JSONObject filters = (JSONObject) requestpayloadobject.get("filters");
		JSONObject efilters = (JSONObject) filters.get("externalFilter");
		efilters.replace("studentId", -6);

		String studentreadinghistoryAPIendpoint = RestAssuredUtil
				.generateReadingLevelProgressApiEndpoint("DevRPLServiceApiUrl", "studentReadingHistoryData");
		// Whenr
		responseBody = postServiceResponse(requestpayloadobject.toString(), studentreadinghistoryAPIendpoint,
				provideAuthenticatedHeaders());
		if (responseBody.statusCode() == 400) {
			assertEquals(responseBody.jsonPath().getString("message"),
					"Error in Fetching Data : Student Id does not exist :");
			assertTrue(responseBody.jsonPath().getString("studentReadingHistoryDataDTO") == null);
			assertTrue(responseBody.jsonPath().getBoolean("SUCCESSFULL") == false);

		} else {

			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());
		}
	}
}