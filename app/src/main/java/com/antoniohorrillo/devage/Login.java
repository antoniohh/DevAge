package com.antoniohorrillo.devage;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private static final int REQUEST_SIGNUP = 0;

    private Button btnlogin;
    private TextView linksignup;

    private TextInputLayout cmpemail;
    private TextInputLayout cmppassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = (Button)findViewById(R.id.btn_login);
        linksignup = (TextView)findViewById(R.id.link_signup);

        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        linksignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {

        cmpemail = (TextInputLayout)findViewById(R.id.cmp_email_wrapper);
        cmppassword = (TextInputLayout)findViewById(R.id.cmp_password_wrapper);

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnlogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.DevAgeTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String txtemail = cmpemail.getEditText().getText().toString();
        String txtpassword = cmppassword.getEditText().getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btnlogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnlogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String txtemail = cmpemail.getEditText().getText().toString();
        String txtpassword = cmppassword.getEditText().getText().toString();

        if (txtemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(txtemail).matches()) {
            cmpemail.setError("Error");
            valid = false;
        } else {
            cmpemail.setError(null);
        }

        if (txtpassword.isEmpty() || txtpassword.length() < 4 || txtpassword.length() > 10) {
            cmppassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            cmppassword.setError(null);
        }

        return valid;
    }
}