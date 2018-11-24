package com.texastoc.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.texastoc.model.season.Season;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Ignore;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;

@Ignore
public class Stepdefs extends SpringBootBaseIntegrationTest {

    private Season seasonToCreate;
    private Season seasonCreated;
    private HttpClientErrorException exception;

    @Given("^season starts now$")
    public void season_starts_now() throws Exception {

        // Arrange
        seasonToCreate = Season.builder()
            .start(LocalDate.now())
            .kittyPerGame(10)
            .tocPerGame(10)
            .quarterlyTocPerGame(10)
            .quarterlyNumPayouts(3)
            .build();
    }

    @When("^the season is created$")
    public void the_season_is_created() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String seasonToCreateAsJson = mapper.writeValueAsString(seasonToCreate);
        HttpEntity<String> entity = new HttpEntity<>(seasonToCreateAsJson ,headers);

        try {
            seasonCreated = restTemplate.postForObject(endpoint() + "/seasons", entity, Season.class);
        } catch (HttpClientErrorException e) {
            exception = e;
        }

    }

    @Then("^the start date should be now$")
    public void the_start_date_should_be_now() throws Exception {
        Assert.assertTrue(seasonCreated.getId() > 0);
        Assert.assertEquals(seasonToCreate.getKittyPerGame(), seasonCreated.getKittyPerGame());
        Assert.assertEquals(seasonToCreate.getTocPerGame(), seasonCreated.getTocPerGame());
        Assert.assertEquals(seasonToCreate.getQuarterlyTocPerGame(), seasonCreated.getQuarterlyTocPerGame());
        Assert.assertEquals(seasonToCreate.getQuarterlyNumPayouts(), seasonCreated.getQuarterlyNumPayouts());
        Assert.assertEquals(seasonToCreate.getStart(), seasonCreated.getStart());
    }

    @Given("^season start date is missing$")
    public void season_start_date_is_missing() throws Exception {
        // Arrange
        seasonToCreate = Season.builder()
            .kittyPerGame(10)
            .tocPerGame(10)
            .quarterlyTocPerGame(10)
            .quarterlyNumPayouts(3)
            .build();
    }

    @Then("^response is \"([^\"]*)\"$")
    public void response_is(String expected) throws Exception {
        Assert.assertEquals(expected, exception.getStatusCode().toString());
    }

}
