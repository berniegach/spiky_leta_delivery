package com.spikingacacia.spikyletadelivery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.spikingacacia.spikyletadelivery.database.ServerAccount;
import com.spikingacacia.spikyletadelivery.main.MainActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    //REMEMBER TO CHANGE THIS WHEN CHANGING BETWEEN ONLINE AND LOCALHOST
    public static final String base_url="https://3.20.17.200/order/"; //online
    //public static final String base_url="http://10.0.2.2/leta_project/android/"; //localhost no connection for testing user accounts coz it doesnt require subscription checking
    private String TAG="LoginA";
    //buyers
    private static ServerAccount serverAccount;
    public static GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 21;
    static public GoogleSignInAccount account;
    private ProgressBar progressBar;
    private View mainView;
    public final static String SAVE_INSTANCE_SERVER_ACCOUNT = "save_server_account";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            sleep(5000);
        } catch (InterruptedException e)
        {
            Log.e(TAG,"failed to sleep");
        }
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");


        progressBar = findViewById(R.id.progress);
        mainView = findViewById(R.id.container);
        showProgress(true);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //initialize the containers
        //buyers
        serverAccount =new ServerAccount();



    }
    @Override
    public void onStart()
    {

        super.onStart();
        //check internet connection
        if(isNetworkAvailable())
        {
            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            account = GoogleSignIn.getLastSignedInAccount(this);
            //proceed to sign in
            if(account!=null)
            {
                Intent intent = new Intent(this, OrdersService.class);
                startService(intent);
                proceedToLogin();
            }
            else
            {
                showProgress(false);
                Log.d(TAG,"email null");
            }
        }
        else
        {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Error")
                    .setMessage("There is not internet connection")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            finishAffinity();
                        }
                    }).create().show();
        }


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            if(account!=null)
            {
                proceedToLogin();
                Log.d(TAG, "email: " + account.getEmail());
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    private void proceedToLogin()
    {
        //get the account details
        new LoginTask(account.getEmail()).execute((Void)null);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static ServerAccount getServerAccount()
    {
        return serverAccount;
    }
    public static void setServerAccount(ServerAccount serverAccount1)
    {
        serverAccount = serverAccount1;
    }
    void showProgress(boolean show)
    {
        progressBar.setVisibility(show? View.VISIBLE : View.GONE);
        mainView.setVisibility( show? View.INVISIBLE :View.VISIBLE);
    }
    public class LoginTask extends AsyncTask<Void, Void, Boolean>
    {
        private String url_get_account =base_url+"get_delivery_account.php";
        private String TAG_SUCCESS="success";
        private String TAG_MESSAGE="message";
        private final String mEmail;
        private  JSONParser jsonParser;
        private int success=0;
        private ServerAccount account;

        LoginTask(String email) {
            mEmail = email;
            jsonParser = new JSONParser();
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //logIn=handler.LogInStaff(mEmail,mPassword);
            //building parameters
            List<NameValuePair>info=new ArrayList<NameValuePair>();
            info.add(new BasicNameValuePair("email",mEmail));
            //getting all account details by making HTTP request
            JSONObject jsonObject= jsonParser.makeHttpRequest(url_get_account,"POST",info);
            try
            {
                success=jsonObject.getInt(TAG_SUCCESS);
                if(success==1)
                {
                    //seccesful
                    JSONArray accountArray=jsonObject.getJSONArray("account");
                    JSONObject accountObject=accountArray.getJSONObject(0);

                    int id = accountObject.getInt("id");
                    String email = accountObject.getString("email");
                    String username = accountObject.getString("username");
                    String location = accountObject.getString("location");
                    String image_type = accountObject.getString("image_type");
                    double wallet = accountObject.getDouble("wallet");
                    String date_added = accountObject.getString("dateadded");
                    String date_changed = accountObject.getString("datechanged");

                    account = new ServerAccount(id,email,username, location,image_type, wallet, date_added, date_changed);

                    return true;
                }
                else
                {
                    String message=jsonObject.getString(TAG_MESSAGE);
                    Log.e(TAG_MESSAGE+TAG,""+message);
                    return false;
                }
            }
            catch (JSONException | NullPointerException e)
            {
                Log.e("JSON",""+e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean successful) {


            if (successful)
            {
                serverAccount = account;
                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                //prevent this activity from flickering as we call the next one
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
            else
            {
                showProgress(false);
                if(success==-2)
                {
                    //the email is not registered
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("No account")
                            .setMessage("There is no delivery account associated with the email?")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Toast.makeText(getBaseContext(), "Sign in failed", Toast.LENGTH_SHORT).show();
                                    mGoogleSignInClient.signOut().addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            Log.d(TAG,"gmail signed out");
                                        }
                                    });
                                    dialog.dismiss();
                                }
                            }).create().show();
                }

            }
        }

    }






}
