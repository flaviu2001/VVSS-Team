package ubb.dragos.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;

@DefaultUrl("https://www.cs.ubbcluj.ro/apps/lucrari")
public class LoginPage extends PageObject {
    @FindBy(xpath="//table[@class='chenar']/tbody/tr[last()]/td/input[1]")
    private WebElementFacade loginButton;

    @FindBy(name="email")
    private WebElementFacade emailInput;

    @FindBy(name="parola")
    private WebElementFacade parolaInput;

    public void enterData(String email, String password){
        emailInput.type(email);
        parolaInput.type(password);
    }

    public void login(){
        loginButton.click();
    }

    public String getInfo(String xpath){
        return find(By.xpath(xpath)).getText();
    }
}
