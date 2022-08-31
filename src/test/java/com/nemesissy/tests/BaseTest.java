package com.nemesissy.tests;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.DataProvider;

import static io.restassured.RestAssured.given;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class BaseTest {

    public Response response;
    public ValidatableResponse vResponse;

    @DataProvider(name = "creds")
    public Object[][] allCreds() {

        /*
        State Name
        State Key
        State ID
         */
        return new String[][] {
                {"Texas" , "Geography-State-04000US48", "04000US48"},
                {"1" , "2", "3"},
                {"1" , "2", "3"},
        };
    }

    @DataProvider(name = "states")
    public Object[] allStates() {

        return new String[] {
                "Texas",
                "Colorado",
                "Seattle",
        };
    }

    public void testAvailability() {

        given().
                contentType("application/json; charset=UTF-8").
        when().
                get("https://datausa.io/api/searchLegacy/?limit=2&dimension=Geography&hierarchy=State&q=").
        then().
                statusCode(200)
        ;

    }

    public File openFile(String filename) {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filename);
        if (resource == null) {
            System.out.println("file not found! " + filename);
            throw new IllegalArgumentException("file not found! " + filename);
        }
        File file = null;
        try {
            file = new File(resource.toURI());
        } catch (Exception c) {
            System.out.println("Unhandled exception on file " + filename);
        }
        return file;
    }

}
