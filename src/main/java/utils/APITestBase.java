package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;

import static utils.Environment.currentEnvironment;
import static io.restassured.RestAssured.given;

public class APITestBase {
    Environment environment = new Environment();

    public Response GET(String EndPoint, String jsonBody, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        return given()
                .cookie(String.valueOf(cookie))
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .body(jsonBody)
                .get(EndPoint)
                .then()
                .extract().response();
    }

    public Response PUT(String EndPoint, String jsonBody, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        return given()
                .cookie(String.valueOf(cookie))
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .body(jsonBody)
                .put(EndPoint)
                .then()
                .extract().response();
    }

    public Response POST(String EndPoint, Object jsonBody, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        Response response;
        response = RestAssured.given()
                .cookie(String.valueOf(cookie))
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .body(jsonBody)
                .post(EndPoint)
                .then()
                .extract().response();
        return response;
    }

    public Response POSTURLEncoded(String EndPoint, Object jsonBody, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        Response response;
        response = RestAssured.given()
                .cookie(String.valueOf(cookie))
                .headers(
                        "Authorization",
                        bearerToken,
                        "Content-Type",
                        "application/x-www-form-urlencoded"
                ).when()
                .body(jsonBody)
                .post(EndPoint)
                .then()
                .extract().response();
        return response;
    }

    public Response PATCH(String EndPoint, Object jsonBody, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        Response response;
        response = RestAssured.given()
                .cookie(String.valueOf(cookie))
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .body(jsonBody)
                .patch(EndPoint)
                .then()
                .extract().response();
        return response;
    }

    public Response POSTFormData(String EndPoint, List<Object> parameterName, List<Object> parameterValue, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        Response response;
        RequestSpecification requestSpecification = RestAssured.given()
                .cookie(String.valueOf(cookie))
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + bearerToken)
                .when();
        for (int i = 0; i < parameterName.size(); i++) {
            Object objectsName = parameterName.get(i);
            Object objectsValues = parameterValue.get(i);
            requestSpecification.multiPart((String) objectsName, objectsValues);
        }
        response = requestSpecification.post(EndPoint).then().extract().response();
        return response;
    }

    public Response PATCHFormData(String EndPoint, List<Object> parameterName, List<Object> parameterValue, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        Response response;
        RequestSpecification requestSpecification = RestAssured.given()
                .cookie(String.valueOf(cookie))
                .contentType(ContentType.MULTIPART)
                .header("Authorization", "Bearer " + bearerToken)
                .when();
        for (int i = 0; i < parameterName.size(); i++) {
            Object objectsName = parameterName.get(i);
            Object objectsValues = parameterValue.get(i);
            requestSpecification.multiPart((String) objectsName, objectsValues);
        }
        response = requestSpecification.patch(EndPoint).then().extract().response();
        return response;
    }

    public Response DELETE(String EndPoint, String jsonBody, String bearerToken, String cookie) {
        environment.variables(currentEnvironment);
        RestAssured.baseURI = environment.baseAPIsURL;
        return given()
                .cookie(String.valueOf(cookie))
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when()
                .body(jsonBody)
                .post(EndPoint)
                .then()
                .extract().response();
    }
}
