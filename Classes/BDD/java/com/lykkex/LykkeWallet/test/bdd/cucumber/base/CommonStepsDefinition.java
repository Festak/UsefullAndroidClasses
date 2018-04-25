package com.lykkex.LykkeWallet.test.bdd.cucumber.base;

import android.support.test.espresso.IdlingRegistry;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.utils.ActivityFinisher;
import com.lykkex.LykkeWallet.gui.utils.GeneratePinCodeUtils;
import com.lykkex.LykkeWallet.gui.utils.PinCodeType;
import com.lykkex.LykkeWallet.gui.utils.StringUtils;

import java.util.Map;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author e.fetskovich on 2/26/18.
 */

public class CommonStepsDefinition extends BaseTestSteps {

    private static final String TAG = "CommonStepsDefinition";

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(LykkeApplication_.getInstance().getIdlingResourceManager().getCountingIdlingResource());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(LykkeApplication_.getInstance().getIdlingResourceManager().getCountingIdlingResource());
        ActivityFinisher.Companion.finishOpenActivities();
    }

    @When("^I fill data (\\S+) into the view with id \\[([^]]+)\\]$")
    public void I_fill_data_into_view_with_id(final String data, final String id) {
        performTypeText(id, data);
    }

    @When("^I fill any data \"([^\"]*)\" into the view with id \\[([^]]+)\\]$")
    public void I_fill_any_data_into_view_with_id(final String data, final String id) {
        performTypeText(id, data);
    }

    @When("^I click on view with id \\[([^]]+)\\]$")
    public void I_click_on_view_with_id(final String id) {
        // We have to use this sleep because HideKeyboardLogic takes some times
        sleepFor(1000);
        performViewClick(id);
    }

    @When("^I click on view with stringId \\[([^]]+)\\]$")
    public void I_click_on_view_with_text(String id){
        performViewClickWithText(id);
    }

    @When("^I click on common dialog button (.*)$")
    public void I_click_on_common_dialog_button(DialogButtonType buttonType) {
        performClickOnCommonDialogButton(buttonType);
    }

    @Then("^I should see view with id \\[([^]]+)\\]$")
    public void I_should_see_view_with_id(final String id) {
        isViewDisplayed(id);
    }

    @Then("^I should see view enabled (true|false) with id \\[([^]]+)\\]$")
    public void I_should_see_view_enabled_with_id(boolean shouldSeeButtonEnabled, final String id) {
        isViewEnabled(id, shouldSeeButtonEnabled);
    }

    @Then("^I should see common dialog button (.*) enabled (true|false)$")
    public void I_should_see_view_enabled_with_id(DialogButtonType buttonType, boolean shouldSeeButtonEnabled) {
        if (buttonType == DialogButtonType.POSITIVE) {
            isViewEnabled(android.R.id.button1, shouldSeeButtonEnabled);
        } else if (buttonType == DialogButtonType.NEGATIVE) {
            isViewEnabled(android.R.id.button2, shouldSeeButtonEnabled);
        }
    }

    @Then("^I should see view with text \"([^\"]*)\"$")
    public void I_should_see_view_with_text(String text){
        isViewDisplayedWihText(text);
    }

    @Then("^I should see view with stringId \\[([^]]+)\\] and args (\\d+)$")
    public void I_should_see_view_with_text(String id, int args){
        isViewDisplayedWithArgsText(id, args);
    }

    @Then("^I should see view visible (true|false) with id \\[([^]]+)\\]$")
    public void I_should_see_view_visible_with_id(boolean shouldSeeButtonVisible, final String id) {
        isViewVisible(id, shouldSeeButtonVisible);
    }

    @Then("^I should see text input layout error (true|false) with id \\[([^]]+)\\] and stringId \\[([^]]+)\\]$")
    public void I_should_see_text_input_layout_error(boolean shouldSeeError, final String id, final String stringId) {
        isTextInputLayoutMatchesText(shouldSeeError, id, stringId);
    }

    @When("^I input pin code (.*) with type (.*)$")
    public void I_input_pin_code(final String pins, PinCodeType pinCodeType) {
        Map<String, Integer> pinCodeViewsId = GeneratePinCodeUtils.INSTANCE.initPinCodeArrayByType(pinCodeType);
        String[] pinsArray = pins.split(" ");
        for (int i = 0; i < pinsArray.length; i++) {
            int buttonViewPinId = pinCodeViewsId.get(pinsArray[i]);
            performViewClick(buttonViewPinId);
        }
    }

    @When("^I select items in list with id \\[([^]]+)\\] on positions (.*)$")
    public void I_select_items_in_list(final String id, String positions) {
        int[] intArray = StringUtils.INSTANCE.convertStringToIntArray(positions, " ");
        selectItemsOnPositions(id, intArray);
    }

    @When("^I do long click on list with id \\[([^]]+)\\] on position (.*)$")
    public void I_do_long_click_on_recycler_view_item(final String id, String positions){
        performLongClickOnRecyclerItem(id, Integer.parseInt(positions));
    }


}
