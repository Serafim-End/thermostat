package com.nikitaend.polproject.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.activity.MainActivity;
import com.nikitaend.polproject.activity.SettingsActiviy;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;
import com.nikitaend.polproject.dialogs.MoreDialog;

import java.util.ArrayList;

/**
 * every element of TemperatureHolder type  and card_schedule card screen
 * @author Endaltsev Nikita
 *         start at 11.05.15.
 */
public class TemperatureAdapter extends ArrayAdapter<TemperatureHolder> {
    
    public ArrayList<TemperatureHolder> mTemperatureHolderList;
    public DialogFragment dialogFragment;
    String title;
    Context mContext;
    private String degree =  (char) 0x00B0 + "C";

    public TemperatureAdapter(Context context, int resource,
                              ArrayList<TemperatureHolder> temperatureHolders, String title) {
        super(context, R.layout.card_schedule, temperatureHolders);
        
        this.mTemperatureHolderList = temperatureHolders;
        this.mContext = context;
        this.title = title;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_schedule, parent, false);
        
        TemperatureHolder data = getItem(position);
        ((TextView) convertView.findViewById(R.id.time_textView))
                .setText(data.startTime + " – " + data.endTime);
        
        if (data.dayNight == "PM") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((ImageView) convertView.findViewById(R.id.dayNight_icon))
                        .setImageDrawable(mContext.getDrawable(R.drawable.night_icon));
            } else { }
        }
        
        ((TextView) convertView.findViewById(R.id.card_temperature_textView))
                .setText("Day Mode " + MainActivity.thermostat.getDayTemperatureValue() + degree);
        
        
        convertView.findViewById(R.id.more_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((Activity) mContext).getFragmentManager();
                dialogFragment = MoreDialog.newInstance(position, title);
                dialogFragment.show(fragmentManager, "editDialogListView");
            }
        });
        
        
        return convertView;
        
    }


}
