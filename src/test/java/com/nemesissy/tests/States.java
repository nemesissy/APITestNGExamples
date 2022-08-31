package com.nemesissy.tests;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class States extends BaseTest {

//    @Test(enabled = true, dataProvider = "states")
    @Test(enabled = true)
    public void verifyNumberOfStates() {

        given().
                log().all().
        when().
                get("https://datausa.io/api/searchLegacy/?limit=60&dimension=Geography&hierarchy=State&q=").
        then().
                log().all().
                statusCode(200).
        assertThat().
                body("results", hasSize(52))
        ;

    }
    @Test(enabled = true)
    public void verifyStatesJson() {

        given().
                log().all().
        when().
                get("https://datausa.io/api/searchLegacy/?limit=2&dimension=Geography&hierarchy=State&q=").
        then().
                log().all().
                statusCode(200).
        assertThat().
                body(matchesJsonSchema(this.openFile("states.json")))
        ;

    }
}
