package org.example.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"org.example.stepdefinitions", "org.example.hooks"},
        plugin = {"pretty", "html:target/cucumber-report.html", "summary"},
        monochrome = true
)
public class RunCucumberTest {
}
