package com.texastoc.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = "pretty",
    features="src/test/resources/quarterly_seasons.feature")
public class RunCucumberTest {
}
