package com.spikingacacia.spikyletadelivery.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spikingacacia.spikyletadelivery.JSONParser;
import com.spikingacacia.spikyletadelivery.LoginActivity;
import com.spikingacacia.spikyletadelivery.R;
import com.spikingacacia.spikyletadelivery.SettingsActivity;
import com.spikingacacia.spikyletadelivery.database.Orders;
import com.spikingacacia.spikyletadelivery.database.ServerAccount;
import com.spikingacacia.spikyletadelivery.main.orders.OrdersActivity;
import com.spikingacacia.spikyletadelivery.main.orders.OrdersFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.spikingacacia.spikyletadelivery.LoginActivity.base_url;
import static com.spikingacacia.spikyletadelivery.LoginActivity.mGoogleSignInClient;

public class MainActivity extends AppCompatActivity implements
        OrdersFragment.OnListFragmentInteractionListener
{
    private String TAG="MainA";
    private static final int PERMISSION_REQUEST_INTERNET=2;
    private ProgressBar progressBar;
    private View mainFragment;
    public static String myLocation = "";
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //first get the location
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        checkIfLocationEnabled();
        getCurrentLocation();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_orders, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        progressBar = findViewById(R.id.progress);
        mainFragment = findViewById(R.id.nav_host_fragment);
    }
    // This callback is called only when there is a saved instance that is previously saved by using
// onSaveInstanceState(). We restore some state in onCreate(), while we can optionally restore
// other state here, possibly usable after onStart() has completed.
// The savedInstanceState Bundle is same as the one used in onCreate().
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        LoginActivity.setServerAccount((ServerAccount) savedInstanceState.getSerializable(LoginActivity.SAVE_INSTANCE_SERVER_ACCOUNT));
        //Log.d(TAG,"main_a has been recreated therefoew we restore server account");
    }
    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(LoginActivity.SAVE_INSTANCE_SERVER_ACCOUNT,LoginActivity.getServerAccount());
        //Log.d(TAG,"main_a is been destroyed threfore we call onsaved instance");
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if( id == R.id.action_wallet)
        {
            /*Intent intent=new Intent(MainActivity.this, WalletActivity.class);
            //prevent this activity from flickering as we call the next one
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);*/
        }

        if (id == R.id.action_settings)
        {
            Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
            //prevent this activity from flickering as we call the next one
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            return true;
        }
        else if( id == R.id.action_sign_out)
        {
            mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>()
            {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    Log.d(TAG,"gmail signed out");
                    finish();
                }
            });
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {

        int stack = getSupportFragmentManager().getBackStackEntryCount();
        if ( stack> 0)
            getSupportFragmentManager().popBackStack();
        else
        {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Quit")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
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
    void showProgress(boolean show)
    {
        progressBar.setVisibility(show? View.VISIBLE : View.GONE);
        mainFragment.setVisibility( show? View.INVISIBLE :View.VISIBLE);
    }
    private boolean checkIfLocationEnabled()
    {
        LocationManager lm = (LocationManager)getBaseContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled) {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage("Location is not enabled")
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel",null)
                    .show();
        }
        if(!network_enabled)
        {
            new AlertDialog.Builder(this)
                    .setMessage("Internet is not enabled")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            ;//startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .show();
        }
        if(!isNetworkAvailable())
        {
            new AlertDialog.Builder(this)
                    .setMessage("No internet")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            ;//startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .show();
            network_enabled = false;
        }
        return gps_enabled && network_enabled;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void getCurrentLocation()
    {
        checkIfLocationEnabled();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //get the users location
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>()
                    {
                        @Override
                        public void onSuccess(Location location)
                        {
                            //Get last known location. In some rare situations this can be null
                            if(location!=null)
                            {
                                final double latitude=location.getLatitude();
                                final double longitude=location.getLongitude();

                                myLocation = String.valueOf(latitude)+":"+String.valueOf(longitude)+":"+"null";
                                Thread thread_location=new Thread()
                                {
                                    @Override
                                    public void run()
                                    {
                                        //get addresses
                                        Geocoder geocoder=new Geocoder(MainActivity.this, Locale.getDefault());
                                        List<Address> addresses;
                                        try
                                        {
                                            addresses=geocoder.getFromLocation(latitude,longitude,10);
                                            myLocation = String.valueOf(latitude)+":"+String.valueOf(longitude)+":"+addresses.get(0).getCountryCode();
                                            new UpdateLastKnownLocationTask().execute((Void)null);
                                        }
                                        catch (IOException e)
                                        {
                                            runOnUiThread(new Runnable()
                                            {
                                                @Override
                                                public void run()
                                                {
                                                    showProgress(false);
                                                    //Snackbar.make(getWindow().getDecorView().getRootView(),"Error getting your location.\nPlease try again.", Snackbar.LENGTH_SHORT).show();
                                                }
                                            });

                                            Log.e(TAG,""+e.getMessage());
                                        }
                                    }
                                };
                                thread_location.start();

                            }

                        }
                    }).addOnFailureListener(this, new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    showProgress(false);
                    Log.e(TAG,"location failed");
                }
            });
        }
        //request the permission
        else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_INTERNET);
        }

    }
    /*
implementation of OrdersFragment.java
 */
    @Override
    public void onOrderClicked(Orders item)
    {
        String date_added=item.getDateAdded();
        String[] date_pieces=date_added.split(" ");
        String unique_name=date_pieces[0]+":"+item.getOrderNumber();
        Intent intent = new Intent(this, OrdersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("unique_order_name",unique_name);
        intent.putExtra("order_format",item.getOrderFormat());
        intent.putExtra("order_status",item.getOrderStatus());
        intent.putExtra("pre_order", item.getPreOrder());
        intent.putExtra("seller_names",item.getSellerNames());
        startActivity(intent);
    }
    private class UpdateLastKnownLocationTask extends AsyncTask<Void, Void, Boolean>
    {
        private String url_update_item = base_url+"update_delivery_last_location.php";
        private String TAG_SUCCESS="success";
        private String TAG_MESSAGE="message";
        private JSONParser jsonParser;
        private int success;
        UpdateLastKnownLocationTask()
        {
            jsonParser = new JSONParser();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            //building parameters
            List<NameValuePair> info=new ArrayList<NameValuePair>();
            info.add(new BasicNameValuePair("email", LoginActivity.getServerAccount().getEmail()));
            info.add(new BasicNameValuePair("location",myLocation));
            JSONObject jsonObject= jsonParser.makeHttpRequest(url_update_item,"POST",info);
            try
            {
                success=jsonObject.getInt(TAG_SUCCESS);
                if(success==1)
                {
                    return true;
                }
                else
                {
                    String message=jsonObject.getString(TAG_MESSAGE);
                    Log.e(TAG_MESSAGE,""+message);
                    return false;
                }
            }
            catch (JSONException e)
            {
                Log.e("JSON",""+e.getMessage());
                return false;
            }
        }
        @Override
        protected void onPostExecute(final Boolean successful)
        {
            if(successful)
            {


            }
            else
            {
                Log.e(TAG, "updating last known location");
            }

        }
    }

}