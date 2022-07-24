package ru.praktikum_services.qa_scooter;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestAssuredClient {
    private RequestSpecification requestSpecification = new RequestSpecBuilder()
            .addFilter(new AllureRestAssured())
            .log(LogDetail.ALL)
            .setContentType(ContentType.JSON)
            .setBaseUri("https://qa-scooter.praktikum-services.ru/")
            .setBasePath("/api/v1/")
            .build();

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public <T> Response post(String path, T body, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .body(body)
                .when()
                .post(path, pathParams);
    }

    public Response delete(String path, Object... pathParams) {
        return given()
                .spec(getRequestSpecification())
                .when()
                .delete(path, pathParams);
    }

}


