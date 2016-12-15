package com.gobinda.assignment.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.gobinda.assignment.R;
import com.gobinda.assignment.commons.PrefHelper;
import com.gobinda.assignment.ui.landing.DashboardActivity;

/**
 * Created by gobinda on 15/12/16.
 */
public class AuthenticationActivity extends FragmentActivity implements LoginFragment.Callback, RegisterFragment.Callback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isRegisteredOrLogin()) return;
        setContentView(R.layout.activity_authentication);
        showLoginFragment();

    }

    private void showLoginFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commit();
    }

    private void showSignUpFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, RegisterFragment.newInstance())
                .commit();
    }

    @Override
    public void switchSignUp() {
      showSignUpFragment();
    }

    @Override
    public void switchLogInUp() {
     showLoginFragment();
    }

    @Override
    public void done(String  email) {
        PrefHelper.setCurrentEmail(email);
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private boolean isRegisteredOrLogin() {
        String email = PrefHelper.getCurrentEmail();
        if (!TextUtils.isEmpty(email)) {
                startActivity(new Intent(this, DashboardActivity.class));
                finish();

            return true;
        }

        return false;
    }
}
