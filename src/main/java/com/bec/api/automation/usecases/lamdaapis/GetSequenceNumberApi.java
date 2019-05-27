package com.bec.api.automation.usecases.lamdaapis;

import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import com.bec.api.automation.utils.RestAssuredUtil;
import com.jayway.restassured.response.Response;

public class GetSequenceNumberApi extends RestAssuredUtil {
	private static Log logger = LogFactory.getLog(GetSequenceNumberApi.class);

	static Response responseBody = null;

	@Test
	public static Map<String, Object> provideAuthenticatedHeaders() {
		Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
		headersMap.put("Content-Type", "application/json");

		return headersMap;
	}

	@Test(priority = 1)
	private void validateGetSequenceNumberApiResponse() throws Throwable {

		String value = getRandomValueFromList(entityList());

		String getSequenceNumberApiEndpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getSequenceNumber");
		String newapiEndpoint = getSequenceNumberApiEndpoint.concat("/").concat(value);

		responseBody = getServiceResponse(newapiEndpoint, provideAuthenticatedHeaders());
		if (200 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			assertTrue(responseBody.body().jsonPath().get("entityName").equals(value));
			int count = responseBody.body().jsonPath().get("count");
			responseBody = getServiceResponse(newapiEndpoint, provideAuthenticatedHeaders());
			assertTrue(responseBody.body().jsonPath().get("count").equals(count + 1));
			logger.info("Success" + responseBody.asString());

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

	private static List<String> entityList() {
		return Arrays.asList(new String[] { "DIM_CLASS", "DIM_SCHOOL", "DIM_DISTRICT", "DIM_STUDENT" });
	}

	private static List<String> invalidentityList() {
		return Arrays.asList(new String[] { "DIM_", "123", "123Dim" });
	}

	public static String getRandomValueFromSet(Set<String> values) {
		return getRandomValueFromList(new ArrayList<String>(values));
	}

	public static String getRandomValueFromList(List<String> values) {
		Collections.shuffle(values);
		return values.get(0);
	}

	@Test(priority = 2)
	private void validateGetSequenceNumberApiWithInvalidentity() throws Throwable {
		String value = getRandomValueFromList(invalidentityList());
		String getDimensionInsertionKeysAPIendpoint = RestAssuredUtil.generateApiEndPoint("DevRPLServiceApiUrl",
				"getSequenceNumber");
		String newapiEndpoint = getDimensionInsertionKeysAPIendpoint.concat("/").concat(value);

		responseBody = getServiceResponse(newapiEndpoint, provideAuthenticatedHeaders());
		if (400 == responseBody.statusCode()) {
			logger.info(responseBody.body().jsonPath().get("message"));
			logger.info("Success" + responseBody.asString());

		} else {
			logger.error(responseBody.body().jsonPath().get("message"));
			throw new RuntimeException("Failed with HTTP error code : " + responseBody.statusCode());

		}

	}

}
