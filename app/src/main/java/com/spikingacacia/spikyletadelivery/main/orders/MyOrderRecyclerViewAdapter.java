package com.spikingacacia.spikyletadelivery.main.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spikingacacia.spikyletadelivery.LoginActivity;
import com.spikingacacia.spikyletadelivery.R;
import com.spikingacacia.spikyletadelivery.database.Orders;


import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.spikingacacia.spikyletadelivery.main.orders.OrdersFragment.*;

public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Orders> mValues;
    private List<Orders> itemsCopy;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MyOrderRecyclerViewAdapter(OnListFragmentInteractionListener listener, Context context)
    {
        mValues = new LinkedList<>();
        mListener = listener;
        itemsCopy=new ArrayList<>();
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_orders, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        /*View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orders, parent, false);*/
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof ViewHolder)
        {
            populateItemRows((ViewHolder) holder, position);
        }
        else if (holder instanceof LoadingViewHolder)
        {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount()
    {
        return mValues == null ? 0 : mValues.size();
    }
    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mValues.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    public void filter(String text)
    {
        mValues.clear();
        if(text.isEmpty())
            mValues.addAll(itemsCopy);
        else
        {
            text=text.toLowerCase();
            for(Orders orderItem:itemsCopy)
            {
                if(orderItem.getSellerNames().toLowerCase().contains(text))
                    mValues.add(orderItem);
            }
        }
        notifyDataSetChanged();
    }
    public void filter(int status)
    {
        mValues.clear();
        if(status==0)
            mValues.addAll(itemsCopy);
        else
        {
            for(Orders orderItem:itemsCopy)
            {
                if(orderItem.getOrderStatus()!=5)
                    mValues.add(orderItem);
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final ImageView image;
        public final TextView mRestaurantToBuyerDistance;
        public final TextView mRestaurantDistance;
        public final TextView mUsernameView;
        public final TextView mDateView;
        public final TextView mPrice;
        public Orders mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            image = view.findViewById(R.id.image);
            mRestaurantToBuyerDistance = (TextView) view.findViewById(R.id.customer_distance);
            mRestaurantDistance = (TextView) view.findViewById(R.id.restaurant_distance);
            mUsernameView = (TextView) view.findViewById(R.id.username);
            mDateView = (TextView) view.findViewById(R.id.date);
            mPrice = view.findViewById(R.id.price);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mUsernameView.getText() + "'";
        }
    }
    public  class LoadingViewHolder extends RecyclerView.ViewHolder
    {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView)
        {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

    }

    public void listUpdated(List<Orders> newitems)
    {
        mValues.clear();
        mValues.addAll(newitems);
        itemsCopy.addAll(newitems);
        notifyDataSetChanged();
    }
    public void listAddProgressBar()
    {
        mValues.add(null);
        notifyDataSetChanged();
    }
    public void listRemoveProgressBar()
    {
        mValues.remove(mValues.size()-1);
        notifyDataSetChanged();
    }
    public void listAddItems(List<Orders> newitems)
    {
        mValues.addAll(newitems);
        itemsCopy.addAll(newitems);
        notifyDataSetChanged();
    }
    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }
    private void populateItemRows(final ViewHolder holder, int position)
    {
        String image_url= LoginActivity.base_url+"src/sellers_pics/";
        holder.mItem = mValues.get(position);
        Double distance_res = Double.valueOf(mValues.get(position).getRestaurantDistance());
        Double distance_buyer = Double.valueOf(mValues.get(position).getRestaurantToBuyerDistance());
        String r_distance = distance_res<1000? String.format("%.0f m",distance_res) : String.format("%.0f km",distance_res/1000);
        String b_distance = distance_buyer<1000? String.format("%.0f m",distance_buyer) : String.format("%.0f km",distance_buyer/1000);
        holder.mRestaurantDistance.setText(r_distance);
        holder.mRestaurantToBuyerDistance.setText(b_distance);
        holder.mPrice.setText(holder.mItem.getDeliveryCharge());
        holder.mUsernameView.setText(mValues.get(position).getSellerNames());
        //format the date
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        PrettyTime p = new PrettyTime();
        try
        {
            holder.mDateView.setText(p.format(format.parse(mValues.get(position).getDateAddedLocal())));
        } catch (ParseException e)
        {
            // e.printStackTrace();
        }
        if(mValues.get(position).getOrderStatus()==5)
            holder.mPrice.setVisibility(View.GONE);

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onOrderClicked(holder.mItem);
                }
            }
        });
        String url=image_url+String.valueOf(mValues.get(position).getSellerId())+'_'+String.valueOf(mValues.get(position).getSellerImageType());
        Glide.with(context).load(url).into(holder.image);
    }
}
