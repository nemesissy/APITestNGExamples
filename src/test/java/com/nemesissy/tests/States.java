package com.nemesissy.tests;

import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;

import static com.nemesissy.tools.IsEven.isEven;
import static com.nemesissy.tools.IsOdd.isOdd;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

public class States extends BaseTest {

    final static Logger log = LogManager.getLogger(States.class);

    @Test(enabled = false)
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
    @Test(enabled = false)
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
    @Test(enabled = false, dataProvider = "states")
    public void verifyStatesDetails(String name, String key, String id) {

        vResponse = given().
                log().all().
        when().
                get("https://datausa.io/api/searchLegacy/?limit=60&dimension=Geography&hierarchy=State&q=").
        then().
                log().all().
        statusCode(200)
        ;
        Response response = vResponse.extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map> all = jsonPathEvaluator.getList("results");
        log.debug("Found ["+all.size()+"] States.");

        for (int i=0; i<all.size(); i++) {
            Map state = all.get(i);
            if (state.get("name").toString().contains(name)) {

                assert state.get("name").toString().contains(name);
                assert state.get("key").toString().contains(key);
                assert state.get("id").toString().contains(id);
            }
        }
    }

    @Test(enabled = true)
    public void verifyStateTexasHamcrest() {

        given().
                log().all().
        when().
                get("https://datausa.io/api/searchLegacy/?limit=60&dimension=Geography&hierarchy=State&q=Texas").
        then().
                log().all().
                statusCode(200).
        assertThat().
                body("results",     hasSize(1)).
                body("results[0]",  hasEntry("hierarchy","State")).
                body("results[0].dimension", instanceOf(String.class)).
                body("results[0].zvalue",    instanceOf(Float.class)).
                body("results[0].dimension", is("Geography")).
                body("results[0].zvalue",    is(0.68087465F)).
                body("query",       hasKey("dimension")).
                body("totals",      hasKey("Geography"))
        ;

    }

    @Test(enabled = false)
    public void verifyStateTexasCustomHamcrest() {

        given().
                log().all().
        when().
                get("https://datausa.io/api/searchLegacy/?limit=2&dimension=Geography&hierarchy=State&q=").
        then().
                log().all().
                statusCode(200).
        assertThat().
                body("results",     hasSize(1)).
                body("totals.Geography.State", isEven()).
                body("totals.Geography.Place", isOdd())
        ;

    }

}
