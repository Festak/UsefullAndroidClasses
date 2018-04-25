package com.lykkex.LykkeWallet.test.bdd.cucumber;

import android.util.Log;

import org.junit.Assume;

import cucumber.api.Scenario;
import cucumber.api.java.Before;

/**
 * @author e.fetskovich on 2/23/18.
 */

public class CucumberSkipsScenario {

    private static final String TAG = "CucumberSkipsScenario";

    @Before("@skip_scenario")
    public void skip_scenario(Scenario scenario) {
        Log.i(TAG, "skip_scenario: "+scenario.getName());
        Assume.assumeTrue(false);
    }

}
