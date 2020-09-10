package com.spikingacacia.spikyletadelivery.database;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Preferences
{
    private SharedPreferences shared_preferences;
    private SharedPreferences.Editor preferences_editor;
    //order types
    //-2 = unpaid, -1 = paid, 0 = deleted, 1 = pending, 2 = payment, 3 = in progress, 4 = delivery,  5 = finished
    int unpaid_count;
    int paid_count;
    int deleted_count;
    int pending_count;
    int payment_count;
    int in_progress_count;
    int delivery_count;
    int finished_count;
    // order info to remember
    private String mpesa_mobile;
    private String delivery_mobile;
    private String order_instructions;
    public Preferences(Context context)
    {
        shared_preferences=context.getSharedPreferences("loginPrefs",MODE_PRIVATE);
        preferences_editor=shared_preferences.edit();

        in_progress_count = shared_preferences.getInt("in_progress_count",0);
        delivery_count = shared_preferences.getInt("delivery_count",0);
        finished_count = shared_preferences.getInt("finished_count",0);
    }


    public int getIn_progress_count()
    {
        return in_progress_count;
    }

    public void setIn_progress_count(int in_progress_count)
    {
        this.in_progress_count = in_progress_count;
        preferences_editor.putInt("in_progress_count", in_progress_count);
        preferences_editor.commit();
    }

    public int getDelivery_count()
    {
        return delivery_count;
    }

    public void setDelivery_count(int delivery_count)
    {
        this.delivery_count = delivery_count;
        preferences_editor.putInt("delivery_count", delivery_count);
        preferences_editor.commit();

    }

    public int getFinished_count()
    {
        return finished_count;
    }

    public void setFinished_count(int finished_count)
    {
        this.finished_count = finished_count;
        preferences_editor.putInt("finished_count", finished_count);
        preferences_editor.commit();
    }



}
