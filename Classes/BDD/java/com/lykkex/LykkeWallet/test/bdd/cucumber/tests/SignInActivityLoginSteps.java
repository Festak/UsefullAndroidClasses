package com.lykkex.LykkeWallet.test.bdd.cucumber.tests;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.lykkex.LykkeWallet.gui.activity.authentication.SignInActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.utils.PinCodeType;
import com.lykkex.LykkeWallet.test.bdd.cucumber.base.BaseTestSteps;
import com.lykkex.LykkeWallet.test.bdd.cucumber.base.CommonStepsDefinition;

import org.junit.Rule;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static junit.framework.Assert.assertNotNull;

/**
 * @author e.fetskovich on 2/20/18.
 */

public class SignInActivityLoginSteps extends BaseTestSteps {
    private Activity activity;

    @Rule
    public ActivityTestRule<SignInActivity_> activityTestRule = new ActivityTestRule<SignInActivity_>(SignInActivity_.class);

    @Given("^I have a SignInActivity$")
    public void I_have_a_SignInActivity() {
        initComponents();
        assertNotNull(activity);
    }

    @When("^I login with email (\\S+) password (\\S+) smscode (\\S+) pincode (.*)$")
    public void I_do_all_login_steps(String email, String password, String smscode, String pin) {
        CommonStepsDefinition stepsDefinition = new CommonStepsDefinition();
        doFirstPartLogin(stepsDefinition, email, password, pin);

        EnterPinActivity_ activity = (EnterPinActivity_)getCurrentActivity();
        boolean isUseShortLogin = activity.nextStepIsMainActivity();

        stepsDefinition.I_input_pin_code(pin, PinCodeType.ACTIVITY);
        if(isUseShortLogin) {
            // do nothing
        } else {
            doSecondPartLogin(stepsDefinition, smscode);
        }
    }

    @When("^I login with email (\\S+) password (\\S+) pincode (.*)$")
    public void I_do_first_part_login_steps(String email, String password, String pin) {
        CommonStepsDefinition stepsDefinition = new CommonStepsDefinition();
        doFirstPartLogin(stepsDefinition, email, password, pin);
    }

    protected void doFirstPartLogin(CommonStepsDefinition stepsDefinition, String email, String password, String pin){
        stepsDefinition.I_fill_data_into_view_with_id(email, "emailEditText");
        stepsDefinition.I_click_on_view_with_id("buttonAction");
        stepsDefinition.I_fill_data_into_view_with_id(password, "editTextPassword");
        stepsDefinition.I_click_on_view_with_id("buttonLogin");
    }

    protected void doSecondPartLogin(CommonStepsDefinition stepsDefinition, String smscode) {
        stepsDefinition.I_fill_data_into_view_with_id(smscode, "codeEditText");
        stepsDefinition.I_click_on_view_with_id("nextButton");
    }


    private void initComponents() {
        activityTestRule.launchActivity(null);
        activity = activityTestRule.getActivity();
    }

}
