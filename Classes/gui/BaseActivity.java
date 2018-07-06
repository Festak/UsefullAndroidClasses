package com.lykkex.LykkeWallet.gui.activity;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.kyc.status.KycStatusActivity_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.managers.AssetPairManager_;
import com.lykkex.LykkeWallet.gui.managers.AssetsManager;
import com.lykkex.LykkeWallet.gui.managers.ClientStateManager;
import com.lykkex.LykkeWallet.gui.models.WalletSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.login.response.model.KysData;
import com.lykkex.LykkeWallet.rest.login.response.model.KysResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Created by LIZA on 23.02.2016.
 */
public class BaseActivity extends AppCompatActivity {

    protected BaseFragment currentFragment;
    protected ProgressDialog progressDialog;

    /*
        Kyc checks needed
    */
    protected KysResult kysResult = null;
    protected boolean shouldFinishActivityOnPending = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            AssetsManager.getInstance().restoreState(savedInstanceState);
            WalletSinglenton.getInstance().restoreState(savedInstanceState);
            ClientStateManager.Companion.getInstance().restoreState(savedInstanceState);
            AssetPairManager_.getInstance_(this).restoreState(savedInstanceState);
        }
        super.onCreate(savedInstanceState);

        View view = findViewById(android.R.id.content);

        if (view != null && view instanceof ViewGroup) {
            hideSoftKeyFromViewGroup((ViewGroup) view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        AssetsManager.getInstance().saveState(outState);
        WalletSinglenton.getInstance().saveState(outState);
        ClientStateManager.Companion.getInstance().saveState(outState);
        AssetPairManager_.getInstance_(this).saveState(outState);
        super.onSaveInstanceState(outState);
    }

    protected void initProgressDialog() {
        progressDialog = LykkeUtils.getWaitingProgressDialog(this, false);
    }

    public void afterViews() {
        progressDialog = LykkeUtils.getWaitingProgressDialog(this, false);
        if (getSupportActionBar() != null) {
//            Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setElevation(0);
        }
    }

    protected void showProgressDialog(boolean show) {

        if(progressDialog == null){
            progressDialog = LykkeUtils.getWaitingProgressDialog(this, false);
        }

        if (progressDialog != null && !isFinishing()) {
            if (show && !progressDialog.isShowing()) {
                progressDialog.show();
            }
            if (!show && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    protected void dismissProgressDialog() {
        if ((progressDialog != null) && (progressDialog.isShowing()) && !isFinishing()) {
            progressDialog.dismiss();
        }
    }

    protected void getKycStatus(String assetID) {
        Call<KysData> callKysData = LykkeApplication_.getInstance().getRestApi1().getKycForAsset(
                assetID);
        callKysData.enqueue(new Callback<KysData>() {
            @Override
            public void onResponse(Call<KysData> call, Response<KysData> response) {
                if ((response.isSuccessful())
                        && (response.body() != null)
                        && (response.code() == 200)
                        && (response.body().getResult() != null)) {
                    kysResult = response.body().getResult();
                    if (kysResult != null) {
                        checkKysStatus();
                    }
                } else if ((response.body() != null) && (response.body().getError() != null)) {
                    LykkeUtils.showError(getFragmentManager(), response.body().getError());
                }

            }

            @Override
            public void onFailure(Call<KysData> call, Throwable t) {

            }
        });
    }

    private void startKycActivity(KysStatusEnum enumInfo, String profileType) {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_KYS_STATUS, enumInfo);
        intent.putExtra(Constants.EXTRA_KYS_SHOW_PENDING, true);
        intent.putExtra(Constants.EXTRA_KYS_PROFILE_TYPE, profileType);
        intent.setClass(this, KycStatusActivity_.class);
        startActivity(intent);
    }

    protected boolean checkKysStatus() {
        if (kysResult == null) {
            return false;
        }
        String profileType = kysResult.getProfileType();
        if (kysResult.isKysNeeded()) {
            switch (KysStatusEnum.valueOf(kysResult.getUserKysStatus())) {
                case RestrictedArea:
                    startKycActivity(KysStatusEnum.RestrictedArea, profileType);
                    finish();
                    return false;
                case Rejected:
                    startKycActivity(KysStatusEnum.Rejected, profileType);
                    finish();
                    return false;
                case Pending:
                    startKycActivity(KysStatusEnum.Pending, profileType);
                    if (shouldFinishActivityOnPending) {
                        finish();
                    }
                    return false;
                case NeedToFillData:
                    kysResult = null;
                    startKycActivity(KysStatusEnum.NeedToFillData, profileType);
                    if (shouldFinishActivityOnPending) {
                        finish();
                    }
                    return false;
                case Ok:

                    return true;

            }
        } else {
            return true;
        }
        return true;
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public boolean hasNoFragment() {
        return getFragmentManager().findFragmentById(R.id.fragmentContainer) == null;
    }

    public void initFragment(BaseFragment fragment, Bundle arg) {
        initFragment(fragment, arg, false);
    }

    public void initFragment(BaseFragment fragment, Bundle arg, boolean skipAnimation) {
        fragment.setArguments(arg);

        ActionBar actionBar = getSupportActionBar();

        fragment.setUpActionBar(actionBar);

        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (!skipAnimation) {
            if (arg != null && arg.getBoolean(Constants.VERTICAL_ANIMATION, false)) {
                transaction.setCustomAnimations(R.animator.enter_anim_y, R.animator.exit_anim_y, R.animator.enter_anim_pop_y, R.animator.exit_anim_pop_y);
            } else {
                transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim_pop, R.animator.exit_anim_pop);
            }
        }

        transaction.replace(R.id.fragmentContainer, fragment, fragment.getClass().getSimpleName());

        if (currentFragment != null && (arg == null ||
                !arg.containsKey(Constants.SKIP_BACKSTACK) || !arg.getBoolean(Constants.SKIP_BACKSTACK))) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }


        /**
         * Fix - >  Fatal Exception: java.lang.IllegalStateException Can not perform this action after onSaveInstanceState
         */
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }


        currentFragment = fragment;
    }


    public void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException ex) {
        }
    }


    public void showKeyboard(View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.SHOW_IMPLICIT);
        } catch (NullPointerException ex) {
        }
    }

    public void hideSoftKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (currentFragment != null) {
            currentFragment.onBackPressed();
            if (currentFragment.isShouldBreakDefaultBackAction()) {
                return;
            }
        }
        super.onBackPressed();

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            String fragmentName = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();

            android.app.Fragment fragment = getFragmentManager().findFragmentByTag(fragmentName);

            if (fragment instanceof BaseFragment) {
                currentFragment = (BaseFragment) fragment;
            }
        }
    }

    public void clearBackStack() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void hideSoftKeyFromViewGroup(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof EditText) {
                child.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            hideSoftKeyboard(v);
                        }
                    }
                });
            }

            if (child instanceof ViewGroup) {
                hideSoftKeyFromViewGroup((ViewGroup) child);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
