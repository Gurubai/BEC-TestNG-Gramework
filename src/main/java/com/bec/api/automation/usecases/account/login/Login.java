package com.bec.api.automation.usecases.account.login;

import com.bec.api.automation.domain.account.login.LoginEntity;
import com.bec.api.automation.utils.RestAssuredUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mkpatil on 09/01/19.
 */
public class Login extends RestAssuredUtil {


    public static Map<String, Object> provideAuthenticatedHeaders() {
        Map<String, Object> headersMap = new LinkedHashMap<String, Object>();
        headersMap.put("Content-Type", "application/json");

        return headersMap;
    }

    static Response responseBody = null;
    private static Log logger = LogFactory.getLog(Login.class);

    //reset
    @Test(priority = 1)
    private void loginTestWithJSONObject() throws Throwable {


        //Given input

        JSONObject loginObject = new JSONObject();
                loginObject.put("username","uncle_scrooge@mail.com");
                loginObject.put("password","duckburg");

        //String endpoint = RestAssuredUtil.generateAccountApiEndpoint("ServiceApiUrl", "login");
        String endpoint1 = RestAssuredUtil.generateReadingLevelProgressApiEndpoint("ServiceApiUrl", "readingLevelProgressData");
      
        //When
       // responseBody = postServiceResponse(loginObject.toString(), endpoint, provideAuthenticatedHeaders());
 responseBody =       postServiceResponse(endpoint1, provideAuthenticatedHeaders());

        //Then

        if (200 == responseBody.statusCode()) {
            logger.info("Login is sucessfull " + responseBody.asString());
            System.out.println(responseBody.getBody().asString());
           System.out.println(responseBody.getBody().asString().contains("token"));
           
           
           
           
       List<Object> token = responseBody.jsonPath().getList("data");
            System.out.println(token);

        } else {
            String failMessage = "Failed to Loging=" + responseBody.asString() + "";
            logger.error("Failed to Loging=" + responseBody.asString());

        }


       /* {"status":200,"error":{},"data":
            {"token":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIxIiwic3ViIjoiVW5jbGUgU2Nyb29nZSIsInVzZXJfaWQiOiIxIiwiZm4iOiJVbmNsZSBTY3Jvb2dlIiwiZW1haWwiOiJ1bmNsZV9zY3Jvb2dlQG1haWwuY29tIiwibG4iOiJTY3Jvb2dlIiwidXNlcl9yb2xlcyI6IlsyLCAxLCAzXSIsImluc3RpdHV0aW9ucyI6IlszXSIsImluc3RJZCI6IjMiLCJ1c2VyR3JvdXBzIjoiWzEsIDEyNiwgMTI5LCAxMzAsIDEzMSwgMTMzLCAxMzQsIDEzNSwgMTM2LCAxMzcsIDEzOCwgMTM5LCAxNDAsIDE0MiwgMTQzLCAxNDQsIDE0NiwgMTUyLCAxNTQsIDE1NSwgMTc1LCAxNzYsIDE4NCwgMjEyLCAyMzcsIDI0NywgMjc3LCAyNzgsIDI5OSwgMzA1LCAzMTMsIDMyMywgMzQ5LCAzNTgsIDM3NCwgNDI0LCA0MjcsIDQ0NywgNTA0XSIsInR5cGUiOiJ0ZW5hbnRBZG1pbiIsImFkbWluIjp0cnVlLCJjdCI6NzE0NTc1MjE1NzM1NTQ0MywiaWF0IjoxNTQ3MDMxMjE4LCJleHAiOjE1NDcxMTc2MTh9.BPJp_emp0Yh-yPcq4FfCtN7jz6BV_UfPYcDinG5Pilo","refreshToken":"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIxIiwic3ViIjoiVW5jbGUgU2Nyb29nZSIsInNlY3JldCI6IjQ3Yjk3MjI0LTZiODYtNGY0ZS04NTlhLTZlYmNmOTliYzczNCIsImN0Ijo3MTQ1NzUyMTU3NDk2MDQ5LCJpYXQiOjE1NDcwMzEyMTgsImV4cCI6MTU3ODU4ODE3MH0.5v81SaWxUzPow3v5XMNAiLkuSSvf74JsBsE0bQ8G2w8"}
        }*/



    }
//    public static String generateString(String filename) throws IOException{
//		String filePath = System.getProperty("user.dir")+"\\Payloads\\"+filename;
//		return new String(Files.readAllBytes(Paths.get(filePath)));
//	}


    @Test(priority = 2)
    private void loginTestWithPayload() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        //Given input
        LoginEntity loginEntity = new LoginEntity();
        loginEntity = objectMapper.readValue(new FileReader("src/main/resources/account/login/login.json"), LoginEntity.class);


        String endpoint = RestAssuredUtil.generateReadingLevelProgressApiEndpoint("ServiceApiUrl", "login");

        //When
        responseBody = postServiceResponse(loginEntity.toString(), endpoint, provideAuthenticatedHeaders());

        //Then

        if (200 == responseBody.statusCode()) {
            logger.info("Login is sucessfull " + responseBody.asString());
            fail(" loginTestWithPayload is failed");
        } else {
            String failMessage = "Failed to Loging=" + responseBody.asString() + "";
            logger.error("Failed to Loging=" + responseBody.asString());

        }


    }


}
