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
import com.nikitaend.polproject.dialogs.MoreDialog;
import com.nikitaend.polproject.time.TimeInterval;

import java.util.ArrayList;

/**
 * @author Endaltsev Nikita
 *         start at 31.05.15.
 */
public class TemperatureNewAdapter extends ArrayAdapter<TimeInterval> {

    public ArrayList<TimeInterval> mTemperatureHolderList;
    public DialogFragment dialogFragment;
    String title;
    Context mContext;

    public TemperatureNewAdapter(Context context, int resource,
                              ArrayList<TimeInterval> temperatureHolders, String title) {
        super(context, R.layout.card_schedule, temperatureHolders);

        this.mTemperatureHolderList = temperatureHolders;
        this.mContext = context;
        this.title = title;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_schedule, parent, false);

        TimeInterval data = getItem(position);
        ((TextView) convertView.findViewById(R.id.time_textView))
                .setText(data.getStartTime().toString() + " - " + data.getEndTime().toString());

        if (data.getStartTime().getHours() > 12) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ((ImageView) convertView.findViewById(R.id.dayNight_icon))
                        .setImageDrawable(mContext.getDrawable(R.drawable.night_icon));
            } else {
            }
        }

        ((TextView) convertView.findViewById(R.id.card_temperature_textView))
                .setText("Day Mode " + MainActivity.thermostat.getDayTemperatureValue());


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
