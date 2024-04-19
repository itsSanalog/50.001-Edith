package com.example.edith.activities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.example.edith.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.common.api.Scope;

/**
 * LoginActivity is an activity class that handles the login process of the user.
 * It uses Google Sign-In and Firebase Authentication for the login process.
 */
public class LoginActivity extends AppCompatActivity {
    public static final int GOOGLE_SIGN_IN_CODE = 10005;
    SignInButton signIn;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;

    /**
     * This method is called when the activity is starting.
     * It sets the content view, checks if the user is already logged in, and sets up Google Sign-In.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        signIn = findViewById(R.id.signIn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id)).requestEmail()
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar"))
                .build();

        signInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if (signInAccount == null) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = signInClient.getSignInIntent();
                startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
            }
        });
    }

    /**
     * This method is called when an activity you launched exits, giving you the requestCode you started it with, the resultCode it returned, and any additional data from it.
     * It handles the result of the Google Sign-In process.
     * @param requestCode The integer request code originally supplied to startActivityForResult(), allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the child activity through its setResult().
     * @param data An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);
                GoogleAccountHolder.getInstance().setAccount(signInAcc);
                String idToken = signInAcc.getIdToken();
                firebaseAuthWithGoogle(idToken);
            } catch (ApiException e) {
                e.printStackTrace();
                Log.e("LoginActivity", "Google sign in failed" + e.getStatusCode());
                Toast.makeText(this, "Sign-in Failed", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * This method is used to authenticate the user with Firebase using Google Sign-In.
     * @param idToken The ID token from Google Sign-In.
     */
    private void firebaseAuthWithGoogle(String idToken) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity", "Sign In: success");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}