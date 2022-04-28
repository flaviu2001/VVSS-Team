package ubb.dragos.steps;

import net.thucydides.core.annotations.Step;
import ubb.dragos.pages.LoginPage;

public class EndUserSteps {
    LoginPage loginPage;

    @Step
    public void enters(String email, String password){
        loginPage.enterData(email, password);
    }

    @Step
    public void login(){
        loginPage.login();
    }

    @Step
    public void openPage(){
        loginPage.open();
    }

    @Step
    public void checkFor(String shouldSee, String xpath){
        assert loginPage.getInfo(xpath).contains(shouldSee);
    }
}
