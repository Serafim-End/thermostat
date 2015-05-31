package com.nikitaend.polproject.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nikitaend.polproject.R;
import com.nikitaend.polproject.activity.ScheduleActivity;
import com.nikitaend.polproject.adapter.holder.TemperatureHolder;

/**
 * 
 * i have made two same edit dialogs one for adding and editing and next
 * for schedule which called from more_dialog
 * these for  dialog_more_edit
 * @author Endaltsev Nikita
 *         start at 11.05.15.
 */
public class EditDialogListVIew  extends DialogFragment {
    
    int indexOfElement;
    String startTime;
    String endTime;
    String dayNight;
    String title;

    public static EditDialogListVIew newInstance(int index, String title) {
        EditDialogListVIew editDialogListVIew = new EditDialogListVIew();
        
        Bundle args = new Bundle();
        args.putInt("index", index);
        
        editDialogListVIew.setArguments(args);
        return editDialogListVIew;
    }
    
    public static EditDialogListVIew newInstance(int indexOfElement, String startTime, 
                                                 String endTime, String dayNight, String title) {
        
        EditDialogListVIew editDialogListVIew = new EditDialogListVIew();
        
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("index", indexOfElement);
        args.putString("startTime", startTime);
        args.putString("endTime", endTime);
        args.putString("dayNight", dayNight);
        
        editDialogListVIew.setArguments(args);
        return editDialogListVIew;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        
        if (args != null) {
            indexOfElement = args.getInt("index");
            title = args.getString("title");
            try {
                startTime = args.getString("startTime");
                endTime = args.getString("endTime");
                dayNight = args.getString("dayNight");
            } catch (Exception e) {}
        }
    }

    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setTitle("Edit interval");
        View v = inflater.inflate(R.layout.dialog_more_edit, null);

        final TextView endTimeTextView = (TextView) v.findViewById(R.id.end_time_textView);
        if (endTime != null) { endTimeTextView.setText(endTime);}
        
//        final Switch dayNightSwitch = (Switch) v.findViewById(R.id.dayNight_switch);
//        if (dayNight != null) {
//            if (dayNight == "PM") {
//                dayNightSwitch.setChecked(true);
//            } else {
//                dayNightSwitch.setChecked(true);
//            }
//        }


        final TextView startTimeTextView = (TextView) v.findViewById(R.id.start_time_textView);
        if (startTime != null) { startTimeTextView.setText(startTime); }
        startTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dayNight = dayNightSwitch.isChecked() ? "PM" : "AM";
                dayNight = "AM";
                startTime = startTimeTextView.getText().toString();
                endTime = endTimeTextView.getText().toString();
                DialogFragment newFragment =  
                        TimePickerFragment.newInstance(true, indexOfElement,
                                startTime, endTime, dayNight, title);
                newFragment.show(getFragmentManager(), "timePicker");
                dismiss();
            }
        });

        endTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dayNight = dayNightSwitch.isChecked() ? "PM" : "AM";
                dayNight = "AM";
                startTime = startTimeTextView.getText().toString();
                endTime = endTimeTextView.getText().toString();
                DialogFragment newFragment = 
                        TimePickerFragment.newInstance(false, indexOfElement,
                                startTime, endTime, dayNight, title);
                newFragment.show(getFragmentManager(), "timePicker");
                dismiss();
            }
        });
        
        
        Button okBtn = (Button) v.findViewById(R.id.more_edit_dismiss_button);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TemperatureHolder holder =
                        ScheduleActivity.temperatureHoldersHash.get(title).get(indexOfElement);
                
                holder.startTime = startTimeTextView.getText().toString();
                holder.endTime = endTimeTextView.getText().toString();
                
//                if (dayNightSwitch.isChecked()) {
//                    holder.dayNight = "PM";
//                } else { holder.dayNight = "AM"; }
                holder.dayNight = "AM";
                ScheduleActivity.adapterCard.notifyDataSetChanged();

                ScheduleActivity.temperatureHoldersHash.get(title).set(indexOfElement, holder);
                dismiss();
            }
        });

        Button backBtn = (Button) v.findViewById(R.id.cancel_edit_dismiss_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
