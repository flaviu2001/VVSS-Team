package ubb.dragos.login;


import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import ubb.dragos.steps.EndUserSteps;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/dragos/credentials.csv")
public class LoginDdt {
    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @ManagedPages(defaultUrl = "https://www.cs.ubbcluj.ro/apps/lucrari")
    public Pages pages;

    public String email;
    public String password;
    public String shouldSee;
    public String xpath;

    @Qualifier
    public String getQualifier() {
        return email;
    }

    @Steps
    public EndUserSteps endUser;

    @Issue("#CSUBB-1")
    @Test
    public void loginTestDDT() {
        endUser.openPage();
        endUser.enters(getEmail(), getPassword());
        endUser.login();
        endUser.checkFor(getShouldSee(), getXpath());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShouldSee() {
        return shouldSee;
    }

    public void setShouldSee(String shouldSee) {
        this.shouldSee = shouldSee;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
}
