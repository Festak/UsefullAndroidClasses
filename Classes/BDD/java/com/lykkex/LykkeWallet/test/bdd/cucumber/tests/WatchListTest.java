package com.lykkex.LykkeWallet.test.bdd.cucumber.tests;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.MainActivity_;
import com.lykkex.LykkeWallet.gui.activity.authentication.SignInActivity_;
import com.lykkex.LykkeWallet.gui.activity.watchlists.MarginWatchListsActivity_;
import com.lykkex.LykkeWallet.test.bdd.cucumber.base.BaseTestSteps;
import com.lykkex.LykkeWallet.test.bdd.cucumber.base.CommonStepsDefinition;

import org.junit.Assume;
import org.junit.Rule;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

/**
 * @author e.fetskovich on 3/13/18.
 */

public class WatchListTest extends BaseTestSteps {
    private static final int HAS_NOT_ELEMENT_IN_LIST = -1;

    private Activity activity;

    @Rule
    public ActivityTestRule<SignInActivity_> activityTestRule = new ActivityTestRule<>(SignInActivity_.class);

    @Given("^I have a SignInActivity for WatchList")
    public void I_have_a_SignInActivity_for_WatchList() {
        initComponents();
        assertNotNull(activity);
    }

    @Given("^I switch tab to the margin wallet$")
    public void I_switch_tab_to_the_margin_wallet() {
        final MainActivity_ mainActivity_ = (MainActivity_) getCurrentActivity();
        mainActivity_.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity_.setCurrentTabTo(R.string.margin_item_demo);
            }
        });
    }

    @Given("^I go to MarginWatchListsActivity$")
    public void I_go_to_MarginWatchListsActivity() {
        CommonStepsDefinition stepsDefinition = new CommonStepsDefinition();
        I_switch_tab_to_the_margin_wallet();
        // # User switchs tab to MarginMainFragment
        stepsDefinition.I_click_on_view_with_id("tradeButton");
        // User enters MarginTradeActivity
        stepsDefinition.I_click_on_view_with_id("optionsRelativeLayout");
        // User enters MarginWatchListsActivity
    }

    @When("^I do long click on margin with recycler id \\[([^]]+)\\] with text \"([^\"]*)\"$")
    public void I_perform_long_click_on_margin(String id, String text) {
        MarginWatchListsActivity_ activity = (MarginWatchListsActivity_) getCurrentActivity();
        int elementId = activity.doesWatchListContainsItem(text);
        if (elementId == HAS_NOT_ELEMENT_IN_LIST) {
            Assume.assumeTrue(false);
        } else {
            performLongClickOnRecyclerItem(id, elementId);
        }
    }

    @Then("^I should see margin with text \"([^\"]*)\"$")
    public void I_should_see_margin_with_text(String text) {
        MarginWatchListsActivity_ activity = (MarginWatchListsActivity_) getCurrentActivity();
        assertNotEquals(activity.doesWatchListContainsItem(text), HAS_NOT_ELEMENT_IN_LIST);
    }

    private void initComponents() {
        activityTestRule.launchActivity(null);
        activity = activityTestRule.getActivity();
    }
}
