/*
 * Copyright (c) 2017 Mindstix Inc.
 */
package com.mindstix.cb.stepdefinition.api;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Weather API test.
 *
 * @author Mindstix
 */
public class APITest {

  private String givenCity;
  private RequestSpecification request;
  private Response response;
  private String baseUrl;

  public APITest() {

  }

  @Given("API baseUrl \"([^\"]*)\"$")
  public void api_url(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Given("^City of \"([^\"]*)\"$")
  public void city_of(String city) {
    this.givenCity = city;
  }

  @Given("^invoke weather API$")
  public void invoke_weather_api() {
    this.request = given().pathParam("city",givenCity);
    this.response = this.request.when().
                        get(this.baseUrl+ "{city}");
  }

  @Then("^verify the response code is \"([^\"]*)\" and body contains city name \"([^\"]*)\"$")
  public void verify_response(int code, String city) {
    this.response.then().
        assertThat().
        statusCode(code).
        body("City", city.trim().isEmpty() ? Matchers.nullValue() : Matchers.equalTo(city));
  }


}
