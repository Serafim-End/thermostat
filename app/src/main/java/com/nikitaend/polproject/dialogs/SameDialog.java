package com.nikitaend.polproject.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.activity.ScheduleActivity;
import com.nikitaend.polproject.activity.ScheduleDaysActivity;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;

import java.util.ArrayList;

/**
 * @author Endaltsev Nikita
 *         start at 29.05.15.
 */
public class SameDialog extends DialogFragment {

    String title;

    public static SameDialog newInstance( String title) {
        SameDialog removeDialog = new SameDialog();

        Bundle args = new Bundle();
        args.putString("title", title);
        removeDialog.setArguments(args);
        return removeDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("The same schedule");
        View v = inflater.inflate(R.layout.dialog_same, null);
        
        ((Button) v.findViewById(R.id.same_no_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
        ((Button) v.findViewById(R.id.same_yes_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<TemperatureHolder> dayHolders = 
                        ScheduleActivity.temperatureHoldersHash.get(title);
                
                for (int i = 0; i < ScheduleActivity.temperatureHoldersHash.size(); i++) {
                    if (ScheduleActivity.temperatureHoldersHash.get(ScheduleDaysActivity.weekDays[i]) != dayHolders) {
                        for (TemperatureHolder holder : dayHolders) {
                            ScheduleActivity.temperatureHoldersHash
                                    .get(ScheduleDaysActivity.weekDays[i]).add(holder);
                        }
                    }
                }
                dismiss();
            }
        });

        return v;
    }


    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}