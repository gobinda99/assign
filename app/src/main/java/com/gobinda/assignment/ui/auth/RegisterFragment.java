package com.gobinda.assignment.ui.auth;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.gobinda.assignment.R;
import com.gobinda.assignment.dao.models.User;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Regex;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gobinda on 5/9/16.
 */
public class RegisterFragment extends Fragment  implements Validator.ValidationListener {

    @Bind(R.id.email)
    @Required(order = 2, messageResId = R.string.email_required)
    @Regex(order = 3, pattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+", messageResId = R.string.email_valid)
    EditText mEmail;

    @Bind(R.id.name)
    @Required(order = 1, messageResId = R.string.name_required)
    EditText mName;


    @Bind(R.id.password)
    @Password(order = 4, messageResId = R.string.password_required)
    @TextRule(order = 5, minLength = 6, messageResId = R.string.password_invalid)
    EditText mPassword;

    private Validator mValidator;

    private Callback mCallback;


    public interface Callback {
        void switchLogInUp();

        void done(String email);
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPassword.setOnEditorActionListener(mDoneKeyListener);

    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onValidationSucceeded() {
        String email = mEmail.getText().toString();
          String name = mName.getText().toString();
          String password = mPassword.getText().toString();
        User user = User.getUser(email);
        if(user != null) {
            AppMsg.makeText(getActivity(), "Email already registered", AppMsg.STYLE_INFO).show();
        } else {
            user = new User(email,name,password);
            int s = User.saveUser(user);
            if(s== 1) {
                AppMsg.makeText(getActivity(), "Registration successful", AppMsg.STYLE_CONFIRM).show();
                mCallback.done(email);
            }
        }


    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        if (failedView instanceof EditText) {
            failedView.requestFocus();
            ((EditText) failedView).setError(failedRule.getFailureMessage());
        }
    }

    @OnClick(R.id.sign_up)
    void onSignUp(Button btn) {
        mValidator.validate();
    }

    @OnClick(R.id.switch_sign_in)
    void onSignIn(Button btn) {
        hideKeyboard(getActivity());
        if (mCallback != null) {
            mCallback.switchLogInUp();
        }
    }


    private TextView.OnEditorActionListener mDoneKeyListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView t, int actionId, KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (isKeyboardShown(mPassword)) {
                    hideSoftKeyboard();
                }
                mValidator.validate();
                handled = true;
            }
            return handled;
        }
    };




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement LoginFragment.Callback");
        }
    }
    public  boolean isKeyboardShown(View view) {
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputManager.isActive(view);
    }

    public void hideSoftKeyboard() {
        // hide soft input keyboard
        View focused = getActivity().getCurrentFocus();
        if (focused instanceof EditText) {
            EditText et = (EditText) focused;

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    public  void hideKeyboard(Activity a) {
        if (a != null) {
            InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);

            try {
                imm.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }
        }
    }


}
