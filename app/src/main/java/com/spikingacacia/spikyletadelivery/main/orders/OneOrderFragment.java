package com.spikingacacia.spikyletadelivery.main.orders;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.spikingacacia.spikyletadelivery.JSONParser;
import com.spikingacacia.spikyletadelivery.LoginActivity;
import com.spikingacacia.spikyletadelivery.R;
import com.spikingacacia.spikyletadelivery.database.Orders;
import com.spikingacacia.spikyletadelivery.util.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static com.spikingacacia.spikyletadelivery.LoginActivity.base_url;


/**
 * A placeholder fragment containing a simple view.
 */
public class OneOrderFragment extends Fragment
{

    private static final String ARG_ORDER = "order";
    private static final String ARG_FORMAT = "order_format";
    private static final String ARG_ORDER_STATUS = "order_status";
    private static final String ARG_PRE_ORDER = "pre_order";
    private String mOrder;
    private int mOrderFormat;
    private int mOrderStatus;
    private int mPreOrder;
    private OnFragmentInteractionListener mListener;
    private String TAG = "one_order_f";
    private String sellerEmail;
    private int orderNumber;
    private String dateAdded;
    private int total;


    public static OneOrderFragment newInstance(String order, int format, int status, int pre_order)
    {
        OneOrderFragment fragment = new OneOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORDER, order);
        args.putInt(ARG_FORMAT, format);
        args.putInt(ARG_ORDER_STATUS, status);
        args.putInt(ARG_PRE_ORDER, pre_order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mOrder = getArguments().getString(ARG_ORDER);
            mOrderFormat = getArguments().getInt(ARG_FORMAT);
            mOrderStatus = getArguments().getInt(ARG_ORDER_STATUS);
            mPreOrder = getArguments().getInt(ARG_PRE_ORDER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_one_orders, container, false);
        TextView t_date=view.findViewById(R.id.date);
        TextView t_username=view.findViewById(R.id.username);
        TextView t_charges=view.findViewById(R.id.charges);
        TextView t_waiter=view.findViewById(R.id.waiter);
        TextView t_status = view.findViewById(R.id.status);

        Button b_restaurant_distance = view.findViewById(R.id.restaurant_distance);
        Button b_restaurant_to_buyer_distance = view.findViewById(R.id.restaurant_to_buyer_distance);
        TextView t_order_number = view.findViewById(R.id.order_number);
        TextView t_buyer_username = view.findViewById(R.id.buyer_username);
        Button b_lock = view.findViewById(R.id.lock);
        Button b_delivery = view.findViewById(R.id.delivery);
        Button b_call_buyer = view.findViewById(R.id.call_buyer);
        TextView t_instructions = view.findViewById(R.id.instructions);

        b_restaurant_distance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mListener!=null)
                    mListener.gotoMaps((String)v.getTag());
            }
        });
        b_restaurant_to_buyer_distance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mListener!=null)
                    mListener.gotoMaps((String)v.getTag());
            }
        });
        b_call_buyer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mListener!=null)
                    mListener.gotoPhone((String)v.getTag());
            }
        });

       //the order status are
        // -3 for new order, -2 = unpaid, -1 = paid, 0 = deleted, 1 = pending, 2 = ..... until 5 = finished
        String[] status_strings_1 = new String[]{"pending","in progress","delivery","payment","finished"};
        String[] status_strings_2 = new String[]{"pending","payment","in progress","delivery","finished"};

        t_status.setText(mOrderFormat==1?status_strings_1[mOrderStatus-1]:status_strings_2[mOrderStatus-1]);


        String username="";
        int table=0;
        int count=0;
        Double total_price=0.0;
        String date_to_show="";
        String waiter="";
        String collect_time="";
        int i_order_type=0;
        String url_code_start_delivery ="";
        String url_code_end_delivery = "";
        String r_distance = "";
        String b_distance = "";
        String order_number = "";
        String charges = "";
        String buyer_names = "";
        String mobile = "";
        String buyer_location = "";
        String instructions = "";
        String seller_location = "";
        String delivery_email = "";
        int delivery_booked = 0;
        String delivery_start_time = "";
        String delivery_end_time = "";
        Iterator iterator= OrdersFragment.ordersLinkedHashMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            LinkedHashMap.Entry<Integer, Orders>set=(LinkedHashMap.Entry<Integer, Orders>) iterator.next();
            Orders orders =set.getValue();
            int itemId= orders.getItemId();
            order_number= String.valueOf(orders.getOrderNumber());
            int orderStatus= orders.getOrderStatus();
            String orderName= orders.getItem();
            orderName=orderName.replace("_"," ");
            String size = orders.getSize();
            double price= orders.getPrice();
            String date_added= orders.getDateAdded();
            String date_added_local= orders.getDateAddedLocal();
            String[] date=date_added.split(" ");
            if(!(date[0]+":"+order_number).contentEquals(mOrder))
                continue;
            username= orders.getSellerNames();
            waiter= orders.getWaiterNames();
            table= orders.getTableNumber();
            collect_time = orders.getCollectTime();
            i_order_type = orders.getOrderType();
            Double distance_res = Double.valueOf(orders.getRestaurantDistance());
            Double distance_buyer = Double.valueOf(orders.getRestaurantToBuyerDistance());
            r_distance = distance_res<1000? String.format("%.0f m",distance_res) : String.format("%.0f km",distance_res/1000);
            b_distance = distance_buyer<1000? String.format("%.0f m",distance_buyer) : String.format("%.0f km",distance_buyer/1000);
            charges = orders.getDeliveryCharge();
            buyer_names = orders.getBuyerUsername();
            mobile = orders.getDeliveryMobile();
            instructions = orders.getDeliveryInstructions();
            buyer_location = orders.getDeliveryLocation();
            seller_location = orders.getSellerLocation();
            delivery_email = orders.getDeliveryEmail();
            delivery_booked = Integer.parseInt(orders.getDeliveryBooked());
            url_code_end_delivery = orders.getUrlCodeStartDelivery();
            url_code_end_delivery = orders.getUrlCodeEndDelivery();
            delivery_start_time = orders.getDeliveryStartTime();
            delivery_end_time = orders.getDeliveryEndTime();


            sellerEmail = orders.getSellerEmail();
            orderNumber = orders.getOrderNumber();
            dateAdded = orders.getDateAdded();
            //add the layouts
            //cardview
            View layout = inflater.inflate(R.layout.order_cardview_layout,null);
            TextView t_count = layout.findViewById(R.id.count);
            TextView t_item = layout.findViewById(R.id.item);
            TextView t_price = layout.findViewById(R.id.price);

            t_count.setText(String.valueOf(count+1));
            t_item.setText(orderName);
            t_price.setText(size+" @ "+String.valueOf(price));


            count+=1;
            total_price+=price;
            date_to_show=date_added_local;
        }
        //set date text
        t_date.setText(date_to_show);
        t_username.setText(username);
        t_waiter.setText(waiter);
        b_restaurant_distance.setText(r_distance);
        b_restaurant_to_buyer_distance.setText(b_distance);
        b_restaurant_to_buyer_distance.setTag(buyer_location);
        b_restaurant_distance.setTag(seller_location);
        t_instructions.setText(instructions);
        b_call_buyer.setTag(mobile);
        t_order_number.setText(order_number);
        t_charges.setText(charges);
        t_buyer_username.setText(buyer_names);
        if(delivery_booked == 1)
        {
            b_lock.setText("Unlock");
            b_delivery.setVisibility(View.VISIBLE);
        }
        if(!delivery_start_time.contentEquals("NULL") && !delivery_start_time.contentEquals("null") && !delivery_start_time.contentEquals("") && delivery_booked == 1 && mOrderStatus == 4)
        {
            b_lock.setVisibility(View.GONE);
            b_delivery.setText("End delivery");
        }
        if(mOrderStatus == 5)
        {
            b_lock.setVisibility(View.GONE);
            b_delivery.setVisibility(View.GONE);
        }
        final int finalDelivery_booked = delivery_booked;
        b_lock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new LockDeliveryTask(sellerEmail, String.valueOf(orderNumber),dateAdded, finalDelivery_booked == 1?"0":"1").execute((Void)null);
            }
        });
        final String finalUrl_code_start_delivery = url_code_start_delivery;
        final String finalUrl_code_end_delivery = url_code_end_delivery;
        b_delivery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mOrderStatus == 3)
                {
                    if(mListener!=null)
                        mListener.startDelivery(finalUrl_code_start_delivery, mOrderStatus);
                }
                else if(mOrderStatus == 4)
                {
                    if(mListener!=null)
                        mListener.endDelivery(finalUrl_code_end_delivery, mOrderStatus);
                }

            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener
    {
        void gotoMaps(String location);
        void gotoPhone(String number);
        void startDelivery(String barcode, int status);
        void endDelivery(String barode, int status);
    }
    private class LockDeliveryTask extends AsyncTask<Void, Void, Boolean>
    {
        private String url_update=base_url+"lock_delivery.php";
        private String TAG_SUCCESS="success";
        private String TAG_MESSAGE="message";
        private JSONParser jsonParser;
        final private String orderNumber;
        private final String dateAdded;
        private final String lock;
        private final String sellerEmail;

        public  LockDeliveryTask(String sellerEmail, String orderNumber, String dateAdded, String lock)
        {
            this.sellerEmail = sellerEmail;
            this.orderNumber = orderNumber;
            this.dateAdded = dateAdded;
            this.lock = lock;
            jsonParser = new JSONParser();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            //getting columns list
            List<NameValuePair> info=new ArrayList<NameValuePair>(); //info for staff count
            info.add(new BasicNameValuePair("seller_email", sellerEmail));
            info.add(new BasicNameValuePair("delivery_email", LoginActivity.getServerAccount().getEmail()));
            info.add(new BasicNameValuePair("order_number",orderNumber));
            info.add(new BasicNameValuePair("lock",lock));
            info.add(new BasicNameValuePair("date_added",dateAdded));

            // making HTTP request
            JSONObject jsonObject= jsonParser.makeHttpRequest(url_update,"POST",info);
            Log.d(TAG,info.toString());
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
                Log.d(TAG,"order update succesful");
                //since there can be multiple asyntasks running at the same time m_index may generate IndexOutOfBoundsException
                requireActivity().onBackPressed();
            }
            else
            {
                Log.e(TAG,"update order failed");
            }
        }
    }
    private class UpdateOrderTask extends AsyncTask<Void, Void, Boolean>
    {
        private String url_update=base_url+"update_seller_order.php";
        private String TAG_SUCCESS="success";
        private String TAG_MESSAGE="message";
        private JSONParser jsonParser;
        final private String orderNumber;
        private final String dateAdded;
        private final String orderStatus;
        private final String updateSellerTotal;
        private final String sellerEmail;

        public  UpdateOrderTask(String sellerEmail, String orderNumber, String dateAdded, String orderStatus, String updateSellerTotal)
        {
            this.sellerEmail = sellerEmail;
            this.orderNumber = orderNumber;
            this.dateAdded = dateAdded;
            this.orderStatus = orderStatus; // order status for unpaid order is -1, delete is 0 and for a succesful order is 1
            this.updateSellerTotal = updateSellerTotal;
            jsonParser = new JSONParser();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            //getting columns list
            List<NameValuePair> info=new ArrayList<NameValuePair>(); //info for staff count
            info.add(new BasicNameValuePair("seller_email", sellerEmail));
            info.add(new BasicNameValuePair("buyer_email", LoginActivity.getServerAccount().getEmail()));
            info.add(new BasicNameValuePair("waiter_email", "unavailable"));
            info.add(new BasicNameValuePair("order_number",orderNumber));
            info.add(new BasicNameValuePair("status",orderStatus));
            info.add(new BasicNameValuePair("update_seller_total",updateSellerTotal));
            info.add(new BasicNameValuePair("m_message",""));
            info.add(new BasicNameValuePair("date_added",dateAdded));

            // making HTTP request
            JSONObject jsonObject= jsonParser.makeHttpRequest(url_update,"POST",info);
            Log.d(TAG,jsonObject.toString());
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
                Log.d(TAG,"order update succesful");
                //since there can be multiple asyntasks running at the same time m_index may generate IndexOutOfBoundsException
                requireActivity().onBackPressed();
            }
            else
            {
                Log.e(TAG,"update order failed");
            }
        }
    }
}