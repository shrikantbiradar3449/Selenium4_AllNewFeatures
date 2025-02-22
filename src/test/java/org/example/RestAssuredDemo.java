package org.example;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
//import io.restassured.internal.path.xml.NodeChildrenImpl;
import org.json.JSONObject;
public class RestAssuredDemo {


    @Test
    public void getCallToAPI(){
        System.out.println("get Call to an API");
    }

    @Test
    public void testNoOfCircuitsIn2021() {
// Base URI
        RestAssured.baseURI = "http://ergast.com/api/f1";

        // Sending GET request
        Response response =
                given()
                        .when()
                        .get("/2021/circuits.json")
                        .then()
                        .assertThat()
                        .statusCode(200)  // Validate HTTP status code
                        .body("MRData.CircuitTable.Circuits", not(empty()))  // Ensure circuits list is not empty
                        .body("MRData.CircuitTable.season", equalTo("2021")) // Validate the season is 2021
                        .extract()
                        .response();

        // Extract JSON Response
        JsonPath jsonPath = response.jsonPath();

        // Get the total circuits count
        int circuitCount = jsonPath.getInt("MRData.CircuitTable.Circuits.size()");
        System.out.println("Total Circuits: " + circuitCount);

        // Extract and print first circuit name and location
        String firstCircuitName = jsonPath.getString("MRData.CircuitTable.Circuits[0].circuitName");
        String firstCircuitLocation = jsonPath.getString("MRData.CircuitTable.Circuits[0].Location.locality");
        System.out.println("First Circuit Name: " + firstCircuitName);
        System.out.println("First Circuit Location: " + firstCircuitLocation);

    }

    @Test
    public void testReqRes() {
        // Set Base URI
        RestAssured.baseURI = "https://reqres.in";

        // Send GET request and store the response
        Response response = given()
                .when()
                .get("/api/users/2")
                .then()
                .statusCode(200) // Validate Status Code
                .body("data.id", equalTo(2)) // Validate user ID is 2
                .body("data.email", notNullValue()) // Ensure email is not null
                .body("data.first_name", equalTo("Janet")) // Validate first name
                .body("data.last_name", equalTo("Weaver")) // Validate last name
                .extract().response();

        // Extract JSON response
        JsonPath jsonPath = response.jsonPath();
        int userId = jsonPath.getInt("data.id");
        String userEmail = jsonPath.getString("data.email");
        String firstName = jsonPath.getString("data.first_name");
        String lastName = jsonPath.getString("data.last_name");
        String avatar = jsonPath.getString("data.avatar");

        // Print extracted values
        System.out.println("User ID: " + userId);
        System.out.println("User Email: " + userEmail);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Avatar URL: " + avatar);

        // Additional Assertions
        Assert.assertEquals(userId, 2, "User ID should be 2");
        Assert.assertNotNull(userEmail, "User email should not be null");
        Assert.assertEquals(firstName, "Janet", "First name should be Janet");
        Assert.assertEquals(lastName, "Weaver", "Last name should be Weaver");
    }

    @Test
    public void testReqResWithoutFluent() {
        // Set Base URI
        RestAssured.baseURI = "https://reqres.in";

        // Create Request Specification
        RequestSpecification request = RestAssured.given();

        // Set Headers (if needed)
        request.header("Content-Type", "application/json");

        // Send GET request and store response
        Response response = request.get("/api/users/2");

        // Validate Status Code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Expected HTTP status code 200");

        // Convert Response to JsonPath
        JsonPath jsonPath = response.jsonPath();

        // Extract Required Fields
        int userId = jsonPath.getInt("data.id");
        String userEmail = jsonPath.getString("data.email");
        String firstName = jsonPath.getString("data.first_name");
        String lastName = jsonPath.getString("data.last_name");
        String avatar = jsonPath.getString("data.avatar");

        // Print Extracted Values
        System.out.println("User ID: " + userId);
        System.out.println("User Email: " + userEmail);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Avatar URL: " + avatar);

        // Additional Assertions
        Assert.assertEquals(userId, 2, "User ID should be 2");
        Assert.assertNotNull(userEmail, "User email should not be null");
        Assert.assertEquals(firstName, "Janet", "First name should be Janet");
        Assert.assertEquals(lastName, "Weaver", "Last name should be Weaver");
    }


    @Test
    public void testReqResPostReq() {

        // Set Base URI
        RestAssured.baseURI = "https://reqres.in";

        // Create Request Specification
        RequestSpecification request = RestAssured.given();

        // Create JSON Payload
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "John Doe");
        requestBody.put("job", "Software Engineer");

        // Set Headers
        request.header("Content-Type", "application/json");

        // Send POST Request and Get Response
        Response response = request.body(requestBody.toString()).post("/api/users");

        // Validate Status Code
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201, "Expected HTTP status code 201");

        // Convert Response to JsonPath
        JsonPath jsonPath = response.jsonPath();

        // Extract Response Fields
        String name = jsonPath.getString("name");
        String job = jsonPath.getString("job");
        String id = jsonPath.getString("id");
        String createdAt = jsonPath.getString("createdAt");

        // Print Response Details
        System.out.println("Name: " + name);
        System.out.println("Job: " + job);
        System.out.println("ID: " + id);
        System.out.println("Created At: " + createdAt);

        // Additional Assertions
        Assert.assertEquals(name, "John Doe", "Name should match input data");
        Assert.assertEquals(job, "Software Engineer", "Job should match input data");
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(createdAt, "Created At should not be null");
    }


    @Test
    public void testReqResPUTwithBasicAuth() {
        // Base URI
        RestAssured.baseURI = "https://dummyjson.com";

        // Valid Authentication Credentials
        String validUsername = "testuser";
        String validPassword = "testpass";

        // Invalid Authentication Credentials
        String invalidUsername = "invalidUser";
        String invalidPassword = "wrongPass";

        // Create RequestSpecification with valid auth
        RequestSpecification request = RestAssured.given()
                .auth()
                .preemptive()
                .basic(validUsername, validPassword) // Using basic authentication
                .header("Content-Type", "application/json");

        // JSON payload for updating the user
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstName", "John");
        requestBody.put("lastName", "Doe");
        requestBody.put("age", 30);

        // Attach JSON data to request
        request.body(requestBody.toString());

        // Send PUT request with valid auth
        Response response = request.put("/users/1");

        // Validate Response Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");

        // Validate Response Time (Should be under 2000ms)
        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 2000, "Response time is too high: " + responseTime + "ms");

        // Validate JSON Response Fields
        Assert.assertEquals(response.jsonPath().getString("firstName"), "John", "First name should be John");
        Assert.assertEquals(response.jsonPath().getString("lastName"), "Doe", "Last name should be Doe");
        Assert.assertEquals(response.jsonPath().getInt("age"), 30, "Age should be 30");
//
//        // Now, test authentication failure
//        RequestSpecification invalidRequest = RestAssured.given()
//                .auth()
//                .preemptive()
//                .basic(invalidUsername, invalidPassword)
//                .header("Content-Type", "application/json");
//
//        // Send PUT request with invalid auth
//        Response invalidResponse = invalidRequest.put("/users/1");
//
//        // Validate Unauthorized Response
//        Assert.assertEquals(invalidResponse.getStatusCode(), 401, "Expected 401 Unauthorized status");
    }





































































//    @Test
//    public void testNoOfCircuitsIn2021ByXml() {
//
////		Validation 1
//
//        given()
//
//                .when().get("http://ergast.com/api/f1/2021/circuits")
//
//                .then().assertThat()
//
//                .statusCode(200)
//
//                .and()
//
//                .header("Content-Length", equalTo("6438")).extract().path("MRData.CircuitTable.Circuit").toString().contains("MRData.CircuitTable.Circuit");

//		Validation 2

//        given()
//
//                .when().get("http://ergast.com/api/f1/2021/circuits").then()
//
//                .extract().path("MRData.CircuitTable.Circuit").;
//
//
//        Assert.assertEquals(circuits.get(0).getAttribute("circuitId"),"albert_park");
//
//        Assert.assertTrue(circuits.get(0).get("CircuitName").toString().equalsIgnoreCase("Albert Park Grand Prix Circuit"));



//    }

}//EOC
