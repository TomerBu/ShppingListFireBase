package tomerbu.edu.shppinglistfirebase.controllers.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tomerbu.edu.shppinglistfirebase.R;
import tomerbu.edu.shppinglistfirebase.models.User;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String TAG = "TomerBu";
    private Button mEmailSignInButton;
    private SharedPreferences prefs;
    Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnResetPassword = (Button) findViewById(R.id.btnReset);
        btnResetPassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
        prefs = getSharedPreferences("emails", MODE_PRIVATE);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);

        //on keyboard action tapped:
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void resetPassword() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(getEmail())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(btnResetPassword,
                                e.getLocalizedMessage(),
                                Snackbar.LENGTH_INDEFINITE).
                                setAction("dismiss", new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(LoginActivity.this,
                        "Sent - Check your email...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private String getEmail() {
        return mEmailView.getText().toString();
    }

    private void populateAutoComplete() {
        ArrayList<String> emails = new ArrayList<>();

        Set<String> savedEmails = prefs.getStringSet("emails", null);
        if (savedEmails != null) {
            for (String email : savedEmails) {
                emails.add(email);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, emails);
        mEmailView.setAdapter(adapter);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    boolean shouldAttemptAnotherLogin = true;

    private void attemptLogin() {
        if (!shouldAttemptAnotherLogin) {
            return;
        }
        shouldAttemptAnotherLogin = false;

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = getEmail();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            loginOrSignUp(email, password);
            saveEmailToPrefs();
        }
    }

    private void saveEmailToPrefs() {
        Set<String> savedEmails = prefs.getStringSet("emails", null);
        if (savedEmails == null)
            savedEmails = new HashSet<>();
        else savedEmails = new HashSet<>(savedEmails);//copy
        //if the email is indeed new to the HashSet
        //(otherwise it already exists in the set and avoided by the add operation)
        if (savedEmails.add(getEmail()))
            prefs.edit().putStringSet("emails", savedEmails).apply();
    }

    private void loginOrSignUp(final String email, final String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        showProgress(false);
                        saveUserLoggedInGotoMain();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidUserException) {
                    //No such user, perform sign up
                    signUpToFirebase(email, password);
                } else {
                    Log.e(TAG, e.getClass().getName());
                    showProgress(false);
                    Snackbar.make(mEmailSignInButton, e.getLocalizedMessage(),
                            Snackbar.LENGTH_INDEFINITE).setAction("dismiss", new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
                }
            }
        });
    }

    private void saveUserLoggedInGotoMain() {
        saveUserData(true);
        Intent intent = new Intent(this, UserListsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void saveUserData(boolean isLoggedIn) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;
        /**
         * Encode user email replacing "." with "," to be able to use it
         * as a Firebase db key
         */
        String email = currentUser.getEmail();

        assert email != null;
        String userName = email.split("@")[0];
        assert userName != null;
        String uid = currentUser.getUid();

        String encEmail = email.replace(".", "_");

        /* Make a user */
        HashMap<String, Object> userAndUidMapping = new HashMap<String, Object>();

        HashMap<String, Object> timeStamp = new HashMap<>();
        timeStamp.put("lastLogin", ServerValue.TIMESTAMP);


        /* Create a HashMap version of the user to add */
        User newUser = new User(userName, email,currentUser.getUid(), timeStamp, isLoggedIn);

        HashMap<String, Object> newUserMap = (HashMap<String, Object>)
                new ObjectMapper().convertValue(newUser, Map.class);

        /* Add the user and UID to the update map */
        userAndUidMapping.put("/uidEmails/"
                + uid, email);

        /* Update the database; it will fail if a user already exists */
        FirebaseDatabase.getInstance().getReference().updateChildren(userAndUidMapping);
    }

    private void signUpToFirebase(final String email, final String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                saveUserLoggedInGotoMain();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e.getClass().getName());
                showProgress(false);
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                //after finished... allow retries
                shouldAttemptAnotherLogin = true;
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        if (!show) {
            shouldAttemptAnotherLogin = true;
        }
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}

