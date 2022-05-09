package ubb.flaviu.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

@DefaultUrl("https://www.cs.ubbcluj.ro")
public class CSUBBPage extends PageObject {

    @FindBy(xpath = "//*[@id=\"s\"]")
    private WebElementFacade searchTerms;

    public void enter_keywords(String keyword) {
        searchTerms.type(keyword);
        searchTerms.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDefinitions() {
        WebElementFacade definitionList = find(By.xpath("//*[@id=\"content\"]"));
        return definitionList.findElements(By.tagName("div")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
