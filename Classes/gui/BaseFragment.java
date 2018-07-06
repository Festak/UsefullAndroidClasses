package com.lykkex.LykkeWallet.gui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;

import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;

/**
 * Created by e.kazimirova on 09.02.2016.
 */

public abstract class BaseFragment extends android.app.Fragment {

    protected ActionBar actionBar;

    private ProgressDialog progressDialog;

    public void setUpActionBar(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    public boolean isShouldBreakDefaultBackAction() {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Activity a = getActivity();
            if (a != null) {
                a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void onBackPressed() {
    }

    /**
     * @deprecated use {@link #showProgressDialog()}  or {@link #hideProgressDialog()} instead.
     */
    protected void showProgressDialog(boolean show) {
        if (show) {
            showProgressDialog();
        } else {
            hideProgressDialog();
        }
    }

    protected void showProgressDialog() {
        if (isActivityNotFinishing()) {
            if (progressDialog == null) {
                progressDialog = LykkeUtils.getWaitingProgressDialog(getActivity(), false);
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    protected void removeFromBackstack(Fragment fragment) {
        if (getActivity() != null) {
            try {
                FragmentManager manager = getActivity().getFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(fragment);
                trans.commit();
                manager.popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && isActivityNotFinishing() && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void dismissProgressDialog(ProgressDialog progressDialog) {
        if ((progressDialog != null) && (progressDialog.isShowing()) && isActivityNotFinishing()) {
            progressDialog.dismiss();
        }
    }

    protected void showProgressDialog(ProgressDialog progressDialog) {
        if (isActivityNotFinishing()) {
            if (progressDialog == null) {
                progressDialog = LykkeUtils.getWaitingProgressDialog(getActivity(), false);
            }
            progressDialog.show();
        }
    }

    protected boolean isActivityNotFinishing() {
        return getActivity() != null && !getActivity().isFinishing();
    }

}
