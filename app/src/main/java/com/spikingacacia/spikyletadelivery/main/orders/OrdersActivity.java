package com.spikingacacia.spikyletadelivery.main.orders;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.snackbar.Snackbar;
import com.spikingacacia.spikyletadelivery.JSONParser;
import com.spikingacacia.spikyletadelivery.LoginActivity;
import com.spikingacacia.spikyletadelivery.R;
import com.spikingacacia.spikyletadelivery.barcode.BarcodeCaptureActivity;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.spikingacacia.spikyletadelivery.LoginActivity.base_url;


public class OrdersActivity extends AppCompatActivity
    implements  OneOrderFragment.OnFragmentInteractionListener
{
    private String TAG="SOOrdersA";
    ActivityResultLauncher<Intent> mGetBarcode = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    Intent intent = result.getData();
                    try
                    {
                        Barcode barcode = intent.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                        barcodeReceived(barcode);
                    }
                    catch (NullPointerException excpetion)
                    {
                        Log.e(TAG,"no barcode");
                        // TODO: remove this its only for testing
                        //onCorrectScan();
                    }

                }
            });
    private static int orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
       String unique_order_name = getIntent().getStringExtra("unique_order_name");
       int order_format = getIntent().getIntExtra("order_format",1);
       int order_status = getIntent().getIntExtra("order_status",-2);
       int pre_order = getIntent().getIntExtra("pre_order",0);
       String seller_names = getIntent().getStringExtra("seller_names");
       setTitle(seller_names);

        Fragment fragment= OneOrderFragment.newInstance(unique_order_name, order_format, order_status, pre_order);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.base,fragment,"order");
        transaction.commit();
    }


    @Override
    public void gotoMaps(String location)
    {
        try
        {
            String[] location_pieces = location.split(":");
            if(location_pieces.length==1)
                location_pieces = location.split(",");
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location_pieces[0] + "," + location_pieces[1]);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(mapIntent);
            }

        } catch (Exception e)
        {
            Toast.makeText(getBaseContext(), "Wrong location", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void gotoPhone(String number)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        startActivity(intent);
    }

    @Override
    public void startDelivery(String barcode, int status)
    {
        orderStatus = status;
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mGetBarcode.launch(intent);
    }

    @Override
    public void endDelivery(String barcode, int status)
    {
        orderStatus = status;
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mGetBarcode.launch(intent);
    }
    private void barcodeReceived(Barcode barcode)
    {
        String s_barcode = barcode.displayValue;
        new UpdateDeliveryTask(String.valueOf(orderStatus) ,s_barcode).execute((Void)null);
    }

    private class UpdateDeliveryTask extends AsyncTask<Void, Void, Boolean>
    {
        private String url_update=base_url+"update_delivery.php";
        private String TAG_SUCCESS="success";
        private String TAG_MESSAGE="message";
        private JSONParser jsonParser;
        private final String orderStatus;
        private final String barcode;

        public  UpdateDeliveryTask(String orderStatus, String barcode)
        {
            this.orderStatus = orderStatus;
            this.barcode = barcode;
            jsonParser = new JSONParser();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            //getting columns list
            List<NameValuePair> info=new ArrayList<NameValuePair>(); //info for staff count
            info.add(new BasicNameValuePair("order_status",orderStatus));
            info.add(new BasicNameValuePair("barcode", barcode));
            // making HTTP request
            JSONObject jsonObject= jsonParser.makeHttpRequest(url_update,"POST",info);
            try
            {
                int success=jsonObject.getInt(TAG_SUCCESS);
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
            if (successful )
            {
                Snackbar.make(getWindow().getDecorView().getRootView(),"Successful", Snackbar.LENGTH_LONG).show();
                onBackPressed();
            }
            else if(!successful)
            {
                Snackbar.make(getWindow().getDecorView().getRootView(),"Error.\nPlease try again.", Snackbar.LENGTH_LONG).show();
            }
        }
    }

}
