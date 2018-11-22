package com.texastoc.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.texastoc.controller.SeasonRestController;
import com.texastoc.model.season.Season;
import com.texastoc.service.SeasonService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Ignore
public class Stepdefs extends SpringBootBaseIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(Stepdefs.class);


    private Season seasonToCreate;
    private Season seasonCreated;

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
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.registerModule(new JavaTimeModule());
        String seasonToCreateAsJson = mapper.writeValueAsString(seasonToCreate);
        HttpEntity<String> entity = new HttpEntity<>(seasonToCreateAsJson ,headers);
        System.out.println("!!! " + seasonToCreateAsJson);

        seasonCreated = restTemplate.postForObject(endpoint() + "/seasons", entity, Season.class);
        System.out.println("\n!!! season created " + seasonCreated);
    }

    @Then("^the start date should be now$")
    public void the_start_date_should_be_now() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
