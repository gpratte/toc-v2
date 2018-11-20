package com.texastoc.cucumber;

import com.texastoc.model.season.Season;
import com.texastoc.service.SeasonService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@Ignore
public class Stepdefs extends SpringBootBaseIntegrationTest {

    @Autowired
    SeasonService seasonService;

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
        seasonCreated = seasonService.createSeason(seasonToCreate);
    }

    @Then("^the start date should be now$")
    public void the_start_date_should_be_now() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
