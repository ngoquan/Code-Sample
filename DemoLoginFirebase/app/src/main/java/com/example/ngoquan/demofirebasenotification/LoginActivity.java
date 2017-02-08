package com.example.ngoquan.demofirebasenotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
* link tutorial login with email and pass: http://www.androidhive.info/2016/06/android-getting-started-firebase-simple-login-registration-auth/
* link tutorial login with facebook: http://androidbash.com/firebase-facebook-login-tutorial-android/
* link tutorial login with google: https://www.androidtutorialpoint.com/firebase/android-firebase-authentication-tutorial-using-firebase-google-login/
* */

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    public final int RC_SIGN_IN = 10001;

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignUp, btnLogin, btnReset, btnSignOut;
    LoginButton loginButton;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private boolean isLoginGoogle = false;



    //Add YOUR Firebase Reference URL instead of the following URL
    Firebase mRef = new Firebase("https://fir-pushnotification-513de.firebaseio.com/users/");
//    Firebase mRef=new Firebase("https://console.firebase.google.com/project/fir-pushnotification-513de/authentication/users");

    //FaceBook callbackManager
    private CallbackManager callbackManager;

//    Google
    GoogleApiClient mGoogleApiClient;
    SignInButton googleSignIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        // Kiểm tra xem đã đăng nhập chưa, nếu đã đăng nhập thì chuyển ngay đến UserDetailActivity
//        if (auth.getCurrentUser() != null) {
////            startActivity(new Intent(LoginActivity.this, MainActivity.class));
////            finish();
//
//            // User is signed in
//            Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
//            String uid = auth.getCurrentUser().getUid();
//            String image = auth.getCurrentUser().getPhotoUrl().toString();
//            intent.putExtra("user_id", uid);
//            if (image != null && !image.equals("")) {
//                intent.putExtra("profile_picture", image);
//            }
//            startActivity(intent);
//            finish();
//            Log.d(TAG, "onAuthStateChanged:signed_in:" + auth.getCurrentUser().getUid());
//
//        }


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.mipmap.ic_launcher);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        loginButton = (LoginButton) findViewById(R.id.btn_login_facebook);
        googleSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        btnSignOut = (Button) findViewById(R.id.btn_signOut);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    googleSignIn.setVisibility(View.GONE);
                    btnSignOut.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.GONE);

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    googleSignIn.setVisibility(View.VISIBLE);
                    btnSignOut.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                }
            }
        };

// Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                signInWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });


//        auth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);
//                authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
//                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();

                                    Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
                                    String uid = auth.getCurrentUser().getUid();
                                    intent.putExtra("user_id", uid);
                                    startActivity(intent);
//                                    finish();
                                }

                            }
                        });
            }
        });

        // Google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        Log.e(TAG, "Google sign in failed: " + connectionResult.getErrorMessage());
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                if (isLoginGoogle) {
                    // logout google
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(LoginActivity.this, "Sign out", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "status: " + status.getStatus());
                        }
                    });
                } else {
                    // logout facebook
                    LoginManager.getInstance().logOut();
                }
            }
        });




    }


    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthStateListener != null) {
            auth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // google sign in success
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // google sign in failed
                Log.e(TAG, "onActivityResult: Google sign in failed.");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());
        Log.d(TAG, "firebaseAuthWithGoogle: " + account.getIdToken());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String uid = task.getResult().getUser().getUid();
                            String name = task.getResult().getUser().getDisplayName();
                            String email = task.getResult().getUser().getEmail();
                            String image = task.getResult().getUser().getPhotoUrl().toString();

//                            //Create a new User and Save it in Firebase database
//                            User user = new User(uid, name, null, email, null);
//                            mRef.child(uid).setValue(user);

                            // Create a new user and save it in Firebase database


                            Log.d(TAG, "uid: " + uid);
                            Log.d(TAG, "name: " + name);
                            Log.d(TAG, "email: " + email);
                            Log.d(TAG, "image: " + image);

                            Customer customer = new Customer(name, email);
                            mFirebaseDatabase.child(uid).setValue(customer);


                            Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
                            intent.putExtra("user_id", uid);
                            if (image != null && !image.equals(""))
                                intent.putExtra("profile_picture", image);
                            startActivity(intent);

                            isLoginGoogle = true;
//                            finish();
                        }
                    }
                });
    }

    private void signInWithFacebook(AccessToken accessToken) {
        Log.d(TAG, "signInWithFacebook: " + accessToken.getToken());
        progressBar.setVisibility(View.VISIBLE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {

                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String uid = task.getResult().getUser().getUid();
                            String name = task.getResult().getUser().getDisplayName();
                            String email = task.getResult().getUser().getEmail();
                            String image = task.getResult().getUser().getPhotoUrl().toString();

//                            //Create a new User and Save it in Firebase database
//                            User user = new User(uid, name, null, email, null);
//                            mRef.child(uid).setValue(user);

                            // Create a new user and save it in Firebase database


                            Log.d(TAG, "uid: " + uid);
                            Log.d(TAG, "name: " + name);
                            Log.d(TAG, "email: " + email);
                            Log.d(TAG, "image: " + image);

                            Customer customer = new Customer(name, email);
                            mFirebaseDatabase.child(uid).setValue(customer);


//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();

                            Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
                            intent.putExtra("user_id", uid);
                            if (image != null && !image.equals(""))
                                intent.putExtra("profile_picture", image);
                            startActivity(intent);

                            isLoginGoogle = false;
//                            finish();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
