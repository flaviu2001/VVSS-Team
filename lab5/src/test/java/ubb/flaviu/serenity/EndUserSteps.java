package ubb.flaviu.serenity;

import net.thucydides.core.annotations.Step;
import ubb.flaviu.pages.CSUBBPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps {

    CSUBBPage CSUBBPage;

    @Step
    public void enters(String keyword) {
        CSUBBPage.enter_keywords(keyword);
    }

    @Step
    public void should_see_definition(String definition) {
        assertThat(CSUBBPage.getDefinitions(), hasItem(containsString(definition)));
    }

    @Step
    public void is_the_home_page() {
        CSUBBPage.open();
    }

    @Step
    public void looks_for(String term) {
        enters(term);
    }
}